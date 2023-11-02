package server.server.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String picture;
    private String role;
}
