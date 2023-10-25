package server.server.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.dtos.request.UserRegistrationRequest;
import server.server.models.User;
import server.server.repository.UserRepository;
import server.server.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public User createNewUser(UserRegistrationRequest userRegistrationRequest) {
        User newUser = User.builder()
                .name(userRegistrationRequest.getName())
                .surname(userRegistrationRequest.getSurname())
                .username(userRegistrationRequest.getUsername())
                .password(userRegistrationRequest.getPassword())
                .email(userRegistrationRequest.getEmail())
                .picture(userRegistrationRequest.getPicture())
                .roleId(userRegistrationRequest.getRoleId())
                .build();
        return userRepository.save(newUser);
    }
}
