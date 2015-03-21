package org.glatres.stockage;

import org.glatres.base.*;
import org.glatres.lang.words.*;

public abstract class StorageSystem {

    private Bot bot;

    public abstract StorageSystem init();

    public abstract <T> T read(String section, String key, Class<T> type);

    public abstract <T> StorageSystem write(String section, String key, T value);

    public StorageSystem bot(Bot bot) {
        this.bot = bot;
        return this;
    }

    public Bot bot() {
        return bot;
    }

    public abstract void shutdown();

    public abstract StorageSystem write(String section, String[] keys, Object[] values);

    public abstract StorageSystem saveWord(String lang, String word, String pronunciation, WordType type);

    public abstract StorageSystem saveWord(WordInfo info);

    public abstract WordInfo getWordInfos(String lang, String word);
}
