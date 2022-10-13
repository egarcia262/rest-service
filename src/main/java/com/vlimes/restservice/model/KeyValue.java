package com.vlimes.restservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "keyvalues")
public class KeyValue extends BaseEntity {

	@Column(name = "valor")
	private String value;

	public KeyValue() {
		super();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	

}
