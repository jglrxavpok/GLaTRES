package org.glatres.intelligence.commands;

import org.glatres.intelligence.*;

public class ExitCommand extends Command {

    @Override
    public String id() {
        return "exit";
    }

    @Override
    public boolean run(String[] args) {
        bot().requestShutdown();
        return true;
    }

}
