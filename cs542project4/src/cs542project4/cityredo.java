package cs542project4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class cityredo {

	relation C = null;
	relation logCity = null;
	tuple cityredo = null;
	tuple citylog = null;
	File city2;
	FileWriter cityredowriter;
	private List<tuple> utuples = new ArrayList<tuple>();
	private BufferedReader pcity2;
	
	public cityredo(relation c, relation logcity){
		this.C = c;
		this.logCity = logcity;
	}
	
	public void Open() {
		C.Open();
		logCity.Open();
		cityredo = C.GetNext();
		citylog = logCity.GetNext();
	}

	public void redo() throws IOException {
		cityredowriter = new FileWriter("city 2.csv");;
		
		while(cityredo.getValues()[0].equals(citylog.getValues()[1]) == true && cityredo.getValues()[4] != null ){
			String[] a = cityredo.getValues();
			a[4] = citylog.getValues()[3].substring(0, citylog.getValues()[3].length()-1);
			cityredo.setValues(a);
	        utuples.add(cityredo);
	        
	        cityredo = C.GetNext();
	        if(cityredo == null){
	        	break;
	        }
	        citylog = logCity.GetNext();
	        if(citylog == null){
	        	break;
	        }
		}
		
		cityredowriter.write("ID,Name,CountryCode,District,Population");
		cityredowriter.write("\n");
		for(int i = 0; i < utuples.size(); i++){
			for(int j = 0; j < 5; j++){
				if(j == 4){
					cityredowriter.write(utuples.get(i).getValues()[j]);
				}
				else{
					cityredowriter.write(utuples.get(i).getValues()[j]+",");
				}
			}
			cityredowriter.write("\n");
		}
		cityredowriter.close();
	}
	
	public void redoprint() throws IOException{
		pcity2 = new BufferedReader(new FileReader("city 2.csv"));
		String line= pcity2.readLine();
		while(line != null){
			System.out.println(line);
			line= pcity2.readLine();
		}
	}
	
	public void close() {
		C.Close();
		logCity.Close();
	}
}
