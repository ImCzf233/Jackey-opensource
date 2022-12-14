package com.viaversion.viaversion.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/CommentStore.class */
public class CommentStore {
    private final char pathSeperator;
    private final int indents;
    private final Map<String, List<String>> headers = Maps.newConcurrentMap();
    private List<String> mainHeader = Lists.newArrayList();

    public CommentStore(char pathSeperator, int indents) {
        this.pathSeperator = pathSeperator;
        this.indents = indents;
    }

    public void mainHeader(String... header) {
        this.mainHeader = Arrays.asList(header);
    }

    public List<String> mainHeader() {
        return this.mainHeader;
    }

    public void header(String key, String... header) {
        this.headers.put(key, Arrays.asList(header));
    }

    public List<String> header(String key) {
        return this.headers.get(key);
    }

    public void storeComments(InputStream inputStream) throws IOException {
        String[] split;
        InputStreamReader reader = new InputStreamReader(inputStream);
        try {
            String contents = CharStreams.toString(reader);
            reader.close();
            StringBuilder memoryData = new StringBuilder();
            String pathSeparator = Character.toString(this.pathSeperator);
            int currentIndents = 0;
            String key = "";
            List<String> headers = Lists.newArrayList();
            for (String line : contents.split("\n")) {
                if (!line.isEmpty()) {
                    int indent = getSuccessiveCharCount(line, ' ');
                    String subline = indent > 0 ? line.substring(indent) : line;
                    if (subline.startsWith("#")) {
                        if (subline.startsWith("#>")) {
                            String txt = subline.startsWith("#> ") ? subline.substring(3) : subline.substring(2);
                            this.mainHeader.add(txt);
                        } else {
                            String txt2 = subline.startsWith("# ") ? subline.substring(2) : subline.substring(1);
                            headers.add(txt2);
                        }
                    } else {
                        int indents = indent / this.indents;
                        if (indents <= currentIndents) {
                            String[] array = key.split(Pattern.quote(pathSeparator));
                            int backspace = (currentIndents - indents) + 1;
                            key = join(array, this.pathSeperator, 0, array.length - backspace);
                        }
                        String separator = key.length() > 0 ? pathSeparator : "";
                        String lineKey = line.contains(CallSiteDescriptor.TOKEN_DELIMITER) ? line.split(Pattern.quote(CallSiteDescriptor.TOKEN_DELIMITER))[0] : line;
                        key = key + separator + lineKey.substring(indent);
                        currentIndents = indents;
                        memoryData.append(line).append('\n');
                        if (!headers.isEmpty()) {
                            this.headers.put(key, headers);
                            headers = Lists.newArrayList();
                        }
                    }
                }
            }
        } catch (Throwable th) {
            reader.close();
            throw th;
        }
    }

    public void writeComments(String yaml, File output) throws IOException {
        String[] split;
        int indentLength = this.indents;
        String pathSeparator = Character.toString(this.pathSeperator);
        StringBuilder fileData = new StringBuilder();
        int currentIndents = 0;
        String key = "";
        for (String h : this.mainHeader) {
            fileData.append("#> ").append(h).append('\n');
        }
        for (String line : yaml.split("\n")) {
            if (line.isEmpty() || line.trim().charAt(0) == '-') {
                fileData.append(line).append('\n');
            } else {
                int indent = getSuccessiveCharCount(line, ' ');
                int indents = indent / indentLength;
                String indentText = indent > 0 ? line.substring(0, indent) : "";
                if (indents <= currentIndents) {
                    String[] array = key.split(Pattern.quote(pathSeparator));
                    int backspace = (currentIndents - indents) + 1;
                    key = join(array, this.pathSeperator, 0, array.length - backspace);
                }
                String separator = !key.isEmpty() ? pathSeparator : "";
                String lineKey = line.contains(CallSiteDescriptor.TOKEN_DELIMITER) ? line.split(Pattern.quote(CallSiteDescriptor.TOKEN_DELIMITER))[0] : line;
                key = key + separator + lineKey.substring(indent);
                currentIndents = indents;
                List<String> header = this.headers.get(key);
                String headerText = header != null ? addHeaderTags(header, indentText) : "";
                fileData.append(headerText).append(line).append('\n');
            }
        }
        Files.write(fileData.toString(), output, StandardCharsets.UTF_8);
    }

    private String addHeaderTags(List<String> header, String indent) {
        StringBuilder builder = new StringBuilder();
        for (String line : header) {
            builder.append(indent).append("# ").append(line).append('\n');
        }
        return builder.toString();
    }

    private String join(String[] array, char joinChar, int start, int length) {
        String[] copy = new String[length - start];
        System.arraycopy(array, start, copy, 0, length - start);
        return Joiner.on(joinChar).join(copy);
    }

    private int getSuccessiveCharCount(String text, char key) {
        int count = 0;
        for (int i = 0; i < text.length() && text.charAt(i) == key; i++) {
            count++;
        }
        return count;
    }
}
