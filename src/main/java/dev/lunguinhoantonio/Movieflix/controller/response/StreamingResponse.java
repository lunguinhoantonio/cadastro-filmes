package dev.lunguinhoantonio.Movieflix.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StreamingResponse(
        @Schema(description = "Código do serviço de streaming")
        Long id,
        @Schema(description = "Nome do serviço de streaming")
        String name
) {
}