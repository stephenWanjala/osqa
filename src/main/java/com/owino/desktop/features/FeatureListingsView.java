package com.owino.desktop.features;
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
import com.owino.core.Result;
import javafx.geometry.Insets;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import com.owino.core.OSQAConfig;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import com.owino.settings.SettingDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.owino.desktop.OSQANavigationEvents;
import com.owino.core.OSQAModel.OSQAModule;
public class FeatureListingsView extends VBox {
    public FeatureListingsView(){
        var appDirResult = SettingDao.getAppDataDir();
        Optional<Path> featuresDir = switch (appDirResult){
            case Result.Success<Path> (Path appDir) -> Optional.of(appDir);
            case Result.Failure<Path> failure -> {
                IO.println(failure.error().getLocalizedMessage());
                yield Optional.empty();
            }
        };
        if (featuresDir.isPresent()) {
            List<OSQAModule> modules = switch (OSQAConfig.listModules(featuresDir.get())){
                case Result.Success<List<OSQAModule>> (List<OSQAModule> modulesValue) -> modulesValue;
                case Result.Failure<List<OSQAModule>> failure -> {
                    IO.println("Failed to load module list:" + failure.error().getLocalizedMessage());
                    yield List.of();
                }
            };
            if (!modules.isEmpty()){
                ObservableList<OSQAModule> listViewContents = FXCollections.observableList(modules);
                var listView = new ListView<OSQAModule>(listViewContents);
                listView.setCellFactory(item -> new ListCell<>(){
                    @Override
                    protected void updateItem(OSQAModule module, boolean empty) {
                        super.updateItem(module, empty);
                        if (empty || module == null){
                            setText("");
                            setGraphic(null);
                        } else {
                            var moduleItemContainer = new VBox(10);
                            var nameLabel = new Label(module.name());
                            var descriptionLabel = new Label(module.description());
                            descriptionLabel.setMaxWidth(700);
                            descriptionLabel.setWrapText(true);
                            moduleItemContainer.getChildren().addAll(nameLabel, descriptionLabel, new Separator());
                            VBox.setMargin(nameLabel,new Insets(12,12,3,12));
                            VBox.setMargin(descriptionLabel,new Insets(3,12,6,12));
                            var blueBackground = new Background(new BackgroundFill(Color.BLUE,new CornerRadii(12),Insets.EMPTY));
                            var blackBackground = new Background(new BackgroundFill(Color.BLACK,new CornerRadii(12),Insets.EMPTY));
                            moduleItemContainer.setOnMouseEntered(_ -> moduleItemContainer.setBackground(blueBackground));
                            moduleItemContainer.setOnMouseExited(_ -> moduleItemContainer.setBackground(blackBackground));
                            setGraphic(moduleItemContainer);
                        }
                    }
                });
                listView.setBorder(Border.EMPTY);
                var moduleSelectionModel = listView.getSelectionModel();
                moduleSelectionModel.setSelectionMode(SelectionMode.SINGLE);
                var moduleSelectedItemProp = moduleSelectionModel.selectedItemProperty();
                moduleSelectedItemProp.addListener((_, _,selectedModule) -> {
                    if (selectedModule != null){
                        EventBus.getDefault().post(new OSQANavigationEvents.OpenFeatureDetailedViewEvent(selectedModule));
                    }
                });
                var listViewContainer = new VBox(listView);
                getChildren().add(listViewContainer);
                setMargin(listViewContainer,new Insets(12,12,12,12));
            }
        }
    }
}
