package com.proyect.supermercado.service;

import java.util.Optional;

import com.proyect.supermercado.dto.LoginRequestDTO;
import com.proyect.supermercado.dto.LoginResponseDTO;
import com.proyect.supermercado.dto.MessageResponseDTO;
import com.proyect.supermercado.dto.UserRequestDTO;
import com.proyect.supermercado.entity.User;
import com.proyect.supermercado.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public MessageResponseDTO register(UserRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();
        Optional<User> userExist = userRepository.findByUsername(request.getUserName());
        if (userExist.isPresent()) {
            response.setMessage("Este correo ya está registrado");
            return response;
        }
        User user = new User();
        user.setUsername(request.getUserName());
        user.setRol(request.getRol().toUpperCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        response.setMessage("Registro exitoso");

        return response;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUserName());
        LoginResponseDTO response = new LoginResponseDTO();

        if (userOptional.isPresent()) {
            response.setMessaje("Este usuario no está registrado");
            return response;
        }


        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña o correo incorrectos");
        }

        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRol());

        response.setMessaje("Inicio de sesión exitoso");
        response.setJwt(token);
        return response;
    }

}
