package br.com.puppyplace.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Address extends AbstractEntity{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Customer customer;

    @Column
    private String street;

    @Column
    private String number;

    @Column
    private String zipcode;

    @Column
    @Enumerated(EnumType.STRING)
    private Enum state;
}
