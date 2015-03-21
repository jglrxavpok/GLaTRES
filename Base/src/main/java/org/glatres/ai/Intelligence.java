package org.glatres.ai;

import org.glatres.base.*;

public abstract class Intelligence {

    private Bot bot;
    private String dictapiKey;

    public Intelligence bot(Bot bot) {
        this.bot = bot;
        return this;
    }

    public Bot bot() {
        return bot;
    }

    /**
     * Sets the key to be used while making requests to dictionaryapi.com
     * @param key
     * @return
     */
    public Intelligence dictionaryapiKey(String key) {
        this.dictapiKey = key;
        return this;
    }

    protected String dictionaryapiKey() {
        return dictapiKey;
    }

    public abstract Intelligence init();

    public abstract void shutdown();

    public abstract void exec(String command, String[] args);

    public abstract boolean hasCommand(String string);

    public abstract void interpretSentence(String[] parts);

}
