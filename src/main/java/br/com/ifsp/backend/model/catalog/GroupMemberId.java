package br.com.ifsp.backend.model.catalog;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class GroupMemberId implements Serializable {
    private Long groupId;
    private Long memberId;
}
