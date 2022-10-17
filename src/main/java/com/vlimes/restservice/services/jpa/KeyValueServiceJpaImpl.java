package com.vlimes.restservice.services.jpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vlimes.restservice.model.KeyValue;
import com.vlimes.restservice.repositories.KeyValueRepository;
import com.vlimes.restservice.services.KeyValueService;

/**
 * KeyValue Jpa Implement
 * 
 * @author 48384541q
 *
 */
@Service
public class KeyValueServiceJpaImpl implements KeyValueService {

	private final KeyValueRepository keyValueRepository;
	
	
	public KeyValueServiceJpaImpl(KeyValueRepository keyValueRepository) {
		this.keyValueRepository = keyValueRepository;
	}

	@Override
	public Set<KeyValue> findAll() {
		Set<KeyValue> keyValues = new HashSet<>();		
		keyValueRepository.findAll().forEach(keyValues :: add);		
		return keyValues;
	}

	@Override
	public KeyValue findById(Long id) {
		return keyValueRepository.findById(id).orElse(null);
	}

	@Override
	public KeyValue save(KeyValue object) {
		return keyValueRepository.save(object);
	}

	@Override
	public void delete(KeyValue object) {
		keyValueRepository.delete(object);
	}

	@Override
	public void deleteById(Long id) {
		keyValueRepository.deleteById(id);
	}

}
