/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ssnagin.collectionmanager.collection.model;

import com.ssnagin.collectionmanager.description.annotations.Description;

/**
 *
 * @author DEVELOPER
 */
public enum MusicGenre {

    @Description(
        name="психоделический рок", 
        description="Ну только самые отбитые шизофреники под такое танцуют"
    )
    PSYCHEDELIC_ROCK,
    @Description(
        name="психоделический облачный рэп", 
        description="Ну только самые отбитые шизофреники под такое танцуют"
    )
    PSYCHEDELIC_CLOUD_RAP,
    @Description(
        name="душа", 
        description="Ну только самые отбитые шизофреники под такое танцуют"
    )
    SOUL,
    @Description(
        name="математический рок", 
        description="Ну только самые отбитые шизофреники под такое танцуют"
    )
    MATH_ROCK,
    @Description(
        name="после панк", 
        description="Ну только самые отбитые шизофреники под такое танцуют"
    )
    POST_PUNK;
}