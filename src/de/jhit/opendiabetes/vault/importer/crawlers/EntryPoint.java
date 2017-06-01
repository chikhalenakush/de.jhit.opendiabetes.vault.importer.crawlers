package de.jhit.opendiabetes.vault.importer.crawlers;

import java.awt.AWTException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.cli.ParseException;

public class EntryPoint {

	String flag, opt;

	public EntryPoint(String flag, String opt) {
		this.flag = flag;
		this.opt = opt;
	}

	public static void main(String args[]) throws SecurityException, AWTException, InterruptedException, IOException,
			ParseException, java.text.ParseException, GeneralSecurityException {

		// Class to get all the passed parameters and perform opetaions as
		// required
		
		Logger logger = Logger.getLogger("MyLog");
		FileHandler fh;
		SimpleDateFormat formats = new SimpleDateFormat("dd-mm-HHMMSS");
		
		String userHome = System.getProperty("user.home");
		System.out.println("Log is saved at location " + userHome);
		fh = new FileHandler(userHome + "/CommandLine_" + formats.format(Calendar.getInstance().getTime()) + ".log");

		// fh = new FileHandler("%h/" + "CrawlerProject_" /*+
		// formats.format(Calendar.getInstance().getTime())*/ + ".log");

		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();
		// fh.setFormatter(formatter);
		fh.setFormatter(new MyCustomFormatterForLogger());
		logger.setUseParentHandlers(false);
		
		logger.info("Command Line application started");
		logger.info("Log is saved at location " +System.getProperty("user.home") + "under name CommandLine");
		
		
		FlagArgumentsClass Flagargument = new FlagArgumentsClass();

		Flagargument.GetOptions(args,logger);

	}

}
