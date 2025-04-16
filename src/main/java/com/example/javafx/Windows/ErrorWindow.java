package com.example.javafx.Windows;

import com.example.javafx.Utils.ButtonUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorWindow {

    public void Display(String title, String message) {
        Display(title, message, 200, 100);
    }

    public void Display(String title, String message, int x, int y) {
        Stage window = new Stage();
        ButtonUtils bu = new ButtonUtils();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        Label Title = new Label(message);

        Button okay = bu.applyEffectsBoolWindow(new Button("Okay"));

        // VBox constructor is being weird here idk why
        VBox Menu = new VBox(Title);
        Menu.getChildren().add(okay);
        Menu.setSpacing(5);

        Menu.setAlignment(Pos.CENTER);

        okay.setOnAction(e -> {
            window.close();
        });

        window.setOnCloseRequest(e -> {
            window.close();
        });

        Scene scene = new Scene(Menu, x, y);
        window.setScene(scene);
        window.showAndWait();
    }
}
