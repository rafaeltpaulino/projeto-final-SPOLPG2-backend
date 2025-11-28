package br.com.ifsp.backend.controller.catalog;

import br.com.ifsp.backend.dto.request.create.CreateMasterRequestDTO;
import br.com.ifsp.backend.dto.response.create.CreateMasterResponseDTO;
import br.com.ifsp.backend.dto.response.view.MasterResponseDTO;
import br.com.ifsp.backend.service.catalog.MasterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<MasterResponseDTO>> listAll() {
        var masters = masterService.listAll();
        var responseDTOS = masters.stream()
                .map(MasterResponseDTO::new)
                .toList();

        return ResponseEntity.ok(responseDTOS);
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
}
