package org.glatres.intelligence;

import java.io.*;
import java.util.*;

import com.google.common.collect.*;

import org.glatres.ai.*;
import org.glatres.intelligence.commands.*;
import org.glatres.lang.words.*;
import org.glatres.utils.*;

public class LearningIntelligence extends Intelligence {

    private Map<String, Command> commands;
    private String lang;

    @Override
    public Intelligence init() {
        commands = Maps.newHashMap();
        addCommand("exit", new ExitCommand());

        lang = "english";
        return this;
    }

    private void addCommand(String id, Command command) {
        command.bot(bot());
        commands.put(id, command);
    }

    @Override
    public void shutdown() {
        // TODO Implement

    }

    @Override
    public void exec(String commandID, String[] args) {
        Command command = commands.get(commandID);
        if (command != null) {
            command.run(args);
        } else {
            System.err.println("No command with id " + commandID);
        }
    }

    @Override
    public boolean hasCommand(String id) {
        return commands.containsKey(id);
    }

    @Override
    public void interpretSentence(String[] words) {
        for (String w : words) {
            WordInfo infos = bot().storageSystem().getWordInfos(lang, w);
            if (infos == null) {
                fetchWord(lang, w);
            }
        }
        System.out.println();
    }

    private void fetchWord(String lang, String word) {
        String key = dictionaryapiKey();
        if (key != null) {
            try {
                String content = IOUtils.readTextURL("http://www.dictionaryapi.com/api/v1/references/learners/xml/" + word + "?key=" + key);
                // TODO: parse XML data
                System.out.println(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Impossible to fetch word '" + word + "' because no dictapiKey was registred");
        }
    }
}
