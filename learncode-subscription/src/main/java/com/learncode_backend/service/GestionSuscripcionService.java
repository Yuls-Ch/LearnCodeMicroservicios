package com.learncode_backend.service;

import com.learncode_backend.model.Subscription;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GestionSuscripcionService extends ICRUD<Subscription, UUID>{
	Page<Subscription> listar(
            String plan,
            String status,
            Pageable pageable
    ) throws Exception;
	
	Subscription obtenerPorId(UUID id) throws Exception;
	
	Subscription editar(Subscription entity) throws Exception;
}