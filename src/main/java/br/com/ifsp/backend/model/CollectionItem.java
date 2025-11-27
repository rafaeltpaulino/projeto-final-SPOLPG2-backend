//Model do collection item
package br.com.ifsp.backend.model;

import br.com.ifsp.backend.enums.ConditionEnum;
import br.com.ifsp.backend.model.catalog.Release; // Sua classe Release (Físico)
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "collection_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionItem {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "acquired_date")
private LocalDate acquiredDate;

@Enumerated(EnumType.STRING)
@Column(name = "media_condition")
private ConditionEnum mediaCondition;

@Enumerated(EnumType.STRING)
@Column(name = "sleeve_condition")
private ConditionEnum sleeveCondition;

@Column(name = "private_notes", columnDefinition = "TEXT")
private String privateNotes;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "release_id", nullable = false)
private Release release;
}
