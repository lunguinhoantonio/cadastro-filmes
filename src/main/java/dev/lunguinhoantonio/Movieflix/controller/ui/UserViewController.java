package dev.lunguinhoantonio.Movieflix.controller.ui;

import dev.lunguinhoantonio.Movieflix.entity.User;
import dev.lunguinhoantonio.Movieflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentPage", "users");
        return "users/lista";
    }

    @GetMapping("/new")
    public String novoForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("currentPage", "users");
        return "users/form";
    }

    @PostMapping
    public String salvar(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String password,
                         RedirectAttributes redirectAttrs) {
        User user = User.builder().name(name).email(email).password(password).build();
        userService.save(user);
        redirectAttrs.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String editarForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
        model.addAttribute("user", user);
        model.addAttribute("currentPage", "users");
        return "users/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String email,
                            RedirectAttributes redirectAttrs) {
        User existing = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
        existing.setName(name);
        existing.setEmail(email);
        userService.saveRaw(existing);
        redirectAttrs.addFlashAttribute("successMessage", "Usuário atualizado com sucesso!");
        return "redirect:/users";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            userService.deleteById(id);
            redirectAttrs.addFlashAttribute("successMessage", "Usuário removido com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Não foi possível remover o usuário.");
        }
        return "redirect:/users";
    }
}