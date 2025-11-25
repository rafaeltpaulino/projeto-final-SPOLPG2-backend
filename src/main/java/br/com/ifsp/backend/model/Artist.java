package br.com.ifsp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "artists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JoinColumn(name = "image_url")
    private String imageUrl;

    @JoinColumn(name = "start_date")
    @Column(nullable = false)
    private LocalDate startDate;

    @JoinColumn(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
