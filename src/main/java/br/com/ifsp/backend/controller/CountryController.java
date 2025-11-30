package br.com.ifsp.backend.controller;

import br.com.ifsp.backend.dto.response.view.CountryResponseDTO;
import br.com.ifsp.backend.dto.response.view.MasterResponseDTO;
import br.com.ifsp.backend.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @Operation(description = "Lista todas os países.")
    @ApiResponse(responseCode = "200", description = "Lista de países retornada")
    @GetMapping
    public ResponseEntity<Page<CountryResponseDTO>> listAll(
            @ParameterObject Pageable pageable
    ) {
        var page = countryService.findAll(pageable);
        Page<CountryResponseDTO> dtoPage = page.map(CountryResponseDTO::new);

        return ResponseEntity.ok(dtoPage);
    }
}
