package br.com.ifsp.backend.service.user;

import br.com.ifsp.backend.dto.request.create.RegisterUserRequestDTO;
import br.com.ifsp.backend.dto.request.patch.ChangePasswordRequestDTO;
import br.com.ifsp.backend.dto.request.patch.PatchUserRequestDTO;
import br.com.ifsp.backend.exceptions.InvalidPasswordException;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.Country;
import br.com.ifsp.backend.model.user.Role;
import br.com.ifsp.backend.model.user.User;
import br.com.ifsp.backend.repository.user.UserRepository;
import br.com.ifsp.backend.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CountryService countryService;

    public UserService(UserRepository userRepository, CountryService countryService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.countryService = countryService;
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

    @Transactional
    public User update(Long currentUserId, PatchUserRequestDTO data) {
        User user = findById(currentUserId);

        if (data.firstName() != null) user.setFirstName(data.firstName());
        if (data.lastName() != null) user.setLastName(data.lastName());
        if (data.birthdate() != null) user.setBirthdate(data.birthdate());
        if (data.bio() != null) user.setBio(data.bio());

        if (data.countryId() != null) {
            Country country = countryService.findById(data.countryId());
            user.setCountry(country);
        }

        userRepository.save(user);

        return user;
    }

    @Transactional
    public void delete(Long userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequestDTO data) {
        User user = findById(userId);

        if (!passwordEncoder.matches(data.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("A senha atual está incorreta.");
        }

        if (passwordEncoder.matches(data.newPassword(), user.getPassword())) {
            throw new InvalidPasswordException("A nova senha deve ser diferente da atual.");
        }

        user.setPassword(passwordEncoder.encode(data.newPassword()));

        userRepository.save(user);
    }
}
