package org.glatres.lang.words;

public class WordInfo {

    private String word;
    private String pronunciation;
    private WordType type;
    private String lang;

    public WordInfo(String lang, String word, String pronunciation, WordType type) {
        this.lang = lang;
        this.word = word;
        this.pronunciation = pronunciation;
        this.type = type;
    }

    public String language() {
        return lang;
    }

    public String word() {
        return word;
    }

    public String pronunciation() {
        return pronunciation;
    }

    public WordType type() {
        return type;
    }

    public String toString() {
        return "WordInfo(lang:" + lang + ", word:" + word() + ", pronunciation:" + pronunciation + ", type:" + type().name() + ")";
    }
}
