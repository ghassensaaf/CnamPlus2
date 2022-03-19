package tn.com.cnamplus.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

import tn.com.cnamplus.entity.*;

public interface IQrCodeService {
	String decodeQR(byte[] qrCodeBytes);
	String decodeQRCode(File qrCodeimage) throws IOException;
	PrisEnCharge qrToPec(String decodedqr) throws ParseException, JSONException, IOException;
}
