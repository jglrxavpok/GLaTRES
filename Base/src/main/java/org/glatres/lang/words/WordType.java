package org.glatres.lang.words;

public enum WordType {

    INVALID("");

    private String id;

    WordType(String s) {
        this.id = s;
    }

    public String id() {
        return id;
    }
}
