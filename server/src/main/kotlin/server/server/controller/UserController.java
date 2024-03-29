package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.dtos.request.UserLoginRequest;
import server.server.dtos.request.UserRegistrationRequest;
import server.server.service.LoginService;
import server.server.service.RegistrationService;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    LoginService loginService;

    @PostMapping ("/registration/step1")
    public ResponseEntity<?> checkNewUserData(
            @RequestBody UserRegistrationRequest userRegistrationRequest
    ){
        return registrationService.checkNewUserData(userRegistrationRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(
            @RequestBody UserRegistrationRequest userRegistrationRequest
    ){
        return registrationService.createNewUser(userRegistrationRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody UserLoginRequest userLoginRequest
            ){
        return loginService.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());
    }

}
