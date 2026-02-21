package dev.lunguinhoantonio.Movieflix.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CategoryResponse(
        @Schema(description = "Código da categoria")
        Long id,
        @Schema(description = "Nome da categoria")
        String name) {
}
