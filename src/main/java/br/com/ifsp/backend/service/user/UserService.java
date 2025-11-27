package br.com.ifsp.backend.service.user;

import br.com.ifsp.backend.dto.request.RegisterUserRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.user.Role;
import br.com.ifsp.backend.model.user.User;
import br.com.ifsp.backend.repository.CountryRepository;
import br.com.ifsp.backend.repository.user.UserRepository;
import br.com.ifsp.backend.service.CountryService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CountryService countryService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterUserRequestDTO data) {
        User newUser = new User();

        var encodedPassword = passwordEncoder.encode(data.password());

        newUser.setUsername(data.username());
        newUser.setEmail(data.email());
        newUser.setPassword(encodedPassword);
        newUser.setRoles(Set.of(Role.ROLE_USER));

        userRepository.save(newUser);

        return newUser;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontado nenhum usuário com o ID: " + id));
    }
}
