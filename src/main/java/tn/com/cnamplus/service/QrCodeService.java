package tn.com.cnamplus.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import lombok.extern.slf4j.Slf4j;
import tn.com.cnamplus.entity.*;
import tn.com.cnamplus.repository.*;

@Slf4j
@Service
public class QrCodeService implements IQrCodeService {

	@Autowired
	UserRepo ur;
	@Autowired 
	IAssureService as;
	@Autowired
	IPatientService ps;
	@Autowired 
	IPrisEnChargeService pecs;
	
	@Override
	public String decodeQR(byte[] qrCodeBytes) {
		// TODO Auto-generated method stub
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(qrCodeBytes);
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
			BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
			HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
			BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
			MultiFormatReader multiFormatReader = new MultiFormatReader();
			Result result = multiFormatReader.decode(binaryBitmap);

			return result.getText();
		} catch (NotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String decodeQRCode(File qrCodeimage) throws IOException {

		BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		try {
			Result result = new MultiFormatReader().decode(bitmap);
			return result.getText();
		} catch (NotFoundException e) {
			System.out.println("There is no QR code in the image");
			return null;
		}

	}

	@Override
	public PrisEnCharge qrToPec(String decodedqr) throws ParseException, JSONException, IOException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		String[] input = decodedqr.split(";");
		String nomA =input[0].split(" ")[0];
		String prenomA =input[0].split(" ",2)[1];
		String qualite = input[1];
		String numA = input[2]+"/"+input[3];
		String nomP =input[4].split(" ")[0];
		String prenomP =input[4].split(" ",2)[1];
		String numD = input[5]+"/"+input[6]+"/"+input[7];
		String dateD = input[9];
		int nbS =  Integer.parseInt(input[10]);
		int nbsps = Integer.parseInt( input[11]);
		float pu = 11.5f;
		Qualite q = null;
		if(qualite.equals("Conjoint"))
			q= Qualite.Conjoint;
		Date startDate = new SimpleDateFormat("dd/MM/yy").parse(dateD);
		Assure a  = new Assure(0,numA, nomA, prenomA, null);
		a=as.addAssure(a);
		Patient p = new Patient(0, nomP, prenomP, q, null, null, null);
		p=ps.addPatient(p, numA, username);
//		(0, numD, nbS, nbsps, dateD, null, p, null, null, null, u)
		PrisEnCharge pec = new PrisEnCharge(0, numD, nbS, nbsps, startDate, null, null, null, null, null, null);
		pecs.addPec(pec, p.getId(), username, pu);
		return pec;
	}
	

}
