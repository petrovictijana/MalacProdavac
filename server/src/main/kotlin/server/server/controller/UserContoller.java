package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.dtos.request.UserRegistrationRequest;
import server.server.service.UserService;

@RestController
@RequestMapping
public class UserContoller {
    @Autowired
    UserService userService;

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

//        return response != null ? ResponseEntity.ok("Korisnik uspesno kreiran") : ResponseEntity.badRequest().build();
    }
}
