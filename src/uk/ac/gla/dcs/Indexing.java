package uk.ac.gla.dcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.JSONException;
import org.json.JSONObject;

public class Indexing {

	
	public static void index(String indexPath) throws IOException
	  {
		  // 0. Specify the analyzer for tokenizing text.
		    //    The same analyzer should be used for indexing and searching
		    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		    
		    // 1. create the index
		   // Directory index = new RAMDirectory();
		    Directory index = FSDirectory.open(new File(indexPath));
		    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);

		    IndexWriter writer = new IndexWriter(index, config);   
		   	BufferedReader br = new BufferedReader(new FileReader("E:\\databackup\\mappingbased_properties_cleaned_en.nt\\mappingbased_properties_cleaned_en.nt"));
			
				String str = "";
				String temp = "";
				String title = "";
				StringBuffer sb = new StringBuffer();
				while((str = br.readLine()) != null)
				{			
					
					if(!str.endsWith(">."))
					{						
						String [] title_text = str.split(">");
					    title = title_text[0].replaceAll("<http://dbpedia.org/resource/", "").replaceAll("_", " ");	
					    if(title.split(" ").length > 4)
					    	title = title.split(" ")[0]+title.split(" ")[1]+title.split(" ")[2]+title.split(" ")[3];
					    title = title.replaceAll("[^0-9a-z A-Z]", "");
						String description = str.replaceAll("<http://dbpedia.org/resource/.*?>","").replaceAll("_", " ").replaceAll("<.*?>", "").replaceAll(">", "").replaceAll("\\.","");					
						//System.out.println(str.replaceAll("<http://dbpedia.org/resource/","").replaceAll("_", " ").replaceAll("<.*?>", "").replaceAll(">", ""));
						if(!title.trim().equals(temp.trim()) )
						{
							
							if(sb.toString().trim().length() > 0)
							{
								description = sb.toString().trim().replaceAll("[^a-z A-Z]", "");
								if(description.length() < 1500)
								addDoc(writer, temp, description); 
								//str = sb.toString().replaceAll("\\^","");
								System.out.println(title);
								
							}
							sb = new StringBuffer(); 
						}
						else
						{
							//System.out.println("good");
							sb.append(description.replaceAll("@en", "").replaceAll("\"","").replaceAll("\\\\u","").
									replaceAll("-","").replaceAll("[0-9]", "").replaceAll("\\_", "").replaceAll("\\^", "").trim()+" ");
						}
						temp = title;      
					}
					  	          
		        }							      
		      System.out.println("total number of documents: "+writer.numDocs());
		      br.close();
		      writer.close();
	  }
	
	  private static void addDoc(IndexWriter w,  String title,  String description) throws IOException {
		    Document doc = new Document();    		   
		    doc.add(new TextField("title", title, Field.Store.YES));		   
		    doc.add(new StringField("description", description, Field.Store.YES));
		    w.addDocument(doc);
		  }
	  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String indexPath = "E:\\databackup\\index_entities";
		  //System.out.println(System.getProperty("user.dir"));
		   try {
			index(indexPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}