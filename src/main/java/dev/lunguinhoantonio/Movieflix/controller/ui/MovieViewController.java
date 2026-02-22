package dev.lunguinhoantonio.Movieflix.controller.ui;

import dev.lunguinhoantonio.Movieflix.entity.Category;
import dev.lunguinhoantonio.Movieflix.entity.Movie;
import dev.lunguinhoantonio.Movieflix.entity.Streaming;
import dev.lunguinhoantonio.Movieflix.service.CategoryService;
import dev.lunguinhoantonio.Movieflix.service.MovieService;
import dev.lunguinhoantonio.Movieflix.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieViewController {

    private final MovieService movieService;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("movies", movieService.findAll());
        model.addAttribute("currentPage", "movies");
        return "movies/lista";
    }

    @GetMapping("/new")
    public String novoForm(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("streamings", streamingService.findAll());
        model.addAttribute("currentPage", "movies");
        return "movies/form";
    }

    @PostMapping
    public String salvar(@RequestParam String title,
                         @RequestParam(required = false) String description,
                         @RequestParam(required = false) String releaseDate,
                         @RequestParam(required = false, defaultValue = "0") double rating,
                         @RequestParam(required = false) List<Long> categoryIds,
                         @RequestParam(required = false) List<Long> streamingIds,
                         RedirectAttributes redirectAttrs) {
        Movie movie = buildMovie(null, title, description, releaseDate, rating, categoryIds, streamingIds);
        movieService.save(movie);
        redirectAttrs.addFlashAttribute("successMessage", "Filme cadastrado com sucesso!");
        return "redirect:/movies";
    }

    @GetMapping("/{id}/edit")
    public String editarForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + id));
        model.addAttribute("movie", movie);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("streamings", streamingService.findAll());
        model.addAttribute("currentPage", "movies");
        return "movies/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @RequestParam String title,
                            @RequestParam(required = false) String description,
                            @RequestParam(required = false) String releaseDate,
                            @RequestParam(required = false, defaultValue = "0") double rating,
                            @RequestParam(required = false) List<Long> categoryIds,
                            @RequestParam(required = false) List<Long> streamingIds,
                            RedirectAttributes redirectAttrs) {
        Movie updated = buildMovie(id, title, description, releaseDate, rating, categoryIds, streamingIds);
        movieService.update(id, updated);
        redirectAttrs.addFlashAttribute("successMessage", "Filme atualizado com sucesso!");
        return "redirect:/movies";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            movieService.deleteById(id);
            redirectAttrs.addFlashAttribute("successMessage", "Filme removido com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Não foi possível remover o filme.");
        }
        return "redirect:/movies";
    }

    private Movie buildMovie(Long id, String title, String description,
                             String releaseDateStr, double rating,
                             List<Long> categoryIds, List<Long> streamingIds) {
        List<Category> categories = new ArrayList<>();
        if (categoryIds != null) {
            categoryIds.forEach(cid ->
                    categoryService.findById(cid).ifPresent(categories::add));
        }

        List<Streaming> streamings = new ArrayList<>();
        if (streamingIds != null) {
            streamingIds.forEach(sid ->
                    streamingService.findById(sid).ifPresent(streamings::add));
        }

        LocalDate releaseDate = null;
        if (releaseDateStr != null && !releaseDateStr.isBlank()) {
            releaseDate = LocalDate.parse(releaseDateStr);
        }

        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setReleaseDate(releaseDate);
        movie.setRating(rating);
        movie.setCategories(categories);
        movie.setStreamings(streamings);
        return movie;
    }
}