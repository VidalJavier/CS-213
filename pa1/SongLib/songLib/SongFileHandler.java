package songLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.LibEntry;

public class SongFileHandler{
	
	public static ArrayList<LibEntry> openAndRead(File songLibrary) throws FileNotFoundException{
		//Load saved user song library from previous session
				
				try{
					if(songLibrary.createNewFile()){
						System.out.println("Created new songLibrary text file");
					} else{
						System.out.println("songLibrary.txt already exists");
					}
				} catch(NullPointerException | IOException e){
					System.out.println("Error in file creation: Null pointer or IO exception");
					System.exit(1);
				}
				
				FileReader fileReader = new FileReader(songLibrary);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line;
				ArrayList<LibEntry> libList = new ArrayList<LibEntry>();
				try{
					while((line = bufferedReader.readLine()) != null){
						String entryProperties[] = line.split(",");
						LibEntry entry = new LibEntry(entryProperties[0], entryProperties[1], entryProperties[2], entryProperties[3]);
						libList.add(entry);
					}
					fileReader.close();
				} catch(IOException e){
					System.out.println("Error in reading file");
					e.printStackTrace();
				}
				
				return libList;
	}
	
	public static void saveAndExit(ArrayList<LibEntry> libList, File songLibrary){
		String newLib = "";
		for(LibEntry entry:libList){
			newLib = newLib + entry.toString() + "\n";
		}
		
		try{
			FileWriter writer = new FileWriter(songLibrary, false);
			writer.write(newLib);
			writer.close();
		} catch(IOException e){
			System.out.println("Error writing to song library text file");
			e.printStackTrace();
		}
	}
}