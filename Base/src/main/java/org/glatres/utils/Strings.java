package org.glatres.utils;

public class Strings {

    public static String join(Object[] values, String inBetween, String surrounding) {
        StringBuffer buffer = new StringBuffer();
        for (Object o : values) {
            if (buffer.length() != 0) {
                buffer.append(inBetween);
            }
            buffer.append(surrounding + o + surrounding);
        }
        return buffer.toString();
    }

}
