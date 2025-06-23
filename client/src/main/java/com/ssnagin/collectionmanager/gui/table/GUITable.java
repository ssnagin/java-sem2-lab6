package com.ssnagin.collectionmanager.gui.table;

import com.ssnagin.collectionmanager.collection.model.MusicBand;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class GUITable<T> {

    protected TableView<T> tableView;

    public GUITable(TableView<T> tableView) {
        this.tableView = tableView;
    }

    public abstract void setProperties();
}
