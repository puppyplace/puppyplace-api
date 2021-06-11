package br.com.puppyplace.core.modules.product.service.impl;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.entities.Product;
import br.com.puppyplace.core.modules.product.dto.ProductDTO;
import br.com.puppyplace.core.modules.product.service.BuildProductFromDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildProductFromDTOImpl implements BuildProductFromDTO{

    private final ModelMapper mapper;

    public Product execute(ProductDTO productDTO) {
        log.info(">>> Building product entity from product DTO");

        var product = mapper.map(productDTO, Product.class);

        product.setDetails(new JSONArray(productDTO.getDetails().stream().map(detail -> {
            JSONObject json = new JSONObject();
            try {
                json.put("description", detail.getDescription());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }).collect((Collectors.toList()))));

        product.setSpecifications(new JSONArray(productDTO.getSpecifications().stream().map(specification -> {
            JSONObject json = new JSONObject();
            try {
                json.put("title", specification.getTitle());
                json.put("description", specification.getDescription());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }).collect((Collectors.toList()))));


        product.setVariant(new JSONArray(productDTO.getVariant().stream().map(variant -> {
            JSONObject json = new JSONObject();
            try {
                json.put("percent_promotional", variant.getPercentPromotional());
                json.put("unit", variant.getUnit());
                json.put("isbn_code", variant.getIsbnCode());
                json.put("price", variant.getPrice());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }).collect((Collectors.toList()))));

        product.setCategories(
                productDTO.getIdCategories().stream()
                .map(uuid -> new Category(uuid))
                .collect(Collectors.toList()));
        
        log.info(">>> Done");
        
        return product;
    }        
}
