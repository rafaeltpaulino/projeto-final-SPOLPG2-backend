package br.com.ifsp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tracks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String position; // A1, B2
    private Integer durationSeconds;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private Release release;
}
