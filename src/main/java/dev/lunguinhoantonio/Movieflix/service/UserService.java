package dev.lunguinhoantonio.Movieflix.service;

import dev.lunguinhoantonio.Movieflix.entity.User;
import dev.lunguinhoantonio.Movieflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }
}
