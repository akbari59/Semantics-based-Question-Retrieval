package uk.ac.gla.dcs;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class DUtil {
	
	private String dbName;
	private String dbServer;
	private String userName;
	private String password;
	private String collection;
	private DB db;
	private MongoClient mongo;
	private DBCollection table;
	private long totalSize, totalNumber;
	
	
	public DUtil(String dbName, String dbServer, String collection, String username, String password){
		this.dbName = dbName;
		this.dbServer = dbServer;
		this.collection = collection;
		this.userName = username;
		this.password = password;
		MongoClient mongo;
		try {
			mongo = new MongoClient(this.dbServer, 27017);
			
			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB(dbName);
			
			boolean auth = db.authenticate(this.userName, this.password.toCharArray());
		 
			/**** Get collection ****/
			// if collection doesn't exists, MongoDB will create it for you
			table = db.getCollection(this.collection);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public DBCollection getDBCollection()
	{
		return this.table;
	}
}
