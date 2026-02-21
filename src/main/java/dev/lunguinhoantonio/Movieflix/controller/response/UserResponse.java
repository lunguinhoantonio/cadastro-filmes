package dev.lunguinhoantonio.Movieflix.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserResponse(
        @Schema(description = "Código do usuário")
        Long id,
        @Schema(description = "Nome do usuário", example = "João Silva")
        String name,
        @Schema(description = "E-mail do usuário", example = "joao@email.com")
        String email
) {
}