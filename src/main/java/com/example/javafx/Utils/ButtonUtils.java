package com.example.javafx.Utils;

import javafx.scene.control.Button;

public class ButtonUtils {

    public Button applyEffectsMainMenu(Button button) {
        button.setMinSize(150, 35);
        return button;
    }

    public Button applyEffectsBoolWindow(Button button) {
        button.setMinSize(100, 25);
        return button;
    }
}
