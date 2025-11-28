package br.com.ifsp.backend.controller.social;

import br.com.ifsp.backend.config.JWTUserDTO;
import br.com.ifsp.backend.dto.request.put.InsertIntoCollectionRequestDTO;
import br.com.ifsp.backend.dto.response.view.CollectionItemResponseDTO;
import br.com.ifsp.backend.dto.response.put.PutCollectionItemResponseDTO;
import br.com.ifsp.backend.model.social.CollectionItem;
import br.com.ifsp.backend.service.social.CollectionItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collection")
public class CollectionItemController {

    private final CollectionItemService collectionItemService;

    public CollectionItemController(CollectionItemService collectionItemService) {
        this.collectionItemService = collectionItemService;
    }

    @PutMapping
    public ResponseEntity<PutCollectionItemResponseDTO> put(@RequestBody @Valid InsertIntoCollectionRequestDTO data, Authentication authentication) {
        JWTUserDTO user = (JWTUserDTO) authentication.getPrincipal();
        var item = collectionItemService.insertIntoCollection(data, user.userID());
        var response = new PutCollectionItemResponseDTO(item);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Lista uma coleção com ordenação (Opções: SortByTitle, SortByYear, SortByAcquiredDate)")
    @GetMapping
    public ResponseEntity<List<CollectionItemResponseDTO>> getMyCollection(
            @RequestParam(required = false, defaultValue = "SortByAcquiredDate")
            String sort,
            Authentication authentication
    ) {
        JWTUserDTO user = (JWTUserDTO) authentication.getPrincipal();

        List<CollectionItem> items = collectionItemService.getCollection(user.userID(), sort);

        List<CollectionItemResponseDTO> dtos = items.stream()
                .map(CollectionItemResponseDTO::new)
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
