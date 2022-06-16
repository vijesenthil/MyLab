package com.gt.user.service;

import com.gt.user.exception.UserException;
import com.gt.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserPayrollService {
    List<User> getAllUsers();
    List<User> getAllUsers(double min, double max, int offset, int limit, String sort);
    void saveAllUsers(MultipartFile file) throws UserException;
    void saveUsers(List<User> users);
    void saveUser(User user);
}
