package tn.com.cnamplus.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.com.cnamplus.entity.*;
import tn.com.cnamplus.repository.*;
@Service
public class PrisEnChargeService implements IPrisEnChargeService {

	@Autowired
	PrisEnChargeRepo pecr;
	@Autowired 
	PatientRepo pr;
	@Autowired
	UserRepo ur;
	@Autowired
	IFactureService fs;
	@Autowired
	ConsultationRepo cr;
	@Autowired
	IConsultaionService cs;
	@Autowired
	IBordereauService pdfs;
	@Override
	public PrisEnCharge addPec(PrisEnCharge pec, long idPatient, String username , float pu) throws JSONException, IOException, ParseException {
		// TODO Auto-generated method stub
		PrisEnCharge pp = pecr.findBynDecision(pec.getNDecision());
		if(pp==null) {
			Patient patient = pr.findById(idPatient).orElse(null);
			User user = ur.findByUsername(username);
			pec.setPatient(patient);
			pec.setUser(user);
			PrisEnCharge p = pecr.save(pec);
			Facture f = fs.addFacture(p, pu);
			p.setFacture(f);
			List<Consultation> consultations = cs.addOnConsultations(p);
			p.setDateFin(consultations.get(p.getNbrSeance()-1).getDate());
			p.setConsultations(consultations);
			p = pecr.save(p);
			return p;
		}
		else return pp;
		
	}

	@Override
	public PrisEnCharge editPec(PrisEnCharge pec, long idPec) {
		// TODO Auto-generated method stub
		PrisEnCharge p = pecr.findById(idPec).orElse(null);
		p.setNDecision(pec.getNDecision());
		p.setDateDebut(pec.getDateDebut());
		p.setDateFin(pec.getDateFin());
		p.setNbrSeance(pec.getNbrSeance());
		return p;
	}

	@Override
	public void deletePec(long idPec) {
		// TODO Auto-generated method stub
		pecr.deleteById(idPec);
	}

	@Override
	public PrisEnCharge findPec(long idPec) {
		// TODO Auto-generated method stub
		return pecr.findById(idPec).orElse(null);
	}

	@Override
	public List<PrisEnCharge> findAllByUser(String username) {
		// TODO Auto-generated method stub
		User user = ur.findByUsername(username);
		return pecr.findAllByUser(user);
	}

	@Override
	public List<PrisEnCharge> findAllByPatient(long idPatient) {
		// TODO Auto-generated method stub
		Patient patient = pr.findById(idPatient).orElse(null);
		return pecr.findAllByPatient(patient);
	}

}
