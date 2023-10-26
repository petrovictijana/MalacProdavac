package server.server.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.server.jwt.JwtGenerator;
import server.server.models.User;
import server.server.repository.UserRepository;
import server.server.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtGenerator jwtGenerator;
    @Override
    public ResponseEntity<?> loginUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if(user == null)
            return new ResponseEntity<>("No such a user", HttpStatus.BAD_REQUEST);

        //Generisati jwt token
        return new ResponseEntity<>(jwtGenerator.generateJwtToken(user), HttpStatus.OK);
    }
}
