package tn.com.cnamplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.com.cnamplus.service.*;
import tn.com.cnamplus.entity.*;

@RestController
@RequestMapping("/assure")
public class AssureController {

	@Autowired
	IAssureService as;
	@PostMapping("/add")
	Assure addAssure(@RequestBody Assure assure) {
		return as.addAssure(assure);
	}
	@PutMapping("/edit")
	Assure updateAssure(@RequestBody Assure assure, @RequestParam long idAssure) {
		assure.setId(idAssure);
		return as.editAssure(assure);
	}
	@DeleteMapping("/delete")
	boolean deleteAssure(@RequestParam long idAssure) {
		if(findById(idAssure) != null) {
			as.deleteAssure(idAssure);
			return true;
		}
		else 
			return false;
	}
	@GetMapping("/findbyid")
	Assure findById(long idAssure){
		return as.findAssure(idAssure);
	}
	@GetMapping("/findall")
	List<Assure> findAll(){
		return as.findAll();
	}
}
