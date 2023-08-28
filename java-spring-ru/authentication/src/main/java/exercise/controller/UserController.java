package exercise.controller;

import exercise.dto.UserDto;
import exercise.model.User;
import exercise.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    // Кодировщик BCrypt
    // Используйте для хеширования пароля
    private final PasswordEncoder encoder;

    @GetMapping(path = "")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    // BEGIN
    @PostMapping(path = "")
    public User registerNewUser(@RequestBody UserDto sentUser) {
        User newUser = new User();

        newUser.setUsername(sentUser.username());
        newUser.setPassword(encoder.encode(sentUser.password()));
        newUser.setEmail(sentUser.email());

        return userRepository.save(newUser);
    }
    // END
}
