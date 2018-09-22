package songLib;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import view.SongLibController;
import model.LibEntry;

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
		
		//Create ListView Model
		//Song title and artist combined for now. Will separate them later
		LibEntry entry1 = new LibEntry("Dive", "Ed Sheeran", "÷", 2017);
		LibEntry entry2 = new LibEntry("Numb", "Linkin Park", "Meteora", 2003);
		LibEntry entry3 = new LibEntry("Firework", "Katy Perry", "Teenage Dream", 2010);
		LibEntry entry4 = new LibEntry("Animals", "Maroon 5", "V", 2014);
		LibEntry[] entryList = {
				entry1,
				entry2,
				entry3,
				entry4
		};
/*
		String[] listItems = {
				entry1.getTitle() + "\t\t\t" + entry1.getArtist(),
				entry2.getTitle() + "\t\t" + entry2.getArtist(),
				entry3.getTitle() + "\t\t" + entry3.getArtist(),
				entry4.getTitle() + "\t\t" + entry4.getArtist()
				};
		listView = new ListView<>();
		for(String item:listItems){
			listView.getItems().add(item);
		}
*/
		listView = new ListView<>();
		for(LibEntry entry:entryList){
			listView.getItems().add(entry.getTitle() + "\t\t" + entry.getArtist());
		}
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//listView.setOnMouseClicked(new EventHandler<MouseEvent>());
		
		listView.getSelectionModel().clearSelection();
		listView.getSelectionModel().selectFirst();
		int selectedIndex = listView.getSelectionModel().getSelectedIndex();
		if(listView.getSelectionModel().isSelected(selectedIndex)){
			LibEntry entry = entryList[selectedIndex];
			String selectedDisplay = entry.getTitle() + "\n"
					+ entry.getArtist() + "\n"
					+ entry.getAlbum() + "\n"
					+ entry.getYear() + "\n";
			listView.getItems().set(selectedIndex, selectedDisplay);
		} else{
			
		}
		
		addEntry.setOnAction(e -> addClicked());
		removeEntry.setOnAction(e -> removeClicked());
		editEntry.setOnAction(e -> editClicked());
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.getChildren().addAll(listView, addEntry, removeEntry, editEntry);
		
		scene = new Scene(layout, 300, 250);
		window.setScene(scene);
		window.show();
		
	}
	
	//Placeholder actions for when we get to buttons
	private void addClicked() {
		System.out.println("Add Clicked");
	}

	private void editClicked() {
		System.out.println("Edit Clicked");
	}

	private void removeClicked() {
		System.out.println("Remove Clicked");
	}

	
	
}
