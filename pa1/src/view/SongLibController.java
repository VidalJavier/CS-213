package view;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class SongLibController {

	@FXML         
	ListView<String> listView;                

	private ObservableList<String> observableList;              

	public void start(Stage mainStage) {
		//TODO: Grab list from text file that stores data from previous session
		// create an empty ObservableList from an ArrayList
		observableList = FXCollections.observableArrayList(); 

		listView.setItems(observableList); 
		
		// select the first item
		listView.getSelectionModel().select(0);

		// set listener for the items
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal)
				-> showItemInputDialog(mainStage));
	}
	
	private void showItem(Stage mainStage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		//alert.initModality(Modality.NONE);
		alert.initOwner(mainStage);
		alert.setTitle("List Item");
		alert.setHeaderText("Selected list item properties");

		String content = "Index: " + listView.getSelectionModel().getSelectedIndex() + "\nValue: " +
				listView.getSelectionModel().getSelectedItem();

		alert.setContentText(content);
		alert.showAndWait();
		//System.out.println("not blocking");
	   }
	
	private void showItemInputDialog(Stage mainStage) {                
		String item = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();

		TextInputDialog dialog = new TextInputDialog(item);
		dialog.initOwner(mainStage); dialog.setTitle("List Item");
		dialog.setHeaderText("Selected Item (Index: " + index + ")");
		dialog.setContentText("Enter name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) { observableList.set(index, result.get()); }
	}
}
