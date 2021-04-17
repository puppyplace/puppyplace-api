package br.com.puppyplace.core.modules.category.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 6255095923967551626L;

    private UUID id;

    @NotEmpty(message = "Name could'nt be empty")
    private String name;    
}
