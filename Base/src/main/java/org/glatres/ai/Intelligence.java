package org.glatres.ai;

import org.glatres.base.*;

public abstract class Intelligence {

    private Bot bot;

    public Intelligence bot(Bot bot) {
        this.bot = bot;
        return this;
    }

    public Bot bot() {
        return bot;
    }

    public abstract Intelligence init();

    public abstract void shutdown();
}
