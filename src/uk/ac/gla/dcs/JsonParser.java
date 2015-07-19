package uk.ac.gla.dcs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.bericotech.clavin.GeoParserFactory;
import com.bericotech.clavin.resolver.ResolvedLocation;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class JsonParser {
	 public static Map sortByValue(Map unsortedMap) {
 		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
 		sortedMap.putAll(unsortedMap);
 		return sortedMap;
 	}
	 

	public static void main(String[] args) throws Exception, JSONException {
		// TODO Auto-generated method stub
	 
		   	BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\root\\Documents\\MONGODB\\output_geo.json"));
			
				String str = "";		
				com.bericotech.clavin.GeoParser parser = GeoParserFactory.getDefault("C:/Users/root/Documents/CLAVIN-master/IndexDirectory");
		       
		        int count = 0, i = 0;
		        while((str = br.readLine()) != null)
		        {
		        	count++;
		        }
		        br.close();
		        br = new BufferedReader(new FileReader("C:\\Users\\root\\Documents\\MONGODB\\output_geo.json"));
		        System.out.println(count);
		      //table to store;
			    DUtil du = new DUtil("location", "imcdserv1.dcs.gla.ac.uk", "glasgow_2","gms","rdm$248");
				DBCollection table = du.getDBCollection();
				table.drop();
				count = 0;
				while((str = br.readLine()) != null)
				{			
					JSONObject obj;
						obj = new JSONObject(str);
						String  id =  obj.get("_id").toString();
						String title = obj.getString("title").replaceAll("<.*?>", "");				
						String description = obj.getString("description").replaceAll("<.*?>", "");							 
						String timeStamp = obj.getString("timeStamp").replaceAll("<.*?>", "");				
						String category = obj.getString("category").replaceAll("<.*?>", "");				
						String url = obj.getString("url").replaceAll("<.*?>", "");				
						String source = obj.getString("source").replaceAll("<.*?>", "");				
						String mainStory = obj.getString("mainStory").replaceAll("<.*?>", "");						
				        List<ResolvedLocation> resolvedLocations = parser.parse(mainStory);
				        HashMap map = new HashMap();
				        //remove the location outside of scotland
				        List<ResolvedLocation> filtered_locations = new <ResolvedLocation> ArrayList() ;
				        
				        BasicDBObject newsDocument = new BasicDBObject();
				        
						    	if(filtered_locations.size() >= 1)
						    	{
						    		
						    		
						    		 ResolvedLocation rl = resolvedLocations.get(0);
						    		// if(!map.containsKey(rl)  )
						    		 {
						    			 System.out.println("good "+id);
						    		 System.out.println("{ \"_id\" :" +id.replaceAll("\\{\"\\$oid\":", "").replaceAll("\\}", "")
						   	        		 + ", \"title\" :" +"\""+title+"\""+ ", \"url\" :" +"\""+url+"\""+ ", \"longitude\" :" +"\""+rl.getGeoname().getLongitude()+
				                             ", \"latitude\" :" +"\""+rl.getGeoname().getLatitude()+"\""+ "\""+"},\n");
						    		    
										newsDocument.put("_id", id);
										newsDocument.put("title", title);
										newsDocument.put("url", url);
										newsDocument.put("timeStamp", timeStamp);
										newsDocument.put("longitude", rl.getGeoname().getLongitude());
										newsDocument.put("latitude", rl.getGeoname().getLatitude());
										table.insert(newsDocument);
						    		
						    		 }
						    		 i++;
						    	}
						    	
						    	
				    	
				    	
				    	    
						
					
					         	          
		        }							      
			 
		      br.close();
		      
		      System.out.println(i+"\n"+count);
		      
	}

}

class ValueComparator implements Comparator {
	 
	Map map;
 
	public ValueComparator(Map map) {
		this.map = map;
	}
 
	public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		return valueB.compareTo(valueA);
	}
}