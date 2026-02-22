package dev.lunguinhoantonio.Movieflix.controller.ui;

import dev.lunguinhoantonio.Movieflix.entity.Category;
import dev.lunguinhoantonio.Movieflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryViewController {

    private final CategoryService categoryService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("currentPage", "categories");
        return "categories/lista";
    }

    @GetMapping("/new")
    public String novaForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("currentPage", "categories");
        return "categories/form";
    }

    @PostMapping
    public String salvar(@RequestParam String name, RedirectAttributes redirectAttrs) {
        categoryService.save(Category.builder().name(name).build());
        redirectAttrs.addFlashAttribute("successMessage", "Categoria cadastrada com sucesso!");
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String editarForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + id));
        model.addAttribute("category", category);
        model.addAttribute("currentPage", "categories");
        return "categories/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @RequestParam String name,
                            RedirectAttributes redirectAttrs) {
        categoryService.save(Category.builder().id(id).name(name).build());
        redirectAttrs.addFlashAttribute("successMessage", "Categoria atualizada com sucesso!");
        return "redirect:/categories";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            categoryService.deleteById(id);
            redirectAttrs.addFlashAttribute("successMessage", "Categoria removida com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage",
                    "Não foi possível remover. Existem filmes vinculados a esta categoria.");
        }
        return "redirect:/categories";
    }
}