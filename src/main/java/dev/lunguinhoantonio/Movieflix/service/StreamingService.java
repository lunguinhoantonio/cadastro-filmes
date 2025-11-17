package dev.lunguinhoantonio.Movieflix.service;

import dev.lunguinhoantonio.Movieflix.entity.Streaming;
import dev.lunguinhoantonio.Movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {
    private final StreamingRepository repository;

    public List<Streaming> findAll() {
        return repository.findAll();
    }

    public Streaming save(Streaming category) {
        return repository.save(category);
    }

    public Optional<Streaming> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
