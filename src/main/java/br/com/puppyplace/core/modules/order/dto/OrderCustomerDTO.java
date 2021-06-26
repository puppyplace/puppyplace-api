package br.com.puppyplace.core.modules.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderCustomerDTO implements Serializable {

    private static final long serialVersionUID = 6234552399505688251L;

    private UUID customerId;

    private String customerName;

    private String customerDocument;

    private String customerEmail;

    private String customerCellphone;

    private LocalDate customerBirthdate;

}
