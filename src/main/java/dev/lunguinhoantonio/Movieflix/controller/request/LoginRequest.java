package dev.lunguinhoantonio.Movieflix.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
        @Schema(description = "E-mail do usuário", example = "usuario@email.com")
        String email,
        @Schema(description = "Senha do usuário", example = "senha123")
        String password
) {
}