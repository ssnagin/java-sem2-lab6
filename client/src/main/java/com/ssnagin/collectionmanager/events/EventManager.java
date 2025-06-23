package com.ssnagin.collectionmanager.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class EventManager {

    private static EventManager instance;
    private final Map<String, List<EventListener<?>>> listeners = new HashMap<>();

    public static synchronized EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public <T> void subscribe(String eventType, EventListener<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public <T> void unsubscribe(String eventType, EventListener<T> listener) {
        List<EventListener<?>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }

    @SuppressWarnings("unchecked") //???
    public <T> void publish(String eventType, T eventData) {
        List<EventListener<?>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (EventListener<?> listener : eventListeners) {
                ((EventListener<T>) listener).onEvent(eventData);
            }
        }
    }
}

/*


EventManager.getInstance().publish(EventType.USER_LOGGED_IN.toString(), user);

====

EventManager.getInstance().subscribe(EventType.USER_LOGGED_IN.toString(),
    (User user) -> handleUserAuthenticated(user));


private void handleUserAuthenticated(User user) {...

====

public void destroy() {
    EventManager.getInstance().unsubscribe(EventType.USER_AUTHENTICATED.toString(),
        this::handleUserAuthenticated);
}

 */