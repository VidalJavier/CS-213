package songLib;

import javafx.fxml.FXML;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import model.LibraryEntry;
import model.UserAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


//TODO Write remove() method, write FXML code, write File storage code
public class SongLibrary extends Application {

    private static final String SONG_LIBRARY = "Song Library";
    private static final String FILE_NAME = "songLibrary.txt";

    private static final String ADD = "Add";
    private static final String REMOVE = "Remove";
    private static final String EDIT = "Edit";

    private static final String ADD_ENTRY = "Add an entry";
    private static final String EDIT_ENTRY = "Edit an entry";

    private static final String BY = "by ";

    private ListView<LibraryEntry> listView;

    //private TextArea textArea;
	
    public static void main(String[] args){
	launch(args);
    }
	
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Scene scene = makeScene(primaryStage);

        primaryStage.setTitle(SONG_LIBRARY);
	    primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Scene makeScene(final Stage primaryStage) throws FileNotFoundException {
        File songLibrary = new File(FILE_NAME);
        ArrayList<LibraryEntry> libList = SongFileHandler.openAndRead(songLibrary);
        primaryStage.setOnCloseRequest(e -> SongFileHandler.saveAndExit(libList, songLibrary));

        Button addEntry = new Button(ADD);
        Button removeEntry = new Button(REMOVE);
        Button editEntry = new Button(EDIT);

        listView = new ListView<>();
        for(LibraryEntry entry : libList){
            listView.getItems().add(entry);
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.getSelectionModel().clearSelection();
        listView.getSelectionModel().selectFirst();
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        
        TextArea textArea = new TextArea();
        textArea.setMinHeight(100.0);
        if(listView.getSelectionModel().getSelectedItem() != null){
            textArea.setText("Title: " + listView.getSelectionModel().getSelectedItem().getTitle()
                    + "\nArtist: " + listView.getSelectionModel().getSelectedItem().getArtist()
                    + "\nAlbum: " + listView.getSelectionModel().getSelectedItem().getAlbum()
                    + "\nYear: " + listView.getSelectionModel().getSelectedItem().getYear()
            );
        } else{
            textArea.setText("Song information");
        }
        
        addEntry.setOnAction(e -> add(listView, libList, primaryStage));
        removeEntry.setOnAction(e -> remove());
        editEntry.setOnAction(e -> edit(listView, libList, primaryStage));
        
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("Clicked");
                if (listView.getSelectionModel().getSelectedItem() != null) {
                    textArea.setText("Title: " + listView.getSelectionModel().getSelectedItem().getTitle()
                            + "\nArtist: " + listView.getSelectionModel().getSelectedItem().getArtist()
                            + "\nAlbum: " + listView.getSelectionModel().getSelectedItem().getAlbum()
                            + "\nYear: " + listView.getSelectionModel().getSelectedItem().getYear()
                    );
                } else {
                    textArea.setText("Song information");
                }
            }
        };
        listView.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        
        
        VBox layout = new VBox(10);
        
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(addEntry, removeEntry, editEntry);
        buttonLayout.setAlignment(Pos.BASELINE_CENTER);
        
        HBox textAreaLayout = new HBox(10);
        textAreaLayout.getChildren().addAll(textArea);
        textAreaLayout.setAlignment(Pos.BASELINE_CENTER);
        
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(listView, buttonLayout, textAreaLayout);
        return new Scene(layout, 300, 350);
    }

    private ArrayList<LibraryEntry> add(final ListView<LibraryEntry> listView, final ArrayList<LibraryEntry> libList,
                                        final Stage primaryStage) {
        UserAction action = UserPrompt.prompt(ADD, ADD_ENTRY, primaryStage);

        if(!action.getConfirmation()) {
            return libList;
        }

        LibraryEntry newEntry = action.getEntry();
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
                        UserPrompt.duplicateEntry();
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
            //listView.getItems().add(String.format("%s\n\t%s%s", entry.getTitle(), BY, entry.getArtist()));
        }
        
        return libList;
    }

    private ArrayList<LibraryEntry> edit(final ListView<LibraryEntry> listView, final ArrayList<LibraryEntry> libList,
                                         final Stage primaryStage) {
//        LibraryEntry modifiedEntry = UserPrompt.prompt(EDIT, EDIT_ENTRY, primaryStage);
//        System.out.println(modifiedEntry.toString());
//
//        //TODO Still need to sort the list with the new entry
//        libList.set(listView.getSelectionModel().getSelectedIndex(), modifiedEntry);
//        listView.getItems().set(listView.getSelectionModel().getSelectedIndex(),
//                String.format("%s\n\t%s%s",modifiedEntry.getTitle(), BY, modifiedEntry.getArtist()));

        return libList;
    }

    //TODO remove() method
    private void remove() {
        System.out.println("Remove Clicked");
    }
    
}
