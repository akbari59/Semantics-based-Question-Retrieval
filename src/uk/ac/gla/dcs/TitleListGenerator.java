package uk.ac.gla.dcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bericotech.clavin.GeoParserFactory;
import com.bericotech.clavin.resolver.ResolvedLocation;
import com.bericotech.clavin.util.TextUtils;

public class TitleListGenerator {
	public static void main(String [] str) 
	{
		try {
			   
			   
			// Instantiate the CLAVIN GeoParser
			       com.bericotech.clavin.GeoParser parser = GeoParserFactory.getDefault("C:/Users/root/Documents/CLAVIN-master/IndexDirectory");
		           ArrayList <ResolvedLocation> al = new ArrayList();
		           HashMap map = new HashMap();
			       BufferedReader br1 = new BufferedReader(new FileReader("E:\\DeskEvent\\result.txt"));
			       String str2 = "";
			       while((str2 = br1.readLine()) != null)
			       {
			    	   System.out.println(str2);
			    	   List<ResolvedLocation> resolvedLocations = parser.parse(str2);
			    	   if(resolvedLocations.size() >= 1)
			    	   {
			    		   ResolvedLocation rl = resolvedLocations.get(0);
			    		   al.add(rl);
			    		   if(!map.containsKey(rl))
			    		   map.put(rl,str2.replaceAll("\"", ""));
			    		   System.out.println(rl.getLocation());
			    	   }
			    	  
			       }
			        
			       Set<ResolvedLocation> targetSet = map.keySet();
			       
	               System.out.println("done!");
			       
		           	         
		       
			// Save the result to the map for display       
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
							pw.println("["+"'"+(resolvedLocation.getLocation().toString()+"\t<br>"+map.get(resolvedLocation)).replaceAll("\"","").replaceAll(":", "")
									.replaceAll("'", "").replaceAll("description.*", "").replaceAll(",", "").replaceAll("Daily Record", "").replaceAll("-", " ").replaceAll("[0-9]","")
									+" "+"'"+","+resolvedLocation.getGeoname().getLatitude()+","+resolvedLocation.getGeoname().getLongitude()+","+i+"],");
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
