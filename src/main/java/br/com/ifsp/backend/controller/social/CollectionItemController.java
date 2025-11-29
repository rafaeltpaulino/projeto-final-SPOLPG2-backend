package br.com.ifsp.backend.controller.social;

import br.com.ifsp.backend.config.JWTUserDTO;
import br.com.ifsp.backend.dto.request.patch.UpdateCollectionItemRequestDTO;
import br.com.ifsp.backend.dto.request.put.InsertIntoCollectionRequestDTO;
import br.com.ifsp.backend.dto.response.view.CollectionItemResponseDTO;
import br.com.ifsp.backend.dto.response.put.PutCollectionItemResponseDTO;
import br.com.ifsp.backend.model.social.CollectionItem;
import br.com.ifsp.backend.service.social.CollectionItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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

    @Operation(description = "Listar minha coleção paginada")
    @GetMapping
    public ResponseEntity<Page<CollectionItemResponseDTO>> getMyCollection(
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") int page, // Padrão página 0
            @RequestParam(defaultValue = "10") int size, // Padrão 10 itens
            Authentication authentication
    ) {
        JWTUserDTO user = (JWTUserDTO) authentication.getPrincipal(); // Ajuste se usar JWTUserDTO

        Page<CollectionItem> itemsPage = collectionItemService.getMyCollection(user.userID(), sort, page, size);

        // Converte a página de Entidades para página de DTOs
        Page<CollectionItemResponseDTO> dtoPage = itemsPage.map(CollectionItemResponseDTO::new);

        return ResponseEntity.ok(dtoPage);
    }

    @Operation(description = "Listar coleção de outro usuário (Pública)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<CollectionItemResponseDTO>> getUserCollection(
            @PathVariable Long userId,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CollectionItem> itemsPage = collectionItemService.getMyCollection(userId, sort, page, size);
        Page<CollectionItemResponseDTO> dtoPage = itemsPage.map(CollectionItemResponseDTO::new);

        return ResponseEntity.ok(dtoPage);
    }

    @Operation(description = "Remover um item da minha coleção")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "403", description = "O item não pertence a você")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long id,
            Authentication authentication
    ) {
        // 1. Pega o usuário logado
        JWTUserDTO user = (JWTUserDTO) authentication.getPrincipal();

        // 2. Chama o serviço passando o ID do Item e o ID do Usuário
        collectionItemService.deleteItem(id, user.userID());

        // 3. Retorna 204 No Content (Padrão REST para deleção bem sucedida)
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Atualizar detalhes de um item da coleção (ex: estado, notas)")
    @PatchMapping("/{id}")
    public ResponseEntity<CollectionItemResponseDTO> updateItem(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCollectionItemRequestDTO data,
            Authentication authentication
    ) {
        // 1. Pega usuário logado
        JWTUserDTO user = (JWTUserDTO) authentication.getPrincipal(); // Ou JWTUserDTO dependendo da config

        // 2. Chama o serviço
        CollectionItem updatedItem = collectionItemService.updateItem(id, user.userID(), data);

        // 3. Retorna o item atualizado
        return ResponseEntity.ok(new CollectionItemResponseDTO(updatedItem));
    }
}
