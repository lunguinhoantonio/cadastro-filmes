package dev.lunguinhoantonio.Movieflix.controller.request;

import jakarta.validation.constraints.NotBlank;

public record StreamingRequest(@NotBlank(message = "Nome do serviço de streaming é obrigatório!") String name) {
}
