package br.com.ifsp.backend.controller;

import br.com.ifsp.backend.dto.request.InsertArtistRequestDTO;
import br.com.ifsp.backend.dto.response.ArtistResponseDTO;
import br.com.ifsp.backend.dto.response.InsertArtistResponseDTO;
import br.com.ifsp.backend.model.Artist;
import br.com.ifsp.backend.service.ArtistService;
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

    @Operation(description = "Insere artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artista inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Informações enviadas inválidas ou faltantes.")
    })
    @PostMapping("/insert")
    public ResponseEntity<InsertArtistResponseDTO> insert(@RequestBody @Valid InsertArtistRequestDTO data) {
        Artist artist = artistService.insertArtist(data);
        var response = new InsertArtistResponseDTO(artist.getId(), artist.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Lista todos os artistas.")
    @GetMapping
    public ResponseEntity<List<ArtistResponseDTO>> listAll() {
        List<Artist> artists = artistService.listAllArtists();
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

        return ResponseEntity.ok(new ArtistResponseDTO(artist));
    }
}
