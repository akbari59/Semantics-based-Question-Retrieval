package uk.ac.gla.dcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ExperimentsSampling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("E:\\databackup\\content.rdf.u8"));
			int count = 0;
			String str = "";	
			String pattern = "<journal>(.*)</jounnal>";
           
		      // Create a Pattern object
		    Pattern r = Pattern.compile(pattern);

		      // Now create matcher object.
		    Matcher m;
		   int information_light = 0;
		   String journal = "";
		   String temp = "";
		   StringBuffer sb = new StringBuffer();
			while((str = br.readLine()) != null)
			{	
				//System.out.println(str);
				if(str.indexOf("</ExternalPage>") < 0)
				{
					sb.append(str);
				}
				else
				{
					//System.out.println(sb);
					pattern = "<topic>(.*)</topic>";
					r = Pattern.compile(pattern);
					m  = r.matcher(sb.toString());
				 
					if(m.find())
					{
						String category_2 ;
						journal = m.group(1);
						System.out.println(journal);
						String category_1 = journal.split("/")[2];
						if(journal.split("/").length > 4)
						category_2= journal.split("/")[3];
						else
						category_2 = null;
						
						pattern = "<d:Description>(.*)</d:Description>";
					    r = Pattern.compile(pattern);
					    m  = r.matcher(sb.toString());
						if(m.find() )
						{
							PrintWriter pw = new PrintWriter("E:\\databackup\\ODP_Entity\\"+count+".txt");
							pw.println(category_1 +"\t"+category_2+"\n"+ m.group(1));
							pw.close();
							/*
							if ((str = m.group(1)).length() > 15)
								pw.println(m.group(1).substring(0,14));
							else
								pw.println(m.group(1).substring(0,5));
								*/
						}			
						
						
						
					}
					
					
					
									
					count++;
					if (count > 50000)
						break;
					//System.out.println(sb);
					sb = new StringBuffer();
				}
				
					          
	        }
			
			br.close();

			//System.out.println(count);      
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						      
	    
	}

}
