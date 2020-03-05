package it.cybsec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import it.cybsec.models.Professore;
import it.cybsec.spring.exceptions.BadRequestException;
import it.cybsec.spring.exceptions.NotFoundException;
import it.cybsec.spring.repository.ProfessoreRepository;

@Service
public class ProfessoreService {

	@Autowired
	private ProfessoreRepository repository;
	
	public List<Professore> findAll() {
			return repository.findAll();
	}
	
	public Professore findById(Integer id) {
		existsById(id);
		return repository.findById(id).get();
	}
	
	public void existsById(Integer id) throws NotFoundException {
		if (!repository.existsById(id))
			throw new NotFoundException(String.format("Id %1$d inesistente!", id));
	}
	
	public Professore insert(Professore professore) throws BadRequestException, NotFoundException {
		professore.setId(null);
		try {
			return repository.save(professore);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Professore updateNotNullValue(Professore professore, Integer id) throws BadRequestException, NotFoundException {
		Professore professoreDB = repository.findById(id).get();
		
		if (professore.getNome() != null)
			professoreDB.setNome(professore.getNome());
		if (professore.getCognome() != null)
			professoreDB.setCognome(professore.getCognome());
		if (professore.getDataNascita() != null)
			professoreDB.setDataNascita(professore.getDataNascita());
		if (professore.getCorsi() != null)
			professoreDB.setCorsi(professore.getCorsi());
		
		try {
			return repository.save(professoreDB);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Professore update(Professore professore, Integer id) throws BadRequestException, NotFoundException {
		professore.setId(id);
		try {
			return repository.save(professore);
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	public Professore delete(Integer id) throws BadRequestException, NotFoundException {
		Professore professore = repository.findById(id).get();
		try {
			repository.delete(professore);
			return professore;
		} catch (DataIntegrityViolationException e) {
			throw new BadRequestException("Errore SQL: " + e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
}
