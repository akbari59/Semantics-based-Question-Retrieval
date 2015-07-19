package uk.ac.gla.dcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVParser {
	 /**
	 * @param filePath_1
	 * @param savePath
	 */
	/**
	 * @param filePath_1
	 * @param savePath
	 */
	public static void concatenate(String filePath_1,  String savePath)
	  {
		  try {
			BufferedReader br = new BufferedReader(new FileReader(filePath_1));
			PrintWriter pw = new PrintWriter(savePath);
			String str = "";
			int count = 0;
			Pattern p ;
		    Matcher m ; // get a matcher object
		    String mainstory = "";
		       					    		     		      		   		    		    
			while((str = br.readLine()) != null)
			{
				int length = str.split("\"").length;
					
				
				 String category = "\"mainStory\"(.*)\"images\" ";
				 p = Pattern.compile(category);			 
				 // find question from the page
				 m = p.matcher(str);
				 if (m.find()) {
					  mainstory =   m.group(1).trim().replaceAll("<.*?>","").replaceAll(":","");
				      System.out.println("matches:\t"+mainstory);
				      pw.println(mainstory);
				         
				 } else {
				      System.out.println("NO MATCH" +str.replaceAll(".*\"mainStory\"", "").replaceAll("<.*?>",""));
				      mainstory = str.replaceAll(".*\"mainStory\"", "").replaceAll("<.*?>","");
				      pw.println(mainstory);
				 }	
			}
			
			br.close();
			pw.close();
			System.out.println(count);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }
	  
	  public static void main(String args[])
	  {
		  concatenate("C:\\Users\\root\\Documents\\MONGODB\\output.csv","e:\\DeskEvent\\result.txt");
	  }

}
