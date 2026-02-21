package dev.lunguinhoantonio.Movieflix.controller;

import dev.lunguinhoantonio.Movieflix.controller.request.MovieRequest;
import dev.lunguinhoantonio.Movieflix.controller.response.MovieResponse;
import dev.lunguinhoantonio.Movieflix.entity.Movie;
import dev.lunguinhoantonio.Movieflix.mapper.MovieMapper;
import dev.lunguinhoantonio.Movieflix.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/movie")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "Recurso responsável pelo gerenciamento dos filmes")
public class MovieController {

    private final MovieService movieService;

    @Operation(
            summary = "Listar todos os filmes",
            description = "Retorna uma lista completa de todos os filmes cadastrados no sistema",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de filmes retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado — token inválido ou ausente",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão para acessar este recurso",
                    content = @Content()
            )
    })
    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAll() {
        return ResponseEntity.ok(movieService.findAll().stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @Operation(
            summary = "Buscar filme por ID",
            description = "Retorna um filme com o ID informado",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = MovieResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Filme não encontrado",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado — token inválido ou ausente",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão para acessar este recurso",
                    content = @Content()
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById(
            @Parameter(description = "ID do filme", example = "1", required = true)
            @PathVariable Long id) {
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Buscar filmes por categoria",
            description = "Retorna todos os filmes que pertencem à categoria informada",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filmes encontrados com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum filme encontrado para a categoria informada",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado — token inválido ou ausente",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão para acessar este recurso",
                    content = @Content()
            )
    })
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> findByCategory(
            @Parameter(description = "ID da categoria", example = "2", required = true)
            @RequestParam Long category) {
        List<MovieResponse> list = movieService.findByCategory(category).stream()
                .map(MovieMapper::toMovieResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Salvar filme",
            description = "Realiza o cadastro de um novo filme no sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Filme salvo com sucesso",
                    content = @Content(schema = @Schema(implementation = MovieResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content()
            )
    })
    @PostMapping
    public ResponseEntity<MovieResponse> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do filme a ser salvo",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MovieRequest.class))
            )
            @Valid @RequestBody MovieRequest request) {
        Movie savedMovie = movieService.save(MovieMapper.toMovie(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(MovieMapper.toMovieResponse(savedMovie));
    }

    @Operation(
            summary = "Alterar filme",
            description = "Atualiza os dados de um filme existente com base no ID informado",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme alterado com sucesso",
                    content = @Content(schema = @Schema(implementation = MovieResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Filme não encontrado",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado — token inválido ou ausente",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão para acessar este recurso",
                    content = @Content()
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(
            @Parameter(description = "ID do filme a ser alterado", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do filme",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MovieRequest.class))
            )
            @Valid @RequestBody MovieRequest request) {
        return movieService.update(id, MovieMapper.toMovie(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Deletar filme por ID",
            description = "Remove permanentemente um filme com base no ID informado",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Filme deletado com sucesso",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Filme não encontrado",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado — token inválido ou ausente",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão para acessar este recurso",
                    content = @Content()
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID do filme a ser deletado", example = "1", required = true)
            @PathVariable Long id) {
        Optional<Movie> optMovie = movieService.findById(id);
        if (optMovie.isPresent()) {
            movieService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}