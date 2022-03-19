package tn.com.cnamplus.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import tn.com.cnamplus.entity.PrisEnCharge;

public interface IPrisEnChargeService {

	PrisEnCharge addPec(PrisEnCharge pec, long idPatient, String username, float pu) throws JSONException, IOException, ParseException;

	PrisEnCharge editPec(PrisEnCharge pec, long idPec);

	void deletePec(long idPec);

	PrisEnCharge findPec(long idPec);

	List<PrisEnCharge> findAllByUser(String username);

	List<PrisEnCharge> findAllByPatient(long idPatient);

}
