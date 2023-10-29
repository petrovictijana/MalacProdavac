package server.server.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import server.server.validation.PasswordValidation;
import server.server.validation.UsernameValidation;

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
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @NotNull(message = "Morate uneti ime")
    @Size(min=2, max=20)
    private String name;

    @NotNull(message = "Morate uneti prezime")
    @Size(min=2, max=20)
    private String surname;

    @NotNull(message = "Morate uneti korisničko ime")
    @UsernameValidation
    private String username;

    @NotNull(message = "Morate uneti lozinku")
    @PasswordValidation
    private String password;

    @NotNull(message = "Morate uneti email")
    @Email(message = "Morate uneti validnu email adresu", regexp = ".+@.+\\..+")
    private String email;

    private String picture;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
