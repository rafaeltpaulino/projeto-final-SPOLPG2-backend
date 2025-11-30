package br.com.ifsp.backend.model.catalog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "group_members")
@Getter
@Setter
public class GroupMember {

    @EmbeddedId
    private GroupMemberId id = new GroupMemberId();

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private GroupArtist group;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private PersonArtist member;

    @Column(length = 100)
    private String role;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "leave_date")
    private LocalDate leaveDate;

    @Column(name = "is_active")
    private boolean active;
}
