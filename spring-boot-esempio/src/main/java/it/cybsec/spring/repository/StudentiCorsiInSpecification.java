package it.cybsec.spring.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import it.cybsec.models.Corso;
import it.cybsec.models.Studente;

@SuppressWarnings("serial")
public class StudentiCorsiInSpecification implements Specification<Studente> {

	private List<Corso> corsi;
	
	public StudentiCorsiInSpecification(List<Corso> corsi) {
		this.corsi = corsi;
	}
	
	@Override
	public Predicate toPredicate(Root<Studente> studente, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return studente.get("corsi").in(corsi);
	}

}
