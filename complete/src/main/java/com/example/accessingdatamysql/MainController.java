package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/wypozycz") // This means URL's start with /wypozycz (after Application path)
public class MainController {
	@Autowired // This means to get the bean called userRepository
				// Which is auto-generated by Spring, we will use it to handle the data
	private RepositorySamochod SamochodRepository;

	@PostMapping(path = "/add") // Map ONLY POST Requests
	public @ResponseBody String addNewSamochod(
			@RequestParam String marka,
			@RequestParam int przebieg,
			@RequestParam int moc, 
			@RequestParam(defaultValue = "false") boolean wypozyczony) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Samochod n = new Samochod();
		n.setMarka(marka);
		n.setPrzebieg(przebieg);
		n.setMoc(moc);
		n.setWypozyczony(wypozyczony);
		SamochodRepository.save(n);
		return "Saved";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Samochod> getAllUsers() {
		// This returns a JSON or XML with the users
		return SamochodRepository.findAll();
	}

	@GetMapping(path = "/delete/{id}")
	public void deleteSamochod(@PathVariable("id") Long id) {
		SamochodRepository.deleteById(id);
	}

	@GetMapping(path = "/wypozyczLUBzwruc/{id}")
	public void wyporzyczLUBzwroc(@PathVariable("id") Long id) {
		Samochod n = SamochodRepository.getById(id);
		n.setWypozyczony(!n.isWypozyczony());
		SamochodRepository.save(n);
	}
	@GetMapping(path = "/zamien/przebieg_na/{nowyPrzebieg}/{id}")
	public void zmienPrzebieg(@PathVariable("id") Long id,
						  @PathVariable("nowyPrzebieg") int nowyPrzebieg) {
		Samochod n = SamochodRepository.getById(id);
		n.setPrzebieg(nowyPrzebieg);
		SamochodRepository.save(n);
	}
	@GetMapping(path = "/zamien/moc_na/{nowaMoc}/{id}")
	public void zmienMoc(@PathVariable("id") Long id,
						  @PathVariable("nowaMoc") int nowaMoc) {
		Samochod n = SamochodRepository.getById(id);
		n.setMoc(nowaMoc);
		SamochodRepository.save(n);
	}

	
}
