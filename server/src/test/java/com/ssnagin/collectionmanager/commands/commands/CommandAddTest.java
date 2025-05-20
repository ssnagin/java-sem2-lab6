package com.ssnagin.collectionmanager.commands.commands;

import com.ssnagin.collectionmanager.collection.CollectionManager;
import com.ssnagin.collectionmanager.collection.model.Coordinates;
import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.collection.wrappers.LocalDateWrapper;
import com.ssnagin.collectionmanager.database.DatabaseManager;
import com.ssnagin.collectionmanager.inputparser.ParsedString;
import com.ssnagin.collectionmanager.networking.ResponseStatus;
import com.ssnagin.collectionmanager.networking.data.client.ClientRequest;
import com.ssnagin.collectionmanager.networking.data.server.ServerResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommandAddTest {

    CollectionManager collectionManager = CollectionManager.getInstance();

    List<MusicBand> musicBandList = new ArrayList<>();
    List<LocalDateWrapper> localDateWrapperList = new ArrayList<>();

    public CommandAddTest() {

        musicBandList.add(new MusicBand().random()); // Propper
        musicBandList.add(new MusicBand().random()); // Corrupted
        musicBandList.get(1).setCoordinates(new Coordinates(999L, 999));

        localDateWrapperList.add(new LocalDateWrapper(new MusicBand()).random()); // Propper
        localDateWrapperList.add(new LocalDateWrapper(new MusicBand()).random()); // Corrupted
        localDateWrapperList.get(1).setId(-111L);
    }

    @Test
    @SneakyThrows
    public void executeCommandTest() {

        ClientRequest request = new ClientRequest(new ParsedString());
        CommandAdd commandAdd = new CommandAdd("add", new DatabaseManager());
        ServerResponse response;

        // 0. Correct MusicBand and LocalDateWrappers:
        int i = 0;

        request.setData(this.musicBandList.get(i));

        Assertions.assertEquals(
                ResponseStatus.OK,
                commandAdd.executeCommand(request).getResponseStatus()
        );

        request.setData(this.localDateWrapperList.get(i));

        Assertions.assertEquals(
                ResponseStatus.OK,
                commandAdd.executeCommand(request).getResponseStatus()
        );

        // 1. Incorrect MusicBand and LocalDateWrappers:
        i += 1;
        request.setData(this.musicBandList.get(i));

        System.out.println(commandAdd.executeCommand(request).toString());

        Assertions.assertEquals(
                ResponseStatus.ERROR,
                commandAdd.executeCommand(request).getResponseStatus()
        );

        request.setData(this.localDateWrapperList.get(i));

        Assertions.assertEquals(
                ResponseStatus.ERROR,
                commandAdd.executeCommand(request).getResponseStatus()
        );


    }
}
