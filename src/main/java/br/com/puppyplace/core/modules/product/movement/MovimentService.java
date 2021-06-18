package br.com.puppyplace.core.modules.product.movement;

import br.com.puppyplace.core.modules.product.movement.dto.InputOutputProductDTO;

public interface MovimentService {
	public void input(InputOutputProductDTO inputOutputProductDTO);
	public void output(InputOutputProductDTO inputOutputProductDTO);	
}
