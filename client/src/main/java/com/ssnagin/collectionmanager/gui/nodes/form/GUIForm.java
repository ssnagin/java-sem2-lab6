package com.ssnagin.collectionmanager.gui.nodes.form;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class GUIForm {

    protected String name;

    protected Button submitButton;

    protected List<Node> fields = new LinkedList<>();

    public GUIForm(String name, Button submitButton, Node... fields) {
        this.name = name;
        
        this.submitButton = submitButton;
        this.fields.addAll(Arrays.asList(fields));

    }
}
