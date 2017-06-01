package UnitTestCases;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
@RunWith(Parameterized.class)
public class JunitIsPathCorrect {
	public String path;
	Boolean expected = false;
	private static Scanner keyboard;
	static Boolean UserInputTrueorFalse=false;
	
	 @Parameterized.Parameters
     public static Collection Path() {
		 
//			keyboard = new Scanner(System.in);
//			System.out.println("Enter path to save CSV");  
//			String  Path = keyboard.next();
//			
//			try{
//				System.out.println("Is the path to save CSV correct? \n Type true for yes \n Type false for no");  
//				
//				UserInputTrueorFalse = keyboard.nextBoolean();
//			}
//		       
//				catch(Exception e)
//				{
//					System.out.println("Entered Value is not Boolean");
//					Path();
//					 
//				}
//		 
//        return Arrays.asList(new Object[][] {
//           
//           { Path,UserInputTrueorFalse},
//          
//           
//        });
        
        
        return Arrays.asList(new Object[][] {
            {  System.getProperty("user.home"), true},
            {  System.getProperty("user.home") + "/RandomFolder",false},
            {  System.getProperty("user.home") + "/testfolder",false},
            { System.getProperty("user.home") + "/desktop",true}
         });
     }
	 
	 public JunitIsPathCorrect(String path, 
		        Boolean expected) {
		       System.out.println("TestJunit-> Path:"+path+" expected:"+expected);
		        this.path = path;		       
		        this.expected =expected;
		     }

		
	 
		@Test
		public void test() throws IOException, ParseException {

			
		Boolean Result = Getpath(path); 
			
					assertEquals(expected, Result);	
		
			}

		private Boolean Getpath(String path2) {
			try {
				
				String outputFolder = path2 + File.separator + "careLink-Export";
				
				
				PrintWriter pw1 = new PrintWriter(new File(outputFolder + (new Date().getTime()) + ".csv"));
				pw1.write("Test");
				pw1.close();
				
				return true;

			} catch (IOException e) {
				

				return false;
			}
		}	

}
