package br.com.puppyplace.core.modules.product.movement;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.puppyplace.core.entities.Moviment;

public interface MovimentRepository extends MongoRepository<Moviment, String> {

}
