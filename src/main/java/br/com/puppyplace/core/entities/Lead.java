package br.com.puppyplace.core.entities;

import br.com.puppyplace.core.commons.enums.Interest;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
public class Lead {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String cellphone;

    @Enumerated(EnumType.STRING)
    private Interest interest = Interest.PRODUCT;
}
