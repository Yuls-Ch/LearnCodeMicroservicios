package com.learncode_backend.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.learncode_backend.model.Subscription;
import com.learncode_backend.repository.GestionSuscripcionRepository;
import com.learncode_backend.service.GestionSuscripcionService;
import jakarta.transaction.Transactional;

@Service
public class GestionSuscripcionServiceImpl extends ICRUDImpl<Subscription, UUID> implements GestionSuscripcionService {
	@Autowired
    private GestionSuscripcionRepository repository;
	
	@Override
	public JpaRepository<Subscription, UUID> getRepository() {
		return repository;
	}
	
	@Override
	public Page<Subscription> listar(String plan, String status, Pageable pageable) throws Exception {
		return repository.listarSuscripciones(plan, status, pageable);
	}

	@Override
	public Subscription obtenerPorId(UUID id) throws Exception {
		Subscription sub = super.findById(id);
		
        return sub;
	}

	@Override
	@Transactional
	public Subscription editar(Subscription entity) throws Exception {
	    
	    if ("ACTIVE".equals(entity.getStatus())) {

	        LocalDateTime nuevoFin =
	                LocalDateTime.now()
	                        .plusMonths(1)
	                        .plusDays(2);

	        repository.activarSuscripcion(
	                entity.getId(),
	                entity.getPlanCode(),
	                nuevoFin
	        );
	    }
	    else if ("CANCELED".equals(entity.getStatus())) {
	        repository.cancelarSuscripcion(
	                entity.getId(),
	                entity.getPlanCode()
	        );
	    }
	    else {
	        repository.actualizarSuscripcion(
	                entity.getId(),
	                entity.getPlanCode(),
	                entity.getStatus()
	        );
	    }
	    Subscription actualizado = super.findById(entity.getId());

	    return actualizado;
	}
}