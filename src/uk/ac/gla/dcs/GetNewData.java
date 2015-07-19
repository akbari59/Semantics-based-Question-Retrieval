package uk.ac.gla.dcs;

import java.net.UnknownHostException;






import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bericotech.clavin.GeoParserFactory;
import com.bericotech.clavin.resolver.ResolvedLocation;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class GetNewData {

	
   public static void main(String [] args) throws Exception
   {
	   

		
	    String dbName = "gmsTry"; //path
		//String dbServer = "localhost";
		String dbServer = "imcdserv1.dcs.gla.ac.uk";		
		String toCollection = "gmsNews"; //category2/category
		MongoClient mongo;
		DBCollection toTable;
		String date = "";
		Date d = new Date();	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		date = sdf.format(d);
		DB db;
	    DBCollection table;
		try {
			//prepare the geo parser
			com.bericotech.clavin.GeoParser parser = GeoParserFactory.getDefault("C:/Users/root/Documents/CLAVIN-master/IndexDirectory");
	        ArrayList <ResolvedLocation> al = new ArrayList();
	        HashMap map = new HashMap();	
			//fetch the data of today
			mongo = new MongoClient(dbServer, 27017);
			System.out.println(mongo.getVersion());
			/**** Get database ****/
			db = mongo.getDB(dbName);		
			db.authenticate("gms", "rdm$248".toCharArray());
			
			toTable = db.getCollection(toCollection);											
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("timeStamp", java.util.regex.Pattern.compile(".*2014|.*2015"));		 
			DBCursor cursor = toTable.find(searchQuery);
		    System.out.println(toTable.count());	
		    String str = "";
		    
		    //table to store;
		    DUtil du = new DUtil("location", "imcdserv1.dcs.gla.ac.uk", "glasgow_1","gms","rdm$248");
			table = du.getDBCollection();
			table.drop();
			int count = 0;
		    //parse it with clavin, then replace it with the database.
			while(cursor.hasNext()) {
			   str = cursor.next().toString();			    
				JSONObject obj;
				obj = new JSONObject(str);
			    if(str.indexOf("title") < 0)
			    	continue;
				String  id =  obj.get("_id").toString();
				String title = obj.getString("title").replaceAll("<.*?>", "");				
				String description = obj.getString("description").replaceAll("<.*?>", "");							 
				String timeStamp = obj.getString("timeStamp").replaceAll("<.*?>", "");				
				String category = obj.getString("category").replaceAll("<.*?>", "");				
				String url = obj.getString("url").replaceAll("<.*?>", "");				
				String source = obj.getString("source").replaceAll("<.*?>", "");				
				String mainStory = obj.getString("mainStory").replaceAll("<.*?>", "");	
				System.out.println(category);
				
		        List<ResolvedLocation> resolvedLocations = parser.parse(title+description+mainStory);
		        BasicDBObject newsDocument = new BasicDBObject();
		    	if(resolvedLocations.size() >= 1)
		    	{
		    		 ResolvedLocation rl = resolvedLocations.get(0);
		    		 for(ResolvedLocation element:resolvedLocations)
		    		 {
		    			 
		    			 if(element.getGeoname().getLatitude()<59 && element.getGeoname().getLatitude()>51 &&(element.getGeoname().getLongitude()>-5 && element.getGeoname().getLongitude() < 10))
		    			 {
		    				 if(element.getGeoname().getLatitude() == 56.0 || element.getGeoname().getLongitude() == -4.0 || element.getGeoname().getLongitude() ==4.25763)
		    					 continue;
		    				 rl = element;
		    				 break;
		    			 }
		    			 else
		    			 {
		    				 continue;
		    			 }
		    				 
		    		 }
		    		 if(rl.getGeoname().getLatitude() == 56.0 || rl.getGeoname().getLongitude() ==4.25763)
		    			 continue;
		    		 //if(!map.containsKey(rl))
		    		 {
		    		   // System.out.println("{ \"_id\" :" +id.replaceAll("\\{\"\\$oid\":", "").replaceAll("\\}", "")
		   	        	//	 + ", \"title\" :" +"\""+title+"\""+ ", \"url\" :" +"\""+url+"\""+ ", \"longitude\" :" +"\""+rl.getGeoname().getLongitude()+
                          //   ", \"latitude\" :" +"\""+rl.getGeoname().getLatitude()+"\""+ "\""+"},\n");
		    	    	DecimalFormat df = new DecimalFormat("#.00"); 
						newsDocument.put("_id", id.replaceAll("\\{\"\\$oid\":", "").replaceAll("\\}", "").replaceAll("\\\\", "").replaceAll("\"", ""));
						newsDocument.put("name",rl.getGeoname().getName());
						newsDocument.put("title", title);
						newsDocument.put("url", url);
						newsDocument.put("timeStamp", timeStamp);
						newsDocument.put("longitude", df.format(rl.getGeoname().getLongitude()));
						newsDocument.put("latitude", df.format(rl.getGeoname().getLatitude()));
						table.insert(newsDocument);
		    		
		    		 }
		    		 count++;
		    	}
		    	
		    	
		    	    
				
			}
			System.out.println(count);
			
			
			//toTable.drop();
			mongo.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

   }
}
