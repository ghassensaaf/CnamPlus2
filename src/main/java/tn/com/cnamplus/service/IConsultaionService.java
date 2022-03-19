package tn.com.cnamplus.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import tn.com.cnamplus.entity.Consultation;
import tn.com.cnamplus.entity.PrisEnCharge;

public interface IConsultaionService {

	List<Consultation> addOnConsultations(PrisEnCharge pec) throws JSONException, IOException, ParseException;
}
