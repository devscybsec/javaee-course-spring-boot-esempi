package it.cybsec.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.cybsec.models.Corso;
import it.cybsec.models.Studente;
import it.cybsec.spring.exceptions.BadRequestException;
import it.cybsec.spring.exceptions.NotFoundException;
import it.cybsec.spring.repository.StudenteRepository;

@Service
public class StudenteService {

	@Autowired
	private StudenteRepository repository;
	
	public List<Studente> findAll(List<Specification<Studente>> specList) {
		if (specList != null && specList.size() > 0) {
			Specification<Studente> where = Specification.where(specList.get(0));
			for (Specification<Studente> specification: specList.subList(1, specList.size()))
				where = where.and(specification);
			return repository.findAll(where);
		} else
			return repository.findAll();
	}
	
	public List<Studente> smartFind(String nome, String cognome, LocalDate dataNascita, Integer anno,
			String dataNascitaMethod, List<Corso> corsi, String nomeCorso) throws BadRequestException {
		List<Specification<Studente>> specList = new ArrayList<Specification<Studente>>();
		
		if (nome != null) 
			specList.add(StudenteRepository.nomeContaining(nome));
		if (cognome != null) 
			specList.add(StudenteRepository.cognomeContaining(cognome));
		if (dataNascita != null || anno != null) {
			if (dataNascita != null) {
				if (dataNascitaMethod.compareTo("less") == 0)
					specList.add(StudenteRepository.dataNascitaLessThanEqual(dataNascita));
				else if (dataNascitaMethod.compareTo("greater") == 0)
					specList.add(StudenteRepository.dataNascitaGreaterThan(dataNascita));
				else
					throw new BadRequestException(String.format("Metodo %1$s per il controllo della data inesistente: usare less o greater!", dataNascitaMethod));
			} else {
				if (dataNascitaMethod.compareTo("less") == 0)
					specList.add(StudenteRepository.annoLessThanEqual(anno));
				else if (dataNascitaMethod.compareTo("greater") == 0)
					specList.add(StudenteRepository.annoGreaterThan(anno));
				else
					throw new BadRequestException(String.format("Metodo %1$s per il controllo dell'anno inesistente: usare less o greater!", dataNascitaMethod));
			}
		}
		
		if (corsi != null || nomeCorso != null) {
			if (corsi != null)
				specList.add(StudenteRepository.corsoAllIn(corsi));
			else
				specList.add(StudenteRepository.corsoNomeContaining(nomeCorso));
		}
		
		return findAll(specList);

	}
	
//	private Specification<Studente> specCorsiAllIn(List<Corso> corsi) {
//	Specification<Studente> spec = Specification.where(StudenteRepository.corsoAllIn(corsi.get(0)));
//	corsi.subList(1, corsi.size()).forEach((corso) -> spec.and(StudenteRepository.corsoAllIn(corso)));
//	return spec;
//}
//
//private Specification<Studente> specNomeContainingAndCognomeContainingAndDataNascitaLessThanEqualAndCorsiAllIn(String nome, String cognome, LocalDate dataNascita, List<Corso> corsi) {
//	Specification<Studente> spec = Specification.
//			where(StudenteRepository.nomeContaining(nome)).
//			and(StudenteRepository.cognomeContaining(cognome)).
//			and(StudenteRepository.dataNascitaLessThanEqual(dataNascita));
//	corsi.forEach((corso) -> spec.and(StudenteRepository.corsoAllIn(corso)));
//	return spec;
//}
//
//private Specification<Studente> specNomeContainingAndCognomeContainingAndDataNascitaGreaterThanAndCorsiAllIn(String nome, String cognome, LocalDate dataNascita, List<Corso> corsi) {
//	Specification<Studente> spec = Specification.
//			where(StudenteRepository.nomeContaining(nome)).
//			and(StudenteRepository.cognomeContaining(cognome)).
//			and(StudenteRepository.dataNascitaGreaterThan(dataNascita));
//	corsi.forEach((corso) -> spec.and(StudenteRepository.corsoAllIn(corso)));
//	return spec;
//}
//	
//	public List<Studente> find(String nome, String cognome, LocalDate dataNascita,
//			String dataNascitaMethod, List<Corso> corsi, String nomeCorso) throws BadRequestException {
//		if (nome != null && cognome != null && dataNascita != null && (corsi != null || nomeCorso != null)) {
//			if (dataNascitaMethod.compareTo("less") == 0) {
//				if (corsi != null)
//					return repository.findAll(specNomeContainingAndCognomeContainingAndDataNascitaLessThanEqualAndCorsiAllIn(nome,cognome,dataNascita,corsi));
//				else if (nomeCorso != null)
//					return repository.findByNomeContainingAndCognomeContainingAndDataNascitaLessThanEqualAndCorsi_NomeContaining(nome,cognome,dataNascita,nomeCorso);
//				else
//					return repository.findByNomeContainingAndCognomeContainingAndDataNascitaLessThanEqual(nome,cognome,dataNascita);
//			} else if (dataNascitaMethod.compareTo("greater") == 0) {
//				if (corsi != null)
//					return repository.findAll(specNomeContainingAndCognomeContainingAndDataNascitaGreaterThanAndCorsiAllIn(nome,cognome,dataNascita,corsi));
//				else if (nomeCorso != null)
//					return repository.findByNomeContainingAndCognomeContainingAndDataNascitaGreaterThanAndCorsi_NomeContaining(nome,cognome,dataNascita,nomeCorso);
//				else
//					return repository.findByNomeContainingAndCognomeContainingAndDataNascitaGreaterThan(nome,cognome,dataNascita);
//			} else {
//				throw new BadRequestException(String.format("Metodo %1$s per il controllo della data inesistente: usare less o greater!", dataNascitaMethod));
//			}
//		} else if (nome != null && cognome != null && dataNascita != null) {
//			if (dataNascitaMethod.compareTo("less") == 0)
//				return repository.findByNomeContainingAndCognomeContainingAndDataNascitaLessThanEqual(nome,cognome,dataNascita);
//			else if (dataNascitaMethod.compareTo("greater") == 0)
//				return repository.findByNomeContainingAndCognomeContainingAndDataNascitaGreaterThan(nome,cognome,dataNascita);
//			else 
//				throw new BadRequestException(String.format("Metodo %1$s per il controllo della data inesistente: usare less o greater!", dataNascitaMethod));
//		} else if (nome != null && cognome != null) {
//			return repository.findByNomeContainingAndCognomeContaining(nome, cognome);
//		} else if (dataNascita != null) {
//			if (dataNascitaMethod.compareTo("less") == 0)
//				return repository.findByDataNascitaLessThanEqual(dataNascita);
//			else if (dataNascitaMethod.compareTo("greater") == 0)
//				return repository.findByDataNascitaGreaterThan(dataNascita);
//			 else
//				 throw new BadRequestException(String.format("Metodo %1$s per il controllo della data inesistente: usare less o greater!", dataNascitaMethod));
//		} else if (corsi != null || nomeCorso != null) {
//			if (corsi != null) {
//				return repository.findAll(specCorsiAllIn(corsi));
//			} else
//				return repository.findByCorsi_NomeContaining(nomeCorso);
//		} else if (nome != null) {
//			return repository.findByNomeContaining(nome);
//		} else if (cognome != null) {
//			return repository.findByCognomeContaining(cognome);
//		} else
//			return repository.findAll();		
//	}
	
