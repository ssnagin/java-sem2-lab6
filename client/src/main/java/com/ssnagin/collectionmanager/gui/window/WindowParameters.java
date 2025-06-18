package com.ssnagin.collectionmanager.gui.window;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class WindowParameters {
    protected Integer minWidth = 1080;
    protected Integer minHeight = 720;

    protected String title = "";

    protected String pageUri = "/com/ssnagin/collectionmanager/fxml/"; // /com/ssnagin/collectionmanager/fxml/test.fxml
    protected String stylesUri = "/com/ssnagin/collectionmanager/css/"; // /com/ssnagin/collectionmanager/css/style.css
}
