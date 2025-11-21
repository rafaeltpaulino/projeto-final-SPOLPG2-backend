package br.com.ifsp.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário é obrigatório.")
    @Size(min = 5, max = 25, message = "O nome de usuário deve ter entre 5 e 25 caracteres.")
    @Column(nullable = false, unique = true, length = 25)
    private String username;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email
    @Size(min = 5, max = 50, message = "O email deve ter entre 5 e 50 caracteres.")
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 5, max = 30, message = "A senha deve ter entre 5 e 30 caracteres")
    @Column(nullable = false)
    private String password;

    @Max(value = 50, message =  "O nome deve ter no máximo 50 caracteres.")
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Max(value = 50, message =  "O sobrenome deve ter no máximo 50 caracteres.")
    @Column(name = "last_name", length = 50)
    private String lastName;

    private LocalDate birthdate;

    @ManyToOne
    @JoinColumn(name = "county_id")
    private CountryModel country;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private String bio;
}