	public Studente findById(Integer id) {
		existsById(id);
		return repository.findById(id).get();
	}
	
	public void existsById(Integer id) throws NotFoundException {
		if (!repository.existsById(id))
			throw new NotFoundException(String.format("Id %1$d inesistente!", id));
	}
	
	public Studente insert(Studente studente) throws BadRequestException, NotFoundException {
		studente.setId(null);
		try {
			return repository.save(studente);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Studente updateNotNullValue(Studente studente, Integer id) throws BadRequestException, NotFoundException {
		Studente studenteDB = repository.findById(id).get();
		
		if (studente.getNome() != null)
			studenteDB.setNome(studente.getNome());
		if (studente.getCognome() != null)
			studenteDB.setCognome(studente.getCognome());
		if (studente.getDataNascita() != null)
			studenteDB.setDataNascita(studente.getDataNascita());
		if (studente.getCorsi() != null)
			studenteDB.setCorsi(studente.getCorsi());
		
		try {
			return repository.save(studenteDB);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Studente update(Studente studente, Integer id) throws BadRequestException, NotFoundException {
		studente.setId(id);
		try {
			return repository.save(studente);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Studente delete(Integer id) throws BadRequestException, NotFoundException {
		Studente studente = repository.findById(id).get();
		try {
			repository.delete(studente);
			return studente;
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Studente aggiungiCorso(Integer id, Corso corso) throws BadRequestException, NotFoundException {
		Studente studente = repository.findById(id).get();
		
		try {
			for (int i = 0; i < studente.getCorsi().size(); i++)
				if (studente.getCorsi().get(i).getId() == corso.getId())
					return studente;
			studente.getCorsi().add(corso);
			return repository.save(studente);
				
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Studente rimuoviCorso(Integer id, Corso corso) throws BadRequestException, NotFoundException {
		Studente studente = repository.findById(id).get();
		try {
			for (int i = 0; i < studente.getCorsi().size(); i++)
				if (studente.getCorsi().get(i).getId() == corso.getId()) {
					studente.getCorsi().remove(i);
					return repository.save(studente);
				}
			return studente;
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
}
