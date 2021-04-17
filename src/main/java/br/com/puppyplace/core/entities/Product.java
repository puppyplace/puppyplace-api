package br.com.puppyplace.core.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "product")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Where(clause="deleted=false")
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

    @Column(name = "promotional_price")
    private Float promotionalPrice;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name="category_product")
    private List<Category> categories;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_partner")    
    private Partner partner;

    @Column
    private String specifications;

    @Column
    private String unit;

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
