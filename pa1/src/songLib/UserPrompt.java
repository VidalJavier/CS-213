package songLib;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.LibraryEntry;
import model.UserAction;

final class UserPrompt {

    private static final String CANCEL = "Cancel";
    private static final String CLOSE = "Close";
    private static final String ERROR = "ERROR";
    private static final String INVALID_MESSAGE = "Invalid entry: Please include both song title and artist!";

    private static final String SONG_TITLE = "Song Title";
    private static final String ARTIST = "Artist";
    private static final String ALBUM = "Album";
    private static final String YEAR = "Year";
	
	static UserAction prompt(final String dialogTitle, final String header, final Stage window) {
		Dialog<ButtonType> addDialog = new Dialog<>();
		addDialog.setTitle(dialogTitle);
		addDialog.setHeaderText(header);

		addDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(15);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));

		TextField title = new TextField();
		TextField artist = new TextField();
		TextField album = new TextField();
		TextField year = new TextField();

		title.setPromptText(SONG_TITLE);
		artist.setPromptText(ARTIST);
		album.setPromptText(ALBUM);
		year.setPromptText(YEAR);

		grid.add(new Label(String.format("%s: ", SONG_TITLE)), 0, 0);
		grid.add(new Label(String.format("%s: ", ARTIST)), 0, 1);
		grid.add(new Label(String.format("%s: ", ALBUM)), 0, 2);
		grid.add(new Label(String.format("%s: ", YEAR)), 0, 3);

        grid.add(title, 1, 0);
        grid.add(artist, 1, 1);
        grid.add(album, 1, 2);
        grid.add(year, 1, 3);

		Node addButton = addDialog.getDialogPane().lookupButton(ButtonType.OK);
		addButton.setDisable(true);

		title.textProperty().addListener((observable, oldValue, newValue) ->
                addButton.setDisable(newValue.trim().isEmpty()));
		artist.textProperty().addListener((observable, oldValue, newValue) ->
                addButton.setDisable(newValue.trim().isEmpty()));

		addDialog.getDialogPane().setContent(grid);

		Optional<ButtonType> result = addDialog.showAndWait();

		if(result.isPresent() && result.get() == ButtonType.OK) {
		    if(title.getText().equals("") || artist.getText().equals("")) {
                errorWithEntry(INVALID_MESSAGE);
                return new UserAction(null, false);
            }
		    LibraryEntry newEntry = new LibraryEntry(title.getText(), artist.getText(), album.getText(), year.getText());
		    return new UserAction(newEntry, true);
        } else {
            return new UserAction(null, false);
        }
	}

	static void errorWithEntry(final String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(ERROR);
        window.setMinWidth(500);

        Label label= new Label();
        label.setText(message);

        Button close = new Button(CLOSE);
        close.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}