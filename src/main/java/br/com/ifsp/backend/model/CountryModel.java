package br.com.ifsp.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class CountryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(value = 100, message = "O nome do país pode ter no máximo 100 caracteres")
    @NotBlank(message = "O nome do país é obrigatório.")
    @Column(nullable = false, unique = true, length = 100)
    String name;
}
