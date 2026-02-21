package dev.lunguinhoantonio.Movieflix.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(
        @Schema(description = "Nome do filme")
        @NotBlank(message = "Título do filme é obrigatório!")
        String title,
        @Schema(description = "Descrição do filme")
        String description,
        @Schema(type = "string", format = "date", description = "Data de lançamento do filme, ex: 19/10/1997")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate releaseDate,
        @Schema(type = "number", format = "double", description = "Pontuação do filme, ex: 7.8")
        double rating,
        @Schema(description = "Lista de códigos de categorias do filme")
        List<Long> categories,
        @Schema(description = "Lista de códigos de serviço de streaming do filme")
        List<Long> streamings
) {
}
