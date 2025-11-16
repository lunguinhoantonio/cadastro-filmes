package dev.lunguinhoantonio.Movieflix.mapper;

import dev.lunguinhoantonio.Movieflix.controller.request.CategoryRequest;
import dev.lunguinhoantonio.Movieflix.controller.response.CategoryResponse;
import dev.lunguinhoantonio.Movieflix.entity.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {
    public static Category toCategory(CategoryRequest categoryRequest) {
        return Category
                .builder()
                .name(categoryRequest.name())
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
