package br.com.ifsp.backend.controller.social;

import br.com.ifsp.backend.config.JWTUserDTO;
import br.com.ifsp.backend.dto.request.create.CreateReviewRequestDTO;
import br.com.ifsp.backend.dto.request.patch.UpdateReviewRequestDTO;
import br.com.ifsp.backend.dto.response.view.ReviewResponseDTO;
import br.com.ifsp.backend.model.social.Review;
import br.com.ifsp.backend.model.user.User;
import br.com.ifsp.backend.service.social.ReviewService;
import br.com.ifsp.backend.service.user.UserService;
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
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @Operation(description = "Criar uma avaliação para um álbum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário já avaliou este álbum"),
            @ApiResponse(responseCode = "404", description = "Álbum (Master) não encontrado")
    })
    @PostMapping
    public ResponseEntity<ReviewResponseDTO> create(@RequestBody @Valid
                                                    CreateReviewRequestDTO data,
                                                    Authentication authentication) {
        JWTUserDTO dto = (JWTUserDTO) authentication.getPrincipal();
        User currentUser = userService.findById(dto.userID());
        Review review = reviewService.saveReview(data, currentUser);
        var response = new ReviewResponseDTO(review);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(description = "Lista reviews de uma master específica (paginado)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de reviews retornada")
    })
    @GetMapping("/master/{masterId}")
    public ResponseEntity<Page<ReviewResponseDTO>> listByMasterId(
            @PathVariable Long masterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // 1. Busca a página de entidades
        Page<Review> reviewsPage = reviewService.findByMasterId(masterId, page, size);

        // 2. Converte para página de DTOs usando o método .map()
        // O .map() do Page funciona igual ao do Stream, mas preserva os metadados de paginação!
        Page<ReviewResponseDTO> responseDTOs = reviewsPage.map(ReviewResponseDTO::new);

        return ResponseEntity.ok(responseDTOs);
    }

    @Operation(description = "Editar uma avaliação existente")
    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateReviewRequestDTO data,
            Authentication authentication
    ) {
        JWTUserDTO user = (JWTUserDTO) authentication.getPrincipal(); // Ou JWTUserDTO

        Review updatedReview = reviewService.update(id, user.userID(), data);

        return ResponseEntity.ok(new ReviewResponseDTO(updatedReview));
    }

    @Operation(description = "Listar todas as avaliações feitas por um usuário específico")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ReviewResponseDTO>> listByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Review> reviewPage = reviewService.listByUser(userId, page, size);

        // Converte para DTO
        Page<ReviewResponseDTO> dtoPage = reviewPage.map(ReviewResponseDTO::new);

        return ResponseEntity.ok(dtoPage);
    }

    // Dica: Se quiser um atalho para "Minhas Reviews" (usuário logado)
    @Operation(description = "Listar MINHAS avaliações")
    @GetMapping("/me")
    public ResponseEntity<Page<ReviewResponseDTO>> listMyReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        JWTUserDTO user = (JWTUserDTO) authentication.getPrincipal(); // Ou JWTUserDTO
        return listByUser(user.userID(), page, size); // Reutiliza a lógica acima
    }

    @Operation(description = "Excluir uma avaliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação excluída com sucesso"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para excluir esta avaliação"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication
    ) {
        JWTUserDTO user = (JWTUserDTO) authentication.getPrincipal(); // Ou JWTUserDTO

        reviewService.delete(id, user.userID());

        return ResponseEntity.noContent().build();
    }
}
