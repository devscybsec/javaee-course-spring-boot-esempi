package it.cybsec.controller.view;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;

import it.cybsec.repository.rest.ImpiegatiRepository;

@Controller
public class ImpiegatiView {

	@Autowired
	ImpiegatiRepository repo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String viewImpiegati() {
		return "impiegati";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String modifyImpiegato(@RequestParam(value="id") Integer id, ModelMap impiegati) {
		impiegati.addAttribute("impiegato", repo.findById(id).get());
		return "updateImpiegato";
	}
	
}
