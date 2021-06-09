package br.com.puppyplace.core.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "product_moviment")
@Data
@Builder
public class Moviment {

	@Id
    public String id;	
	private String productId;	
	private String operationValue;	
	private String operationDate;	
	private String idAssociateEntity;	
	
}
