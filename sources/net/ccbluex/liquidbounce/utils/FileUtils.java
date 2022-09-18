package net.ccbluex.liquidbounce.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/FileUtils.class */
public class FileUtils {
    public static void unpackFile(File file, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        IOUtils.copy(FileUtils.class.getClassLoader().getResourceAsStream(name), fos);
        fos.close();
    }
}
