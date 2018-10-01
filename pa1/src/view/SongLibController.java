package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.LibraryEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SongLibController {

    private static final String FILE_NAME = "songLibrary.txt";
    private static final String SONG_DETAILS = "Song details";

    private static final String DUPLICATE_MESSAGE = "Error: Cannot add duplicate song";
    private static final String INVALID_MESSAGE = "Error: Must include both song and artist";

    @FXML TextField song;
    @FXML TextField artist;
    @FXML TextField album;
    @FXML TextField year;
    @FXML TextArea details;
    @FXML Button add;
    @FXML Button edit;
    @FXML Button delete;
    @FXML Button cancel;

    @FXML ListView<LibraryEntry> listView;

	public void start(final Stage primaryStage) throws FileNotFoundException {
        File songLibrary = new File(FILE_NAME);
        ArrayList<LibraryEntry> libList = SongFileHandler.openAndRead(songLibrary);
        primaryStage.setOnCloseRequest(e -> SongFileHandler.saveAndExit(libList, songLibrary));

        for(LibraryEntry entry : libList){
            listView.getItems().add(entry);
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.getSelectionModel().clearSelection();
        listView.getSelectionModel().selectFirst();

        selectSong(listView);

        EventHandler<MouseEvent> eventHandler = e -> {
            selectSong(listView);
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
//            edit.setOnAction(h -> edit(listView, libList, primaryStage, selectedIndex));
//            delete.setOnAction(g -> delete());
        };

        add.setOnAction(f -> add(listView, libList));
//        edit.setOnAction(h -> edit(listView, libList, primaryStage, 0));
//        delete.setOnAction(g -> delete());

        listView.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

	}
	
    private void selectSong(final ListView<LibraryEntry> listView) {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            details.setEditable(false);
            details.setText("Song: " + listView.getSelectionModel().getSelectedItem().getTitle()
                    + "\nArtist: " + listView.getSelectionModel().getSelectedItem().getArtist()
                    + "\nAlbum: " + listView.getSelectionModel().getSelectedItem().getAlbum()
                    + "\nYear: " + listView.getSelectionModel().getSelectedItem().getYear());
        } else {
            details.setEditable(false);
            details.setText(SONG_DETAILS);
        }
    }

    private ArrayList<LibraryEntry> add(final ListView<LibraryEntry> listView, final ArrayList<LibraryEntry> libList) {
	    if(song.getText().equals("") || artist.getText().equals("")) {
            UserPrompt.invalidEntry(INVALID_MESSAGE);
            return libList;
        }

        LibraryEntry newEntry = new LibraryEntry(song.getText(), artist.getText(), album.getText(), year.getText());
        if (libList.size() == 0) {
            libList.add(newEntry);
        } else {
            int index;
            for (index = 0; index < libList.size(); index++) {
                LibraryEntry curEntry = libList.get(index);
                if(newEntry.getTitle().compareTo(curEntry.getTitle()) <= 0) {
                    break;
                }
            }

            if (index == libList.size()) {
                libList.add(newEntry);
            } else if (newEntry.getTitle().compareTo(libList.get(index).getTitle()) < 0) {
                libList.add(index, newEntry);
            } else {
                while (newEntry.getTitle().compareTo(libList.get(index).getTitle()) == 0) {
                    if (newEntry.getArtist().compareTo(libList.get(index).getArtist()) < 0) {
                        break;
                    } else if (newEntry.getArtist().compareTo(libList.get(index).getArtist()) == 0){
                        UserPrompt.invalidEntry(DUPLICATE_MESSAGE);
                        return libList;
                    } else {
                        index++;
                    }
                }
                libList.add(index, newEntry);
            }
        }

        listView.getItems().clear();
        for(LibraryEntry entry : libList){
            listView.getItems().add(entry);
        }

        return libList;
    }

}
