package com.proyect.supermercado.service;

import com.proyect.supermercado.dto.LoginRequestDTO;
import com.proyect.supermercado.dto.LoginResponseDTO;
import com.proyect.supermercado.dto.MessageResponseDTO;
import com.proyect.supermercado.dto.RegisterRequestDTO;
import com.proyect.supermercado.entity.User;
import com.proyect.supermercado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public MessageResponseDTO register(RegisterRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();

        Optional<User> userExist = userRepository.findByUserName(request.getUserName());
        if (userExist.isPresent()) {
            response.setMessage("Este usuario ya está registrado");
            return response;
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setRol(request.getRol().toUpperCase());

        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);

        response.setMessage("Registro exitoso");
        return response;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        LoginResponseDTO response = new LoginResponseDTO();

        Optional<User> userOptional = userRepository.findByUserName(request.getUserName());

        if (userOptional.isEmpty()) {
            response.setMessage("Usuario no encontrado");
            return response;
        }
        User user = userOptional.get();
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            response.setMessage("Contraseña incorrecta");
            return response;
        }
        String token = jwtService.generateToken(user.getId(), user.getUserName(), user.getRol());
        response.setMessage("Inicio de sesión exitoso");
        response.setJwt(token);
        return response;
    }
}