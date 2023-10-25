package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.server.dtos.request.UserRegistrationRequest;
import server.server.service.UserService;

@RestController
public class UserContoller {
    @Autowired
    UserService userService;
    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(
            @RequestBody UserRegistrationRequest userRegistrationRequest
    ){
        var response = userService.createNewUser(userRegistrationRequest);

        return response != null ? ResponseEntity.ok("Korisnik uspesno kreiran") : ResponseEntity.badRequest().build();
    }
}
