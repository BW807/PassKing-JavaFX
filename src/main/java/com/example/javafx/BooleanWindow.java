package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BooleanWindow {

    private boolean response;

    public boolean Display(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        Label Title = new Label(message);

        Button Yes = applyEffects(new Button("Yes"));
        Button No = applyEffects(new Button("No"));

        // VBox constructor is being weird here idk why
        VBox Menu = new VBox(Title);
        Menu.getChildren().add(Yes);
        Menu.getChildren().add(No);
        Menu.setSpacing(5);

        Menu.setAlignment(Pos.CENTER);

        Yes.setOnAction(e -> {
            response = true;
            window.close();
        });

        No.setOnAction(e -> {
            response = false;
            window.close();
        });

        window.setOnCloseRequest(e -> {
            response = false;
            window.close();
        });

        Scene scene = new Scene(Menu, 200, 100);
        window.setScene(scene);
        window.showAndWait();
        return response;
    }

    private Button applyEffects(Button button) {
        button.setMinSize(100, 25);
        return button;
    }
}
