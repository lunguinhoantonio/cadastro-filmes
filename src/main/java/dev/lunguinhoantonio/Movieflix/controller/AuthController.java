package dev.lunguinhoantonio.Movieflix.controller;

import dev.lunguinhoantonio.Movieflix.config.TokenService;
import dev.lunguinhoantonio.Movieflix.controller.request.LoginRequest;
import dev.lunguinhoantonio.Movieflix.controller.request.UserRequest;
import dev.lunguinhoantonio.Movieflix.controller.response.LoginResponse;
import dev.lunguinhoantonio.Movieflix.controller.response.UserResponse;
import dev.lunguinhoantonio.Movieflix.entity.User;
import dev.lunguinhoantonio.Movieflix.exception.UsernameOrPasswordInvalidException;
import dev.lunguinhoantonio.Movieflix.mapper.UserMapper;
import dev.lunguinhoantonio.Movieflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movieflix/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registrar(@RequestBody UserRequest request) {
        User savedUser = service.save(UserMapper.toUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authenticate = authenticationManager.authenticate(userAndPass);

            User user = (User) authenticate.getPrincipal();

            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException ex) {
            throw new UsernameOrPasswordInvalidException("Senha ou usuário inválido!");
        }
    }
}
