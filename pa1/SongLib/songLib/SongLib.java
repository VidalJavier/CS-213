package songLib;

import java.io.File;
import java.util.ArrayList;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import songLib.UserPrompt;
import view.SongLibController;
import model.LibEntry;

//TODO Write removeClicked() method, write FXML code, write File storage code
public class SongLib extends Application{
	
	Stage window;
	Scene scene;
	Button addEntry, removeEntry, editEntry;
	ListView<String> listView;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Setup window and elements
		window = primaryStage;
		window.setTitle("Song Library");
		
		
		addEntry = new Button("Add");
		removeEntry = new Button("Remove");
		editEntry = new Button("Edit");
		
		//Load saved user song library from previous session
		//TODO Move this to its own method
		File songLibrary = new File("songLibrary.txt");
		
		ArrayList<LibEntry> libList = SongFileHandler.openAndRead(songLibrary);
		
		window.setOnCloseRequest(e -> SongFileHandler.saveAndExit(libList, songLibrary));
		
		listView = new ListView<>();
		for(LibEntry entry:libList){
			listView.getItems().add(entry.getTitle() + "\n\tby " + entry.getArtist());
		}
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		listView.getSelectionModel().clearSelection();
		listView.getSelectionModel().selectFirst();
		int selectedIndex = listView.getSelectionModel().getSelectedIndex();
		if(listView.getSelectionModel().isSelected(selectedIndex)){
			LibEntry entry = libList.get(selectedIndex);
			String selectedDisplay = entry.getTitle() + "\n\tby "
					+ entry.getArtist() + "\n\t"
					+ entry.getAlbum() + "\n\t"
					+ entry.getYear();
			listView.getItems().set(selectedIndex, selectedDisplay);
		} else{
			
		}
		
		addEntry.setOnAction(e -> addClicked(listView, libList));
		removeEntry.setOnAction(e -> removeClicked());
		editEntry.setOnAction(e -> editClicked(listView, libList));
		
		VBox layout = new VBox(10);
		HBox buttonLayout = new HBox(10);
		buttonLayout.getChildren().addAll(addEntry, removeEntry, editEntry);
		buttonLayout.setAlignment(Pos.BASELINE_CENTER);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.getChildren().addAll(listView, buttonLayout);
		
		
		scene = new Scene(layout, 300, 250);
		window.setScene(scene);
		window.show();
		
	}
	
	
	private ArrayList<LibEntry> addClicked(ListView<String> listView, ArrayList<LibEntry> libList) {
		
		LibEntry newEntry = UserPrompt.promptUserAdd();
		System.out.println(newEntry.toString());
		
		//TODO Still need to sort the list with the new entry
		libList.add(newEntry);
		listView.getItems().add(newEntry.getTitle() + "\n\tby " + newEntry.getArtist());
		
		System.out.println("Add Clicked");
		return libList;
	}

	private ArrayList<LibEntry> editClicked(ListView<String> listView, ArrayList<LibEntry> libList) {
		
		LibEntry modifiedEntry = UserPrompt.promptUserEdit();
		System.out.println(modifiedEntry.toString());
		
		//TODO Still need to sort the list with the new entry
		libList.set(listView.getSelectionModel().getSelectedIndex(), modifiedEntry);
		listView.getItems().set(listView.getSelectionModel().getSelectedIndex(), modifiedEntry.getTitle() + "\n\tby " + modifiedEntry.getArtist());
		
		System.out.println("Edit Clicked");
		return libList;
	}

	//TODO removeClicked() method
	private void removeClicked() {
		System.out.println("Remove Clicked");
	}
	
}
