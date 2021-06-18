package br.com.puppyplace.core.modules.product.converters;

import br.com.puppyplace.core.commons.exceptions.ResourceNotFoundException;
import br.com.puppyplace.core.entities.Category;
import br.com.puppyplace.core.modules.variant.dto.VariantDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Converter
public class JSONArrayConverter implements AttributeConverter<List<?>, String> {

    @Override
    public String convertToDatabaseColumn(List<?> jsonData) {
        String json;
        ObjectMapper objectMapper = new ObjectMapper();
        try{

            json = jsonData.toString();

            List<?> l = jsonData
                    .stream()
                    .map(item -> {
                        try {
                            return   objectMapper.writeValueAsString(item);
                        } catch (JsonProcessingException e) {
                            return e;
                        }
                    }).collect(Collectors.toList());

            json = l.toString();
        }
        catch (NullPointerException ex)
        {
            //extend error handling here if you want
            json = "";
        }
        return json;
    }

    @Override
    public List<?> convertToEntityAttribute(String jsonDataAsJson) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(jsonDataAsJson, new TypeReference<List>(){});
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}