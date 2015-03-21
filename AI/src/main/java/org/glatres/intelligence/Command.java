package org.glatres.intelligence;

import org.glatres.base.*;

public abstract class Command {

    private Bot bot;

    public Command bot(Bot bot) {
        this.bot = bot;
        return this;
    }

    public Bot bot() {
        return bot;
    }

    public abstract String id();

    public abstract boolean run(String[] args);
}
