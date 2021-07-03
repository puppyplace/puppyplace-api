package br.com.puppyplace.core.entities;

import br.com.puppyplace.core.commons.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address extends AbstractEntity{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Column
    private String street;

    @Column
    private String number;

    @Column
    private String complement;

    @Column
    private String zipcode;

    @Column
    private String city;

    @Column
    private String district;

    @Column
    @Enumerated(EnumType.STRING)
    private StateEnum state;

    @ColumnDefault("false")
    @Column(name = "is_main")
    private boolean isMain;
}
