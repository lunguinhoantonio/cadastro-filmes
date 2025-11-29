package dev.lunguinhoantonio.Movieflix.repository;

import dev.lunguinhoantonio.Movieflix.entity.Category;
import dev.lunguinhoantonio.Movieflix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findMovieByCategories(List<Category> categories);
}
