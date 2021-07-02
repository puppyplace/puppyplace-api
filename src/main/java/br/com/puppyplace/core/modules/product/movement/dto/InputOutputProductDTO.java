package br.com.puppyplace.core.modules.product.movement.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.puppyplace.core.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputOutputProductDTO {
	private Product product;
	private Double operationValue;
	private LocalDateTime operationDate;
	private UUID idAssociateEntity;		
}
