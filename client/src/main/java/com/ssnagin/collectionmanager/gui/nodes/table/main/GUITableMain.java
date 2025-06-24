package com.ssnagin.collectionmanager.gui.nodes.table.main;


import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.model.MusicGenre;
import com.ssnagin.collectionmanager.gui.nodes.table.GUITable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GUITableMain extends GUITable<MusicBand> {

    private Integer currentLocalId = 1;
    protected TableColumn<Integer, Integer> localIdColumn;

    protected TableColumn<MusicBand, Long> idColumn;
    
    protected TableColumn<MusicBand, String> ownedUsernameColumn;
    
    protected TableColumn<MusicBand, String> nameColumn;
    
    protected TableColumn<MusicBand, Long> coordXColumn;
    
    protected TableColumn<MusicBand, Integer> coordYColumn;
    
    protected TableColumn<MusicBand, Long> participantsColumn;
    
    protected TableColumn<MusicBand, Integer> singlesColumn;
    
    protected TableColumn<MusicBand, String> bestAlbumNameColumn;
    
    protected TableColumn<MusicBand, Long> bestAlbumTracksColumn;
    
    protected TableColumn<MusicBand, String> genreColumn;

    public GUITableMain(TableView<MusicBand> tableView) {
        super(tableView);
    }

    @Override
    public void setProperties() {

//        localIdColumn.setCellValueFactory(cellData ->
//                new SimpleObjectProperty<>(currentLocalId++));
//
//        tableView.getItems().addListener((ListChangeListener<MusicBand>) change -> {
//            currentLocalId = 1;
//        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        coordXColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getX()));

        coordYColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getY()));

        participantsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfParticipants"));
        singlesColumn.setCellValueFactory(new PropertyValueFactory<>("singlesCount"));

        bestAlbumNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBestAlbum().getName()));
        bestAlbumTracksColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getBestAlbum().getTracks()));

        genreColumn.setCellValueFactory(cellData -> {
            MusicGenre genre = cellData.getValue().getGenre();
            return new SimpleStringProperty(genre != null ? genre.toString() : "");
        });


    }
}
