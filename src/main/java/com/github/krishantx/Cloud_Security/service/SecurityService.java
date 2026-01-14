package com.github.krishantx.Cloud_Security.service;

import java.util.Map;
import java.util.Optional;

import com.github.krishantx.Cloud_Security.model.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.krishantx.Cloud_Security.model.UserModel;
import com.github.krishantx.Cloud_Security.repo.UserRepo;

@Service
public class SecurityService {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);

    public ResponseEntity<?> signup(UserModel userModel) {
        try {
            // Try to save the user to database
            String password = bCryptPasswordEncoder.encode(userModel.getPassword());
            userModel.setPassword(password);
            userRepo.save(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
        } catch (DataIntegrityViolationException e) {
            // If we catch a unique exception, Return a conflict.
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already exists");
        }
    }


    public ResponseEntity<?> login(LoginModel loginModel) {
        // Check if user exists in the database
        Optional<UserModel> userModel = userRepo.findByUsername(loginModel.getUsername());

        if (userModel.isEmpty())  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username does not exist");

        // Check if the password entered is the same as that in the database
        if (!bCryptPasswordEncoder.matches(loginModel.getPassword(), userModel.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        String jwt = jwtUtil.generateKey(loginModel.getUsername());
        // If the user validates return the JWT
        return ResponseEntity.ok(Map.of("token", jwt));
    }
    
}
