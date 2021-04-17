package br.com.puppyplace.core.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Lead extends AbstractEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column
    private String name;
    
    @Column
    private String email;
}
