package dev.lunguinhoantonio.Movieflix.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record MovieResponse(
        @Schema(description = "Código do filme")
        Long id,
        @Schema(description = "Nome do filme")
        String title,
        @Schema(description = "Descrição do filme")
        String description,
        @Schema(type = "string", format = "date", description = "Data de lançamento do filme, ex: 19/10/1997")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate releaseDate,
        @Schema(type = "number", format = "double", description = "Pontuação do filme, ex: 7.8")
        double rating,
        @Schema(description = "Lista de códigos de categorias do filme")
        List<CategoryResponse> categories,
        @Schema(description = "Lista de códigos de serviço de streaming do filme")
        List<StreamingResponse> streamings
) {
}
