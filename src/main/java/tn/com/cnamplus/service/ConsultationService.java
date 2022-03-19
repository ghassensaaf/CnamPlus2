package tn.com.cnamplus.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.com.cnamplus.entity.Consultation;
import tn.com.cnamplus.entity.PrisEnCharge;
import tn.com.cnamplus.repository.ConsultationRepo;

@Service
public class ConsultationService implements IConsultaionService {

	@Autowired
	ConsultationRepo cr;

	@Override
	public List<Consultation> addOnConsultations(PrisEnCharge p) throws JSONException, IOException, ParseException{
		List<Consultation> consultations = new ArrayList<Consultation>();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(p.getDateDebut());
		for (int i = 0; i < p.getNbrSeance(); i++) {
			Consultation c = new Consultation();
			if (i == 0) {
				c.setDate(p.getDateDebut());
			}
			c.setDate(cal.getTime());
			c.setIndice(i + 1);
			c.setPrisEnCharge(p);
			if (p.getSeanceParSemain() == 3) {
				cal.add(Calendar.DATE, 2);
				while (checkDate(cal.getTime())) {
					cal.add(Calendar.DATE, 1);
				}
			} else if (p.getSeanceParSemain() == 2) {
				cal.add(Calendar.DATE, 3);
				while (checkDate(cal.getTime())) {
					cal.add(Calendar.DATE, 1);
				}
			} else {
				cal.add(Calendar.DATE, 7);
				while (checkDate(cal.getTime())) {
					cal.add(Calendar.DATE, 1);
				}
			}
			consultations.add(c);
		}
		return (List<Consultation>) cr.saveAll(consultations);
	}

	boolean checkDate(Date date) throws JSONException, IOException, ParseException {
		List<Date> holidays = getHolidays();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,0);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || holidays.contains(cal.getTime()))
			return true;
		else
			return false;
	}

	List<Date> getHolidays() throws JSONException, IOException, ParseException{
		List<Date> holidays = new ArrayList<Date>();
		JSONObject json = readJsonFromUrl("https://www.googleapis.com/calendar/v3/calendars/en.TN%23holiday%40group.v.calendar.google.com/events?key=AIzaSyB0EV1rd6b0dUbTHb_yp3kpl35NRYkELt0");
		JSONArray events= json.getJSONArray("items");
		for (int i = 0; i < events.length(); i++) {
			  JSONObject event =  events.getJSONObject(i);
			  String date = event.getJSONObject("start").getString("date");
			  SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			  holidays.add(sd.parse(date));
			}
		return holidays;
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		java.io.InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

}
