package com.gt.user.service;

import com.gt.user.model.User;
import com.gt.user.repository.UserRepository;
import com.gt.user.utility.CsvUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserPayrollService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveAllUsers(MultipartFile file) {
        try {
            List<User> users = CsvUtility.csvToUsers(file.getInputStream());
            saveUsers(users);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store CSV data: " + e.getMessage());
        }
    }

    public void saveUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
