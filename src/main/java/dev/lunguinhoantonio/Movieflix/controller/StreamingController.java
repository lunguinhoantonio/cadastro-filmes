package dev.lunguinhoantonio.Movieflix.controller;

import dev.lunguinhoantonio.Movieflix.controller.request.StreamingRequest;
import dev.lunguinhoantonio.Movieflix.controller.response.StreamingResponse;
import dev.lunguinhoantonio.Movieflix.entity.Streaming;
import dev.lunguinhoantonio.Movieflix.mapper.StreamingMapper;
import dev.lunguinhoantonio.Movieflix.service.StreamingService;
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

@RestController
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
@Tag(name = "Streaming", description = "Recurso responsável pelo gerenciamento dos serviços de streaming")
public class StreamingController {

    private final StreamingService streamingService;

    @Operation(
            summary = "Listar todos os streamings",
            description = "Retorna uma lista completa de todos os serviços de streaming cadastrados no sistema",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de streamings retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StreamingResponse.class)))
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
    public ResponseEntity<List<StreamingResponse>> findAll() {
        List<StreamingResponse> list = streamingService.findAll().stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Buscar streaming por ID",
            description = "Retorna um serviço de streaming com o ID informado",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Streaming encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = StreamingResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Streaming não encontrado",
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
    public ResponseEntity<StreamingResponse> findById(
            @Parameter(description = "ID do streaming", example = "1", required = true)
            @PathVariable Long id) {
        return streamingService.findById(id)
                .map(streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Salvar streaming",
            description = "Realiza o cadastro de um novo serviço de streaming no sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Streaming salvo com sucesso",
                    content = @Content(schema = @Schema(implementation = StreamingResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content()
            )
    })
    @PostMapping
    public ResponseEntity<StreamingResponse> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do streaming a ser salvo",
                    required = true,
                    content = @Content(schema = @Schema(implementation = StreamingRequest.class))
            )
            @Valid @RequestBody StreamingRequest request) {
        Streaming newStreaming = StreamingMapper.toStreaming(request);
        Streaming savedStreaming = streamingService.save(newStreaming);
        return ResponseEntity.status(HttpStatus.CREATED).body(StreamingMapper.toStreamingResponse(savedStreaming));
    }

    @Operation(
            summary = "Deletar streaming por ID",
            description = "Remove permanentemente um serviço de streaming com base no ID informado",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Streaming deletado com sucesso",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Streaming não encontrado",
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
            @Parameter(description = "ID do streaming a ser deletado", example = "1", required = true)
            @PathVariable Long id) {
        streamingService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}