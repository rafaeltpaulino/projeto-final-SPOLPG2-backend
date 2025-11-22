package br.com.ifsp.backend.controller;

import br.com.ifsp.backend.dto.request.RegisterUserRequestDTO;
import br.com.ifsp.backend.dto.request.UserLoginRequestDTO;
import br.com.ifsp.backend.dto.response.RegisterUserResponseDTO;
import br.com.ifsp.backend.dto.response.UserLoginResponseDTO;
import br.com.ifsp.backend.model.User;
import br.com.ifsp.backend.repository.UserRepository;
import br.com.ifsp.backend.service.CountryService;
import br.com.ifsp.backend.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> userLogin(@RequestBody @Valid UserLoginRequestDTO data){
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        Authentication authentication = authenticationManager.authenticate(user);

        return null;
    }

    @RequestMapping("/register")
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody @Valid RegisterUserRequestDTO data) {
        User newUser = userService.registerUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponseDTO(newUser.getUsername(), newUser.getEmail()));
    }
}
