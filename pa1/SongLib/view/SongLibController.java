/*
    Javier Vidal
    Jimmy Wen
*/
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

    private int selectedIndex;
    private File songLibrary;

	public void start(final Stage primaryStage) throws FileNotFoundException {
        songLibrary = new File(FILE_NAME);
        ArrayList<LibraryEntry> libList = SongFileHandler.openAndRead(songLibrary);

        for(LibraryEntry entry : libList){
            listView.getItems().add(entry);
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.getSelectionModel().clearSelection();
        listView.getSelectionModel().selectFirst();
        selectedIndex = 0;

        selectSong(listView);

        EventHandler<MouseEvent> eventHandler = e -> {
            selectSong(listView);
            selectedIndex = listView.getSelectionModel().getSelectedIndex();
            song.setText(libList.get(selectedIndex).getTitle());
            artist.setText(libList.get(selectedIndex).getArtist());
            album.setText(libList.get(selectedIndex).getAlbum());
            year.setText(libList.get(selectedIndex).getYear());

            editAction(libList);
            deleteAction(libList);
        };

        add.setOnAction(e -> add(listView, libList));
        editAction(libList);
        deleteAction(libList);
        cancel.setOnAction(e -> cancel());

        listView.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

    private void deleteAction(ArrayList<LibraryEntry> libList) {
        delete.setOnAction(g -> {
            if (!libList.isEmpty()) {
                boolean check = ErrorBox.confirmation(DELETE_CONFIRMATION);
                if(check) {
                    delete(listView, libList);
                }
            }
        });
    }

    private void editAction(ArrayList<LibraryEntry> libList) {
        edit.setOnAction(f -> {
            if(!libList.isEmpty()) {
                boolean check = ErrorBox.confirmation(EDIT_CONFIRMATION);
                if (check) {
                    edit(listView, libList, song.getText(), artist.getText(), album.getText(), year.getText());
                }
            }
        });
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
            selectedIndex = 0;
            libList.add(newEntry);
        } else {
            int index;
            for (index = 0; index < libList.size(); index++) {
                LibraryEntry curEntry = libList.get(index);
                if(newEntry.getTitle().toLowerCase().compareTo(curEntry.getTitle().toLowerCase()) <= 0) {
                    break;
                }
            }

            if (index == libList.size()) {
                libList.add(newEntry);
                selectedIndex = index;
            } else if (newEntry.getTitle().toLowerCase().compareTo(libList.get(index).getTitle().toLowerCase()) < 0) {
                libList.add(index, newEntry);
                selectedIndex = index;
            } else {
                boolean check = true;
                while (newEntry.getTitle().toLowerCase().compareTo(libList.get(index).getTitle().toLowerCase()) == 0) {
                    if (newEntry.getArtist().toLowerCase().compareTo(libList.get(index).getArtist().toLowerCase()) < 0) {
                        libList.add(index, newEntry);
                        selectedIndex = index;
                        check = false;
                        break;
                    } else if (newEntry.getArtist().toLowerCase().compareTo(libList.get(index).getArtist().toLowerCase()) == 0){
                        ErrorBox.invalidEntry(DUPLICATE_MESSAGE);
                        return;
                    } else if(index == libList.size() - 1) {
                        libList.add(newEntry);
                        selectedIndex = index + 1;
                        check = false;
                        break;
                    }
                    index++;
                }

                if(check) {
                    libList.add(index , newEntry);
                    selectedIndex = index;
                }
            }
        }

        listView.getItems().clear();
        for(LibraryEntry entry : libList){
            listView.getItems().add(entry);
        }

        listView.getSelectionModel().select(selectedIndex);
        selectSong(listView);
        SongFileHandler.save(libList, songLibrary);
    }

    private void edit(final ListView<LibraryEntry> listView, final ArrayList<LibraryEntry> libList,
                      final String song, final String artist, final String album, final String year) {
        if(song.equals("") || artist.equals("")) {
            ErrorBox.invalidEntry(INVALID_MESSAGE);
            return;
        }

        for (int index = 0; index < libList.size(); index++) {
            LibraryEntry entry = libList.get(index);
            if(entry.getTitle().compareTo(song) == 0 && entry.getArtist().compareTo(artist) == 0 && index != selectedIndex) {
                ErrorBox.invalidEntry(DUPLICATE_MESSAGE);
                return;
            }
        }

        delete(listView, libList);
        add(listView, libList);
    }

    private void delete(final ListView<LibraryEntry> listView, final ArrayList<LibraryEntry> libList) {
	    libList.remove(selectedIndex);
        listView.getItems().clear();

        for(LibraryEntry entry : libList){
            listView.getItems().add(entry);
        }

        if(libList.size() > selectedIndex) {
            listView.getSelectionModel().select(selectedIndex);
            selectSong(listView);
        } else if(libList.size() == selectedIndex) {
            selectedIndex = selectedIndex - 1;
            listView.getSelectionModel().select(selectedIndex);
            selectSong(listView);
        } else if(libList.size() == 0) {
            selectSong(listView);
        }

        SongFileHandler.save(libList, songLibrary);
    }

    private void cancel() {
	    song.clear();
	    artist.clear();
	    album.clear();
	    year.clear();
    }
}
