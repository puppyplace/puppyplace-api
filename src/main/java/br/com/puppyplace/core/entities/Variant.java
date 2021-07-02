package br.com.puppyplace.core.entities;

import lombok.*;
import javax.persistence.*;
import java.util.UUID;

@Entity(name = "product_variant")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variant extends AbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private int stock;

    @Column(name="percent_promotional")
    private Float percentPromotional;

    @Column(name = "isbn_code", nullable = false)
    private String isbnCode;

    public Float getPromotionalPrice() {
        return (percentPromotional != null && price != null) ? price - (percentPromotional * 100 / price) : 0;
    }
}
