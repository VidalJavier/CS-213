package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

final class UserPrompt {

    private static final String CLOSE = "Close";
    private static final String ERROR = "ERROR";

	static void invalidEntry(final String message) {
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