package org.glatres.lang.words;

public enum WordType
{

    INVALID(""), NOUN("noun"), VERB("verb"), ADJECTIVE("adjective"), ADVERB("adverb"), PREPOSITION("preposition"), PRONOUN("pronoun"),
    CONTRACTION("contraction"), ARTICLE("article"), CONJUNCTION("conjunction"), ABBREVIATION("abbreviation"), INTERJECTION("interjection");

    private String id;

    WordType(String s) {
        this.id = s;
    }

    public String id() {
        return id;
    }
}
