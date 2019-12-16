package it.cybsec.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cybsec.models.Professore;

@Repository
public interface ProfessoreRepository extends JpaRepository<Professore, Integer> {

}
