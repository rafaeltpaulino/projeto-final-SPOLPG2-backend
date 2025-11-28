package br.com.ifsp.backend.controller.catalog;

import br.com.ifsp.backend.dto.request.create.CreateLabelRequestDTO;
import br.com.ifsp.backend.dto.response.create.CreateLabelResponseDTO;
import br.com.ifsp.backend.dto.response.view.LabelResponseDTO;
import br.com.ifsp.backend.service.catalog.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @Operation(description = "Cria gravadoras.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gravadora criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Informações enviadas inválidas ou faltantes.")
    })
    @PostMapping
    public ResponseEntity<CreateLabelResponseDTO> create(CreateLabelRequestDTO data) {
        var label = labelService.createLabel(data);
        var response = new CreateLabelResponseDTO(label.getId(), label.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Lista todas as gravadoras.")
    @ApiResponse(responseCode = "200", description = "Lista de gravadoras retornada")
    @GetMapping
    public ResponseEntity<List<LabelResponseDTO>> listAll() {
        var labels = labelService.listAll();
        var responseDTOS = labels.stream()
                .map(LabelResponseDTO::new)
                .toList();

        return ResponseEntity.ok(responseDTOS);
    }

    @Operation(description = "Busca gavadora por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gravadora encontrado."),
            @ApiResponse(responseCode = "404", description = "Gravadora não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LabelResponseDTO> findById(@PathVariable @Valid Long id) {
        var label = labelService.findById(id);
        var response = new LabelResponseDTO(label);

        return ResponseEntity.ok(response);
    }
}
