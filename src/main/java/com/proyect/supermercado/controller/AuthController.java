package com.proyect.supermercado.controller;


import com.proyect.supermercado.dto.LoginRequestDTO;
import com.proyect.supermercado.dto.LoginResponseDTO;
import com.proyect.supermercado.dto.MessageResponseDTO;
import com.proyect.supermercado.dto.RegisterRequestDTO;
import com.proyect.supermercado.repository.UserRepository;
import com.proyect.supermercado.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.proyect.supermercado.service.JwtService;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {

    private final  AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO){
        try {
            MessageResponseDTO response= authService.register(requestDTO);
            return  ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
        try{
            LoginResponseDTO responseDTO = authService.login(requestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}