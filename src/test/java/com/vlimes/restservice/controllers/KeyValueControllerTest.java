package com.vlimes.restservice.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlimes.restservice.exceptions.KeyValueInvalidRequestException;
import com.vlimes.restservice.exceptions.KeyValueNotFoundException;
import com.vlimes.restservice.model.KeyValue;
import com.vlimes.restservice.services.KeyValueService;


@WebMvcTest(value = KeyValueController.class)
@AutoConfigureMockMvc
class KeyValueControllerTest {
	
	KeyValueController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    ObjectMapper mapper;
	
	private KeyValue mockKeyValue1;
	private KeyValue mockKeyValue2;
	private KeyValue mockKeyValue3;
	
	@MockBean
	KeyValueService keyValueService;
	
	@BeforeEach
    void setUp() throws Exception {
		controller = new KeyValueController(keyValueService);
		
		mockKeyValue1 = new KeyValue();
		mockKeyValue1.setId(1L);
		mockKeyValue1.setValue("Test value 1");
		
		mockKeyValue2 = new KeyValue();
		mockKeyValue2.setId(2L);
		mockKeyValue2.setValue("Test value 2");
		
		mockKeyValue3 = new KeyValue();
		mockKeyValue3.setId(3L);
		mockKeyValue3.setValue("Test value 3");
	}
	
	@Test
	void testFindAll() throws Exception {
		Set<KeyValue> keyValues = new HashSet<>(Arrays.asList(mockKeyValue1, mockKeyValue2, mockKeyValue3));
	    
	    Mockito.when(keyValueService.findAll()).thenReturn(keyValues);
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/api/keyvalues")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	void testGetKeyValue() throws Exception {
		
		Mockito.when(keyValueService.findById(mockKeyValue1.getId())).thenReturn(mockKeyValue1);		
		
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/api/keyvalues/1")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.value", is("Test value 1")));
		
	}

	@Test
	void testAddKeyValue() throws Exception {
		KeyValue mockKeyValue = new KeyValue();
		mockKeyValue.setId(1L);
		mockKeyValue.setValue("Test value");
		
		Mockito.when(keyValueService.save(mockKeyValue)).thenReturn(mockKeyValue);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/keyvalues")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(mockKeyValue));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.value", is("Test value")));
	}

	@Test
	void testUpdateKeyValue() throws Exception {
		KeyValue mockKeyValueToUpdate = new KeyValue();
		mockKeyValueToUpdate.setId(1L);
		mockKeyValueToUpdate.setValue("Test value updated");

	    Mockito.when(keyValueService.findById(mockKeyValue1.getId())).thenReturn(mockKeyValue1);
	    Mockito.when(keyValueService.save(mockKeyValueToUpdate)).thenReturn(mockKeyValueToUpdate);

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/keyvalues")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(mockKeyValueToUpdate));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.value", is("Test value updated")));
	}
	
	@Test
	public void testUpdateKeyValue_nullId() throws Exception {
		KeyValue mockKeyValueToUpdate = new KeyValue();
		mockKeyValueToUpdate.setId(null);
		mockKeyValueToUpdate.setValue("Test value updated");

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/keyvalues")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(mockKeyValueToUpdate));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isBadRequest())
	            .andExpect(result ->
	                assertTrue(result.getResolvedException() instanceof KeyValueInvalidRequestException))
	            .andExpect(result ->
	            	assertEquals("KeyValue or ID must not be null", result.getResolvedException().getMessage()));
	    }

	@Test
	public void testUpdateKeyValue_recordNotFound() throws Exception {
		KeyValue mockKeyValueToUpdate = new KeyValue();
		mockKeyValueToUpdate.setId(9L);
		mockKeyValueToUpdate.setValue("Test value updated");

	    Mockito.when(keyValueService.findById(mockKeyValueToUpdate.getId())).thenReturn(null);

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/keyvalues")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(mockKeyValueToUpdate));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isNotFound())
	            .andExpect(result ->
	                assertTrue(result.getResolvedException() instanceof KeyValueNotFoundException))
	            .andExpect(result ->
	            	assertEquals("Could not find keyvalue " + mockKeyValueToUpdate.getId(), result.getResolvedException().getMessage()));
	}
	
	@Test
	public void testDeteteKeyValue() throws Exception {
		KeyValue mockKeyValueToDelete = new KeyValue();
		mockKeyValueToDelete.setId(2L);
		
		Mockito.when(keyValueService.findById(mockKeyValueToDelete.getId())).thenReturn(mockKeyValue2);

	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/api/keyvalues/2")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}

	@Test
	public void testDeteteKeyValue_notFound() throws Exception {
		KeyValue mockKeyValueToDelete = new KeyValue();
		mockKeyValueToDelete.setId(9L);
		
		Mockito.when(keyValueService.findById(mockKeyValueToDelete.getId())).thenReturn(null);

	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/api/keyvalues/9")
	            .contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().isNotFound())
	            .andExpect(result ->
                    assertTrue(result.getResolvedException() instanceof KeyValueNotFoundException))
	            .andExpect(result ->
	            	assertEquals("Could not find keyvalue " + mockKeyValueToDelete.getId(), result.getResolvedException().getMessage()));
	}

}
