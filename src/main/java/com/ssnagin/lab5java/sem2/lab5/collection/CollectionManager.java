/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.collection;

import com.ssnagin.lab5java.sem2.lab5.collection.model.MusicBand;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * 
 * @author developer
 */
public class CollectionManager {
    
    // Using singleton
    
    @Getter
    private static CollectionManager instance = new CollectionManager();
    
    @Getter
    @Setter
    private TreeSet<MusicBand> collection = new TreeSet<>();

    public void addElement(MusicBand element) {
        this.collection.add(element);
    }
    
    public MusicBand getElementById(long otherId) {
        MusicBand result = null;
        
        for (MusicBand row : this.collection) {
            if ((row.getId()).equals(otherId)) {
                result = row;
                break;
            }
        }
        return result;
    }

    public MusicBand getLowestElement() {
        if (this.collection.isEmpty()) {
            return null;
        }
        return this.collection.first();
    }
    
    public void removeElement(MusicBand musicBand) {
        this.collection.remove(musicBand);
    }
    
    public void removeAllElements() {this.collection.clear();}

    public MusicBand getElementById(Long id) {

        for (MusicBand element : this.getCollection()) {
            if (Objects.equals(element.getId(), id)) return element;
        }
        return null;
    }
    
    public void removeElementById(Long id) {
        
        MusicBand toRemove = getElementById(id);
        
        if (toRemove == null) throw new NoSuchElementException("No such element with id");
        
        this.removeElement(toRemove);
    }
    
    public int getSize() {
        return this.collection.size();
    }
    
    public boolean isEmpty() {
        return this.collection.isEmpty();
    }

    public int removeLower(MusicBand element) {
        if (element == null || collection.isEmpty()) {
            return 0;
        }

        // Получаем подмножество элементов, которые меньше заданного
        TreeSet<MusicBand> lowerElements = new TreeSet<>(collection.tailSet(element, false));

        // Запоминаем количество элементов для удаления
        int count = lowerElements.size();

        // Удаляем все элементы из основной коллекции
        collection.removeAll(lowerElements);

        return count;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        if (this.collection.isEmpty()) {
            result.append("My stomach is as empty as this Collection...");
            return result.toString();
        }
        
        result.append("CollectionManager={\n");
        
        for (MusicBand musicBand : this.collection) {
            result.append(musicBand.toString()).append("\n");
        }
        
        result.append("}\n");
        
        return result.toString();
    }
}
