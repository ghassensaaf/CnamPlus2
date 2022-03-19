package tn.com.cnamplus.service;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.com.cnamplus.entity.Facture;
import tn.com.cnamplus.entity.PrisEnCharge;
import tn.com.cnamplus.repository.FactureRepo;
@Service
public class FactureService implements IFactureService {

	@Autowired
	FactureRepo fr;
	@Override
	public Facture addFacture(PrisEnCharge pec , float pu) {
		// TODO Auto-generated method stub
		Facture facture = new Facture();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.setTime(new Date());
		facture.setAnnee(calendar.get(Calendar.YEAR));
		facture.setNumFacture((fr.countFacturePerYear(facture.getAnnee())+1)+"/"+facture.getAnnee());
		facture.setPrisEnCharge(pec);
		facture.setDateFacture(new Date());
		facture.setTva(7);
		facture.setPrixUnitaire(pu);
		facture.setTotalTtc(facture.getPrixUnitaire()*pec.getNbrSeance());
		facture.setMntTva((float) (facture.getTotalTtc()*(0.01*facture.getTva())));
		facture.setTotalHT(facture.getTotalTtc()-facture.getMntTva());
		return fr.save(facture);
	}

}
