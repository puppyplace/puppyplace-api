package br.com.puppyplace.core.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import br.com.puppyplace.core.modules.product.converters.JSONArrayConverter;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.json.JSONArray;

@Entity(name = "product")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Where(clause="deleted=false")
@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
public class Product extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Float price;

    @Column(name = "promotional_percent")
    private Float promotionalPercent;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name="category_product")
    private List<Category> categories;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_partner")    
    private Partner partner;

    @Column(columnDefinition = "nvarchar")
    @Convert(converter= JSONArrayConverter.class)
    private JSONArray specifications;

    @Column(columnDefinition = "nvarchar")
    @Convert(converter= JSONArrayConverter.class)
    private JSONArray details;

    @Column(columnDefinition = "nvarchar")
    @Convert(converter= JSONArrayConverter.class)
    private JSONArray variant;

    @Column(name = "product_code", unique = true)
    private String productCode;

    @Column(name = "isbn_code")
    private String isbnCode;
    
    @ColumnDefault("false")
    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "deleted_at")
	protected Date deletedAt;
}
