package dev.lunguinhoantonio.Movieflix.controller;

import dev.lunguinhoantonio.Movieflix.controller.request.UserRequest;
import dev.lunguinhoantonio.Movieflix.controller.response.UserResponse;
import dev.lunguinhoantonio.Movieflix.entity.User;
import dev.lunguinhoantonio.Movieflix.mapper.UserMapper;
import dev.lunguinhoantonio.Movieflix.repository.UserRepository;
import dev.lunguinhoantonio.Movieflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movieflix/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registrar(@RequestBody UserRequest request) {
        User savedUser = service.save(UserMapper.toUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(savedUser));
    }
}
