package com.vlimes.restservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.vlimes.restservice.model.KeyValue;

public interface KeyValueRepository extends CrudRepository<KeyValue, Long> {

}
