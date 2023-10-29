package server.server.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import server.server.dtos.response.LoginFailedResponse;
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
        User user = userRepository.findByUsername(username);
        if(user == null)
            return new ResponseEntity<>(new LoginFailedResponse(true, false), HttpStatus.BAD_REQUEST);

        if(!BCrypt.checkpw(password, user.getPassword()))
            return new ResponseEntity<>(new LoginFailedResponse(false, true), HttpStatus.BAD_REQUEST);

        //Generisati jwt token
        return new ResponseEntity<>(jwtGenerator.generateJwtToken(user), HttpStatus.OK);
    }
}
