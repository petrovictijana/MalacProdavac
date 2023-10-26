package server.server.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.dtos.request.UserLoginRequest;
import server.server.dtos.request.UserRegistrationRequest;
import server.server.service.RegistrationService;

@RestController
@RequestMapping
@Api(tags = "User")
public class UserController {
    @Autowired
    RegistrationService userService;

    @GetMapping("/registration/step1")
    public ResponseEntity<?> checkNewUserData(
            @RequestBody UserRegistrationRequest userRegistrationRequest
    ){
        return userService.checkNewUserData(userRegistrationRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(
            @RequestBody UserRegistrationRequest userRegistrationRequest
    ){
        return userService.createNewUser(userRegistrationRequest);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody UserLoginRequest userLoginRequest
            ){
        return null;
    }

}
