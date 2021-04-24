package br.com.puppyplace.core.entities;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Customer extends AbstractEntity{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;

    @Column
    private String cellphone;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String password; // todo: criptografar

    @ColumnDefault("true")
    @Column(name = "active")
    private boolean active;


}
