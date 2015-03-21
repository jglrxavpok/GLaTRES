package org.glatres;

import java.util.*;

import com.google.common.collect.*;

import org.glatres.base.*;

public class GLaTRES extends Bot {

    public GLaTRES() {
        super("GLaTRES");
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (alive()) {
            System.out.print("Waiting input: ");
            String line = sc.nextLine();
            String[] parts = split(line);
            String[] args = new String[parts.length - 1];
            for (int i = 0; i < args.length; i++) {
                args[i] = parts[i + 1];
            }
            if (intelligence().hasCommand(parts[0])) {
                intelligence().exec(parts[0], args);
            } else {
                intelligence().interpretSentence(line.split(" "));
            }
        }
        sc.close();
        shutdown();
    }

    private String[] split(String line) {
        char[] chars = line.toCharArray();
        List<String> list = Lists.newArrayList();
        boolean inString = false;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '"') {
                inString = !inString;
            } else if (c == ' ') {
                if (!inString) {
                    list.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                }
            } else {
                buffer.append(c);
            }
        }
        if (buffer.length() != 0) {
            list.add(buffer.toString());
        }
        return list.toArray(new String[0]);
    }

}
