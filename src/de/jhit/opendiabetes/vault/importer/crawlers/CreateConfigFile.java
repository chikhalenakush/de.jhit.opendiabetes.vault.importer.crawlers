package de.jhit.opendiabetes.vault.importer.crawlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class CreateConfigFile {

	public static String encodedPW[];

	public void createFile(String username, String password, Logger logger) throws GeneralSecurityException {
		// TODO Auto-generated method stub

		// Creating a local file in Project location
		File file = null; // Used to craete a config file
		try {
			logger.info("Inside Class for Generating Config file");
			Path currentRelativePath = Paths.get("");

			file = new File(currentRelativePath.toAbsolutePath() + "/config_" + (new Date().getTime()) +".txt"); 
			// current location from where program ran
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			CreateSecurePasswordClass CreateSecurePassword = new CreateSecurePasswordClass();
			logger.info("Class CreateConfigFile, creating Hash password");
			encodedPW = CreateSecurePassword.createHash(username, password, logger); // In config file Password is saved as hashed

			writer.println("#Username for access on http://carelink.minimed.eu");			
			writer.println("UserName: " + username);
			writer.println("#Password for access on http://carelink.minimed.eu");
			writer.println("Password: " + encodedPW[0]);
			writer.println("#Device for pump communication. Available options are: stick, bgdevice");			
			writer.println("Device: " + "stick");
			writer.println("#Select Pump Type. Available options are: Minimed, Paradigm");
			writer.println("Pump: " + "Minimed");
			writer.println("#Serial number of the pump you upload data from. 10 characters for Minimed 600 pumps. 6 characters for Paradgim pumps");
			writer.print("SN: " + "1234567890");
			writer.print("\t \t" + "#SN Number should be of 10 characters (alpha numeric only)");
			writer.println();			
			writer.close();
			logger.info("Config file created at location " + file);
			System.out.println("Config file created at location " + file);
			

		} catch (IOException e) {
			// do something
			if (file.exists()) {
				System.out.println("There is an issue opening newly created config file, you can edit it at l�ocation"
						+ file.getAbsolutePath());
			} else {
				System.out.println("There is an issue creatting config file, contact adminnistrator");
				JOptionPane.showMessageDialog(null, "There is an issue creatting config file, contact adminnistrator");
			}
		}
	}

}
