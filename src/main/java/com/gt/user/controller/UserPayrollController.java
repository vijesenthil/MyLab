package com.gt.user.controller;

import com.gt.user.model.User;
import com.gt.user.response.ResponseMessage;
import com.gt.user.response.ResponseResult;
import com.gt.user.service.UserPayrollService;
import com.gt.user.utility.CsvUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserPayrollController {
    @Autowired
    UserPayrollService userPayrollService;

    @GetMapping("/users")
    public ResponseEntity<ResponseResult> getAllUsers(@RequestParam(name = "min") Optional<Double> minParam,
                                                      @RequestParam(name = "max") Optional<Double> maxParam,
                                                      @RequestParam(name = "offset") Optional<Integer> offsetParam,
                                                      @RequestParam(name = "limit") Optional<Integer> limitParam,
                                                      @RequestParam(name = "sort") Optional<String> sortParam) {
        try {
            double min = minParam.isPresent() ? minParam.get() : 0.0;
            double max = maxParam.isPresent() ? maxParam.get() : 4000.0;
            int offset = offsetParam.isPresent() ? offsetParam.get() : 0;
            int limit = limitParam.isPresent() ? limitParam.get() : 0;
            String sort = sortParam.isPresent() ? sortParam.get() : "NO_SORT";

            List<User> users = userPayrollService.getAllUsers(min, max, offset, limit, sort);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new ResponseResult(users), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public List<ResponseEntity<ResponseMessage>> uploadFile(@RequestParam("file") MultipartFile[] file) {
        return Arrays.stream(file)
                        .parallel()
                        .map(f -> uploadFile(f))
                        .collect(Collectors.toList());
    }

    private ResponseEntity<ResponseMessage> uploadFile(MultipartFile file) {
        int status = 0;
        if (CsvUtility.isCsvFormat(file)) {
            try {
                userPayrollService.saveAllUsers(file);
                status = 1;
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(status));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseMessage(status, e.getMessage()));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage(status, "Please upload a CSV file! Invalid file given: " + file.getOriginalFilename()));
    }
}