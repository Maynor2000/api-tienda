package com.ifee09.com.tienda.security;

public class Security {
	private final static String tokken = "jksdncjk4564l!sbkjak56s56sd6v6sdvsd56ascva55as5asc1acacsa56cccaa";
	public Security() {
		
	}
	public static boolean aut(String token) {
		if(tokken.equals(token))return true;
		else return false;
	}
}
