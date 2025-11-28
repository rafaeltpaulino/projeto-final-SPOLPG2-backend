package br.com.ifsp.backend.controller.catalog;

import br.com.ifsp.backend.dto.request.create.CreateArtistRequestDTO;
import br.com.ifsp.backend.dto.request.patch.PatchArtistRequestDTO;
import br.com.ifsp.backend.dto.response.view.ArtistResponseDTO;
import br.com.ifsp.backend.dto.response.create.CreateArtistResponseDTO;
import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.service.catalog.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Operation(description = "Cria artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artista criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Informações enviadas inválidas ou faltantes.")
    })
    @PostMapping
    public ResponseEntity<CreateArtistResponseDTO> create(@RequestBody @Valid CreateArtistRequestDTO data) {
        Artist artist = artistService.insertArtist(data);
        var response = new CreateArtistResponseDTO(artist.getId(), artist.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Lista todos os artistas.")
    @ApiResponse(responseCode = "200", description = "Lista de artistas retornada")
    @GetMapping
    public ResponseEntity<List<ArtistResponseDTO>> listAll() {
        var artists = artistService.listAll();
        List<ArtistResponseDTO> responseDTOS = artists.stream()
                .map(ArtistResponseDTO::new)
                .toList();

        return ResponseEntity.ok(responseDTOS);
    }

    @Operation(description = "Busca artista por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artista encontrado."),
            @ApiResponse(responseCode = "404", description = "Artista não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> findById(@PathVariable Long id) {
        Artist artist = artistService.findById(id);
        var response = new ArtistResponseDTO(artist);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Atualizar dados de um artista")
    @PatchMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> update(
            @PathVariable
            Long id,
            @RequestBody @Valid
            PatchArtistRequestDTO data
    ) {
        Artist updatedArtist = artistService.update(id, data);
        return ResponseEntity.ok(new ArtistResponseDTO(updatedArtist));
    }

    @Operation(description = "Excluir um artista (apenas se não tiver obras)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Artista excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Não é possível excluir (possui vínculos)"),
            @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
