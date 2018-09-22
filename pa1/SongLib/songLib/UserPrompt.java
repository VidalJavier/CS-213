package songLib;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.LibEntry;

public class UserPrompt{
	
	public static LibEntry promptUserAdd(){
		//Create custom dialog
				Dialog<LibEntry> addDialog = new Dialog<>();
				addDialog.setTitle("Add");
				addDialog.setHeaderText("Add an entry");
				
				//Set button types
				ButtonType addButtonType = new ButtonType("Add", ButtonData.OK_DONE);
				//Button cancel = new Button("Cancel");
				//cancel.setOnAction(e -> window.close());
				addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
				
				//Create input fields
				GridPane grid = new GridPane();
				grid.setHgap(15);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 20, 20, 20));
				
				TextField title = new TextField();
				TextField artist = new TextField();
				TextField album = new TextField();
				TextField year = new TextField();
				title.setPromptText("Song title");
				artist.setPromptText("Artist");
				album.setPromptText("Album");
				year.setPromptText("Year");
				
				grid.add(new Label("Song title: "), 0, 0);
				grid.add(title, 1, 0);
				grid.add(new Label("Artist: "), 0, 1);
				grid.add(artist, 1, 1);
				grid.add(new Label("Album: "), 0, 2);
				grid.add(album, 1, 2);
				grid.add(new Label("Year: "), 0, 3);
				grid.add(year, 1, 3);
				
				Node addButton = addDialog.getDialogPane().lookupButton(addButtonType);
				addButton.setDisable(true);
				
				title.textProperty().addListener((observable, oldValue, newValue) -> {
					addButton.setDisable(newValue.trim().isEmpty());
				});
				artist.textProperty().addListener((observable, oldValue, newValue) -> {
					addButton.setDisable(newValue.trim().isEmpty());
				});
				
				addDialog.getDialogPane().setContent(grid);
				LibEntry newEntry = new LibEntry();
				addDialog.setResultConverter(addThis -> {
					newEntry.setTitle(title.getText()).setAlbum(album.getText()).setArtist(artist.getText()).setYear(year.getText());
					return newEntry;
				});
				
				Optional<LibEntry> result = addDialog.showAndWait();
				return result.get();
	}
	
	//TODO May want to change the prompt text by requiring input to this method to get the current values
	//Pretty much the same as promptUserAdd(), but with different labels
	public static LibEntry promptUserEdit(){
		//Create custom dialog
				Dialog<LibEntry> editDialog = new Dialog<>();
				editDialog.setTitle("Edit");
				editDialog.setHeaderText("Edit an entry");
				
				//Set button types
				ButtonType editButtonType = new ButtonType("Edit", ButtonData.OK_DONE);
				//Button cancel = new Button("Cancel");
				//cancel.setOnAction(e -> window.close());
				editDialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);
				
				//Create input fields
				GridPane grid = new GridPane();
				grid.setHgap(15);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 20, 20, 20));
				
				TextField title = new TextField();
				TextField artist = new TextField();
				TextField album = new TextField();
				TextField year = new TextField();
				title.setPromptText("Song title");
				artist.setPromptText("Artist");
				album.setPromptText("Album");
				year.setPromptText("Year");
				
				grid.add(new Label("Song title: "), 0, 0);
				grid.add(title, 1, 0);
				grid.add(new Label("Artist: "), 0, 1);
				grid.add(artist, 1, 1);
				grid.add(new Label("Album: "), 0, 2);
				grid.add(album, 1, 2);
				grid.add(new Label("Year: "), 0, 3);
				grid.add(year, 1, 3);
				
				Node editButton = editDialog.getDialogPane().lookupButton(editButtonType);
				editButton.setDisable(true);
				
				title.textProperty().addListener((observable, oldValue, newValue) -> {
					editButton.setDisable(newValue.trim().isEmpty());
				});
				artist.textProperty().addListener((observable, oldValue, newValue) -> {
					editButton.setDisable(newValue.trim().isEmpty());
				});
				
				editDialog.getDialogPane().setContent(grid);
				LibEntry newEntry = new LibEntry();
				editDialog.setResultConverter(addThis -> {
					newEntry.setTitle(title.getText()).setAlbum(album.getText()).setArtist(artist.getText()).setYear(year.getText());
					return newEntry;
				});
				
				Optional<LibEntry> result = editDialog.showAndWait();
				return result.get();
	}
	
}