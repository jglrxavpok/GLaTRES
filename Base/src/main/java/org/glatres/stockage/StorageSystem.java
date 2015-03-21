package org.glatres.stockage;

import org.glatres.base.*;

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

}
