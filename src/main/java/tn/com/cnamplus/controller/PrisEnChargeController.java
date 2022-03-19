package tn.com.cnamplus.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.com.cnamplus.entity.*;
import tn.com.cnamplus.service.*;

@RestController
@RequestMapping("/prisencharge")
public class PrisEnChargeController {

	@Autowired
	IPrisEnChargeService pecs;
	@Autowired
	IQrCodeService qrs;
	@PostMapping("/add")
	PrisEnCharge addPec(@RequestBody PrisEnCharge pec, @RequestParam("id Patient") long idPatient , @RequestParam("prix unitaire") float pu) throws JSONException, IOException, ParseException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return pecs.addPec(pec, idPatient, username , pu);
	}
	
	@PostMapping("/uploadQrCode")
    public PrisEnCharge uploadQrCode(@RequestParam("filename") String qr) throws IOException, ParseException {
		File qrFile = new File("C:/CnamPlus/QR/"+qr);
		String s = qrs.decodeQRCode(qrFile);
		return qrs.qrToPec(s);
    }
}
