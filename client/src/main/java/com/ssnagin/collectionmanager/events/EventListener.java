package com.ssnagin.collectionmanager.events;

@FunctionalInterface
public interface EventListener<T> {
    void onEvent(T eventData);
}