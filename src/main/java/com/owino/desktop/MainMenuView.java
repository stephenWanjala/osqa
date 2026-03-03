package com.owino.desktop;
/*
 * Copyright (C) 2026 Samuel Owino
 *
 * OSQA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OSQA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OSQA.  If not, see <https://www.gnu.org/licenses/>.
 */
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
public class MainMenuView extends VBox {
    public MainMenuView(Stage window){
        setMinWidth(200);
        setMaxWidth(300);
        setStyle("-fx-background-color: #2c3e50;");
        var moduleTitleView = new Text("OSQA");
        var settingsButton = new Button("Config Folder");
        moduleTitleView.setFont(Font.font(21));
        settingsButton.setFont(Font.font(17));
        settingsButton.setOnAction( _ -> {
            var directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select config destination:");
            File selectedDir = directoryChooser.showDialog(window);
            if (selectedDir != null)
                IO.println("Selected Dir " + selectedDir.getAbsolutePath());
        });
        var menuItemMargin = new Insets(12,12,12,12);
        getChildren().add(moduleTitleView);
        getChildren().add(settingsButton);
        setMargin(moduleTitleView,menuItemMargin);
        setMargin(settingsButton,menuItemMargin);
    }
}
