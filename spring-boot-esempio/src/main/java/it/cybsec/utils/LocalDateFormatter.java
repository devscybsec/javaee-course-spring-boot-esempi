package it.cybsec.utils;

import java.text.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class LocalDateFormatter {

	private static DateTimeFormatter dateFormat;
	
	static {
		dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		dateFormat = dateFormat.withLocale(Locale.ITALY);
		dateFormat = dateFormat.withZone(ZoneId.of("UTC"));
	}
	
	static public LocalDate parse(String stringDate) throws ParseException {
		if (stringDate == null)
			return null;
		return LocalDate.parse(stringDate,dateFormat);
	}
	
	static public String format(LocalDate localDate) throws ParseException {
		if (localDate == null)
			return null;
		return localDate.format(dateFormat);
	}
	
}
