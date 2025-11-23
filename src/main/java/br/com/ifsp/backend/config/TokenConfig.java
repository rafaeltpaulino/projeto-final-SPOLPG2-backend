package br.com.ifsp.backend.config;

import br.com.ifsp.backend.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateJWT(User user) {
        return JWT.create()
                .withClaim("userID", user.getId())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(Algorithm.HMAC256(secret));
    }

    public Optional<JWTUserDTO> validateJTW(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserDTO.builder()
                    .userID(decodedJWT.getClaim("userID").asLong())
                    .email(decodedJWT.getSubject())
                    .build());
        }catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
 }
