package tn.com.cnamplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.com.cnamplus.entity.*;
import tn.com.cnamplus.service.*;

@RestController
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	IPatientService ps;
	
	@PostMapping("/add")
	Patient addPatient(@RequestBody Patient patient, @RequestParam("Num Assure") String nAssure) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return ps.addPatient(patient, nAssure, username);
	}
	@PutMapping("/edit")
	Patient editPatient(@RequestBody Patient patient, @RequestParam("Id Patient") long idPatient) {
		return ps.editPatient(patient, idPatient);
	}
	@GetMapping("/findAllByUser")
	List<Patient> findAllByUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return ps.findAll(username);
	}
	@GetMapping("/findById")
	Patient findById(@RequestParam("Id Patient") long idPatient) {
		return ps.findPatient(idPatient);
	}
	@DeleteMapping("/delete")
	boolean deletePatient(@RequestParam("Id Patient") long idPatient) {
		if(findById(idPatient)!=null) {
			ps.deletePatient(idPatient);
			return true;
		}
		else return false;
	}
}
