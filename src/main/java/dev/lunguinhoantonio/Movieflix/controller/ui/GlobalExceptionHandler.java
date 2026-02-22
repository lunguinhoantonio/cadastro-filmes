package dev.lunguinhoantonio.Movieflix.controller.ui;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleNotFound(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorTitle", "Registro não encontrado");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResource(Model model) {
        model.addAttribute("errorTitle", "Página não encontrada");
        model.addAttribute("errorMessage", "A rota que você acessou não existe.");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Erro inesperado");
        model.addAttribute("errorMessage", "Ocorreu um erro interno. Tente novamente.");
        return "error";
    }
}
