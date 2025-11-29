package br.com.ifsp.backend.controller.catalog;

import br.com.ifsp.backend.dto.request.create.CreateReleaseRequestDTO;
import br.com.ifsp.backend.dto.response.create.CreateReleaseResponseDTO;
import br.com.ifsp.backend.dto.response.view.MasterResponseDTO;
import br.com.ifsp.backend.dto.response.view.ReleaseResponseDTO;
import br.com.ifsp.backend.service.catalog.ReleaseService;
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
@RequestMapping("/releases")
public class ReleaseController {

    private final ReleaseService releaseService;

    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    @Operation(description = "Cria um Release, que é uma edição específica de uma Master(um álbum abstrato)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Release criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Informações enviadas inválidas ou faltantes.")
    })
    @PostMapping
    public ResponseEntity<CreateReleaseResponseDTO> create(@RequestBody @Valid CreateReleaseRequestDTO data) {
        var release = releaseService.createRelease(data);
        var response = new CreateReleaseResponseDTO(release.getId(), release.getTitle());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Lista todos os releases.")
    @ApiResponse(responseCode = "200", description = "Lista de releases retornada")
    @GetMapping
    public ResponseEntity<Page<ReleaseResponseDTO>> listAll(
            @RequestParam(required = false) String title,
            @ParameterObject Pageable pageable
    ) {
        var page = releaseService.findAll(title, pageable);
        Page<ReleaseResponseDTO> dtoPage = page.map(ReleaseResponseDTO::new);

        return ResponseEntity.ok(dtoPage);
    }

    @Operation(description = "Busca releases por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Release encontrado."),
            @ApiResponse(responseCode = "404", description = "Release não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReleaseResponseDTO> findById(@PathVariable @Valid Long id) {
        var release = releaseService.findById(id);
        var response = new ReleaseResponseDTO(release);

        return ResponseEntity.ok(response);
    }
}
