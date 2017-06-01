package UnitTestCases;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
@RunWith(Parameterized.class)

public class JunitAreDatesCorrect {
	public static String dateFormat = "dd/MM/yyyy";
	public Date FormatedStartDate;
	public Date FormatedEndDate;
	Boolean Result = false;
	public String fromdate,todate;
	Boolean expected = false;
	  
	
	 @Parameterized.Parameters
     public static Collection Dates() {
        return Arrays.asList(new Object[][] {
           { "15/05/2015", "20/05/2016", true},
           { "18-05/2015", "15/05/2015",false},
           { "15-05/2015", "20/05/2016",false},
           { "01/01/1985", "20/05/2016",false},
           { "15/05/2015", "20/05/2018",false},
           { "30/05/2017", "30/05/2017",true},
           { "30/05/2017", "32/05/2017",false},
           { "30/05/2015", "29/02/2017",false}
        });
     }
	
	 public JunitAreDatesCorrect(String startdate, 
		        String enddate,Boolean expected) {
		       System.out.println("TestJunit-> startdate:"+startdate+" enddate:"+enddate);
		        this.fromdate = startdate;
		        this.todate = enddate;
		        this.expected =expected;
		     }
	 

	@Test
	public void test() throws IOException, ParseException {

//		System.out.println("from date for checking dates is " + fromdate);
//		System.out.println("to date for checking dates is " + todate);
	System.out.println("Expected result is " + expected);
		
	Result = (GetStratDate(fromdate) && GetEndDate(fromdate,todate)); 
		
				assertEquals(expected, Result);	
	
		}	

	private boolean GetEndDate(String fromdate,String todate) throws ParseException {
	
	
				Date Todaydate = new Date();

				SimpleDateFormat FormatTodayDate = new SimpleDateFormat(dateFormat);
				String todayDate = FormatTodayDate.format(Todaydate);
				
				Date ValidStardDate = new SimpleDateFormat(dateFormat).parse("01/01/1998");
				FormatedStartDate = new SimpleDateFormat(dateFormat).parse(fromdate);

				try {
					FormatedEndDate = new SimpleDateFormat(dateFormat).parse(todate);
				} catch (Exception e) {
	
					//System.out.println("End Date is not in correct format \n Date should be in Format of DD/MM/YYYY  Example: 13/03/2017");
					return false;
				}
				try {
					DateFormat ValidEndDate = new SimpleDateFormat(dateFormat); // To validate End date
					ValidEndDate.setLenient(false);
					ValidEndDate.parse(todate);
					if (FormatedEndDate.after(new SimpleDateFormat(dateFormat).parse(todayDate))
							|| FormatedEndDate.before(ValidStardDate)) {
						//System.out.println("You can only enter End date between 01/01/1998 and Today's Date!! ");
						return false;
					}
					if (FormatedEndDate.before(FormatedStartDate)) {
						//System.out.println("End Date cannot be earlier than Start date");
						return false;
					}
					return true;
				} catch (Exception e) {
	
				//	System.out.println("End Date is not Valid");
					return false;
				}

	}

	private boolean GetStratDate(String fromdate) throws ParseException {
		// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				
						SimpleDateFormat FormatTodayDate = new SimpleDateFormat(dateFormat); // to format today's date as DD/MM/YYYY
						FormatTodayDate.setLenient(false);
						Date Todaydate = new Date();
						String todayDate = FormatTodayDate.format(Todaydate);
						
						

						Date ValidStardDate = new SimpleDateFormat(dateFormat).parse("01/01/1998"); 
						// To validate Beginning of Date

						try {
							
							FormatedStartDate = new SimpleDateFormat(dateFormat).parse(fromdate);
						} catch (Exception e) {
				
							//System.out.println("Start Date is not in correct format \n Date should be in Format of DD/MM/YYYY  Example: 13/03/2017");
							return false;
						}
						try {
							DateFormat ValidStartdate = new SimpleDateFormat(dateFormat); // To validate start date
							ValidStartdate.setLenient(false);
							ValidStartdate.parse(fromdate);
							if (FormatedStartDate.before(ValidStardDate)
									|| FormatedStartDate.after(new SimpleDateFormat(dateFormat).parse(todayDate))) {
								//System.out.println("You can only enter Start date between 01/01/1998 and Today's Date!!");
								return false;
							}
							return true;
						} catch (Exception e) {
				
							//System.out.println("Start Date is not Valid");
							return false;

						}
				

	}

//		assertEquals(CrawlerCarelink.TrueorFalse, result);
	}

