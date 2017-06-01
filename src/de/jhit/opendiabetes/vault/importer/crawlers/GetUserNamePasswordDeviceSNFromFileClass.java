package de.jhit.opendiabetes.vault.importer.crawlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

public class GetUserNamePasswordDeviceSNFromFileClass {
	String readLine = "";

	

	String UserName = null, Password = null, Device = null, SN = null, DecrypetedPassowrd = null, PathForCsv = null;
	public String[] GetUPDSArray(BufferedReader b, Logger logger) throws IOException {
		// TODO Auto-generated method stub
		logger.info("Inside class GetUserNamePasswordDeviceSNFromFileClass");
		while ((readLine = b.readLine()) != null) {
			
			if (readLine.toLowerCase().contains("username:")) {
				UserName = readLine.substring(readLine.lastIndexOf(":")+1);

				String TempUserNameString = UserName.replaceFirst("\\s", "");

				TempUserNameString = TempUserNameString.indexOf(" ") > 0
						? TempUserNameString.substring(0, TempUserNameString.indexOf(" ")) : TempUserNameString;
				UserName = TempUserNameString;
			
				logger.info("Inside class GetUserNamePasswordDeviceSNFromFileClass, Get userName from command line input");
			}
			if (readLine.toLowerCase().contains("password:")) {
				Password = readLine.substring(+9);

				String TempPasswordString = Password.replaceFirst("\\s", "");

				// TempPasswordString = TempPasswordString.indexOf(" ")
				// > 0
				// ? TempPasswordString.substring(0,
				// TempPasswordString.indexOf(" ")) :
				// TempPasswordString;
				Password = TempPasswordString;

			
				logger.info("Inside class GetUserNamePasswordDeviceSNFromFileClass, Get Password from command line input");

			}

			if (readLine.toLowerCase().contains("device:") && readLine.contains("#")) {
				Device = readLine.substring(readLine.lastIndexOf(":")+1);

				String TempDeviceString = Device.replaceFirst("\\s", "");

				TempDeviceString = TempDeviceString.indexOf("#") > 0
						? TempDeviceString.substring(0, TempDeviceString.indexOf("#")) : TempDeviceString;
						Device = TempDeviceString.replaceAll("\\s+$", "");
				//Device = TempDeviceString;
				
				logger.info("Inside class GetUserNamePasswordDeviceSNFromFileClass, Get Device from command line input");
			}
			if (readLine.toLowerCase().contains("sn:") && readLine.contains("#")) {
				SN = readLine.substring(readLine.lastIndexOf(":")+1);

				String TempSNString = SN.replaceFirst("\\s", "");

				TempSNString = TempSNString.indexOf(" ") > 0
						? TempSNString.substring(0, TempSNString.indexOf(" ")) : TempSNString;
						SN = TempSNString.replaceAll("\\s+$", "");
				
				
				logger.info("Inside class GetUserNamePasswordDeviceSNFromFileClass, Get SN from command line input");
			}
			if (readLine.toLowerCase().contains("path to save csv:")) {
				PathForCsv = readLine.substring(17);
				
				String TempPathForCsvString = PathForCsv.replaceFirst("\\s", "");

				
				
				logger.info("Inside class GetUserNamePasswordDeviceSNFromFileClass, Get PathForCsv from command line input");
			}
			
	}
		return new String[] {UserName,Password,Device,SN,PathForCsv};	
	}
}


