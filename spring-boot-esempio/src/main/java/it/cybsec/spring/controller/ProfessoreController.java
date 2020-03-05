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

import it.cybsec.models.Corso;
import it.cybsec.models.Professore;
import it.cybsec.service.ProfessoreService;
import it.cybsec.spring.exceptions.BadRequestException;
import it.cybsec.spring.exceptions.NotFoundException;

@RestController
@RequestMapping("/professore")
public class ProfessoreController {

	@Autowired
	private ProfessoreService service;

	@GetMapping
	public List<Professore> findAll() throws ParseException {
		return service.findAll();
	}
	
	@PostMapping
	public Professore insertProfessore(@RequestBody Professore professore) throws BadRequestException {
		return service.insert(professore);
	}
	
	@GetMapping("/{id}")
	public Professore findProfessore(@PathVariable Integer id) {
		return service.findById(id);
	}
	
	@GetMapping("/{id}/corsi")
	public List<Corso> findStudenteCorsi(@PathVariable Integer id) {
		return service.findById(id).getCorsi();
	}
	
	@GetMapping("/{id}/corsi_n")
	public Integer findStudenteCorsiCount(@PathVariable Integer id) {
		return service.findById(id).getCorsi().size();
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST, RequestMethod.PUT })
	public Professore updateProfessore(@RequestBody Professore professore, @PathVariable Integer id) throws NotFoundException, BadRequestException {
		service.existsById(id);
		return service.update(professore, id);
	}
	
	@PatchMapping("/{id}")
	public Professore updateNonNullValueProfessore(@RequestBody Professore professore, @PathVariable Integer id) throws NotFoundException, BadRequestException {
		service.existsById(id);
		return service.updateNotNullValue(professore, id);
	}

	@DeleteMapping("/{id}")
	public Professore deleteProfessore(@PathVariable Integer id) throws NotFoundException {
		service.existsById(id);
		return service.delete(id);
	}

}
