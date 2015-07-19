package uk.ac.gla.dcs;
// step 2
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searching {
	
	public static String search(String query, String indexPath) throws ParseException, CorruptIndexException, IOException
	  {
		    // 2. query
		    String querystr = query;
		    Directory index = FSDirectory.open(new File(indexPath));
		    // the "title" arg specifies the default field to use
		    // when no field is explicitly specified in the query.
		    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		    Query q = new QueryParser(Version.LUCENE_40, "title", analyzer).parse(querystr);
	        
		    // 3. search
		    int hitsPerPage =50;
		    IndexReader reader = DirectoryReader.open(index);
		    IndexSearcher searcher = new IndexSearcher(reader);
		    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		    searcher.search(q, collector);
		  //  ScoreDoc[] hits = collector.topDocs().scoreDocs;
		    ScoreDoc[] hits = collector.topDocs().scoreDocs;
		    
		    StringBuffer sb = new StringBuffer();
		    // 4. display results
		   // System.out.println("Found " + collector.getTotalHits() + " hits.");
		    for(int i=0;i<hits.length;++i) {
		      int docId = hits[i].doc;
		      Document d = searcher.doc(docId);
		      String str = ((i + 1)  +d.get("title").replaceAll(querystr, "")  + " "+d.get("description").replaceAll(d.get("title"),"").replace("@en .","") +"\n\n");
		      
		      sb.append(str.replaceAll("[^a-z A-Z]", ""));
		     // System.out.println(str);
		    }
		    // reader can only be closed when there
		    // is no need to access the documents any more.
		    reader.close();
		    
		    return sb.toString();
	  }
	
  public static void searchAll(String filePath, String savePath)
  {
		try {
			String indexPath = "E:\\databackup\\index_entities";
			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();
			PrintWriter pw = new PrintWriter("E:\\databackup\\list_entities\\"+savePath);
			HashMap m = new HashMap();
			 BufferedReader br = new BufferedReader(new FileReader(filePath));
		        String str = "";
		        String result = "";
		       
		        while((str = br.readLine()) != null)
		        {
		        	
		        	result = search(str, indexPath);
		        	m.put(str,result);
		        }
		        br.close();
		     
		    BufferedReader br1 = new BufferedReader(new FileReader(filePath));
		    while((str = br1.readLine()) != null)
		    {
		    	  Iterator entries = m.entrySet().iterator();
			    	while (entries.hasNext()) {
			    	  Entry thisEntry = (Entry) entries.next();
			    	  String key = (String)thisEntry.getKey();
			    	  String value = (String)thisEntry.getValue();
			    	  if(value.indexOf(str) > 0 && !str.equals(key))
			    	  {
			    		  System.out.println(str+ ":"+key);
			    		  pw.println(key+":"+str);
			    		 
			    		  
			    	  }		 
		               }
		    
		       
		  
		    }    
		    br1.close();
		    pw.close();
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }
  public static void main(String args[])
  
  {
	
	 
	  //System.out.println(System.getProperty("user.dir"));
	  // index(indexPath);
	  
		  //String indexPath = "E:\\databackup\\index_entities";
		  String path = "E:\\databackup\\gms_entitylist1"; 
		  
		  String files;
		  File folder = new File(path);
		  File[] listOfFiles = folder.listFiles(); 
		 
		  for (int i = 0; i < listOfFiles.length; i++) 
		  {
		 
		   if (listOfFiles[i].isFile()) 
		   {
		   files = listOfFiles[i].getName();
		   String filePath = path+"\\"+files;
		   searchAll(filePath,files);
		    }
		  }
		
		 // searchAll("E:\\databackup\\gms_entitylist");
		  //search("High Commissioner",indexPath);
		  
	
  }
}
