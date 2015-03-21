package org.glatres.lang.words;

public enum WordType {

    INVALID(""), ADJECTIVE("adjective");

    private String id;

    WordType(String s) {
        this.id = s;
    }

    public String id() {
        return id;
    }
}
