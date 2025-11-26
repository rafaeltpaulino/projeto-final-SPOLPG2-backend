package br.com.ifsp.backend.model.catalog;

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

    private String title;

    private String position;

    private Integer durationSeconds;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private Release release;
}
