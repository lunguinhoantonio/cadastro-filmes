package dev.lunguinhoantonio.Movieflix.controller;

import dev.lunguinhoantonio.Movieflix.controller.request.CategoryRequest;
import dev.lunguinhoantonio.Movieflix.controller.response.CategoryResponse;
import dev.lunguinhoantonio.Movieflix.entity.Category;
import dev.lunguinhoantonio.Movieflix.mapper.CategoryMapper;
import dev.lunguinhoantonio.Movieflix.service.CategoryService;
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
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Recurso responsável pelo gerenciamento das categorias")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Listar todas as categorias",
            description = "Retorna uma lista completa de todas as categorias cadastradas no sistema",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de categorias retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))
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
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> list = categoryService.findAll().stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna uma categoria com o ID informado",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada",
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
    public ResponseEntity<CategoryResponse> findById(
            @Parameter(description = "ID da categoria", example = "1", required = true)
            @PathVariable Long id) {
        return categoryService.findById(id)
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Salvar categoria",
            description = "Realiza o cadastro de uma nova categoria no sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoria salva com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content()
            )
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da categoria a ser salva",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CategoryRequest.class))
            )
            @Valid @RequestBody CategoryRequest request) {
        Category newCategory = CategoryMapper.toCategory(request);
        Category savedCategory = categoryService.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toCategoryResponse(savedCategory));
    }

    @Operation(
            summary = "Deletar categoria por ID",
            description = "Remove permanentemente uma categoria com base no ID informado",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Categoria deletada com sucesso",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada",
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
            @Parameter(description = "ID da categoria a ser deletada", example = "1", required = true)
            @PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}