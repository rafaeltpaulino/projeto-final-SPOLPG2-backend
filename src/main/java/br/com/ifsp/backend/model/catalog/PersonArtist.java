package br.com.ifsp.backend.model.catalog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PERSON")
@Getter
@Setter
public class PersonArtist extends Artist {

    @Column(name = "start_date")
    private LocalDate birthDate;

    @Column(name = "end_date")
    private LocalDate deathDate;

    @OneToMany(mappedBy = "member")
    private List<GroupMember> memberOf = new ArrayList<>();
}