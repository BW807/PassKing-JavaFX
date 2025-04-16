package com.example.javafx;

import com.example.javafx.Windows.BooleanWindow;
import com.example.javafx.Utils.ButtonUtils;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Password Manager V1.0");

        Scene scene = initializeMainMenu(stage);

    //    Scene scene = new Scene(MainMenu, 320, 240);

        stage.setScene(scene);
        stage.show();
    }

    public Editor edit = new Editor();

    private Scene initializeMainMenu(Stage stage) {
        Label topMenuLabel = new Label("Welcome to PassKing V1.0");
        ButtonUtils bu = new ButtonUtils();

        Button generateButton = bu.applyEffectsMainMenu(new Button("Generator"));
        Button closeButton = bu.applyEffectsMainMenu(new Button("Close"));
        Button editButton = bu.applyEffectsMainMenu(new Button("Editor"));
        Button viewButton = bu.applyEffectsMainMenu(new Button("Viewer"));

        HBox Title = new HBox(topMenuLabel);
        Title.setAlignment(Pos.BASELINE_CENTER);
        VBox Buttons = new VBox(editButton, generateButton, viewButton, closeButton);
        Buttons.setSpacing(5);
        Buttons.setAlignment(Pos.BASELINE_CENTER);
        VBox MainMenu = new VBox(Title, Buttons);
        MainMenu.setAlignment(Pos.CENTER);

        generateButton.setOnAction(e -> {
            Generator gen = new Generator();
            gen.Display();
        });

        viewButton.setOnAction(e -> {
            Viewer viewer = new Viewer();
            try {
                viewer.Display();
            } catch (IOException ex) {
                System.out.println("Something has gone wrong");
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        });

        editButton.setOnAction(e -> {
            try {
                edit.Display();
            } catch (IOException ex) {
                System.out.println("Something has gone wrong");
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        });

        closeButton.setOnAction(e -> {
            stage.close();
        });

        return new Scene(MainMenu, 320, 240);
    }

    public static void main(String[] args) {
        launch();
    }
}