package com.example.javafx;

import com.example.javafx.Utils.ButtonUtils;
import com.example.javafx.Windows.StringWindow;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Viewer extends Main {

    TextArea Tarea = new TextArea();

    public void Display() throws IOException {
        Stage window = new Stage();
        ButtonUtils bu = new ButtonUtils();
        window.setTitle("Viewer");
        window.initModality(Modality.APPLICATION_MODAL);

        //Constructing menu
        Label title = new Label("Welcome to the Viewing page");
        Label reminder = new Label("Here you can safely check your saved files.");
        Button importButton = bu.applyEffectsBoolWindow(new Button("Open File"));
        importButton.setPrefSize(500, 100);

        title.setAlignment(Pos.TOP_CENTER);

        String defaultPage = edit.fileUtils.importCombos(true, null);

        Tarea.setText(defaultPage);
        Tarea.setPrefSize(450, 400);

        VBox vBox = new VBox(title, reminder, Tarea, importButton);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(15);

        importButton.setOnAction(e -> {
            try {
                StringWindow namePrompt = new StringWindow();
                String updatedList = edit.fileUtils.importCombos(false, namePrompt.Display("Name Selection", "Input File Name"));

                if (updatedList != null) {
                    Tarea.setText(updatedList);
                }
            } catch (IOException ex) {
                System.out.println("Feel free to ignore the error if exited early.");
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(vBox, 600, 650);
        window.setScene(scene);

        window.show();
    }
}
