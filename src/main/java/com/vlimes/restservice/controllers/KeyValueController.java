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
@RequestMapping("/api") //esta sera la raiz de la url, es decir http://127.0.0.1:8080/api/
public class KeyValueController {

	private final KeyValueService keyValueService;

	KeyValueController(KeyValueService service) {
    	this.keyValueService = service;
    }

    /*Este método se hará cuando por una petición GET (como indica la anotación) se llame a la url 
    http://127.0.0.1:8080/api/keyvalues*/
    @GetMapping("/keyvalues")
    public Set<KeyValue> findAll(){
        //retornará todos los usuarios
        return keyValueService.findAll();
    }

    /*Este método se hará cuando por una petición GET (como indica la anotación) se llame a la url + el id de un usuario
    http://127.0.0.1:8080/api/keyvalues/1*/
    @GetMapping("/keyvalues/{id}")
    public KeyValue getKeyValue(@PathVariable long id){
        KeyValue keyvalue = keyValueService.findById(id);

        if(keyvalue == null) {
            throw new KeyValueNotFoundException(id);
        }
        //retornará al usuario con id pasado en la url
        return keyvalue;
    }

    /*Este método se hará cuando por una petición POST (como indica la anotación) se llame a la url
    http://127.0.0.1:8080/api/keyvalues/  */
    @PostMapping("/keyvalues")
    public KeyValue addKeyValue(@RequestBody KeyValue keyvalue) {
        //keyvalue.setId(0L);

        //Este metodo guardará al usuario enviado
        keyValueService.save(keyvalue);

        return keyvalue;

    }
    /*Este método se hará cuando por una petición PUT (como indica la anotación) se llame a la url
    http://127.0.0.1:8080/api/keyvalues/  */
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

        //este metodo actualizará al usuario enviado

        return keyvalue;
    }

    /*Este método se hará cuando por una petición DELETE (como indica la anotación) se llame a la url + id del usuario
    http://127.0.0.1:8080/api/keyvalues/1  */
    @DeleteMapping("keyvalues/{id}")
    public String deteteKeyValue(@PathVariable long id) {

        KeyValue keyvalue = keyValueService.findById(id);

        if(keyvalue == null) {
            throw new KeyValueNotFoundException(id);
        }

        keyValueService.deleteById(id);

        //Esto método, recibira el id de un usuario por URL y se borrará de la bd.
        return "Deleted keyvalue id - "+id;
    }

}
