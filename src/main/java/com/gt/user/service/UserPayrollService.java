package com.gt.user.service;

import com.gt.user.exception.DataLoadException;
import com.gt.user.exception.UserException;
import com.gt.user.model.User;
import com.gt.user.repository.UserRepository;
import com.gt.user.utility.CsvUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserPayrollService implements IUserPayrollService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsers(double min, double max, int offset, int limit, String sort) {
        List<User> allUsers = getAllUsers();
        int applyLimit = allUsers.size();
        if (limit > 0) {
            applyLimit = limit;
        }
        List<User> users = allUsers.stream()
                .filter(user -> user.getSalary() >= min && user.getSalary() <= max)
                .limit(applyLimit)
                .collect(Collectors.toList());

        Comparator<User> nameComparator = (u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName());
        Comparator<User> salaryComparator = Comparator.comparingDouble(User::getSalary);
        if ("NAME".equalsIgnoreCase(sort)) {
            users.sort(nameComparator);
        } else if("SALARY".equalsIgnoreCase(sort)) {
            users.sort(salaryComparator);
        }
        if (offset != 0 && users.size() >= offset) {
            User toAdd = users.get(offset - 1);
            users.remove(offset - 1);
            users.add(0, toAdd);
        }
        //users.stream().sorted(nameComparator.thenComparing(salaryComparator));
        return users;
    }

    @Override
    public void saveAllUsers(MultipartFile file) throws UserException {
        try {
            List<User> users = CsvUtility.csvToUsers(file.getInputStream());
            // validate csv file data
            List<User> userList = Optional.ofNullable(users)
                    .get()
                    .stream()
                    .filter(user -> user.getSalary() >= 0.0)
                    .map(user -> findByNameAndDelete(user))
                    .collect(Collectors.toList());
            if (null != userList && userList.size() > 0) {
                saveUsers(userList);
            }
        } catch (IOException | DataLoadException e) {
            throw new UserException("Failed to store CSV data: ", e);
        }
    }

    private User findByNameAndDelete(User user) {
        User byName = userRepository.findByName(user.getName());
        if (null != byName) {
            userRepository.delete(byName);
        }
        return user;
    }

    @Override
    public void saveUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
