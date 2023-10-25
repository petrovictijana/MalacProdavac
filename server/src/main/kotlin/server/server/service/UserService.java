package server.server.service;

import server.server.dtos.request.UserRegistrationRequest;
import server.server.models.User;

public interface UserService {
    public User createNewUser(UserRegistrationRequest userRegistrationRequest);
}
