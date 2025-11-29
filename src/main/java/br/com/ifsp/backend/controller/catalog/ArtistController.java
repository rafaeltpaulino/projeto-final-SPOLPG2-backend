package br.com.ifsp.backend.controller.catalog;

import br.com.ifsp.backend.dto.request.create.CreateGroupRequestDTO;
import br.com.ifsp.backend.dto.request.create.CreatePersonRequestDTO;
import br.com.ifsp.backend.dto.request.patch.PatchArtistRequestDTO;
import br.com.ifsp.backend.dto.response.view.ArtistResponseDTO;
import br.com.ifsp.backend.dto.response.create.CreateArtistResponseDTO;
import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.service.catalog.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/person")
    public ResponseEntity<ArtistResponseDTO> createPerson(@RequestBody @Valid CreatePersonRequestDTO data) {
        Artist artist = artistService.createPerson(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ArtistResponseDTO(artist));
    }

    @PostMapping("/group") // POST /artists/group
    public ResponseEntity<ArtistResponseDTO> createGroup(@RequestBody @Valid CreateGroupRequestDTO data) {
        Artist artist = artistService.createGroup(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ArtistResponseDTO(artist));
    }

    @Operation(description = "Listar artistas paginados (com filtro opcional por nome)")
    @GetMapping
    public ResponseEntity<Page<ArtistResponseDTO>> listAll(
            @RequestParam(required = false) String name, // ?name=Pink
            @ParameterObject Pageable pageable // ?page=0&size=10&sort=name,asc
    ) {
        Page<Artist> page = artistService.findAll(pageable, name);

        // Converte a Página de Entidades para Página de DTOs
        Page<ArtistResponseDTO> dtoPage = page.map(ArtistResponseDTO::new);

        return ResponseEntity.ok(dtoPage);
    }

    @Operation(description = "Buscar artista por ID (Detalhes completos)")
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> findById(@PathVariable Long id) {
        Artist artist = artistService.findById(id);
        return ResponseEntity.ok(new ArtistResponseDTO(artist));
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
