package uk.ac.gla.dcs;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
 


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bericotech.clavin.GeoParserFactory;
import com.bericotech.clavin.resolver.ResolvedLocation;
import com.bericotech.clavin.util.TextUtils;



public class Example {
	public static void main(String [] str) 
	{
		try {
			   
			   
			// Instantiate the CLAVIN GeoParser
			       com.bericotech.clavin.GeoParser parser = GeoParserFactory.getDefault("C:/Users/root/Documents/CLAVIN-master/IndexDirectory");
		        
			        // Unstructured text file about Somalia to be geoparsed
			       File inputFile = new File("E:\\DeskEvent\\result.txt");
			        
			       // Grab the contents of the text file as a String
			       String inputString = TextUtils.fileToString(inputFile);
			       HashMap map = new HashMap();
			       int freq = 0;
		           // Parse location names in the text into geographic entities
		           List<ResolvedLocation> resolvedLocations = parser.parse(inputString);
		           for (ResolvedLocation resolvedLocation : resolvedLocations)
		           {
		        	   if(map.containsKey(resolvedLocation.getLocation().toString().replaceAll("\"","").replaceAll(":.*", "")))
		        	   {
		        		   freq = (int) map.get(resolvedLocation.getLocation().toString().replaceAll("\"","").replaceAll(":.*", ""));
		        		   map.remove(resolvedLocation.getLocation().toString().replaceAll("\"","").replaceAll(":.*", ""));
		        		   map.put(resolvedLocation.getLocation().toString().replaceAll("\"","").replaceAll(":.*", ""), freq+1);
		        	   }
		        	   else
		        	   {
		        		   map.put(resolvedLocation.getLocation().toString().replaceAll("\"","").replaceAll(":.*", ""),1);
		        	   }
		           }
			           
		        
		           Set<ResolvedLocation> targetSet = new HashSet<>(resolvedLocations);
			       
		           	         
		       
			       
		       PrintWriter pw = new PrintWriter("C:\\Users\\chen\\Desktop\\display.html");
		       BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\chen\\Desktop\\template.txt"));		
		       String str1 = "";
		       StringBuffer sb = new StringBuffer("");
		       int i = 1;
		       System.out.println("size:  "+targetSet.size());
				while((str1 = br.readLine()) != null)
				{
					if(str1.indexOf("var locations")<0)
						
					{
						sb.append(str1);
					}
					else
					{
						sb.append(str1);
						pw.print(sb);
						for (ResolvedLocation resolvedLocation : targetSet)
						{
							i++;
										
							if(resolvedLocation.getGeoname().getPrimaryCountryName() == "United Kingdom" || resolvedLocation.getGeoname().getPrimaryCountryName() == "Ireland")
							pw.println("["+"'"+resolvedLocation.getLocation().toString().replaceAll("\"","").replaceAll(":.*", "").replaceAll("'", "")+" "+map.get(resolvedLocation.getLocation().toString().replaceAll("\"","").replaceAll(":.*", ""))+"'"+","+resolvedLocation.getGeoname().getLatitude()+","+resolvedLocation.getGeoname().getLongitude()+","+i+"],");
							else
							System.out.println(resolvedLocation.getGeoname().getPrimaryCountryName() );							
						}
						pw.print("[\"Scotland\", 55, -4, 300]");
					    sb = new StringBuffer("");
					}
				}
				pw.print(sb);
				br.close();
				pw.close();
				
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
	}
	

}
