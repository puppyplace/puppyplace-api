package br.com.puppyplace.core.modules.product.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Detail {

    @JsonProperty("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}