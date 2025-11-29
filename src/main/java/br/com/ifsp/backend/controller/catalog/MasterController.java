package br.com.ifsp.backend.controller.catalog;

import br.com.ifsp.backend.dto.request.create.CreateMasterRequestDTO;
import br.com.ifsp.backend.dto.request.patch.UpdateMasterRequestDTO;
import br.com.ifsp.backend.dto.response.create.CreateMasterResponseDTO;
import br.com.ifsp.backend.dto.response.view.MasterResponseDTO;
import br.com.ifsp.backend.model.catalog.Master;
import br.com.ifsp.backend.service.catalog.MasterService;
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
@RequestMapping("/masters")
public class MasterController {

    private final MasterService masterService;

    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    @Operation(description = "Cria masters. Uma master é um álbum em abstrato, ou seja, nenhuma edição específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Master criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Informações enviadas inválidas ou faltantes.")
    })
    @PostMapping
    public ResponseEntity<CreateMasterResponseDTO> create(@RequestBody @Valid CreateMasterRequestDTO data) {
        var master = masterService.createMaster(data);
        var response = new CreateMasterResponseDTO(master.getId(), master.getTitle());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Lista todas as masters.")
    @ApiResponse(responseCode = "200", description = "Lista de masters retornada")
    @GetMapping
    public ResponseEntity<Page<MasterResponseDTO>> listAll(
            @RequestParam(required = false) String title,
            @ParameterObject Pageable pageable
    ) {
        var page = masterService.findAll(title, pageable);
        Page<MasterResponseDTO> dtoPage = page.map(MasterResponseDTO::new);

        return ResponseEntity.ok(dtoPage);
    }

    @Operation(description = "Busca masters por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Master encontrado."),
            @ApiResponse(responseCode = "404", description = "Master não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MasterResponseDTO> findById(@PathVariable Long id) {
        var master = masterService.findById(id);
        var response = new MasterResponseDTO(master);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Atualizar uma Obra (Master Release)")
    @PatchMapping("/{id}")
    public ResponseEntity<MasterResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMasterRequestDTO data
    ) {
        Master updatedMaster = masterService.update(id, data);
        return ResponseEntity.ok(new MasterResponseDTO(updatedMaster));
    }

    @Operation(description = "Excluir uma Obra (Apenas se não tiver discos ou reviews)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Obra excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Não é possível excluir (possui vínculos)"),
            @ApiResponse(responseCode = "404", description = "Obra não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Se quiser restringir apenas para ADMIN:
        // if (!user.isAdmin()) return Forbidden...

        masterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
