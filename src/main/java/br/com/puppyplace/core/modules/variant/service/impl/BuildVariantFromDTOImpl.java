package br.com.puppyplace.core.modules.variant.service.impl;

import br.com.puppyplace.core.entities.Variant;
import br.com.puppyplace.core.modules.product.service.ProductService;
import br.com.puppyplace.core.modules.variant.dto.VariantDTO;
import br.com.puppyplace.core.modules.variant.service.BuildVariantFromDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class BuildVariantFromDTOImpl implements BuildVariantFromDTO {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductService productService;

    @Override
    public Variant execute(UUID productID, VariantDTO variantDTO) {
        var variant = mapper.map(variantDTO, Variant.class);
        var product = productService.findOne(productID);

        variant.setProduct(product);

        return variant;
    }
}
