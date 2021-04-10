package br.com.puppyplace.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractEntity {

	@CreatedDate
	@Column(name = "created_at")
	protected Date createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	protected Date updatedAt;
	
}
