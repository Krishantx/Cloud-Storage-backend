package com.github.krishantx.Cloud_Security.Controller;
import com.github.krishantx.Cloud_Security.model.LoginModel;
import com.github.krishantx.Cloud_Security.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.krishantx.Cloud_Security.service.SecurityService;

@RestController
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginModel loginModel) {
        return securityService.login(loginModel);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserModel userModel) {
        return securityService.signup(userModel);

    }

    @GetMapping("/secure-endpoint")
    public String secure() {
        return "Auth";
    }

}
