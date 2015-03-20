package org.glatres.base;

import java.io.*;

import org.glatres.ai.*;
import org.glatres.stockage.*;

public abstract class Bot {

    private StockageSystem stockage;
    private String name;
    private Intelligence intelligence;
    private File rootFile;

    public Bot(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Bot stockageSystem(StockageSystem system) {
        this.stockage = system;
        return this;
    }

    public StockageSystem stockageSystem() {
        return stockage;
    }

    public Bot intelligence(Intelligence intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public Intelligence intelligence() {
        return intelligence;
    }

    public Bot init() {
        stockage.bot(this);
        intelligence.bot(this);
        stockage.init();
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

    public void shutdown() {
        stockage.shutdown();
        intelligence.shutdown();
    }

}
