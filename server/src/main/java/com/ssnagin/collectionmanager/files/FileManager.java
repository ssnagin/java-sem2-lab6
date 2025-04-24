/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
import com.ssnagin.collectionmanager.commands.CommandManager;
import com.ssnagin.collectionmanager.console.Console;
import com.ssnagin.collectionmanager.files.adapters.LocalDateAdapter;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeSet;

/**
 * @author developer
 */
public class FileManager {

    @Getter
    private static FileManager instance = new FileManager();

    public Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();


    public void write(MusicBand musicBand, String path) {
        try (PrintWriter collectionPrintWriter = new PrintWriter(new File(path))) {
            collectionPrintWriter.println(gson.toJson(musicBand));
            Console.println("Collection was saved");
        } catch (IOException exception) {
            Console.error("Can't open executable file");
        }
    }

    public void write(TreeSet<MusicBand> musicBands, String path) {
        try (PrintWriter collectionPrintWriter = new PrintWriter(new File(path))) {
            collectionPrintWriter.println(gson.toJson(musicBands));
            Console.println("Collection was saved");
        } catch (IOException exception) {
            Console.error("Can't open executable file");
        }
    }

    public TreeSet<MusicBand> readCollection(String path) throws IOException {
        String buffResult = read(path);

        if (buffResult.isEmpty()) buffResult = "[]";

        return gson.fromJson(buffResult, new TypeToken<TreeSet<LocalDateWrapper>>() {
        }.getType());
    }

    public List<ParsedString> readCommands(String path, CommandManager commandManager) throws IOException {
        String buffResult = read(path);

        if (buffResult.isEmpty()) return null;

        String[] lines = buffResult.split(System.lineSeparator());

        for (String line : lines) {

        }
        return null;
    }

    public String read(String path) throws IOException {
        if (path == null || path.isEmpty()) return null;

        FileInputStream fileInputStream = new FileInputStream(path);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        byte[] bytes = bufferedInputStream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
