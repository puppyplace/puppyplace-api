package br.com.puppyplace.core.modules.product.movement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.puppyplace.core.entities.Moviment;
import br.com.puppyplace.core.modules.product.movement.MovimentRepository;
import br.com.puppyplace.core.modules.product.movement.MovimentService;
import br.com.puppyplace.core.modules.product.movement.dto.InputOutputProductDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MovimentServiceImpl implements MovimentService {
	
	@Autowired
	private MovimentRepository movimentRepository;	
	
	public void input(InputOutputProductDTO inputOutputProductDTO) {
		
		log.info(">>> An new input of product {} with {} as value received", inputOutputProductDTO.getProduct(), inputOutputProductDTO.getOperationValue());
		
		var moviment = Moviment.builder()
						.idAssociateEntity(inputOutputProductDTO.getIdAssociateEntity().toString())
						.operationDate(inputOutputProductDTO.getOperationDate().toString())
						.operationValue(inputOutputProductDTO.getOperationValue().toString())
						.productId(inputOutputProductDTO.getProduct().getId().toString())
						.build();
		
		movimentRepository.save(moviment);
		
		log.info(">>> Moviment persisted");
		
	}

	public void output(InputOutputProductDTO inputOutputProductDTO) {
		
		log.info(">>> An new output of product {} with {} as value received", inputOutputProductDTO.getProduct(), inputOutputProductDTO.getOperationValue());
		
		var moviment = Moviment.builder()
						.idAssociateEntity(inputOutputProductDTO.getIdAssociateEntity().toString())
						.operationDate(inputOutputProductDTO.getOperationDate().toString())
						.operationValue(String.valueOf(inputOutputProductDTO.getOperationValue() * (-1)))
						.productId(inputOutputProductDTO.getProduct().getId().toString())
						.build();

		movimentRepository.save(moviment);
		
		log.info(">>> Moviment persisted");
		
	}
}
