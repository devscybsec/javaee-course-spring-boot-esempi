package it.cybsec.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cybsec.models.Corso;

@Repository
public interface CorsoRepository extends JpaRepository<Corso, Integer> {

}
