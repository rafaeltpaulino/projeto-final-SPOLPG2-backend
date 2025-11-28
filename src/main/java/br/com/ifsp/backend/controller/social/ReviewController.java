package br.com.ifsp.backend.controller.social;

import br.com.ifsp.backend.config.JWTUserDTO;
import br.com.ifsp.backend.dto.request.create.CreateReviewRequestDTO;
import br.com.ifsp.backend.dto.response.view.ReviewResponseDTO;
import br.com.ifsp.backend.model.social.Review;
import br.com.ifsp.backend.model.user.User;
import br.com.ifsp.backend.service.social.ReviewService;
import br.com.ifsp.backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(description = "Lista todos os reviews de uma master específica.")
    @ApiResponse(responseCode = "200", description = "Lista dos reviews retornada")
    @GetMapping("/master/{masterId}")
    public ResponseEntity<List<ReviewResponseDTO>> listByMasterId(@PathVariable Long masterId) {
        var reviews = reviewService.findByMasterId(masterId);

        List<ReviewResponseDTO> responseDTOS = reviews.stream()
                .map(ReviewResponseDTO::new)
                .toList();

        return ResponseEntity.ok(responseDTOS);
    }
}
