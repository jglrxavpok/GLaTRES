package org.glatres.stockage;

import org.glatres.base.*;

public abstract class StockageSystem {

    private Bot bot;

    public abstract StockageSystem init();

    public abstract StockageSystem switchPartition(String newPartition);

    public abstract String partition();

    public abstract <T> T read(String section, String key, Class<T> type);

    public abstract <T> StockageSystem write(String section, String key, T value);

    public StockageSystem bot(Bot bot) {
        this.bot = bot;
        return this;
    }

    public Bot bot() {
        return bot;
    }

    public abstract void shutdown();

}
