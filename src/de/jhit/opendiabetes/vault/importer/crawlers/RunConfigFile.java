package de.jhit.opendiabetes.vault.importer.crawlers;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;

public class RunConfigFile {
	String UPDSArray[];
	String DecrypetedPassowrd = null;

	public void runfile(Logger logger) throws SecurityException, AWTException, InterruptedException, GeneralSecurityException {
		// TODO Auto-generated method stub
		try {
			logger.info("Inside class RunConfigFile");
			Path currentRelativePath = Paths.get("");

			File f = new File(currentRelativePath.toAbsolutePath() + "/config.txt");
			if (f.exists()) {
				logger.info("Inside class RunConfigFile, File exist");
				BufferedReader b = new BufferedReader(new FileReader(f));
				GetUserNamePasswordDeviceSNFromFileClass GetUPDS = new GetUserNamePasswordDeviceSNFromFileClass();
				logger.info("Inside class RunConfigFile, Geeting info from config file");
				 UPDSArray = GetUPDS.GetUPDSArray(b,logger);

				CreateSecurePasswordClass CreateSecurePassword = new CreateSecurePasswordClass();
				byte[] salt = new String("12345678").getBytes();

				// Decreasing this speeds down startup time and can be useful
				// during testing, but it also makes it easier for brute force
				// attackers
				int iterationCount = 40000;
				int keyLength = 128;
				try{
				if (UPDSArray[0] != null & !UPDSArray[0].isEmpty() && UPDSArray[1] != null && !UPDSArray[1].isEmpty()) {
					logger.info("Inside class RunConfigFile, Username and Password is not empty");
					SecretKeySpec createSecretKey = CreateSecurePasswordClass.createSecretKey(UPDSArray[0].toCharArray(),
							salt, iterationCount, keyLength,logger);

					DecrypetedPassowrd = CreateSecurePasswordClass.decrypt(UPDSArray[1], createSecretKey,logger);
					if (UPDSArray[2] != null && !UPDSArray[2].isEmpty() && UPDSArray[3] != null && !UPDSArray[3].isEmpty()) {
						LoginDeatilsClass LoginDetails = new LoginDeatilsClass();
						logger.info("Inside class RunConfigFile, device and SN number is not empty");
						if (LoginDetails.CheckConnection(UPDSArray[0], DecrypetedPassowrd,logger)) {
							SimulateMouseClass SM = new SimulateMouseClass();
							SM.startmagic(UPDSArray[0], DecrypetedPassowrd, UPDSArray[2], UPDSArray[3],logger);
						} else {

						}
					} else {
						logger.info("Inside class RunConfigFile,Empty Device or SN Number, Try changing or runnning Config file again");
						System.out.println("Empty Device or SN Number, Try changing or runnning Config file again");
						return;
					}
				} else {
					logger.info("Inside class RunConfigFile,Empty UserName or Password, Try Runnig Config file again");
					System.out.println("Empty UserName or Password, Try Runnig Config file again");
					return;
				}
				}catch(Exception e){
					logger.info("Inside class RunConfigFile,Username or password was changed, Please initilize the config file once again");
					System.out.println("Username or password was changed, Please initilize the config file once again");
				}
			
			}else {
				logger.info("Inside class RunConfigFile,File does not exist");
				System.out.println("Config File does not exist");
				return;
			}
		} catch (IOException e) {
			logger.info("Inside class RunConfigFile,error occured during checking config file");
			e.printStackTrace();
		}

	}
}