package dev.lunguinhoantonio.Movieflix.mapper;

import dev.lunguinhoantonio.Movieflix.controller.request.StreamingRequest;
import dev.lunguinhoantonio.Movieflix.controller.response.StreamingResponse;
import dev.lunguinhoantonio.Movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {
    public static Streaming toStreaming(StreamingRequest request) {
        return Streaming
                .builder()
                .name(request.name())
                .build();
    }

    public static StreamingResponse toStreamingResponse(Streaming streaming) {
        return StreamingResponse
                .builder()
                .id(streaming.getId())
                .name(streaming.getName())
                .build();
    }
}
