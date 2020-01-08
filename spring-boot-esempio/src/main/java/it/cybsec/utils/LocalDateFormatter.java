package it.cybsec.utils;

import java.text.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class LocalDateFormatter {

	private static DateTimeFormatter defaultDateFormat;
	
	static {
		defaultDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		defaultDateFormat = defaultDateFormat.withLocale(Locale.ITALY);
		defaultDateFormat = defaultDateFormat.withZone(ZoneId.of("UTC"));
	}
	
	static public DateTimeFormatter getFormatter() {
		return defaultDateFormat;
	}
	
	static public LocalDate parse(String stringDate) throws ParseException {
		if (stringDate == null) return null;
		return LocalDate.parse(stringDate,defaultDateFormat);
	}
	
	static public String format(LocalDate localDate) throws ParseException {
		if (localDate == null) return null;
		return localDate.format(defaultDateFormat);
	}
	
	static public LocalDate parse(String stringDate, DateTimeFormatter dateFormat) throws ParseException {
		if (stringDate == null) return null;
		return LocalDate.parse(stringDate,dateFormat);
	}
	
	static public String format(LocalDate localDate, DateTimeFormatter dateFormat) throws ParseException {
		if (localDate == null) return null;
		return localDate.format(dateFormat);
	}
	
}
