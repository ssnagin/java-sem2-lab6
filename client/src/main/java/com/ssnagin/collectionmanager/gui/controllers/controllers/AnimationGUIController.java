package com.ssnagin.collectionmanager.gui.controllers.controllers;

import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.console.ClientConsole;
import com.ssnagin.collectionmanager.events.EventType;
import com.ssnagin.collectionmanager.gui.commands.commands.GUICommandShow;
import com.ssnagin.collectionmanager.gui.controllers.GUIController;
import com.ssnagin.collectionmanager.gui.nodes.animation.AnimationElement;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class AnimationGUIController extends GUIController {

    @FXML
    public Canvas visualizationCanvas;

    private AnimationElement animationElement;

    @FXML
    @Override
    protected void initialize() {
        if (isInitialized) return;
        isInitialized = true;

        initEventListeners();

        animationElement = new AnimationElement(visualizationCanvas);
    }

    @FXML
    protected void initEventListeners() {
//        eventManager.subscribe(EventType.COLLECTION_DATA_LOADED.toString(),
//            eventData -> {
//
//                ClientConsole.println(eventData.toString());
//
//                if (eventData instanceof ObservableList) {
//                    ObservableList<MusicBand> bands = (ObservableList<MusicBand>) eventData;
//                    animationElement.updateBands(bands);
//                }
//            });

        eventManager.subscribe(EventType.COLLECTION_DATA_LOADED.toString(),
                eventData -> {
//                    ClientConsole.println("Received data: " + eventData);
                    if (eventData instanceof ObservableList) {
                        ObservableList<MusicBand> bands = (ObservableList<MusicBand>) eventData;
//                        ClientConsole.println("Bands count: " + bands.size());
//                        if (!bands.isEmpty()) {
//                            ClientConsole.println("First band coords: " +
//                                    bands.get(0).getCoordinates().getX() + "," +
//                                    bands.get(0).getCoordinates().getY());
//                        }
                        animationElement.updateBands(bands);
                    }
                });
    }
}
