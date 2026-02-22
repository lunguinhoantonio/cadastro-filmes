package dev.lunguinhoantonio.Movieflix.controller.ui;

import dev.lunguinhoantonio.Movieflix.service.CategoryService;
import dev.lunguinhoantonio.Movieflix.service.MovieService;
import dev.lunguinhoantonio.Movieflix.service.StreamingService;
import dev.lunguinhoantonio.Movieflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MovieService movieService;
    private final CategoryService categoryService;
    private final StreamingService streamingService;
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalMovies",     movieService.findAll().size());
        model.addAttribute("totalCategories", categoryService.findAll().size());
        model.addAttribute("totalStreamings", streamingService.findAll().size());
        model.addAttribute("totalUsers",      userService.findAll().size());
        model.addAttribute("currentPage", "home");
        return "index";
    }
}