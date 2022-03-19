package tn.com.cnamplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.com.cnamplus.entity.*;
import tn.com.cnamplus.repository.*;
@Service
public class PatientService implements IPatientService {

	@Autowired
	PatientRepo pr;
	@Autowired
	AssureRepo ar;
	@Autowired
	UserRepo ur;
	
	@Override
	public Patient addPatient(Patient patient, String nAssure, String username) {
		// TODO Auto-generated method stub
		User user= ur.findByUsername(username);
		Assure assure = ar.findBynAssure(nAssure);
		patient.setAssure(assure);
		patient.setUser(user);
		return pr.save(patient);
	}

	@Override
	public Patient editPatient(Patient patient, long patientId) {
		// TODO Auto-generated method stub
		Patient p = pr.findById(patientId).orElse(null);
		p.setNom(patient.getNom());
		p.setPrenom(patient.getPrenom());
		p.setQualite(patient.getQualite());
		return pr.save(p);
	}

	@Override
	public void deletePatient(long patientId) {
		// TODO Auto-generated method stub
		pr.deleteById(patientId);
	}

	@Override
	public Patient findPatient(long patientId) {
		// TODO Auto-generated method stub
		return pr.findById(patientId).orElse(null);
	}

	@Override
	public List<Patient> findAll(String username) {
		// TODO Auto-generated method stub
		User user= ur.findByUsername(username);
		return (List<Patient>) pr.findAllByUser(user);
	}

}
