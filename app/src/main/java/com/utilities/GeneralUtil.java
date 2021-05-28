package com.utilities;

public class GeneralUtil {

    public final static String SPACE = " ";

    public static String getIndent(int indent) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < indent; i++) {
            builder.append(SPACE);
        }

        return  builder.toString();
    }

    /**
     * Removes last added commata (,) closes JSONArray with (]) and closes JSONObject (}) at last with correct indent.
     *
     * @param indent
     * @param builder
     * @return
     */
    public static String finalizeJSON(String indent, StringBuilder builder) {
        String tmp = builder.substring(0, builder.length() - 2);
        builder = new StringBuilder(tmp);
        builder.append("\n");

        builder.append(indent);
        builder.append("  ]\n");
        builder.append(indent);
        builder.append("}");

        return builder.toString();
    }
}
