package com.learncode_backend.service;

import java.util.List;

public interface ICRUD <T, ID> {
	T save(T bean) throws Exception;
	T update(T bean) throws Exception;
	void deleteByID(ID cod) throws Exception;
	T findById(ID cod) throws Exception;
	List<T> findAll() throws Exception;
}
