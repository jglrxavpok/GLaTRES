package org.glatres;

import java.io.*;
import java.util.*;

import com.google.common.collect.*;

import org.glatres.base.*;
import org.glatres.intelligence.*;
import org.glatres.stockage.*;

public class StartGLaTRES {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("GLaTRES root file was not specified. Add the argument \"rootFile=<your path here>\"");
        }
        Map<String, String> properties = fetchProperties(args);
        File rootFile = new File(properties.get("rootFile"));
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        Bot bot = new GLaTRES();
        bot.rootFile(rootFile);
        bot.intelligence(new LearningIntelligence());
        if (properties.containsKey("dictapi.com-key")) {
            bot.intelligence().dictionaryapiKey(properties.get("dictapi.com-key"));
        }
        bot.storageSystem(new SQLStorage());
        bot.init();
        new Thread(bot).start();
    }

    private static Map<String, String> fetchProperties(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        for (String s : args) {
            String[] parts = s.split("=");
            map.put(parts[0], parts[1]);
        }
        return map;
    }
}
