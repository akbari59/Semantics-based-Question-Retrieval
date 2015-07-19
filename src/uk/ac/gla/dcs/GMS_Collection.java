package uk.ac.gla.dcs;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

public class GMS_Collection {

	public static void main(String[] args) throws JSONException, FileNotFoundException {
		// TODO Auto-generated method stub
		String dbName = "path";
		//String dbServer = "localhost";
		String dbServer = "imcdserv1.dcs.gla.ac.uk";		
		String toCollection = "category2";
		MongoClient mongo;
		DBCollection toTable;
		String date = "";
		Date d = new Date();	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		date = sdf.format(d);
		DB db;
	    DBCollection table;
	    HashMap map = new HashMap();	
		try {
			//prepare the geo parser
			
	        ArrayList <ResolvedLocation> al = new ArrayList();
	       
	        
			//fetch the data of today
			mongo = new MongoClient(dbServer, 27017);
			System.out.println(mongo.getVersion());
			/**** Get database ****/
			db = mongo.getDB(dbName);		
			db.authenticate("gms", "rdm$248".toCharArray());			
			toTable = db.getCollection(toCollection);											
			BasicDBObject searchQuery = new BasicDBObject();
			//searchQuery.put("","");		 
			DBCursor cursor = toTable.find(searchQuery);
		    System.out.println(toTable.count());	
		    String str = "";
		    
		    //table to store;
		  
		    DUtil du = new DUtil("path", "imcdserv1.dcs.gla.ac.uk", "category2","gms","rdm$248");
			table = du.getDBCollection();
			//table.drop();
			System.out.println(cursor.count());
			int count = 1;
			while(cursor.hasNext()) {
				str = cursor.next().toString();	
				//System.out.println(str);
				JSONObject obj;
				obj = new JSONObject(str);    
				String  id =  obj.get("_id").toString();	
				String category = obj.getString("path").replaceAll("<.*?>", "");															      
		        BasicDBObject newsDocument = new BasicDBObject();
		        id = id.replaceAll("\\{\"\\$oid\":", "").replaceAll("\\}", "").replaceAll("\\\\", "").replaceAll("\"", "").trim();
		        //System.out.println(id);
		        map.put(id,category);	    	
		    	count++;	    			    	    				
			}
			System.out.println(count);
			
			//toTable.drop();
			mongo.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    dbName = "gmsTry"; //path
		//String dbServer = "localhost";
		dbServer = "imcdserv1.dcs.gla.ac.uk";		
	    toCollection = "gmsNews"; //category2/category
		
		
	     date = "";
		 d = new Date();	
		
		date = sdf.format(d);
	
	    
		try {
			//prepare the geo parser
			
	        ArrayList <ResolvedLocation> al = new ArrayList();
	        	
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
		    
		    
		    DUtil du = new DUtil("gmsTry", "imcdserv1.dcs.gla.ac.uk", "gms","gmsNews","rdm$248");
			table = du.getDBCollection();
			int count = 1;
			
			System.out.println("size of cursor: "+cursor.size());
			while(cursor.hasNext()) {
			    str = cursor.next().toString();			    
				JSONObject obj;
				obj = new JSONObject(str);
			    if(str.indexOf("title") < 0)
			    	continue;
				String  id =  obj.get("_id").toString();
				id = id.replaceAll("\\{\"\\$oid\":", "").replaceAll("\\}", "").replaceAll("\\\\", "").replaceAll("\"", "");
				String title = obj.getString("title").replaceAll("<.*?>", "");				
				String description = obj.getString("description").replaceAll("<.*?>", "");							 
				String timeStamp = obj.getString("timeStamp").replaceAll("<.*?>", "");				
				String category = obj.getString("category").replaceAll("<.*?>", "");				
				String url = obj.getString("url").replaceAll("<.*?>", "");				
				String source = obj.getString("source").replaceAll("<.*?>", "");				
				String mainStory = obj.getString("mainStory").replaceAll("<.*?>", "");	
					
		        //{"$oid":"544fc20de4b0c8ffb9c72e4e"} 544fe499e4b0387e021b2c16  55354a27e4b03e9c47bbdadd
		        BasicDBObject newsDocument = new BasicDBObject();
		       
		    	//if(map.containsKey(id.trim()))
		         if(count < 15000)
		    	{
		    		System.out.println(mainStory);
		    		PrintWriter pw = new PrintWriter("E:\\databackup\\new\\"+count+".txt");	
		    		pw.print(mainStory.replaceAll("\\n","").replaceAll("[^\\w ]",""));
		    		count++;
		    		pw.close();
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
