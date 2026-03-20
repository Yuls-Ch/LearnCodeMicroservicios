package com.learncode_backend.service.impl;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.learncode_backend.service.ICRUD;

public abstract class ICRUDImpl<T, ID> implements ICRUD<T, ID> {
	public abstract JpaRepository<T, ID> getRepository();
	
	@Override
	public T save(T bean) throws Exception {
		return getRepository().save(bean);
	}

	@Override
	public T update(T bean) throws Exception {
		return getRepository().save(bean);
	}

	@Override
	public void deleteByID(ID cod) throws Exception {
		getRepository().deleteById(cod);
	}

	@Override
	public T findById(ID cod) throws Exception {
		return getRepository().findById(cod).orElse(null);
	}

	@Override
	public List<T> findAll() throws Exception {
		return getRepository().findAll();
	}
}
