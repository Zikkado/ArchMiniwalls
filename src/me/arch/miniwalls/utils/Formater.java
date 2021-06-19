package me.arch.miniwalls.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Formater {
	
	
	public static String Time(String formato, String Zone) {
		return ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(Zone)).format(DateTimeFormatter.ofPattern(formato));
	}

	public static String Tempo(int tempo) {
		int mins = tempo / 60;
		int remainderSecs = tempo - mins * 60;
		if (mins < 60)
			return ((mins < 10) ? "0" : "") + mins + ":" + ((remainderSecs < 10) ? "0" : "") + remainderSecs + "";
		int hours = mins / 60;
		int remainderMins = mins - hours * 60;
		return ((hours < 10) ? "0" : "") + hours + ":" + ((remainderMins < 10) ? "0" : "") + remainderMins + ":"
				+ ((remainderSecs < 10) ? "0" : "") + remainderSecs + "";
	}
	
	public static String Porcentage(double var) {
		return   (int) (var * 5) + "%";
	}
	
}
