package tn.com.cnamplus.service;

import java.util.List;

import tn.com.cnamplus.entity.Patient;

public interface IPatientService {

	Patient addPatient(Patient patient, String nAssure, String username);

	Patient editPatient(Patient patient, long patientId);

	void deletePatient(long patientId);

	Patient findPatient(long patientId);

	List<Patient> findAll(String username);

}
