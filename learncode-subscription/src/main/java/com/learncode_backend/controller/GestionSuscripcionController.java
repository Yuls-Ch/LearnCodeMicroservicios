package com.learncode_backend.controller;

import com.learncode_backend.client.AuthClient;
import com.learncode_backend.dto.GestionSuscripcionDTO;
import com.learncode_backend.dto.UserDTO;
import com.learncode_backend.model.Subscription;
import com.learncode_backend.service.GestionSuscripcionService;
import com.learncode_backend.utils.ApiResponse;
import com.learncode_backend.utils.ModeloNotFoundException;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/gestionSuscripcion")
public class GestionSuscripcionController {
	@Autowired
    private GestionSuscripcionService service;
	
	@Autowired
    private AuthClient authClient;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> listar(
            @RequestParam(defaultValue = "TODO") String plan,
            @RequestParam(defaultValue = "TODO") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws Exception {

        Page<Subscription> data = service.listar(plan, status, PageRequest.of(page, size));

        List<GestionSuscripcionDTO> dtoList = new ArrayList<>();

        for (Subscription sub : data.getContent()) {

            GestionSuscripcionDTO dto = mapper.map(sub, GestionSuscripcionDTO.class);
            dto.setPlan(sub.getPlanCode());

            UserDTO user = null;
            try {
                user = authClient.getUser(sub.getUserId());
            } catch (Exception e) {
            	user = new UserDTO();
                user.setFullName("Usuario desconocido");
                user.setEmail("desconocido@correo.com");
                user.setPhoto("https://ui-avatars.com/api/?name=Usuario+Desconocido");
            }
            
            dto.setFullName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setPhoto(
                user.getPhoto() != null
                    ? user.getPhoto()
                    : "https://ui-avatars.com/api/?name=" + user.getFullName().replace(" ", "+")
            );

            dtoList.add(dto);
        }

        Page<GestionSuscripcionDTO> info = new PageImpl<>(
                dtoList,
                PageRequest.of(page, size),
                data.getTotalElements()
        );

        ApiResponse<Page<GestionSuscripcionDTO>> response =
                new ApiResponse<>(true, "Lista de Suscripciones", info);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> obtener(@PathVariable UUID id) throws Exception {
        Subscription sub = service.obtenerPorId(id);

        if (sub == null)
            throw new ModeloNotFoundException("Suscripcion con id: " + id + " no existe");

        GestionSuscripcionDTO dto = mapper.map(sub, GestionSuscripcionDTO.class);
        dto.setPlan(sub.getPlanCode());

        UserDTO user = null;
        try {
            user = authClient.getUser(sub.getUserId());
        } catch (Exception e) {
        	user = new UserDTO();
            user.setFullName("Usuario desconocido");
            user.setEmail("desconocido@correo.com");
            user.setPhoto("https://ui-avatars.com/api/?name=Usuario+Desconocido");
        }
        
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoto(
            user.getPhoto() != null
                ? user.getPhoto()
                : "https://ui-avatars.com/api/?name=" + user.getFullName().replace(" ", "+")
        );

        ApiResponse<GestionSuscripcionDTO> responseDTO =
                new ApiResponse<>(true, "Suscripcion encontrada", dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> editar(
            @PathVariable UUID id,
            @Valid @RequestBody GestionSuscripcionDTO data
    ) throws Exception {
        Subscription existente = service.obtenerPorId(id);

        if (existente == null) {
            throw new ModeloNotFoundException("Suscripción con id: " + id + " no existe");
        }
        
        existente.setPlanCode(data.getPlan());
        existente.setStatus(data.getStatus());
        
        Subscription actualizado = service.editar(existente);
        
        GestionSuscripcionDTO dto = mapper.map(actualizado, GestionSuscripcionDTO.class);
        dto.setPlan(actualizado.getPlanCode());
        
        UserDTO user = null;
        try {
            user = authClient.getUser(actualizado.getUserId());
        } catch (Exception e) {
        	user = new UserDTO();
            user.setFullName("Usuario desconocido");
            user.setEmail("desconocido@correo.com");
            user.setPhoto("https://ui-avatars.com/api/?name=Usuario+Desconocido");
        }

        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoto(
            user.getPhoto() != null
                ? user.getPhoto()
                : "https://ui-avatars.com/api/?name=" + user.getFullName().replace(" ", "+")
        );

        ApiResponse<GestionSuscripcionDTO> responseDTO =
                new ApiResponse<>(true, "Suscripción actualizada", dto);

        return ResponseEntity.ok(responseDTO);
    }
}