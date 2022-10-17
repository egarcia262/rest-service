package com.vlimes.restservice.bootstrap;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.vlimes.restservice.model.KeyValue;
import com.vlimes.restservice.services.KeyValueService;

/**
 * Test data load.
 * 
 * @author 48384541q
 *
 */
@Component
public class DataLoader implements CommandLineRunner {

	private final KeyValueService keyValueService;
	
	
	public DataLoader(KeyValueService keyValueService) {
		super();
		this.keyValueService = keyValueService;
	}


	@Override
	public void run(String... args) throws Exception {
		int count = keyValueService.findAll().size();

        if (count == 0 ){
            loadData();
        }

	}
	
	// Set data for test
	private void loadData() {
		
		ArrayList<String> values = new ArrayList<String>( 
		            Arrays.asList("One", "Two", "Three", "Four", "Five")); 
		
	    for(String value : values) {
			KeyValue keyValue = new KeyValue();
	    	keyValue.setValue(value);
			
			keyValueService.save(keyValue);
		}
	}

}
