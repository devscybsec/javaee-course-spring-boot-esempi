package it.cybsec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import it.cybsec.models.Corso;
import it.cybsec.spring.exceptions.BadRequestException;
import it.cybsec.spring.exceptions.NotFoundException;
import it.cybsec.spring.repository.CorsoRepository;

@Service
public class CorsoService {

	@Autowired
	private CorsoRepository repository;
	
	public List<Corso> findAll() {
			return repository.findAll();
	}
	
	public Corso findById(Integer id) {
		existsById(id);
		return repository.findById(id).get();
	}
	
	public void existsById(Integer id) throws NotFoundException {
		if (!repository.existsById(id))
			throw new NotFoundException(String.format("Id %1$d inesistente!", id));
	}
	
	public Corso insert(Corso corso) throws BadRequestException, NotFoundException {
		corso.setId(null);
		try {
			return repository.save(corso);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Corso updateNotNullValue(Corso corso, Integer id) throws BadRequestException, NotFoundException {
		Corso corsoDB = repository.findById(id).get();
		
		if (corso.getNome() != null)
			corsoDB.setNome(corso.getNome());
		if (corso.getDescrizione() != null)
			corsoDB.setDescrizione(corso.getDescrizione());
		
		try {
			return repository.save(corsoDB);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Corso update(Corso corso, Integer id) throws BadRequestException, NotFoundException {
		corso.setId(id);
		try {
			return repository.save(corso);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Corso delete(Integer id) throws BadRequestException, NotFoundException {
		Corso corso = repository.findById(id).get();
		try {
			repository.delete(corso);
			return corso;
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
}
