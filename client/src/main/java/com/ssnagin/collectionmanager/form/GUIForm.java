package com.ssnagin.collectionmanager.form;

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

    protected List<TextField> fields = new LinkedList<>();

    public GUIForm(String name, Button submitButton, TextField... fields) {
        this.name = name;

        this.submitButton = submitButton;
        this.fields.addAll(Arrays.asList(fields));
    }
}
