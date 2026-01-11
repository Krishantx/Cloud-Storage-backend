package com.github.krishantx.Cloud_Security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.krishantx.Cloud_Security.model.UserModel;
import com.github.krishantx.Cloud_Security.repo.UserRepo;

@Service
public class SecurityService {
    
    @Autowired
    private UserRepo userRepo;

    public String signup(UserModel userModel) {
        // Check if username doesn't already exists
        // If it does, return error
        // If username is unique return us
    }

    public String login(String username, String password) {
        // Validate username and password.
        Optional<UserModel> user = userRepo.findByUsername(username);

        if (user.isPresent()) {
            System.out.println(user.get().getPassword());
        }



        // If no data found in database return error.
        // If validation successfull generate a JWT token.
        return new String("Yahoo");
    }
    
}
