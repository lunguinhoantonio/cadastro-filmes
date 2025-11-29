package dev.lunguinhoantonio.Movieflix.mapper;

import dev.lunguinhoantonio.Movieflix.controller.request.UserRequest;
import dev.lunguinhoantonio.Movieflix.controller.response.UserResponse;
import dev.lunguinhoantonio.Movieflix.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User toUser(UserRequest request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
