package com.gt.user.service;

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
        if (offset != 0) {

        }
        List<User> users = allUsers.stream()
                .filter(user -> user.getSalary() >= min && user.getSalary() <= max)
                .limit(applyLimit)
                .collect(Collectors.toList());
        if (!"NO_SORT".equals(sort) &&
                ("NAME".equalsIgnoreCase(sort) || "SALARY".equalsIgnoreCase(sort))) {
            users.sort(userComparator);
        }
        return users;
    }

    @Override
    public void saveAllUsers(MultipartFile file) {
        try {
            List<User> users = CsvUtility.csvToUsers(file.getInputStream());
            // validate csv file data
            List<User> userList = Optional.ofNullable(users)
                    .get()
                    .stream()
                    .filter(user -> user.getSalary() >= 0.0)
                    .collect(Collectors.toList());
            saveUsers(userList);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store CSV data: " + e.getMessage());
        }
    }

    @Override
    public void saveUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    Comparator<User> userComparator = (o1, o2) -> {
        // name1 < name2
        // when name1 != name2 && name1 < name2
        // or name1 = name2 && salary1 > salary2
        String name1 = o1.getName().toUpperCase();
        String name2 = o2.getName().toUpperCase();
        if (!name1.equals(name2)) {
            return name1.compareTo(name2);
        } else {
            return Double.compare(o1.getSalary(), o2.getSalary());
        }
    };
}
