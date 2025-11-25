package br.com.ifsp.backend.controller;

import br.com.ifsp.backend.config.TokenConfig;
import br.com.ifsp.backend.dto.request.RegisterUserRequestDTO;
import br.com.ifsp.backend.dto.request.UserLoginRequestDTO;
import br.com.ifsp.backend.dto.response.RegisterUserResponseDTO;
import br.com.ifsp.backend.dto.response.UserLoginResponseDTO;
import br.com.ifsp.backend.model.User;
import br.com.ifsp.backend.service.AuthService;
import br.com.ifsp.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(description = "Endpoint para login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas ou usuário não existe.")
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> userLogin(@RequestBody @Valid UserLoginRequestDTO data){
        UserLoginResponseDTO loginResponseDTO = authService.userLogin(data);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @Operation(description = "Endpoint para criação de usuários.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Não foi possível criar o usuário.")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody @Valid RegisterUserRequestDTO data) {
        User newUser = userService.registerUser(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponseDTO(newUser.getUsername(), newUser.getEmail()));
    }
}
