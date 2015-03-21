package org.glatres.intelligence;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import com.google.common.collect.*;

import org.glatres.ai.*;
import org.glatres.intelligence.commands.*;
import org.glatres.lang.words.*;
import org.w3c.dom.*;
import org.xml.sax.*;

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
            WordInfo infos = bot().storageSystem().getWordInfos(lang, w + "[0]");
            if (infos == null) {
                fetchWord(lang, w);
                infos = bot().storageSystem().getWordInfos(lang, w + "[0]");
            }
            System.out.println(infos);
        }
        System.out.println();
    }

    private void fetchWord(String lang, String word) {
        String key = dictionaryapiKey();
        if (key != null) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse("http://www.dictionaryapi.com/api/v1/references/learners/xml/" + word + "?key=" + key);
                NodeList functionalLabel = doc.getElementsByTagName("fl");
                NodeList pronunciationElems = doc.getElementsByTagName("pr");

                String pronunciation = null;
                Node prElem = pronunciationElems.item(0);
                if (prElem.hasChildNodes()) {
                    Node child = prElem.getChildNodes().item(0);
                    pronunciation = child.getNodeValue();
                }

                List<WordInfo> list = Lists.newArrayList();
                for (int i = 0; i < functionalLabel.getLength(); i++) {
                    WordType type = null;
                    Node flElem = functionalLabel.item(i);
                    // String entry = flElem.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
                    // if (!entry.contains(word))
                    //    continue;
                    if (flElem.hasChildNodes()) {
                        Node child = flElem.getChildNodes().item(0);
                        String text = child.getNodeValue();
                        type = WordType.valueOf(text.toUpperCase());
                        list.add(new WordInfo(lang, word + "[" + i + "]", pronunciation, type));
                    }
                }

                for (WordInfo info : list) {
                    bot().storageSystem().saveWord(info);
                    System.out.println("registred " + info);
                }
            } catch (IOException | ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Impossible to fetch word '" + word + "' because no dictapiKey was registred");
        }
    }
}
