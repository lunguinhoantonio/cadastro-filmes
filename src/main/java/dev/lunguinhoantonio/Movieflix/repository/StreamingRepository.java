package dev.lunguinhoantonio.Movieflix.repository;

import dev.lunguinhoantonio.Movieflix.entity.Streaming;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamingRepository extends JpaRepository<Streaming, Long> {
}
