package it.cybsec.spring.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.criteria.Predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.cybsec.models.Corso;
import it.cybsec.models.Studente;
import it.cybsec.spring.specifications.StudentiCorsiInSpecification;

@Repository
public interface StudenteRepository extends JpaRepository<Studente, Integer>, JpaSpecificationExecutor<Studente> {

	List<Studente> findByCorsi_NomeContaining(String nomeCorso);
	
	List<Studente> findByNomeContaining(String nome);
	List<Studente> findByCognomeContaining(String cognome);
	
	List<Studente> findByDataNascitaLessThanEqual(LocalDate dataNascita);
	List<Studente> findByDataNascitaGreaterThan(LocalDate dataNascita);
	
	List<Studente> findByNomeContainingAndCognomeContaining(String nome, String cognome);
	
	List<Studente> findByNomeContainingAndCognomeContainingAndDataNascitaLessThanEqual(String nome, String cognome, LocalDate dataNascita);
	List<Studente> findByNomeContainingAndCognomeContainingAndDataNascitaGreaterThan(String nome, String cognome, LocalDate dataNascita);
	
	List<Studente> findByNomeContainingAndCognomeContainingAndDataNascitaLessThanEqualAndCorsi_NomeContaining(String nome, String cognome, LocalDate dataNascita, String nomeCorsi);
	List<Studente> findByNomeContainingAndCognomeContainingAndDataNascitaGreaterThanAndCorsi_NomeContaining(String nome, String cognome, LocalDate dataNascita, String nomeCorsi);	

	static Specification<Studente> corsoIn(List<Corso> corsi) {
		return (studente, cq, cb) -> studente.get("corsi").in(corsi); 
	}
	
//	Implementazione con interfaccia anonime
	
	@SuppressWarnings("serial")
	static Specification<Studente> corsoInInterface(List<Corso> corsi) {
		return new Specification<Studente>() {

			@Override
			public Predicate toPredicate(Root<Studente> studente, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return studente.get("corsi").in(corsi);
			}
			
		};
	}
	
//	Implementazione con classe specifica che implementa Specification
	
	static Specification<Studente> corsoInClass(List<Corso> corsi) {
		return new StudentiCorsiInSpecification(corsi);
	}
	
	static Specification<Studente> corsoNomeContaining(String nomeCorso) {
		return (studente, cq, cb) -> cb.like(studente.join("corsi").get("nome"), "%" + nomeCorso + "%"); 
	}
	
	static Specification<Studente> corsoAllIn(List<Corso> corsi) {
		return (studente, cq, cb) -> {
			Predicate pred = cb.isMember(corsi.get(0),studente.get("corsi"));
			for (Corso corso: corsi.subList(1, corsi.size()))
				pred = cb.and(pred, cb.isMember(corso, studente.get("corsi")));
			return pred;				
		};
	}
	
	static Specification<Studente> nomeContaining(String nome) {
		return (studente, cq, cb) -> cb.like(studente.get("nome"), "%" + nome + "%"); 
	}
	
	static Specification<Studente> cognomeContaining(String cognome) {
		return (studente, cq, cb) -> cb.like(studente.get("cognome"), "%" + cognome + "%"); 
	}
	
	static Specification<Studente> annoLessThanEqual(Integer anno) {
		return (studente, cq, cb) -> cb.lessThanOrEqualTo(cb.function("year", Integer.class, studente.get("dataNascita")), anno); 
	}
	
	static Specification<Studente> annoGreaterThan(Integer anno) {
		return (studente, cq, cb) -> cb.greaterThan(cb.function("year", Integer.class, studente.get("dataNascita")), anno); 
	}
	
	static Specification<Studente> dataNascitaLessThanEqual(LocalDate dataNascita) {
		return (studente, cq, cb) -> cb.lessThanOrEqualTo(studente.get("dataNascita"), dataNascita); 
	}
	
	static Specification<Studente> dataNascitaGreaterThan(LocalDate dataNascita) {
		return (studente, cq, cb) -> cb.greaterThan(studente.get("dataNascita"), dataNascita); 
	}

}
