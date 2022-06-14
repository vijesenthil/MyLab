package com.gt.user;

import com.gt.user.model.User;
import com.gt.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserPayrollSystem /*implements CommandLineRunner*/ {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPayrollSystem.class);

    @Autowired
    private UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(UserPayrollSystem.class, args);
    }

   /* @Override
    public void run(String... args) throws Exception {
        LOGGER.info("StartApplication...");

        repository.save(new User("Java", 100.0));
        repository.save(new User("Node", 200.0));
        repository.save(new User("Python", 300.0));

        System.out.println("\nfindAll()");
        repository.findAll().forEach(x -> System.out.println(x));

        System.out.println("\nfindById(1L)");
        repository.findById(1l).ifPresent(x -> System.out.println(x));

    }*/
}
