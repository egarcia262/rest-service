package com.vlimes.restservice.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlimes.restservice.exceptions.KeyValueInvalidRequestException;
import com.vlimes.restservice.exceptions.KeyValueNotFoundException;
import com.vlimes.restservice.model.KeyValue;
import com.vlimes.restservice.services.KeyValueService;

@RestController
@RequestMapping("/api")
public class KeyValueController {

	private final KeyValueService keyValueService;

	KeyValueController(KeyValueService service) {
    	this.keyValueService = service;
    }

    /*
     * Get all register request
     * 
     * http://127.0.0.1:8080/api/keyvalues
     * */
    @GetMapping("/keyvalues")
    public Set<KeyValue> findAll(){
        return keyValueService.findAll();
    }

    /*
     * Get specific register request
     * 
     * http://127.0.0.1:8080/api/keyvalues/{id}
     * */
    @GetMapping("/keyvalues/{id}")
    public KeyValue getKeyValue(@PathVariable long id){
        KeyValue keyvalue = keyValueService.findById(id);

        if(keyvalue == null) {
            throw new KeyValueNotFoundException(id);
        }
        
        return keyvalue;
    }

    /*
     * Add register request
     * 
     * http://127.0.0.1:8080/api/keyvalues/  
     * */
    @PostMapping("/keyvalues")
    public KeyValue addKeyValue(@RequestBody KeyValue keyvalue) {
        
    	keyValueService.save(keyvalue);

        return keyvalue;

    }
    
    /*
     * Update request
     * 
     * http://127.0.0.1:8080/api/keyvalues/  
     * */
    @PutMapping("/keyvalues")
    public KeyValue updateKeyValue(@RequestBody KeyValue keyvalue) {
    	if (keyvalue == null || keyvalue.getId() == null) {
            throw new KeyValueInvalidRequestException("KeyValue or ID must not be null");
        }
    	
        KeyValue keyValueFound = keyValueService.findById(keyvalue.getId());
        
        if (keyValueFound == null) {
            throw new KeyValueNotFoundException(keyvalue.getId());
        }
        
        keyValueService.save(keyvalue);
        
        return keyvalue;
    }

    /*
     * Delete register request
     * 
     * http://127.0.0.1:8080/api/keyvalues/{id}  
     * */
    @DeleteMapping("keyvalues/{id}")
    public String deteteKeyValue(@PathVariable long id) {

        KeyValue keyvalue = keyValueService.findById(id);

        if(keyvalue == null) {
            throw new KeyValueNotFoundException(id);
        }

        keyValueService.deleteById(id);

        return "Deleted keyvalue id - "+id;
    }

}
