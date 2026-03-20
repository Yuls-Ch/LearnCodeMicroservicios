package com.learncode_backend.controller;

import com.learncode_backend.dto.GestionClienteDTO;
import com.learncode_backend.model.User;
import com.learncode_backend.service.GestionClienteService;
import com.learncode_backend.utils.ApiResponse;
import com.learncode_backend.utils.BusinessException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/gestionCliente")
public class GestionClienteController {
    
    @Autowired
    private GestionClienteService service;
	
    @Autowired
    private ModelMapper mapper;
	
    @GetMapping
    public ResponseEntity<ApiResponse<?>> listarClientes(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {

        Page<User> data = service.listarClientes(search, status, PageRequest.of(page, size));
        Page<GestionClienteDTO> info = data.map(user -> mapper.map(user, GestionClienteDTO.class));
        ApiResponse<Page<GestionClienteDTO>> response = new ApiResponse<>(true, "Lista de clientes", info);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<?>> obtenerCliente(@PathVariable String email) throws Exception {
        email = URLDecoder.decode(email, StandardCharsets.UTF_8);
        User user = service.obtenerCliente(email);

        if (user == null) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Cliente con email: " + email + " no existe", null), HttpStatus.NOT_FOUND);
        }

        GestionClienteDTO dto = mapper.map(user, GestionClienteDTO.class);
        return new ResponseEntity<>(new ApiResponse<>(true, "Cliente encontrado", dto), HttpStatus.OK);
    }
    
    @PutMapping("/{email}")
    public ResponseEntity<ApiResponse<?>> editarCliente(
            @PathVariable String email,
            @Valid @RequestBody GestionClienteDTO data
    ) throws Exception {

        email = URLDecoder.decode(email, StandardCharsets.UTF_8);

        if (data == null) throw new BusinessException("Datos inválidos");
        
        User user = mapper.map(data, User.class);
        User actualizado = service.editarCliente(email, user);
        
        if (actualizado == null) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Cliente con email: " + email + " no existe", null), HttpStatus.NOT_FOUND);
        }

        GestionClienteDTO dto = mapper.map(actualizado, GestionClienteDTO.class);
        return new ResponseEntity<>(new ApiResponse<>(true, "Cliente actualizado", dto), HttpStatus.OK);
    }
    
 
}