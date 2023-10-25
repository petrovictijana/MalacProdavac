package server.server.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String picture;
    private int roleId;
}
