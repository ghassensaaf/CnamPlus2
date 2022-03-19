package tn.com.cnamplus.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import tn.com.cnamplus.entity.*;
import tn.com.cnamplus.repository.*;
@Service
public class BordereauService implements IBordereauService {
	
	@Autowired
	PrisEnChargeRepo pecr;
	@Autowired
	UserRepo ur;
	@Autowired
	BordereauRepo br;
	
	
	@Override
	public Bordereau addBordereau(List<Long> pecsIds, String username) throws IOException {
		// TODO Auto-generated method stub
		User user = ur.findByUsername(username);
		Bordereau bordereau = new Bordereau();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.setTime(new Date());
		String nbor = (br.countBordereauPerYear(calendar.get(Calendar.YEAR))+1)+"-"+calendar.get(Calendar.YEAR);
		String path = "C:/CnamPlus/"+username+"/b"+nbor+"/";
		new File(path).mkdirs();
		List<PrisEnCharge> pecs = (List<PrisEnCharge>) pecr.findAllById(pecsIds);
		bordereau.setAnnee(calendar.get(Calendar.YEAR));
		bordereau.setDate(new Date());
		bordereau.setNBord(nbor);
		bordereau.setTotalTtc(0);
		bordereau.setUser(user);
		bordereau.setPrisEnCharges(pecs);
		for (PrisEnCharge pec : pecs) {
			generateFacturePdf(pec.getFacture(), path, user);
			generateSeancesPdf(pec, path, user);
			bordereau.setTotalTtc(bordereau.getTotalTtc() + pec.getFacture().getTotalTtc());	
			
		}
		for (PrisEnCharge pec : pecs) {
			pec.setBordereau(bordereau);
		}
		bordereau = br.save(bordereau);
		generateBordereauTxt(bordereau, path, user);
		return bordereau;
	}
	

	
	public void generateFacturePdf(Facture facture , String Path, User user) throws IOException {
		// TODO Auto-generated method stub
		try {
			String pattern = "EEEEE dd/MM/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
			Font font8 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
			Font font12 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
			Font font16 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16);
			
			
			Document doc = new Document(PageSize.A4);
			
			float width = doc.getPageSize().getWidth();
            float[] columnDefinitionSizeH = { 33.33F, 33.33F, 33.33F };
            float[] columnDefinitionSizeD = { 35.0F, 65.0F };
//            float[] columnDefinitionSizeM = { 35.0F, 65.0F };

            PdfPTable headertable = null;
            PdfPTable detailstable = null;
//            PdfPTable moneytable = null;
            PdfPCell cell = null;
            Paragraph p = null;
			String nfacture = facture.getNumFacture().replace("/", "-");
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(Path+"/f"+nfacture+".pdf"));
			// setting font family, color
			doc.open();
			Paragraph titre = new Paragraph("FACTURE N° "+ facture.getNumFacture(), font16);
			detailstable = new PdfPTable(columnDefinitionSizeD);
			detailstable.getDefaultCell().setBorder(0);
			detailstable.setHorizontalAlignment(0);
			detailstable.setTotalWidth(width - 72);
			detailstable.setLockedWidth(true);
			detailstable.addCell(new Phrase("Date :",font12));
			detailstable.addCell(new Phrase(simpleDateFormat.format(facture.getDateFacture()),font12));
			detailstable.addCell(new Phrase("Patient :",font12));
			detailstable.addCell(new Phrase(facture.getPrisEnCharge().getPatient().getNom()+" "+ facture.getPrisEnCharge().getPatient().getPrenom(),font12));
			detailstable.addCell(new Phrase("N° Assuré :",font12));
			detailstable.addCell(new Phrase(facture.getPrisEnCharge().getPatient().getAssure().getNAssure(),font12));
			detailstable.addCell(new Phrase("Qualité :",font12));
			detailstable.addCell(new Phrase(facture.getPrisEnCharge().getPatient().getQualite().toString(),font12));
			detailstable.addCell(new Phrase("N° Decision :",font12));
			detailstable.addCell(new Phrase(facture.getPrisEnCharge().getNDecision(),font12));
			detailstable.addCell(new Phrase("Date Debut :",font12));
			detailstable.addCell(new Phrase(simpleDateFormat.format(facture.getPrisEnCharge().getDateDebut()),font12));
			detailstable.addCell(new Phrase("Date Fin :",font12));
			detailstable.addCell(new Phrase(simpleDateFormat.format(facture.getPrisEnCharge().getDateFin()),font12));
			detailstable.addCell(new Phrase("Nombre Seance :",font12));
			detailstable.addCell(new Phrase(""+facture.getPrisEnCharge().getNbrSeance()+ " Seances.",font12));

			
			titre.setAlignment(Paragraph.ALIGN_CENTER);
			
   

			headertable = new PdfPTable(columnDefinitionSizeH);
			headertable.getDefaultCell().setBorder(0);
			headertable.setHorizontalAlignment(0);
			headertable.setTotalWidth(width - 72);
			headertable.setLockedWidth(true);
			p = new Paragraph("\n"+user.getNom() +" "+ user.getPrenom()+"\n\n"
							+ "KINESITHERAPEUTE \n\n"
							+ "Avenue de la Republique Immeuble Les Arcades\n"
							+ "-2eme etage B4-, Hammamet, 8050.\n\n", font8);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headertable.addCell(cell);
			
			
			Image logojpg = Image.getInstance("./Files/pictures/kine.jpg");
			cell = new PdfPCell(logojpg);
			cell.setBorder(0);
			cell.getCellEvent();
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headertable.addCell(cell);
			
