package br.com.puppyplace.core.modules.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SpecificationDTO implements Serializable {

    private static final long serialVersionUID = -753845326956722798L;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

}