package dev.lunguinhoantonio.Movieflix.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @Schema(description = "Nome da categoria", example = "Ação")
        @NotBlank(message = "Nome da categoria é obrigatório!")
        String name
) {
}