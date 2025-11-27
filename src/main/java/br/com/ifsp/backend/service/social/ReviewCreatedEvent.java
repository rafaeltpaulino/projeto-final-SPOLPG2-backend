package br.com.ifsp.backend.service.social;

import lombok.Getter;
import lombok.Setter;

public record ReviewCreatedEvent(
         Long masterId
) {
}
