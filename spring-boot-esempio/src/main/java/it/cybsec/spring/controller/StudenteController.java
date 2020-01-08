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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.cybsec.models.Corso;
import it.cybsec.models.Studente;
import it.cybsec.service.StudenteService;
import it.cybsec.spring.exceptions.BadRequestException;
import it.cybsec.spring.exceptions.NotFoundException;
import it.cybsec.utils.LocalDateFormatter;

@RestController
@RequestMapping("/studente")
public class StudenteController {

	@Autowired
	private StudenteService service;

	@GetMapping
	public List<Studente> findStudenti(
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cognome", required = false) String cognome,
			@RequestParam(value = "datanascita", required = false) String dataNascita,
			@RequestParam(value = "anno", required = false) Integer anno,
			@RequestParam(value = "metodo", required = false, defaultValue = "greater") String dataNascitaMethod,
			@RequestParam(value = "corsi", required = false) List<Corso> corsi,
			@RequestParam(value = "corso_nome", required = false) String corsiNome
			) throws ParseException {
		return service.smartFind(nome, cognome, LocalDateFormatter.parse(dataNascita), anno, dataNascitaMethod, corsi, corsiNome);
	}
	
	@PostMapping
	public Studente insertStudente(@RequestBody Studente studente) throws BadRequestException {
		return service.insert(studente);
	}
	
	@GetMapping("/{id}")
	public Studente findStudente(@PathVariable Integer id) {
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
	public Studente updateStudente(@RequestBody Studente studente, @PathVariable Integer id) throws NotFoundException, BadRequestException {
		service.existsById(id);
		return service.update(studente, id);
	}
	
	@RequestMapping(value = "/{id}/aggiungi_corso", method = { RequestMethod.POST, RequestMethod.PUT })
	public Studente updateStudenteAggiungiCorso(@RequestBody Corso corso, @PathVariable Integer id) throws NotFoundException, BadRequestException {
		service.existsById(id);
		return service.aggiungiCorso(id, corso);
	}
	
	@RequestMapping(value = "/{id}/rimuovi_corso", method = { RequestMethod.POST, RequestMethod.PUT })
	public Studente updateStudenteRimuoviCorso(@RequestBody Corso corso, @PathVariable Integer id) throws NotFoundException, BadRequestException {
		service.existsById(id);
		return service.rimuoviCorso(id, corso);
	}
	
	@PatchMapping("/{id}")
	public Studente updateNonNullValueStudente(@RequestBody Studente studente, @PathVariable Integer id) throws NotFoundException, BadRequestException {
		service.existsById(id);
		return service.updateNotNullValue(studente, id);
	}

	@DeleteMapping("/{id}")
	public Studente deleteStudente(@PathVariable Integer id) throws NotFoundException {
		service.existsById(id);
		return service.delete(id);
	}

}
