package br.com.ifsp.backend.model.catalog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "releases_labels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseLabel {

    @EmbeddedId
    private ReleaseLabelId id = new ReleaseLabelId();

    @ManyToOne
    @MapsId("releaseId")
    @JoinColumn(name = "release_id")
    @JsonBackReference
    private Release release;

    @ManyToOne
    @MapsId("labelId")
    @JoinColumn(name = "label_id")
    private Label label;

    @Column(name = "catalog_number", length = 100)
    private String catalogNumber;

    @Column(name = "entity_role", length = 50)
    private String role;
}
