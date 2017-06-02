package de.jhit.opendiabetes.vault.importer.crawlers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class CreateConfigFile {

	public static String encdecpw[];

	public void createfile(String username, String password, Logger logger) throws GeneralSecurityException {
		// TODO Auto-generated method stub

		// Creating a local file in Project location
		File file = null;
		try {
			logger.info("Inside Class for Generating Config file");
			Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			
		
			File newfilef = new File(currentRelativePath.toAbsolutePath() + "/config.txt");
			 file = new File(currentRelativePath.toAbsolutePath() + "/config.txt");
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			CreateSecurePasswordClass CreateSecurePassword = new CreateSecurePasswordClass();
			logger.info("Class CreateConfigFile, creating Hash password");
			encdecpw = CreateSecurePassword.CreateHash(username, password,logger);
			// encdecpw[] = CreateSecurePassword.CreateHash(username,password);

			writer.println("UserName: " + username);
			writer.println("Password: " + encdecpw[0]);
			writer.print("Device: " + "stick");
			writer.print("\t \t" + "#stick,bgdevice");
			writer.println();
			writer.print("SN: " + "1234567890");
			writer.print("\t \t" + "#SN Number should be of 10 characters (alpha numeric only)");
			writer.println();
			writer.println("Path to Save CSV:" + "S:/");
			writer.close();
			logger.info("Config file created at location "+ currentRelativePath.toAbsolutePath() + "/config.txt");

			// opening the recently created file in notepad
			//ProcessBuilder pb = new ProcessBuilder("Notepad.exe", currentRelativePath.toAbsolutePath() + "/config.txt");
			//pb.start();
			Desktop desktop = Desktop.getDesktop();
			desktop.edit(file);
			//desktop.open(currentRelativePath.toAbsolutePath() + "/config.txt");
			//Desktop.getDesktop().open(File currentRelativePath.getAbsolutePath() + "/config.txt");
			

		} catch (IOException e) {
			// do something
			if(file.exists()){
			JOptionPane.showMessageDialog(null, "There is an issue opening newly created config file, you can edit it at löocation"
					+ file.getAbsolutePath());
			}
			else
			{
				JOptionPane.showMessageDialog(null, "There is an issue creatting config file, contact adminnistrator"
						);
			}
		}
	}

}
