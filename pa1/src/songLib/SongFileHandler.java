package songLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.LibraryEntry;

public class SongFileHandler {
	
	public static ArrayList<LibraryEntry> openAndRead(File songLibrary) throws FileNotFoundException {
		//Load saved user song library from previous session
				
				try{
					if(songLibrary.createNewFile()) {
						System.out.println("Created new songLibrary text file");
					} else {
						System.out.println("songLibrary.txt already exists");
					}
				} catch(NullPointerException | IOException e) {
					System.out.println("Error in file creation: Null pointer or IO exception");
					System.exit(1);
				}
				
				FileReader fileReader = new FileReader(songLibrary);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line;
				ArrayList<LibraryEntry> libList = new ArrayList<LibraryEntry>();
				try{
					while((line = bufferedReader.readLine()) != null) {
						String entryProperties[] = line.split(",");
						if(entryProperties.length > 0) {
							LibraryEntry entry = new LibraryEntry(entryProperties[0], entryProperties[1],
									entryProperties[2], entryProperties[3]);
							libList.add(entry);
						}
					}
					fileReader.close();
				} catch(IOException e) {
					System.out.println("Error in reading file");
					e.printStackTrace();
				}
				
				return libList;
	}
	
	public static void saveAndExit(ArrayList<LibraryEntry> libList, File songLibrary) {
		String newLib = "";
		for(LibraryEntry entry:libList) {
			newLib = newLib + entry.toString() + "\n";
		}
		
		try {
			FileWriter writer = new FileWriter(songLibrary, false);
			writer.write(newLib);
			writer.close();
		} catch(IOException e) {
			System.out.println("Error writing to song library text file");
			e.printStackTrace();
		}
	}
}