package com.learncode_backend.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.learncode_backend.model.User;

public interface GestionClienteService extends ICRUD<User, UUID>{
	Page<User> listarClientes(
            String search,
            String status,
            Pageable pageable
    );
	
	User obtenerCliente(String email);
	
    User editarCliente(String email, User user);
}
