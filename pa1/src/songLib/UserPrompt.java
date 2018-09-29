package songLib;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.LibraryEntry;

final class UserPrompt {

    private static final String CANCEL = "Cancel";
    private static final String CLOSE = "Close";
    private static final String ERROR = "ERROR";
    private static final String MESSAGE = "Cannot add duplicate song!";

    private static final String SONG_TITLE = "Song Title";
    private static final String ARTIST = "Artist";
    private static final String ALBUM = "Album";
    private static final String YEAR = "Year";
	
	static LibraryEntry prompt(final String dialogTitle, final String header, final Stage window) {
		Dialog<LibraryEntry> addDialog = new Dialog<>();
		addDialog.setTitle(dialogTitle);
		addDialog.setHeaderText(header);

		ButtonType addButtonType = new ButtonType(dialogTitle, ButtonData.OK_DONE);
		Button cancel = new Button(CANCEL);
		cancel.setOnAction(e -> window.close());
		addDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

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

		Node addButton = addDialog.getDialogPane().lookupButton(addButtonType);
		addButton.setDisable(true);

		title.textProperty().addListener((observable, oldValue, newValue) ->
                addButton.setDisable(newValue.trim().isEmpty()));
		artist.textProperty().addListener((observable, oldValue, newValue) ->
                addButton.setDisable(newValue.trim().isEmpty()));

		addDialog.getDialogPane().setContent(grid);
		manageEntry(addDialog, title, artist, album, year);

		Optional<LibraryEntry> result = addDialog.showAndWait();
		return result.get();
	}

	static void duplicateEntry() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(ERROR);
        window.setMinWidth(250);

        Label label= new Label();
        label.setText(MESSAGE);

        Button close = new Button(CLOSE);
        close.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

	private static void manageEntry(final Dialog<LibraryEntry> entryDialog, final TextField title,
									final TextField artist, final TextField album, final TextField year) {
        LibraryEntry newEntry = new LibraryEntry();
		entryDialog.setResultConverter(addThis -> {
			newEntry.setTitle(title.getText());
			newEntry.setAlbum(album.getText());
			newEntry.setArtist(artist.getText());
			newEntry.setYear(year.getText());
			return newEntry;
		});
	}
}