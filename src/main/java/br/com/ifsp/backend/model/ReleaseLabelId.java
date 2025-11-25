package br.com.ifsp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseLabelId implements Serializable {

    @Column(name = "release_id")
    private Long releaseId;

    @Column(name = "label_id")
    private Long labelId;
}
