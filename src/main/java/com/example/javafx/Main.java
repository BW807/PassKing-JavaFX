package com.example.javafx;

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
        stage.setTitle("PassKing V1.0");

        Scene scene = initializeMainMenu(stage);

        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram(stage);
        });

    //    Scene scene = new Scene(MainMenu, 320, 240);

        stage.setScene(scene);
        stage.show();
    }


    private Scene initializeMainMenu(Stage stage) {
        Label topMenuLabel = new Label("Welcome to PassKing V1.0");
        Button generateButton = applyEffects(new Button("Generator"));
        Button closeButton = applyEffects(new Button("Close"));
        Button editButton = applyEffects(new Button("Edit"));
        Button viewButton = applyEffects(new Button("View"));

        HBox Title = new HBox(topMenuLabel);
        Title.setAlignment(Pos.BASELINE_CENTER);
        VBox Buttons = new VBox(generateButton, editButton, viewButton, closeButton);
        Buttons.setSpacing(5);
        Buttons.setAlignment(Pos.BASELINE_CENTER);
        VBox MainMenu = new VBox(Title, Buttons);
        MainMenu.setAlignment(Pos.CENTER);

        generateButton.setOnAction(e -> {
            Generator gen = new Generator();
            gen.Display();
        });

        closeButton.setOnAction(e -> closeProgram(stage));

        return new Scene(MainMenu, 320, 240);
    }


    private Button applyEffects(Button button) {
        button.setMinSize(150, 35);
        return button;
    }

    public static void main(String[] args) {
        launch();
    }

    public void closeProgram(Stage stage) {
        BooleanWindow bool = new BooleanWindow();

        System.out.println("Logic for saving on close here");

        if (bool.Display("Closing Program", "Are you sure you want to close?")) {
            stage.close();
        }
    }
}