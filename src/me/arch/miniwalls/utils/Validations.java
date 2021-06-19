package me.arch.miniwalls.utils;

public class Validations {
	
	
	
	
	
	public static boolean isBoolean(String value) {
		try {
			Boolean.valueOf(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isInteger(String value) {
		try {
			Integer.valueOf(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isDouble(String value) {
		try {
			Double.valueOf(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isFloat(String value) {
		try {
			Double.valueOf(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
