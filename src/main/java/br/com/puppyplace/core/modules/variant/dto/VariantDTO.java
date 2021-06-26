package br.com.puppyplace.core.modules.variant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariantDTO implements Serializable {
    private static final long serialVersionUID = 291678832931506131L;

    private UUID id;

    @JsonProperty("unit")
    private String unit;

    @Positive
    @JsonProperty("stock")
    private int stock;

    @Positive
    @JsonProperty("price")
    @DecimalMin(value = "0.0", inclusive = false, message = "Field price is mandatory")
    private Float price;

    @Positive
    @JsonProperty("percent_promotional")
    @DecimalMin(value = "0.0", inclusive = false, message = "Field percent_promotional is mandatory")
    private Float percentPromotional;

    @Positive
    @JsonProperty("price_promotional")
    @DecimalMin(value = "0.0", inclusive = false, message = "Field price_promotional is mandatory")
    private Float pricePromotional;

    @JsonProperty("isbn_code")
    private String isbnCode;
}

