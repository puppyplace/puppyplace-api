package br.com.puppyplace.core.modules.payment;

import java.util.UUID;

public interface PaymentService {
	public void executeOrder(UUID orderID);	
}
