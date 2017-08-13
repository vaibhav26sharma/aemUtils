package org.aemUtils.tech.aemUtils.core.servlets;
/*import com.google.common.base.Stopwatch;
 */
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LogParser {



	/*public void parseLog(){*/

	/**
	 * parseLogger reads the AEM OOTB error.log file and creates a new log file
	 * out of it containing just the error traces 
	 * 
	 * sourcePath= Path to read the error.log file from
	 * destinationPath= Path to write & save the errorOnly.log file to
	 */
	private static final String FILENAME = "C://Users//vaisharm//Documents//AEM_DEV//Instances//Author62//crx-quickstart//logs//error.log";

	public void parseLogger(String sourcePath, String destinationPath){


		FileReader fr=null;
		BufferedReader br=null;
		ArrayList<String> al = new ArrayList<String>();
		Stream<String> stream=null;
		List<String> errorOnlyList= new ArrayList<String>();

		try {

			fr= new FileReader(FILENAME);
			br= new BufferedReader(fr);
			stream= br.lines();

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				al.add(sCurrentLine);
			}


			for(int i=0;i<al.size();i++){
				String line= al.get(i);

				if(line.contains("ERROR")){

					errorOnlyList.add(al.get(i) + '\n');
				}


			}

			try{  
				FileOutputStream fileOut = new FileOutputStream("C:\\Users\\vaisharm\\Documents\\AEM_DEV\\Instances\\Author62\\crx-quickstart\\logs\\errorOnly.log");  
				ObjectOutputStream oos = new ObjectOutputStream (fileOut);  
				oos.writeObject (errorOnlyList);  
			}catch(Exception e){  
				System.err.println(e.getMessage());  
			}  

		}catch(IOException e){


		}



	}

}