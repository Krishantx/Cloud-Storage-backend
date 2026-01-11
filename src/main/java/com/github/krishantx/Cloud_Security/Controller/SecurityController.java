package com.github.krishantx.Cloud_Security.Controller;
import com.github.krishantx.Cloud_Security.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.krishantx.Cloud_Security.service.SecurityService;

@RestController
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/login")
    public String login() {
        String username = "krishant";
        String password = "mfcj43ow";

        return securityService.login(username, password);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserModel userModel) {

        return securityService.signUp(userModel);

    }

}
