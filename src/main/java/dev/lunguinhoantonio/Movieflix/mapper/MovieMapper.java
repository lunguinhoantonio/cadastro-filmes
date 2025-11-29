package dev.lunguinhoantonio.Movieflix.mapper;

import dev.lunguinhoantonio.Movieflix.controller.request.MovieRequest;
import dev.lunguinhoantonio.Movieflix.controller.response.CategoryResponse;
import dev.lunguinhoantonio.Movieflix.controller.response.MovieResponse;
import dev.lunguinhoantonio.Movieflix.controller.response.StreamingResponse;
import dev.lunguinhoantonio.Movieflix.entity.Category;
import dev.lunguinhoantonio.Movieflix.entity.Movie;
import dev.lunguinhoantonio.Movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MovieMapper {
    public static Movie toMovie(MovieRequest request) {
        List<Category> categories = request.categories().stream()
                .map(categoryId -> Category.builder().id(categoryId).build())
                .toList();

        List<Streaming> streamings = request.streamings().stream()
                .map(streamingId -> Streaming.builder().id(streamingId).build())
                .toList();

        return Movie.builder()
                .title(request.title())
                .description(request.description())
                .releaseDate(request.releaseDate())
                .rating(request.rating())
                .categories(categories)
                .streamings(streamings)
                .build();
    }

    public static MovieResponse toMovieResponse(Movie movie) {
        List<CategoryResponse> categories = movie.getCategories().stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();

        List<StreamingResponse> streamings = movie.getStreamings().stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .categories(categories)
                .streamings(streamings)
                .build();
    }
}
