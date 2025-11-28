package br.com.ifsp.backend.controller.user;

import br.com.ifsp.backend.config.JWTUserDTO;
import br.com.ifsp.backend.dto.request.patch.ChangePasswordRequestDTO;
import br.com.ifsp.backend.dto.request.patch.PatchUserRequestDTO;
import br.com.ifsp.backend.dto.response.create.RegisterUserResponseDTO;
import br.com.ifsp.backend.dto.response.patch.PatchUserResponseDTO;
import br.com.ifsp.backend.dto.response.view.UserResponseDTO;
import br.com.ifsp.backend.model.user.User;
import br.com.ifsp.backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Lista todos os usuários.")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        var users = userService.listAll();
        var responseDTOS = users.stream()
                .map(UserResponseDTO::new)
                .toList();

        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable @Valid Long id) {
        var user = userService.findById(id);
        var response = new UserResponseDTO(user);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Atualizar perfil do usuário")
    @PatchMapping("/{id}")
    public ResponseEntity<PatchUserResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid
            PatchUserRequestDTO data,
            Authentication authentication
    ) {
        JWTUserDTO currentUser = (JWTUserDTO) authentication.getPrincipal();

        boolean isAdmin = currentUser.roles().contains("ROLE_ADMIN");

        if (!currentUser.userID().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User updatedUser = userService.update(id, data);

        var response = new PatchUserResponseDTO(updatedUser.getId(), updatedUser.getUsername());

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Excluir conta do usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            Long id,
            Authentication authentication
    ) {
        JWTUserDTO currentUser = (JWTUserDTO) authentication.getPrincipal();

        boolean isAdmin = currentUser.roles().contains("ROLE_ADMIN");

        if (!currentUser.userID().equals(id) && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Alterar senha do usuário")
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable
            Long id,
            @RequestBody @Valid
            ChangePasswordRequestDTO data,
            Authentication authentication
    ) {
        JWTUserDTO currentUser = (JWTUserDTO) authentication.getPrincipal();

        if (!currentUser.userID().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.changePassword(id, data);

        return ResponseEntity.ok().build();
    }
}
