package br.com.ifsp.backend.model.catalog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tracks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 20)
    private String position;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @ManyToOne
    @JoinColumn(name = "release_id")
    @JsonBackReference
    private Release release;
}
