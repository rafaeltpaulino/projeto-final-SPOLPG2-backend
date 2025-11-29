package br.com.ifsp.backend.model.catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("GROUP")
@Getter
@Setter
public class GroupArtist extends Artist {

    @Column(name = "start_date")
    private LocalDate formationDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> members = new ArrayList<>();
}