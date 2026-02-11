package dev.lunguinhoantonio.Movieflix.repository;

import dev.lunguinhoantonio.Movieflix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findUserByEmail(String email);
}
