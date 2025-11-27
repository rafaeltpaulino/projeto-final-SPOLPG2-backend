//Model do review
package br.com.ifsp.backend.model;

import br.com.ifsp.backend.model.catalog.Master;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews", uniqueConstraints = {
@UniqueConstraint(columnNames = {"user_id", "master_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Min(0)
@Max(5)
@Column(nullable = false)
private Integer rating;

@Column(columnDefinition = "TEXT")
private String comment;

@CreationTimestamp
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "master_id", nullable = false)
private Master master;
}
