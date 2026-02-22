package dev.lunguinhoantonio.Movieflix.controller.ui;

import dev.lunguinhoantonio.Movieflix.entity.Streaming;
import dev.lunguinhoantonio.Movieflix.service.StreamingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/streamings")
@RequiredArgsConstructor
public class StreamingViewController {

    private final StreamingService streamingService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("streamings", streamingService.findAll());
        model.addAttribute("currentPage", "streamings");
        return "streamings/lista";
    }

    @GetMapping("/new")
    public String novoForm(Model model) {
        model.addAttribute("streaming", new Streaming());
        model.addAttribute("currentPage", "streamings");
        return "streamings/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("streaming") Streaming streaming,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("currentPage", "streamings");
            return "streamings/form";
        }
        streamingService.save(streaming);
        redirectAttrs.addFlashAttribute("successMessage", "Streaming cadastrado com sucesso!");
        return "redirect:/streamings";
    }

    @GetMapping("/{id}/edit")
    public String editarForm(@PathVariable Long id, Model model) {
        Streaming streaming = streamingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Streaming não encontrado: " + id));
        model.addAttribute("streaming", streaming);
        model.addAttribute("currentPage", "streamings");
        return "streamings/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("streaming") Streaming streaming,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("currentPage", "streamings");
            return "streamings/form";
        }
        streaming.setId(id);
        streamingService.save(streaming);
        redirectAttrs.addFlashAttribute("successMessage", "Streaming atualizado com sucesso!");
        return "redirect:/streamings";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            streamingService.deleteById(id);
            redirectAttrs.addFlashAttribute("successMessage", "Streaming removido com sucesso!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage",
                    "Não foi possível remover. Existem filmes vinculados a este streaming.");
        }
        return "redirect:/streamings";
    }
}
