package de.jhit.opendiabetes.vault.importer.crawlers;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import UnitTestCases.*;


public class FlagArgumentsClass {

	String UPDSArray[];
	String DecrypetedPassowrd = null;

	// Method to get and set parameters passeed.
	void GetOptions(String[] args, Logger logger) throws IOException, ParseException, SecurityException, AWTException,
			InterruptedException, java.text.ParseException, GeneralSecurityException {
		// TODO Auto-generated method stub
		logger.info("Inside Class FlagArgumentsClass");
		int count = args.length;

		// Here we can define as many options as we want
		CommandLine commandLine;
		Option option_u = Option.builder("u").hasArg().required(false).desc("The A option").longOpt("opt3").build();
		// Option option_p =
		// Option.builder("p").hasArg().required(false).desc("The r
		// option").longOpt("opt1").build();
		Option option_c = Option.builder("c").required(false).desc("The S option").longOpt("opt2").build();
		Option option_init = Option.builder().required(false).desc("The init option").longOpt("init").build();
		Option option_applet = Option.builder().required(false).desc("The applet option").longOpt("applet").build();
		Option option_crawler = Option.builder().required(false).desc("The Crawler option").longOpt("crawler").build();
		Option option_fromdate = Option.builder("from").hasArg().required(false).desc("The from option").longOpt("from")
				.build();
		Option option_todate = Option.builder("to").hasArg().required(false).desc("The to option").longOpt("to")
				.build();
		Option option_test = Option.builder().required(false).desc("The test option").longOpt("test").build();

		Options options = new Options();
		CommandLineParser parser = new DefaultParser();

		// adding options defined above
		options.addOption((org.apache.commons.cli.Option) option_u);
		// options.addOption((org.apache.commons.cli.Option) option_p);
		options.addOption((org.apache.commons.cli.Option) option_c);
		options.addOption((org.apache.commons.cli.Option) option_init);
		options.addOption((org.apache.commons.cli.Option) option_applet);
		options.addOption((org.apache.commons.cli.Option) option_crawler);
		options.addOption((org.apache.commons.cli.Option) option_fromdate);
		options.addOption((org.apache.commons.cli.Option) option_todate);
		options.addOption((org.apache.commons.cli.Option) option_test);

		// Command line argument into list
		ArrayList<String> testArgs1 = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {

			testArgs1.add(args[i]);
		}

		String[] testArgs2 = new String[testArgs1.size()];
		testArgs2 = testArgs1.toArray(testArgs2); // getting all command line
													// aragumest passed into
													// local string variable

		try {
			logger.info("Getting all the user input flag and its value");
			String username = null;
			String password = null;
			String fromdate = null, todate = null, DecrypetedPassowrd = null;

			commandLine = parser.parse(options, testArgs2);

			if (commandLine.hasOption("u")) {

				username = commandLine.getOptionValue("u");

			}

			if (commandLine.hasOption("c")) {

			}

			if (commandLine.hasOption("init")) {

			}

			if (commandLine.hasOption("applet")) {

			}
			if (commandLine.hasOption("from")) {

				fromdate = commandLine.getOptionValue("from");

			}
			if (commandLine.hasOption("to")) {

				todate = commandLine.getOptionValue("to");

			}
			if (commandLine.hasOption("test")) {

				// System.out.println(commandLine.getOptionValue("test"));
			}

			// If no argument is passed it will show below statement
			{
				String[] remainder = commandLine.getArgs();
				// System.out.print("Remaining arguments: ");
				if (!(args.length > 0)) {
					logger.info("Arguments missing");
					System.out.println("Arguments missing");
				}

			}

			// checking if init parametrs is passed

			if (commandLine.hasOption("u") /* || commandLine.hasOption("p") */ || commandLine.hasOption("c")
					|| commandLine.hasOption("init")) {
				logger.info("User input flag such as -u or -c or -init");
				if (commandLine.hasOption("u")
						/* && commandLine.hasOption("p") */ && commandLine.hasOption("c")
						&& commandLine.hasOption("init")) {
					logger.info("Input entered by user is for initilizing config file");
					System.out.println("Inside u");
					Console c = System.console();
					if (c == null) {
						System.err.println("No console.");
						logger.info("Ask user for password");
						final JPasswordField pf = new JPasswordField(); 
						password = JOptionPane.showConfirmDialog( null, pf, "Password", 
						    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ) == JOptionPane.OK_OPTION 
						      ? new String( pf.getPassword() ) : ""; 
					}else{

					logger.info("Ask user for password");
					password = new String(c.readPassword("Password: "));
					}
					// Class to create config file with user given details
					CreateConfigFile config = new CreateConfigFile();
					config.createfile(username, password, logger);

				} else {
					logger.info("Arguments should be in format :-u username -c -init");
					System.out.println("Arguments should be in format :-u username -c -init");
					return;
				}
			}

			else if (commandLine.hasOption("applet") || commandLine.hasOption("applet")) {
				if (commandLine.hasOption("test") && commandLine.hasOption("applet")) {

					System.out.println("Starting Unit Test case for Applet Wrapper");
					
					UnitTestCases.TestAppletWrapper TestApplet = new UnitTestCases.TestAppletWrapper();
					System.out.println("Unit Test case for From Device and SN Number");
					TestApplet.AreDetailsCorrect();

					UnitTestCases.TestLoginDetails TestLogin = new UnitTestCases.TestLoginDetails();
					System.out.println("Unit Test case for UserName and Password");
					TestLogin.IsLoginCorrect();

				} else if (commandLine.hasOption("applet")) {
					logger.info("Inside applet");
					System.out.println("Inside applet");
					RunConfigFile RunConfig = new RunConfigFile();
					RunConfig.runfile(logger);
				}
			} else if (commandLine.hasOption("from") || commandLine.hasOption("to") || commandLine.hasOption("crawler")
					|| commandLine.hasOption("test")) {

				if (commandLine.hasOption("test") && commandLine.hasOption("crawler")) {
					
					System.out.println("Starting Unit Test case for Crawler");
					
					UnitTestCases.TestDatesClass TestDates = new UnitTestCases.TestDatesClass();
					System.out.println("Unit Test case for From Date and To Date");
					TestDates.callmethod();

					UnitTestCases.TestLoginDetails TestLogin = new UnitTestCases.TestLoginDetails();
					
					System.out.println("Unit Test case for UserName and Password");
					TestLogin.IsLoginCorrect();

					UnitTestCases.TestPathToSaveData TestPath = new UnitTestCases.TestPathToSaveData();
					System.out.println("Unit Test case for Path to Save CSV");
					TestPath.CheckPath();
					return;

				} else if(commandLine.hasOption("test")) {
					System.out.println("For Starting crawler unit test argument should be -test -crawler");

				}
				logger.info("User input -applet or -from or -to");
				if (commandLine.hasOption("from") && commandLine.hasOption("to") && commandLine.hasOption("crawler")) {

					logger.info("Input entered by user is for Crawling");
					// Crawler logic
					CheckDatesClass checkdates = new CheckDatesClass();
					if (checkdates.GetStratDate(fromdate, logger)) {

						logger.info("from date is correct");

						if (checkdates.GetEndDate(fromdate, todate, logger)) {

							logger.info("End date is correct");

							try {
								logger.info("Inside logic for checking config file availabe for crawler");
								Path currentRelativePath = Paths.get("");

								File f = new File(currentRelativePath.toAbsolutePath() + "/config.txt");
								if (f.exists()) {
									logger.info("config file is availabe");
									BufferedReader b = new BufferedReader(new FileReader(f));
									GetUserNamePasswordDeviceSNFromFileClass GetUPDS = new GetUserNamePasswordDeviceSNFromFileClass();

									UPDSArray = GetUPDS.GetUPDSArray(b, logger);
									logger.info("Function is called to get Encrypted username and passowrd");
									CreateSecurePasswordClass CreateSecurePassword = new CreateSecurePasswordClass();
									byte[] salt = new String("12345678").getBytes();

									// Decreasing this speeds down startup time
									// and can be useful
									// during testing, but it also makes it
									// easier for brute force
									// attackers
									int iterationCount = 40000;
									int keyLength = 128;
									try {
										if (UPDSArray[0] != null & !UPDSArray[0].isEmpty() && UPDSArray[1] != null
												&& !UPDSArray[1].isEmpty()
												&& UPDSArray[4] != null & !UPDSArray[4].isEmpty()) {
											logger.info("username and passowrd is not empty");
											SecretKeySpec createSecretKey = CreateSecurePasswordClass.createSecretKey(
													UPDSArray[0].toCharArray(), salt, iterationCount, keyLength,
													logger);
											DecrypetedPassowrd = CreateSecurePasswordClass.decrypt(UPDSArray[1],
													createSecretKey, logger);
											LoginDeatilsClass LoginDetails = new LoginDeatilsClass();
											if (LoginDetails.CheckConnection(UPDSArray[0], DecrypetedPassowrd,
													logger)) {
												logger.info("username and passowrd Enetered are correct");
												CrawlerClass Crawler = new CrawlerClass();
												Crawler.GenerateDocument(LoginDetails.getcookies(), fromdate, todate,
														UPDSArray[4], logger);
												logger.info("CSV FIle generated");
											} else {
											}
										}
									} catch (Exception e) {
										logger.info("Username or password was changed in config file");
										System.out.println(
												"Username or password or Path to save CSV was changed, Please initilize the config file once again");
									}
								} else {
									logger.info("config file does not exist");
									System.out.println("File does not exist");
									return;
								}
							} catch (IOException e) {
								logger.info("Issue with getting file");
								e.printStackTrace();
							}

						}
					}

				} else {
					System.out.println(
							"For Starting crawler argument should be in format: -from 15/05/2015 -to 20/05/2017 -crawler");
				}
			}
		} catch (ParseException exception) {
			System.out.print("Parse error: ");
			System.out.println(exception.getMessage());
		}
	}
}
