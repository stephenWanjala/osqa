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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.UUID;
public class WelcomeView extends StackPane {
    public WelcomeView(){
        var itemsContainer = new VBox(22);
        var welcomeAscii = """
                 _____ _____  _____  ___
                |  _  /  ___||  _  |/ _ \\
                | | | \\ `--. | | | / /_\\ \\
                | | | |`--. \\| | | |  _  |
                \\ \\_/ /\\__/ /\\ \\/' / | | |
                 \\___/\\____/  \\_/\\_\\_| |_/
                
                Welcome to OSQA!
                """;
        itemsContainer.getChildren().add(new Text(welcomeAscii));
        itemsContainer.getChildren().add(new Text("Create new Module or Select Module to Proceed!"));
        var addModuleButton = new Button("Add New Verification");
        addModuleButton.setOnAction(event -> {
            fireEvent(AppEvents.openModuleFormEvent(UUID.randomUUID().toString(),false));
        });
        itemsContainer.getChildren().add(addModuleButton);
        getChildren().add(itemsContainer);
        setMargin(itemsContainer, new Insets(45));
    }
}
