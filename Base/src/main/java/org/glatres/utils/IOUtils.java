package org.glatres.utils;

import java.io.*;
import java.net.*;

public class IOUtils {

    public static String readTextURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                buffer.append(currentLine);
                buffer.append("\n");
            }
        }

        return buffer.toString();
    }

}
