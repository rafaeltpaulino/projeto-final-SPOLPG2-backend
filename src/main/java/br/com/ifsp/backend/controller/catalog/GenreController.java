package br.com.ifsp.backend.controller.catalog;

import br.com.ifsp.backend.dto.request.InsertGenreRequestDTO;
import br.com.ifsp.backend.dto.response.GenreResponseDTO;
import br.com.ifsp.backend.dto.response.InsertGenreResponseDTO;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.service.catalog.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @Operation(description = "Cria gêneros musicais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gênero musical criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Informações enviadas inválidas ou faltantes.")
    })
    @PostMapping
    public ResponseEntity<InsertGenreResponseDTO> create(@RequestBody @Valid InsertGenreRequestDTO data) {
        Genre genre = genreService.insertGenre(data);
        var response = new InsertGenreResponseDTO(genre.getId(), genre.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Lista todos os gêneros musicais")
    @ApiResponse(responseCode = "200", description = "Lista de gêneros musicais retornada")
    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> listAll() {
        var genres = genreService.listAll();
        List<GenreResponseDTO> responseDTOS = genres.stream()
                .map(GenreResponseDTO::new)
                .toList();

        return ResponseEntity.ok(responseDTOS);
    }

    @Operation(description = "Busca gênero musical por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gênero musical  encontrado."),
            @ApiResponse(responseCode = "404", description = "Gênero musical  não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> findById(@PathVariable @Valid Long id) {
        var genre = genreService.findById(id);
        var response = new GenreResponseDTO(genre);

        return ResponseEntity.ok(response);
    }
}
