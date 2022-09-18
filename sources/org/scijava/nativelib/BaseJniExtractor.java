package org.scijava.nativelib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:org/scijava/nativelib/BaseJniExtractor.class */
public abstract class BaseJniExtractor implements JniExtractor {
    private static final Logger LOGGER = Logger.getLogger("org.scijava.nativelib.BaseJniExtractor");
    private static final String JAVA_TMPDIR = "java.io.tmpdir";
    private Class libraryJarClass;
    private String[] nativeResourcePaths;

    public abstract File getNativeDir();

    public abstract File getJniDir();

    public BaseJniExtractor() throws IOException {
        init(null);
    }

    public BaseJniExtractor(Class libraryJarClass) throws IOException {
        init(libraryJarClass);
    }

    private void init(Class libraryJarClass) throws IOException {
        this.libraryJarClass = libraryJarClass;
        String mxSysInfo = MxSysInfo.getMxSysInfo();
        if (mxSysInfo != null) {
            this.nativeResourcePaths = new String[]{"META-INF/lib/" + mxSysInfo + "/", "META-INF/lib/"};
        } else {
            this.nativeResourcePaths = new String[]{"META-INF/lib/"};
        }
    }

    @Override // org.scijava.nativelib.JniExtractor
    public File extractJni(String libPath, String libname) throws IOException {
        String mappedlibName = System.mapLibraryName(libname);
        LOGGER.log(Level.FINE, "mappedLib is " + mappedlibName);
        if (null == this.libraryJarClass) {
            this.libraryJarClass = getClass();
        }
        URL lib = this.libraryJarClass.getClassLoader().getResource(libPath + mappedlibName);
        if (null == lib && mappedlibName.endsWith(".jnilib")) {
            lib = getClass().getClassLoader().getResource(libPath + mappedlibName.substring(0, mappedlibName.length() - 7) + ".dylib");
            if (null != lib) {
                mappedlibName = mappedlibName.substring(0, mappedlibName.length() - 7) + ".dylib";
            }
        }
        if (null != lib) {
            LOGGER.log(Level.FINE, "URL is " + lib.toString());
            LOGGER.log(Level.FINE, "URL path is " + lib.getPath());
            return extractResource(getJniDir(), lib, mappedlibName);
        }
        LOGGER.log(Level.INFO, "Couldn't find resource " + libPath + " " + mappedlibName);
        throw new IOException("Couldn't find resource " + libPath + " " + mappedlibName);
    }

    @Override // org.scijava.nativelib.JniExtractor
    public void extractRegistered() throws IOException {
        LOGGER.log(Level.FINE, "Extracting libraries registered in classloader " + getClass().getClassLoader());
        for (int i = 0; i < this.nativeResourcePaths.length; i++) {
            Enumeration<URL> resources = getClass().getClassLoader().getResources(this.nativeResourcePaths[i] + "AUTOEXTRACT.LIST");
            while (resources.hasMoreElements()) {
                URL res = resources.nextElement();
                LOGGER.log(Level.FINE, "Extracting libraries listed in " + res);
                BufferedReader r = new BufferedReader(new InputStreamReader(res.openStream(), "UTF-8"));
                while (true) {
                    String line = r.readLine();
                    if (line != null) {
                        URL lib = null;
                        for (int j = 0; j < this.nativeResourcePaths.length; j++) {
                            lib = getClass().getClassLoader().getResource(this.nativeResourcePaths[j] + line);
                            if (lib != null) {
                                break;
                            }
                        }
                        if (lib != null) {
                            extractResource(getNativeDir(), lib, line);
                        } else {
                            throw new IOException("Couldn't find native library " + line + "on the classpath");
                        }
                    }
                }
            }
        }
    }

    File extractResource(File dir, URL resource, String outputName) throws IOException {
        InputStream in = resource.openStream();
        String prefix = outputName;
        String suffix = null;
        int lastDotIndex = outputName.lastIndexOf(46);
        if (-1 != lastDotIndex) {
            prefix = outputName.substring(0, lastDotIndex);
            suffix = outputName.substring(lastDotIndex);
        }
        deleteLeftoverFiles(prefix, suffix);
        File outfile = File.createTempFile(prefix, suffix);
        LOGGER.log(Level.FINE, "Extracting '" + resource + "' to '" + outfile.getAbsolutePath() + "'");
        FileOutputStream out = new FileOutputStream(outfile);
        copy(in, out);
        out.close();
        in.close();
        outfile.deleteOnExit();
        return outfile;
    }

    void deleteLeftoverFiles(final String prefix, final String suffix) {
        File tmpDirectory = new File(System.getProperty(JAVA_TMPDIR));
        File[] files = tmpDirectory.listFiles(new FilenameFilter() { // from class: org.scijava.nativelib.BaseJniExtractor.1
            @Override // java.io.FilenameFilter
            public boolean accept(File dir, String name) {
                return name.startsWith(prefix) && name.endsWith(suffix);
            }
        });
        if (files == null) {
            return;
        }
        for (File file : files) {
            try {
                file.delete();
            } catch (SecurityException e) {
            }
        }
    }

    static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] tmp = new byte[8192];
        while (true) {
            int len = in.read(tmp);
            if (len > 0) {
                out.write(tmp, 0, len);
            } else {
                return;
            }
        }
    }
}
