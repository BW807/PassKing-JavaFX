package com.example.javafx.Windows;

import com.example.javafx.Utils.ButtonUtils;
import com.example.javafx.Utils.Combo;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ComboWindow {

        private Combo result = null;

        public Combo Display() {
            Stage window = new Stage();
            ButtonUtils bu = new ButtonUtils();
            window.setTitle("Combo Creation");
            window.initModality(Modality.APPLICATION_MODAL);

            Label Title = new Label("Input your information below: \n(All fields are optional)");

            HBox website = makeRow("Website : ");
            HBox user = makeRow("Username : ");
            HBox email = makeRow("Email : ");
            HBox password = makeRow("Password : ");

            Button okay = bu.applyEffectsBoolWindow(new Button("Done"));

            VBox Menu = new VBox(Title, website, user, email, password, okay);
            Menu.setSpacing(4);

            Menu.setAlignment(Pos.CENTER);

            okay.setOnAction(e -> {
                TextField webField = (TextField) website.getChildren().get(1);
                TextField userField = (TextField) user.getChildren().get(1);
                TextField emailField = (TextField) email.getChildren().get(1);
                TextField passwordField = (TextField) password.getChildren().get(1);

                result = new Combo(webField.getText(), userField.getText(), emailField.getText(), passwordField.getText());
                window.close();
            });

            window.setOnCloseRequest(e -> {
                window.close();
            });

            Scene scene = new Scene(Menu, 300, 250);

            window.setScene(scene);
            window.showAndWait();
            return result;
        }

        private HBox makeRow(String label) {
            HBox row = new HBox();

            Label preface = new Label(label);
            preface.setPrefSize(100, 20);

            TextField answerField = new TextField("");
            answerField.setMaxWidth(150);

            row.getChildren().add(preface);
            row.getChildren().add(answerField);
            row.setSpacing(5);
            row.setAlignment(Pos.CENTER);

            return row;
        }
    }
