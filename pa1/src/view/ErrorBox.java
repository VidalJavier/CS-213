package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

final class ErrorBox {

    private static final String CLOSE = "Close";
    private static final String ERROR = "ERROR";
    private static final String YES = "Yes";
    private static final String NO = "No";

    private static boolean answer;

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

    static boolean confirmation(final String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(ERROR);
        window.setMinWidth(500);

        Label label= new Label();
        label.setText(message);

        Button yes = new Button(YES);
        Button no = new Button(NO);

        yes.setOnAction(e -> {
            answer = true;
            window.close();});
        no.setOnAction(e -> {
            answer = false;
            window.close();});

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}