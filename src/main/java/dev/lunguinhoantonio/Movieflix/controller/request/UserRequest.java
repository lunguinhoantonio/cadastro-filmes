package dev.lunguinhoantonio.Movieflix.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserRequest(
        @Schema(description = "Nome do usuário", example = "João Silva")
        String name,
        @Schema(description = "E-mail do usuário", example = "joao@email.com")
        String email,
        @Schema(description = "Senha do usuário", example = "senha123")
        String password
) {
}