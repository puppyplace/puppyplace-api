package br.com.puppyplace.core.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@CreatedDate
	@Column(name = "created_at")
	protected Date createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	protected Date updatedAt;
	
}
