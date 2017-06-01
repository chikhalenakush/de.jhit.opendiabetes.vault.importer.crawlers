package de.jhit.opendiabetes.vault.importer.crawlers;

import java.util.logging.Logger;

import com.sun.glass.events.KeyEvent;

public class LanguageClass {

	public int getReplacment(String lang, Logger logger) {
		// TODO Auto-generated method stub
		logger.info("Inside Class, LanguageClass");
		if(lang.contains("en")){
			logger.info("Inside Class LanguageClass, English language");
			return KeyEvent.VK_N;
		}
		if(lang.contains("de")){
			logger.info("Inside Class LanguageClass, Deutsch language");
			return KeyEvent.VK_W;
		}
		if(lang.contains("fr")){
			logger.info("Inside Class LanguageClass, French language");
			return KeyEvent.VK_W;
		}
		
		
		return 0;
	}

}
