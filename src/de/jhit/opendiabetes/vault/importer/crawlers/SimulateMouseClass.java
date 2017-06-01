package de.jhit.opendiabetes.vault.importer.crawlers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.awt.event.KeyEvent;

public class SimulateMouseClass {

	public void startmagic(String loginname, String loginPassword, String device, String sn, Logger logger)
			throws AWTException, InterruptedException, SecurityException, IOException {
		logger.info("Inside Class Simulate mouse and Method Startmagic");
		// TODO Auto-generated method stub
		Boolean BG = false;
		Boolean Stick = false;
		if (device.toLowerCase().equals("stick")) {
			Stick = true;
		} else if (device.toLowerCase().equals("bgdevice")) {
			BG = true;
		} else {
			logger.info("Device not properly selected)");
			System.out.println("Device not properly selected, Try changing config file");
			return;
		}
		if (sn.length() != 10) {
			logger.info("SN Number should be of 10 characters (alpha numeric only)");
			System.out.println(
					"SN Number should be of 10 characters (alpha numeric only)" + ", Try changing config file");
			return;
		} else {
			for (int i = 0; i < sn.length(); i++) {
				char c = sn.charAt(i);
				if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a) {
					logger.info("SN Number should be of alpha numeric only");
					System.out.println("SN Number should be of alpha numeric only" + ", Try changing config file");
					return;
				}
			}

		}
		try {

			Boolean IsEnglishOrDeutsch = false;
			String Lang = null;
			int ReplacmentForN = 0;

			WebDriver driver = null;
			Robot robot = new Robot();
			logger.info("Inside Method Startmagic Before Desired Cap");
			DesiredCapabilities capabilities = null;
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			logger.info("(Inside Method Startmagic) DesiredCap filled ");
			try {
				URL url = null; // Url to get IEDriver from a Folder in Eclipse

				File filewhereIEDriverislocated;
				String DestExtractJarDir = null;
				url = this.getClass().getClassLoader().getSystemResource("IEDriverServer.exe");

				logger.info("(Inside Method Startmagic) Checking for IEDriver path ");
				

				String location = SimulateMouseClass.class.getResource("SimulateMouseClass.class").toString();

				// checking if program is ran from Jar
				if (location.startsWith("rsrc:") || location.endsWith(".jar")
						&& !new File(location.substring(location.indexOf(':') + 1)).isDirectory()) {
					logger.info("(Inside Method Startmagic) Running from Jar file ");
					// Program ran from Jar
					if (url.toString().contains(".jar")) {

				

						int index = url.toString().lastIndexOf(".jar");// split("jar")[0];
						String urlbeforejar = url.toString().substring(0, index); // get
																					// string
																					// before
																					// .jar
																					// Exampe
																					// C:/users/achikhale/desktop/crawler.jar
						// we will have in urlbeforejar
						// C:/users/achikhale/desktop/crawler

				
						int index2 = urlbeforejar.toString().lastIndexOf("/");

						String JarFileNameWithoutJar = urlbeforejar.substring(urlbeforejar.lastIndexOf("/") + 1); // Only
																													// Jar
																													// name
																													// without
																													// Jar
				
						String Jarname = JarFileNameWithoutJar + ".jar";

				

						urlbeforejar = urlbeforejar.replace("jar:file:/", "");
				

						java.util.jar.JarFile jarfile = new java.util.jar.JarFile(
								new java.io.File(urlbeforejar + ".jar")); // jar
																			// file
																			// path(here
																			// sqljdbc4.jar)
						java.util.Enumeration<java.util.jar.JarEntry> enu = jarfile.entries();
						while (enu.hasMoreElements()) {
							String userHome = System.getProperty("user.home");

				

							DestExtractJarDir = userHome + "/extractjar/"; // abc
																			// is
																			// my
																			// destination
																			// directory
							java.util.jar.JarEntry je = enu.nextElement();

							java.io.File fl = new java.io.File(DestExtractJarDir, je.getName());

							Boolean check = false;

							if (fl.exists())
								// boolean check = new
								// File(DestExtractJarDir,"IEDriverServer.exe").exists();
								check = new File(DestExtractJarDir, "IEDriverServer.exe").exists();

							if (check) {
								
							} else {
								
								if (!fl.exists()) {
									fl.getParentFile().mkdirs();
									fl = new java.io.File(DestExtractJarDir, je.getName());
								}
								if (je.isDirectory()) {
									continue;
								}
								java.io.InputStream is = jarfile.getInputStream(je);
								java.io.FileOutputStream fo = new java.io.FileOutputStream(fl);
								while (is.available() > 0) {
									fo.write(is.read());
								}
								fo.close();
								is.close();

							}
							
						}
						

					}
					logger.info("(Inside Method Startmagic) Setthing IEDriver path from Extracted Jar ");
					filewhereIEDriverislocated = new File(DestExtractJarDir + "/IEDriverServer.exe");
				} else {
					logger.info("(Inside Method Startmagic) Setthing IEDriver path directly from Project folder ");
					filewhereIEDriverislocated = new File(url.getFile());
				}

				if (filewhereIEDriverislocated.exists()) {
					System.setProperty("webdriver.ie.driver", filewhereIEDriverislocated.getAbsolutePath());

					driver = new InternetExplorerDriver(capabilities);

					driver.manage().window().maximize();
					driver.get("https://carelink.minimed.eu/patient/entry.jsp?bhcp=1");
				} else {
					JOptionPane.showMessageDialog(null, "IEDriver is not selected");
					return;
				}

			} catch (Exception e) {
				logger.info("Inside catch of Class Simulate mouse and Method Startmagic");
				JOptionPane.showMessageDialog(null,
						"Please Check if the correct IEDriver is selected OR IE settings are not correctly");
					return;
			}
			Boolean isPresent = driver.findElements(By.id("j_username")).size() > 0;

			if (isPresent) {
				driver.findElement(By.id("j_username")).sendKeys(loginname);
				driver.findElement(By.id("j_password")).sendKeys(loginPassword);
				Thread.sleep(2000);

				driver.findElement(By.id("j_password")).sendKeys(Keys.ENTER);
			} else {
				Thread.sleep(4000);
				if (driver.findElements(By.id("j_username")).size() > 0) {
					driver.findElement(By.id("j_username")).sendKeys(loginname);
					driver.findElement(By.id("j_password")).sendKeys(loginPassword);
					Thread.sleep(2000);

					driver.findElement(By.id("j_password")).sendKeys(Keys.ENTER);
				} else {
					Thread.sleep(6000);
					if (driver.findElements(By.id("j_username")).size() > 0) {
						driver.findElement(By.id("j_username")).sendKeys(loginname);
						driver.findElement(By.id("j_password")).sendKeys(loginPassword);
						Thread.sleep(2000);

						driver.findElement(By.id("j_password")).sendKeys(Keys.ENTER);
					} else {
						logger.info("Website has not fully loaded close and try running again");
						JOptionPane.showMessageDialog(null, "Website has not fully loaded close and try running again");
						return;
					}
				}
			}

			Thread.sleep(4000);

			Boolean isUploadPresent = driver.findElements(By.id("upload")).size() > 0;
			if (isUploadPresent) {
				driver.findElement(By.id("upload")).sendKeys(Keys.ENTER);
			} else {
				Thread.sleep(6000);
				if (driver.findElements(By.id("upload")).size() > 0) {
					driver.findElement(By.id("upload")).sendKeys(Keys.ENTER);
				} else {
					Thread.sleep(8000);

					if (driver.findElements(By.id("upload")).size() > 0) {
						driver.findElement(By.id("upload")).sendKeys(Keys.ENTER);
					} else {
						logger.info("upload section has not fully loaded close IE and try running Java program again");
						JOptionPane.showMessageDialog(null,
								"upload section has not fully loaded close IE and try running Java program again");
						return;
					}
				}
			}
			Thread.sleep(10000);

			Boolean CompleAutoClick = false;
			Thread.sleep(2000);
			try {
				Lang = driver.findElement(By.tagName("html")).getAttribute("lang");

				LanguageClass Language = new LanguageClass();

				ReplacmentForN = Language.getReplacment(Lang, logger);

			} catch (Exception e) {
				logger.info("Site not fully loaded");
				JOptionPane.showMessageDialog(null, "Site not fully loaded");
			}

			if (driver.findElements(By.tagName("object")).size() > 0) {
				CompleAutoClick = Runningappletbuttons(sn, BG, Stick, robot, ReplacmentForN, logger);

			} else {
				Thread.sleep(10000);
				if (driver.findElements(By.tagName("object")).size() > 0) {
					CompleAutoClick = Runningappletbuttons(sn, BG, Stick, robot, ReplacmentForN, logger);

				} else {
					Thread.sleep(12000);
					if (driver.findElements(By.tagName("object")).size() > 0) {
						CompleAutoClick = Runningappletbuttons(sn, BG, Stick, robot, ReplacmentForN, logger);

					} else {
						logger.info(
								"Applet section has not fully loaded close IE and try running again Java program again");
						JOptionPane.showMessageDialog(null,
								"Applet section has not fully loaded close IE and try running again Java program again");
						return;
					}
				}

			}

		} catch (Exception e) {
			logger.info("Please Check if the correct IEDriver is selected OR IE settings are not correctly");
			JOptionPane.showMessageDialog(null,
					"Please Check if the correct IEDriver is selected OR IE settings are not correctly");
			return;
		} finally {
			// JOptionPane.showMessageDialog(null, "Please Check if the correct
			// IEDriver File Location is selected OR IE settings are correctly
			// selected");
			Thread.currentThread().interrupt();

		}

	}

	private Boolean Runningappletbuttons(String sn, Boolean BG, Boolean Stick, Robot robot, int replacmentForN,
			Logger logger) throws InterruptedException, AWTException {
		logger.info("Inside Method Runningappletbuttons");
		// First selecting Minimed

		Thread.sleep(500);

		robot.keyPress(KeyEvent.VK_SHIFT);
	robot.keyPress(KeyEvent.VK_TAB);
	Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_TAB);
		// System.out.println("First shift tab");

		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		Thread.sleep(2000);
		// First selecting ALT + N
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);

		// Second selecting Minimed series 6000
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_TAB);

		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_TAB);

		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);

		// Second selecting ALT + N
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		// Third selecting ALT + N
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);

		// Entering Value for Device
		Thread.sleep(2000);
		type(sn);
		// Fourth selecting ALT + N
		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);

if (Stick) {
			logger.info("Inside Method Runningappletbuttons,Stick is selected");
			// System.out.println("Stick is selected");
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
			 Thread.sleep(4000);
		}
		if (BG) {
			logger.info("Inside Method Runningappletbuttons,BG is selected");
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);

			Thread.sleep(2000);

		}

		
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(replacmentForN);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_ALT);
		if (BG) {
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_F);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_ALT);
		}
		return true;

	}

	private static void leftClick() throws AWTException {
		Robot robot = new Robot();
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(200);
	}

	private void type(int i) throws AWTException {
		Robot robot = new Robot();
		robot.delay(40);
		robot.keyPress(i);
		robot.keyRelease(i);
	}

	private static void type(String s) throws AWTException {
		Robot robot = new Robot();
		byte[] bytes = s.getBytes();
		for (byte b : bytes) {
			int code = b;
			// keycode only handles [A-Z] (which is ASCII decimal [65-90])
			if (code > 96 && code < 123)
				code = code - 32;
			robot.delay(40);
			robot.keyPress(code);
			robot.keyRelease(code);
		}
	}

}
