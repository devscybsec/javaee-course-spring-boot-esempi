package it.cybsec.spring.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.cybsec.models.Studente;
import it.cybsec.models.Corso;
import it.cybsec.service.CorsoService;
import it.cybsec.spring.exceptions.BadRequestException;
import it.cybsec.spring.exceptions.NotFoundException;

@RestController
@RequestMapping("/corso")
public class CorsoController {

	@Autowired
	private CorsoService service;

	@GetMapping
	public List<Corso> findAll() throws ParseException {
		return service.findAll();
	}
	
	@PostMapping
	public Corso insertProfessore(@RequestBody Corso corso) throws BadRequestException {
		return service.insert(corso);
	}
	
	@GetMapping("/{id}")
	public Corso findProfessore(@PathVariable Integer id) {
		return service.findById(id);
	}
	
	@GetMapping("/{id}/studenti")
	public List<Studente> findStudenteCorsi(@PathVariable Integer id) {
		return service.findById(id).getStudenti();
	}
	
	@GetMapping("/{id}/studenti_n")
	public Integer findStudenteCorsiCount(@PathVariable Integer id) {
		return service.findById(id).getStudenti().size();
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST, RequestMethod.PUT })
	public Corso updateProfessore(@RequestBody Corso corso, @PathVariable Integer id) throws NotFoundException, BadRequestException {
		service.existsById(id);
		return service.update(corso, id);
	}
	
	@PatchMapping("/{id}")
	public Corso updateNonNullValueProfessore(@RequestBody Corso corso, @PathVariable Integer id) throws NotFoundException, BadRequestException {
		service.existsById(id);
		return service.updateNotNullValue(corso, id);
	}

	@DeleteMapping("/{id}")
	public Corso deleteProfessore(@PathVariable Integer id) throws NotFoundException {
		service.existsById(id);
		return service.delete(id);
	}

}
