package com.ssnagin.collectionmanager.scripts;

import com.ssnagin.collectionmanager.scripts.exceptions.ScriptRecursionException;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class ScriptManager {

    @Getter
    private static ScriptManager instance = new ScriptManager();

    private Set<String> activeScripts;

    private Stack<Scanner> inputScanners;
    private Scanner defaultScanner;

    public ScriptManager() {

        this.defaultScanner = new Scanner(System.in);

        this.inputScanners = new Stack<>();
        this.inputScanners.push(defaultScanner);

        activeScripts = new HashSet<>();
    }

    public Scanner getCurrentScanner() {
        return inputScanners.peek();
    }

    public void pushFileScanner(File file) throws IOException, ScriptRecursionException {
        String canonicalPath = file.getCanonicalPath();
        if (activeScripts.contains(canonicalPath)) {
            throw new ScriptRecursionException("Recursion Detected!");
        }
        activeScripts.add(canonicalPath);
        inputScanners.push(new Scanner(file));
    }

    public void popScanner(File file) throws IOException {
        if (inputScanners.size() > 1) {
            Scanner scn = inputScanners.pop();
            activeScripts.remove(file.getCanonicalPath());
            scn.close();
        }
    }

    public void removeScanners() {
        while (inputScanners.size() > 1) {
            Scanner scn = inputScanners.pop();
            scn.close();
        }
    }

    public void clearActiveScripts() {
        this.activeScripts.clear();
    }
}
