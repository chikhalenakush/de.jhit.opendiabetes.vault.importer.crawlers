package de.jhit.opendiabetes.vault.importer.crawlers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class LoginDeatilsClass {
	private Map<String, String> loginCookies = new HashMap<String, String>();
	public static String UserAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

	public Map<String, String> getcookies() {
		return this.loginCookies;

	}
	
	public Boolean CheckConnection(String username, String password, Logger logger) {
		// TODO Auto-generated method stub
		try {
			logger.info("Inside class CheckConnection");

			Connection.Response res = Jsoup.connect("https://carelink.minimed.eu/patient/j_security_check")
					.data("j_username", username).data("j_password", password).method(Connection.Method.POST).execute();
			loginCookies = res.cookies();
			System.out.println("correct Username and Password");
			logger.info("correct Username and Password");
			return true;
		} catch (Exception e) {
			logger.info("Incorrect Username or Password");
			System.out.println("Incorrect Username or Password");
			return false;
		}

	}
}
