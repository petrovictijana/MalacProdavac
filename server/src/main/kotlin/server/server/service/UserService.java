package server.server.service;

import org.springframework.http.ResponseEntity;
import server.server.dtos.request.UserRegistrationRequest;
import server.server.dtos.response.EmailUsernameAvailabilityResponse;
import server.server.models.User;

public interface UserService {
    ResponseEntity<EmailUsernameAvailabilityResponse> checkNewUserData(UserRegistrationRequest userRegistrationRequest);
    ResponseEntity<?> createNewUser(UserRegistrationRequest userRegistrationRequest);
}
