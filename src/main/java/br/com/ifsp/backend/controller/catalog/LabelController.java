package br.com.ifsp.backend.controller.catalog;

import br.com.ifsp.backend.dto.request.create.CreateLabelRequestDTO;
import br.com.ifsp.backend.dto.request.patch.UpdateLabelRequestDTO;
import br.com.ifsp.backend.dto.response.create.CreateLabelResponseDTO;
import br.com.ifsp.backend.dto.response.view.LabelResponseDTO;
import br.com.ifsp.backend.model.catalog.Label;
import br.com.ifsp.backend.service.catalog.LabelService;
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
    public ResponseEntity<Page<LabelResponseDTO>> listAll(
            @RequestParam(required = false) String name,
            @ParameterObject Pageable pageable) {
        var page = labelService.findAll(name, pageable);
        Page<LabelResponseDTO> dtoPage = page.map(LabelResponseDTO::new);

        return ResponseEntity.ok(dtoPage);
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

    @Operation(description = "Atualizar dados de uma Gravadora")
    @PatchMapping("/{id}")
    public ResponseEntity<LabelResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateLabelRequestDTO data
    ) {
        Label updatedLabel = labelService.update(id, data);
        return ResponseEntity.ok(new LabelResponseDTO(updatedLabel));
    }

    @Operation(description = "Excluir uma Gravadora (Apenas se não tiver discos)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Gravadora excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Não é possível excluir (possui vínculos)"),
            @ApiResponse(responseCode = "404", description = "Gravadora não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Se quiser restringir a ADMIN, faça a verificação aqui

        labelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
