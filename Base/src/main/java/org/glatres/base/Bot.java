package org.glatres.base;

import java.io.*;

import org.glatres.ai.*;
import org.glatres.stockage.*;

public abstract class Bot implements Runnable {

    private StorageSystem storage;
    private String name;
    private Intelligence intelligence;
    private File rootFile;
    private boolean alive;

    public Bot(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Bot storageSystem(StorageSystem system) {
        this.storage = system;
        return this;
    }

    public StorageSystem storageSystem() {
        return storage;
    }

    public Bot intelligence(Intelligence intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public Intelligence intelligence() {
        return intelligence;
    }

    public Bot init() {
        alive = true;
        storage.bot(this);
        intelligence.bot(this);
        storage.init();
        intelligence.init();

        return this;
    }

    public Bot rootFile(File rootFile) {
        this.rootFile = rootFile;
        return this;
    }

    public File rootFile() {
        return rootFile;
    }

    public void requestShutdown() {
        alive = false;
    }

    public void shutdown() {
        storage.shutdown();
        intelligence.shutdown();
    }

    public boolean alive() {
        return alive;
    }

}
