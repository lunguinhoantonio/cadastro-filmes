package dev.lunguinhoantonio.Movieflix.repository;

import dev.lunguinhoantonio.Movieflix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
