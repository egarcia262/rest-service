package com.vlimes.restservice.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KeyValueTest {

	KeyValue keyValue;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		keyValue =  new KeyValue();
	}

	@Test
	public void testGetValue() throws Exception {
		String value = "Test value";

		keyValue.setValue(value);

        assertTrue(keyValue.getValue().equalsIgnoreCase(value));
	}

	@Test
	public void testGetId() throws Exception {
		Long id = 4L;

		keyValue.setId(id);

        assertEquals(id, keyValue.getId());
	}

}
