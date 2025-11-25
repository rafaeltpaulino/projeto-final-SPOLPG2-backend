package br.com.ifsp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "releases_labels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseLabel {

    @EmbeddedId
    private ReleaseLabelId id = new ReleaseLabelId();

    @ManyToOne
    @MapsId("releaseId")
    @JoinColumn(name = "release_id")
    private Release release;

    @ManyToOne
    @MapsId("labelId")
    @JoinColumn(name = "label_id")
    private Label label;

    @Column(name = "catalog_number")
    private String catalogNumber;

    @Column(name = "entity_role")
    private String role;
}
