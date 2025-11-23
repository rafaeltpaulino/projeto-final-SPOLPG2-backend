package br.com.ifsp.backend.service;

import br.com.ifsp.backend.config.TokenConfig;
import br.com.ifsp.backend.dto.request.UserLoginRequestDTO;
import br.com.ifsp.backend.dto.response.UserLoginResponseDTO;
import br.com.ifsp.backend.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthService(AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    public UserLoginResponseDTO userLogin(UserLoginRequestDTO data) {
        var passwordAuthToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication authentication = authenticationManager.authenticate(passwordAuthToken);

        User user = (User) authentication.getPrincipal();
        String token = tokenConfig.generateJWT(user);

        return new UserLoginResponseDTO(token);
    }
}