			p = new Paragraph("tel : "+ user.getTel() +"\n\n"
							+ "Mat. Fiscal : "+ user.getMatriculeFiscale() +"\n\n"
							+ "Code Cnam : "+ user.getCodeCnam() +"\n\n"
							+ "RIB : "+ user.getRib() +"\n\n", font8);
			cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headertable.addCell(cell);
			
            doc.add(headertable);            
            doc.add(new Paragraph("\n\n"));
            doc.add(titre);
            doc.add(new Paragraph("\n\n"));
			doc.add(detailstable);
			doc.close();
			writer.close();
		} catch (DocumentException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void generateSeancesPdf(PrisEnCharge pec, String path, User user) {
		
	}
	void generateBordereauPdf(Bordereau bordereau, String path) {
			
	}
	void generateBordereauTxt(Bordereau bordereau, String path,User user) {
		try {
		      File bTxt = new File(path+"bordereau-"+bordereau.getNBord()+".txt");
		      bTxt.createNewFile();
		      FileWriter myWriter = new FileWriter(path+"bordereau-"+bordereau.getNBord()+".txt");
		      myWriter.write(firstLine(bordereau)+secondLines(bordereau, user));
		      
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	String firstLine(Bordereau bordereau) {
		String fline="";
		String nBord=bordereau.getNBord().split("-")[0];
		String annee=bordereau.getNBord().split("-")[1];
		String codeCnam1=bordereau.getUser().getCodeCnam().split("/")[0];
		String codeCnam2=bordereau.getUser().getCodeCnam().split("/")[1];
		String codeCnam3=bordereau.getUser().getCodeCnam().split("/")[2];

		String nbrPecs = Integer.toString(bordereau.getPrisEnCharges().size()) ;
		int total =(int) bordereau.getTotalTtc()*1000;
		String ttc = ""+total;
		while(nBord.length()<3) {
			nBord ="0"+nBord;
		}
		while(ttc.length()<10) {
			ttc = "0"+ttc;
		}
		while(nbrPecs.length()<3) {
			nbrPecs = "0"+nbrPecs;
		}
		while(codeCnam1.length()<2) {
			codeCnam1 = "0"+codeCnam1;
		}
		while(codeCnam2.length()<8) {
			codeCnam2 = "0"+codeCnam2;
		}
		String cnam = codeCnam1+codeCnam2+codeCnam3;
		
		fline = "1"+annee+nBord+cnam+"000000000000000000000000000000000000000000000000"+nbrPecs+"0000000000000000"+ttc+"00000000000000000000000000000000000000\r\n";
		return fline;
	}
	String secondLines(Bordereau bordereau, User user) {
		String pattern = "yyyyMMdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
		String pecs="";
		String nBord=bordereau.getNBord().split("-")[0];
		String annee=bordereau.getNBord().split("-")[1];
		String codeCnam1=bordereau.getUser().getCodeCnam().split("/")[0];
		String codeCnam2=bordereau.getUser().getCodeCnam().split("/")[1];
		String codeCnam3=bordereau.getUser().getCodeCnam().split("/")[2];
		
		while(nBord.length()<3) { nBord ="0"+nBord;}
		while(codeCnam1.length()<2) {codeCnam1 = "0"+codeCnam1;}
		while(codeCnam2.length()<8) {codeCnam2 = "0"+codeCnam2;}
		
		String cnam = codeCnam1+codeCnam2+codeCnam3;
		for (PrisEnCharge pec : bordereau.getPrisEnCharges()) {
			String nFac  = pec.getFacture().getNumFacture();
			String nDec1 = pec.getNDecision().split("/")[0];
			String nDec2 = pec.getNDecision().split("/")[1];
			String nDec3 = pec.getNDecision().split("/")[2];
			String nAss1 = pec.getPatient().getAssure().getNAssure().split("/")[0];
			String nAss2 = pec.getPatient().getAssure().getNAssure().split("/")[1];
			String nbsps = "00"+pec.getSeanceParSemain();
			String nbs	 = Integer.toString(pec.getNbrSeance());
			int total =(int) pec.getFacture().getTotalTtc()*1000;
			String ttc = ""+total;
			int mntTvaint = ((int) (Math.floor(total * 6.542)/100)) + 1;
			String mntTva = Integer.toString(mntTvaint);
			String ht = Integer.toString(total - mntTvaint);
			String tva = Integer.toString(pec.getFacture().getTva()*1000);
			while(nFac.split("/")[0].length()<2) {
				nFac = "0"+nFac;
			}
			while(ttc.length()<10) {
				ttc="0"+ttc;
			}
			while(mntTva.length()<10) {
				mntTva="0"+mntTva;
			}
			while(ht.length()<10) {
				ht="0"+ht;
			}
			while(tva.length()<10) {
				tva="0"+tva;
			}
			while(nAss1.length()<10) {
				nAss1="0"+nAss1;
			}
			while(nAss2.length()<2) {
				nAss2="0"+nAss2;
			}
			while(nDec3.length()<6) {
				nDec3="0"+nDec3;
			}
			while(nbs.length()<3) {
				nbs="0"+nbs;
			}
			
			String nDec= nDec1+user.getCodePres()+nDec2+nDec3;
			String nAss= nAss1+nAss2;
			pecs += "2"+annee+nBord+cnam+annee+"        "+nFac+nDec+nAss+nbsps+nbs+simpleDateFormat.format(pec.getDateDebut())+simpleDateFormat.format(pec.getDateFin())+ttc+ht+tva+mntTva+simpleDateFormat.format(pec.getFacture().getDateFacture())+"\r\n";
		}
		
		return pecs;
	}




	
	
}
