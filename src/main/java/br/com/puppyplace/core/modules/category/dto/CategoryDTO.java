package br.com.puppyplace.core.modules.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 6255095923967551626L;

    private UUID id;

    @NotEmpty(message = "Name couldn't be empty")
    private String name;    
}
