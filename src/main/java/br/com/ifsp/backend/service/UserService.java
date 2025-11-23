package br.com.ifsp.backend.service;

import br.com.ifsp.backend.dto.request.RegisterUserRequestDTO;
import br.com.ifsp.backend.model.Role;
import br.com.ifsp.backend.model.User;
import br.com.ifsp.backend.repository.CountryRepository;
import br.com.ifsp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CountryRepository countryRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
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
}
