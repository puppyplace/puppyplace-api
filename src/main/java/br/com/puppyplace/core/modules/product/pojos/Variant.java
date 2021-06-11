package br.com.puppyplace.core.modules.product.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Variant {
    @JsonProperty("unit")
    private String unit;

    @JsonProperty("isbn_code")
    private String isbn_code;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("percent_promotional")
    private Float percentPromotional;

    public String getUnit() {
        return unit;
    }

    public void setTUnit(String unit) {
        this.unit = unit;
    }

    public String getIsbnCode() {
        return isbn_code;
    }

    public void setIsbnCode(String isbn_code) {
        this.isbn_code = isbn_code;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPercentPromotional() {
        return percentPromotional;
    }

    public void setPercentPromotional(Float percentPromotional) {
        this.percentPromotional = percentPromotional;
    }
}