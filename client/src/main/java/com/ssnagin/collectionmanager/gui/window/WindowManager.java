package com.ssnagin.collectionmanager.gui.window;

import com.ssnagin.collectionmanager.commands.interfaces.Manageable;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

public class WindowManager implements Manageable<Window> {

    @Getter
    public static WindowManager instance = new WindowManager();

    TreeSet<Window> windows;

    public WindowManager() {
        windows = new TreeSet<>();
    }

    @Override
    public void register(Window object) {
        this.windows.add(object);
    }

    @Override
    public Window get(String name) {
        Optional<Window> window = windows.stream()
                .filter(w -> w.getName().equalsIgnoreCase(name))
                .findFirst();
        return window.orElse(null);
    }

    public TreeSet<Window> getAll() {
        return this.windows;
    }

    public void hide(String windowName) {
        Window window = get(windowName);
        if (window != null) {
            window.hide();
        }
    }

    public void close(String windowName) {
        Window window = get(windowName);
        if (window != null) {
            window.close();
        }
    }

    public void remove(Window window) {
        if (window != null) {
            window.close();
            windows.remove(window);
        }
    }

    public void remove(String windowName) {
        Window window = get(windowName);
        if (window != null) {
            remove(window);
        }
    }

    public boolean isWindowShowing(String windowName) {
        Window window = get(windowName);
        return window != null && window.isShowing();
    }
}
