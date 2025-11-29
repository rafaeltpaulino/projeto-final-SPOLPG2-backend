package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.GroupArtist;
import br.com.ifsp.backend.model.catalog.PersonArtist;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
// A MÁGICA: Oculta campos nulos do JSON final
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtistResponseDTO {

    // --- Campos Comuns ---
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String countryName;
    private String type; // "GROUP" ou "PERSON"

    // --- Campos de PESSOA (ficarão null se for banda) ---
    private LocalDate birthDate;
    private LocalDate deathDate;

    // --- Campos de BANDA (ficarão null se for pessoa) ---
    private LocalDate formationDate;
    private LocalDate endDate;
    private List<MemberSummaryDTO> members;
    private List<BandSummaryDTO> memberOf;// Lista simplificada de membros

    // Construtor Inteligente
    public ArtistResponseDTO(Artist artist) {
        // 1. Mapeia o Básico
        this.id = artist.getId();
        this.name = artist.getName();
        this.description = artist.getDescription();
        this.imageUrl = artist.getImageUrl();
        this.countryName = artist.getCountry() != null ? artist.getCountry().getName() : null;

        // 2. Mapeia Específicos usando Polimorfismo
        if (artist instanceof GroupArtist group) {
            this.type = "GROUP";
            this.formationDate = group.getFormationDate();
            this.endDate = group.getEndDate();

            // Converte a lista de GroupMember para DTOs simples
            this.members = group.getMembers().stream()
                    .map(gm -> new MemberSummaryDTO(
                            gm.getMember().getId(),
                            gm.getMember().getName(),
                            gm.getRole(),
                            gm.getJoinDate(),
                            gm.isActive()
                    ))
                    .toList();
        }
        else if (artist instanceof PersonArtist person) {
            this.type = "PERSON";
            this.birthDate = person.getBirthDate();
            this.deathDate = person.getDeathDate();

            // --- NOVA LÓGICA: Mapeia as bandas que a pessoa participa ---
            // Verifica se a lista não é nula para evitar erros
            if (person.getMemberOf() != null) {
                this.memberOf = person.getMemberOf().stream()
                        .map(gm -> new BandSummaryDTO(
                                gm.getGroup().getId(),
                                gm.getGroup().getName(),
                                gm.getRole(),
                                gm.getJoinDate(),
                                gm.getLeaveDate(), // Importante saber se já saiu
                                gm.isActive(),
                                gm.getGroup().getImageUrl() // Legal mostrar a foto da banda
                        ))
                        .toList();
            }
        }
    }

    public record BandSummaryDTO(
            Long id,
            String name,
            String role,
            LocalDate joinDate,
            LocalDate leaveDate,
            boolean active,
            String imageUrl
    ) {}

    // DTO Interno (Record) para resumir o membro na lista
    public record MemberSummaryDTO(
            Long id,
            String name,
            String role,
            LocalDate joinDate,
            boolean active
    ) {}


}