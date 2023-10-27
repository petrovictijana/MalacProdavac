package server.server.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private String picture;

    private long role_id;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", role_id=" + role_id +
                '}';
    }
}
