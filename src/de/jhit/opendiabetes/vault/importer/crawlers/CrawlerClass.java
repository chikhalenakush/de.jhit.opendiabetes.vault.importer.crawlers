package de.jhit.opendiabetes.vault.importer.crawlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class CrawlerClass {
	public static String UserAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";


	public void GenerateDocument(Map<String, String> loginCookies, String startDate, String endDate, String uPDSArray, Logger logger) {
		// TODO Auto-generated method stub
		logger.info("Inside class CrawlerClass");
		try {
			Connection.Response ReportDocument = Jsoup
					.connect("https://carelink.minimed.eu/patient/main/selectCSV.do?t=11?t=11?t=11?t=11").timeout(60000)
					.ignoreContentType(false).userAgent(UserAgent).cookies(loginCookies)
					.header("Content-Type", "text/csv; charset=UTF-8")
					.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
					.header("Content-length", "101").data("report", "11").data("listSeparator", ",")
					// .data("customerID","50577452") // customer Id can be
					// optional.
					.data("datePicker2", startDate) // start date
					.data("datePicker1", endDate) // End date
					.header("X-Requested-With", "XMLHttpRequest").method(Connection.Method.GET).execute();

			//String userHome = System.getProperty("user.home");
			String userHome = uPDSArray;
			String outputFolder = userHome + File.separator + "careLink-Export";
			
			System.out.println("File will be saved to location " + userHome + " with name: " + "\"careLink-Export"
					+ (new Date().getTime()) + ".csv\"");
			PrintWriter pw1 = new PrintWriter(new File(outputFolder + (new Date().getTime()) + ".csv"));
			pw1.write(ReportDocument.body());
			pw1.close();
			System.out.println("Export Sucessfull!");
			logger.info("Export Sucessfull!");
		//	return true;

		} catch (IOException e) {
			logger.info("There is an issue Downloading File. Please try again after some time!!");
			System.out.println("There is an issue Downloading File. Please try checking path or try again after some time!!");
			Path currentRelativePath = Paths.get("");
			System.out.println("To change path check config file at " +currentRelativePath.toAbsolutePath());
			

			//return false;
		}

		
	}

}
