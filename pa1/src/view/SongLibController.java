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

    private static final String EDIT_CONFIRMATION = "Are you sure you want to edit the selected song?";
    private static final String DELETE_CONFIRMATION = "Are you sure you want to delete the selected song?";

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
            song.setText(libList.get(selectedIndex).getTitle());
            artist.setText(libList.get(selectedIndex).getArtist());
            album.setText(libList.get(selectedIndex).getAlbum());
            year.setText(libList.get(selectedIndex).getYear());

            edit.setOnAction(f -> {
                boolean check = ErrorBox.confirmation(EDIT_CONFIRMATION);
                if(check) {
                    edit(listView, libList, selectedIndex, song.getText(), artist.getText(), album.getText(), year.getText());
                }
            });
            delete.setOnAction(g -> {
                boolean check = ErrorBox.confirmation(DELETE_CONFIRMATION);
                if(check) {
                    delete(listView, libList, selectedIndex);
                }
            });
        };

        add.setOnAction(e -> add(listView, libList));
        cancel.setOnAction(e -> cancel());

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

    private void add(final ListView<LibraryEntry> listView, final ArrayList<LibraryEntry> libList) {
	    if(song.getText().equals("") || artist.getText().equals("")) {
            ErrorBox.invalidEntry(INVALID_MESSAGE);
            return;
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
                        ErrorBox.invalidEntry(DUPLICATE_MESSAGE);
                        return;
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
    }

    private void edit(final ListView<LibraryEntry> listView, final ArrayList<LibraryEntry> libList,
                      final int selectedIndex, final String song, final String artist,
                      final String album, final String year) {
        for (LibraryEntry entry : libList) {
            if(entry.getTitle().compareTo(song) == 0 && entry.getArtist().compareTo(artist) == 0) {
                ErrorBox.invalidEntry(DUPLICATE_MESSAGE);
                return;
            }
        }

        libList.set(selectedIndex, new LibraryEntry(song, artist, album, year));
        listView.getItems().clear();
        for(LibraryEntry entry : libList){
            listView.getItems().add(entry);
        }
    }

    private void delete(final ListView<LibraryEntry> listView, final ArrayList<LibraryEntry> libList,
                        final int selectedIndex) {
        libList.remove(selectedIndex);
        listView.getItems().clear();

        for(LibraryEntry entry : libList){
            listView.getItems().add(entry);
        }

        if(libList.size() > selectedIndex) {
            listView.getSelectionModel().select(selectedIndex);
            selectSong(listView);
        } else if(libList.size() == selectedIndex) {
            int index = selectedIndex - 1;
            listView.getSelectionModel().select(index);
            selectSong(listView);
        } else if(libList.size() == 0) {
            selectSong(listView);
        }
    }

    private void cancel() {
	    song.clear();
	    artist.clear();
	    album.clear();
	    year.clear();
    }
}
