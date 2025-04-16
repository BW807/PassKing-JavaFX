package com.example.javafx.Windows;

import com.example.javafx.Utils.ButtonUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StringWindow {

    private String response;

    public String Display(String title, String message) {
        Stage window = new Stage();
        ButtonUtils bu = new ButtonUtils();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        Label Title = new Label(message);

        TextField answerField = new TextField("");
        answerField.setMaxWidth(200);
        answerField.setMinHeight(40);

        Button Yes = bu.applyEffectsBoolWindow(new Button("Done"));

        // VBox constructor is still weird here
        VBox Menu = new VBox(Title);
        Menu.getChildren().add(answerField);
        Menu.getChildren().add(Yes);
        Menu.setSpacing(5);

        Menu.setAlignment(Pos.CENTER);

        Yes.setOnAction(e -> {
            response = answerField.getText().toString();
            window.close();
        });

        window.setOnCloseRequest(e -> {
            BooleanWindow choice = new BooleanWindow();

            if (choice.Display("Exit?", "Are you sure you want to exit?\nYour changes will not be saved")) {
                response = null;
                window.close();
            }
            else {
                e.consume();
            }
        });

        Scene scene = new Scene(Menu, 300, 100);
        window.setScene(scene);
        window.showAndWait();

        if (response != null) {
            if (response.equals("")) {
                return null;
            }
        }
        return response;
    }
}
