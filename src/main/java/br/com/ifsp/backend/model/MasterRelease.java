package br.com.ifsp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "master_releases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "release_year")
    private Integer releaseYear;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "artists_masters",
            joinColumns = @JoinColumn(name = "master_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> artists = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "genres_masters",
            joinColumns = @JoinColumn(name = "master_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "masterRelease")
    private List<Release> releases = new ArrayList<>();
}
