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
        return Display(title, message, 200, 100, null);
    }

    public boolean Display(String title, String message, int x, int y) {
        return Display(title, message, x, y, null);
        //Recursion, very cse 205 core
    }

    public boolean Display(String title, String message, int x, int y, String extraMessage) {
        Stage window = new Stage();
        ButtonUtils bu = new ButtonUtils();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        Label frontMessage = new Label(message);

        Button Yes = bu.applyEffectsBoolWindow(new Button("Yes"));
        Button No = bu.applyEffectsBoolWindow(new Button("No"));

        // VBox constructor is being weird here idk why
        VBox Menu = new VBox(frontMessage);

        if (extraMessage != null) {
            Label backMessage = new Label(extraMessage);
            Menu.getChildren().add(backMessage);
        }

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

        Scene scene = new Scene(Menu, x, y);
        window.setScene(scene);
        window.showAndWait();
        return response;
    }
}
