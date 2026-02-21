package dev.lunguinhoantonio.Movieflix.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record StreamingRequest(
        @Schema(description = "Nome do serviço de streaming", example = "Netflix")
        @NotBlank(message = "Nome do serviço de streaming é obrigatório!")
        String name
) {
}