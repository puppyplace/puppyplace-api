package br.com.puppyplace.core.modules.product.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("promotional_price")
    private Float promotionalPrice;

    @NotNull
    private Integer stock;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @NotNull
    @JsonProperty("id_categories")
    private List<UUID> idCategories;

    @NotEmpty
    private String specifications;

    @NotEmpty
    private String unit;

    @NotEmpty
    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("isbn_code")
    private String isbnCode;
    
}
