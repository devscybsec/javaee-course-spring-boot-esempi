package it.cybsec.repository.rest;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.cybsec.model.Impiegato;

@RepositoryRestResource(collectionResourceRel = "impiegati", path = "impiegati")
public interface ImpiegatiRepository extends PagingAndSortingRepository<Impiegato, Integer> {

	List<Impiegato> findByNome(@Param("nome") String nome);
	
	List<Impiegato> findByCognome(@Param("cognome") String cognome);
	
	List<Impiegato> findBySettore(@Param("settore") String settore);
	
}
