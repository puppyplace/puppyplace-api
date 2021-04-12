package br.com.puppyplace.core.modules.category.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import br.com.puppyplace.core.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 6255095923967551626L;

    private UUID id;

    @NotEmpty
    private String name;

    public CategoryDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}
