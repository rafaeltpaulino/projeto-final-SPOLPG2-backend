package br.com.ifsp.backend.model.catalog;

import br.com.ifsp.backend.model.Country;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "releases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private String format;

    private String barcode;

    @Column(name = "is_main")
    private boolean main; // Se é a versão principal da Master

    @ManyToOne
    @JoinColumn(name = "master_id", nullable = false)
    @JsonBackReference
    private Master master;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReleaseLabel> labels = new ArrayList<>();

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Track> tracks = new ArrayList<>();
}
