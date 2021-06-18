package br.com.puppyplace.core.modules.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class DetailDTO implements Serializable {

    private static final long serialVersionUID = -753845326956722798L;

    @JsonProperty("description")
    private String description;

}