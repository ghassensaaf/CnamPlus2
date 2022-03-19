package tn.com.cnamplus.service;

import tn.com.cnamplus.entity.Facture;
import tn.com.cnamplus.entity.PrisEnCharge;

public interface IFactureService {
	Facture addFacture(PrisEnCharge pec , float pu);
}
