package br.com.puppyplace.core.modules.product.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.puppyplace.core.entities.Variant;
import br.com.puppyplace.core.modules.variant.dto.VariantDTO;
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

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @NotNull
    @JsonProperty("id_categories")
    private List<UUID> idCategories;

    @NotEmpty
    private List<SpecificationDTO> specifications;

    private List<VariantDTO> variants;

    private List<DetailDTO> details;

    @JsonProperty("product_code")
    private String productCode;

}
