package com.learncode_backend.controller;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.learncode_backend.utils.ApiResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import com.learncode_backend.dto.UserDTO;
import com.learncode_backend.model.User;
import com.learncode_backend.service.GestionClienteService;
import com.learncode_backend.service.UserService;
import com.learncode_backend.utils.ModeloNotFoundException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService service;
    @Autowired
    private GestionClienteService gestionClienteService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal Jwt jwt) {

        User user = service.getOrCreateUser(jwt);

        if (user == null) {
            throw new ModeloNotFoundException("Usuario no encontrado");
        }

        return mapper.map(user, UserDTO.class);
    }

    @GetMapping("/internal/user/{id}")
    public UserDTO getById(@PathVariable UUID id) throws Exception {

        User user = service.findById(id);

        if (user == null) {
            throw new ModeloNotFoundException("Usuario no existe");
        }

        return mapper.map(user, UserDTO.class);
    }
    

    @GetMapping("/users/id/{email}") 
    public ResponseEntity<ApiResponse<UUID>> obtenerIdPorEmail(@PathVariable String email) {
        
        email = URLDecoder.decode(email, StandardCharsets.UTF_8);

        User user = gestionClienteService.obtenerCliente(email); 
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Usuario no encontrado", null));
        }
        
        return ResponseEntity.ok(new ApiResponse<>(true, "ID recuperado", user.getId()));
    }
}
