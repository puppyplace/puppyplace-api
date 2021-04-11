package br.com.puppyplace.core.modules.product.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = -753845326956722798L;

	private UUID id;

    @NotEmpty
    private String description;

    @NotEmpty
    private String title;

    @NotNull
    @DecimalMin("0.01")
    private Float price;

    @DecimalMin("0.01")
    private Float promotional_price;

    @NotNull
    private Integer stock;

    private String avatar_url;

    @NotNull
    private UUID id_category;

    @NotNull
    private UUID id_partner;

    @NotEmpty
    private String specifications;

    @NotEmpty
    private String unit;

    @NotEmpty
    private String product_code;
    
    private String isbn_code;	
}
