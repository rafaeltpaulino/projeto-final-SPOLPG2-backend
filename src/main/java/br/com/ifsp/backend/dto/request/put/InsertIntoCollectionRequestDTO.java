package br.com.ifsp.backend.dto.request.put;

import br.com.ifsp.backend.model.social.ConditionEnum;

import java.time.LocalDate;

public record InsertIntoCollectionRequestDTO(
        Long releaseId,
        LocalDate acquiredDate,
        ConditionEnum mediaCondition,
        ConditionEnum sleeveCondition,
        String privateNotes
) {
}
