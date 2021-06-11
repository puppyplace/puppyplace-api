package br.com.puppyplace.core.modules.product.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Specification {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}