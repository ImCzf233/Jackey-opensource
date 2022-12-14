package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:org/scijava/nativelib/NativeLibraryUtil.class */
public class NativeLibraryUtil {
    private static final String DELIM = "/";
    private static final String JAVA_TMPDIR = "java.io.tmpdir";
    private static Architecture architecture = Architecture.UNKNOWN;
    private static final Logger LOGGER = Logger.getLogger("org.scijava.nativelib.NativeLibraryUtil");

    /* loaded from: Jackey Client b2.jar:org/scijava/nativelib/NativeLibraryUtil$Architecture.class */
    public enum Architecture {
        UNKNOWN,
        LINUX_32,
        LINUX_64,
        WINDOWS_32,
        WINDOWS_64,
        OSX_32,
        OSX_64,
        OSX_PPC
    }

    /* loaded from: Jackey Client b2.jar:org/scijava/nativelib/NativeLibraryUtil$Processor.class */
    public enum Processor {
        UNKNOWN,
        INTEL_32,
        INTEL_64,
        PPC
    }

    public static Architecture getArchitecture() {
        Processor processor;
        if (Architecture.UNKNOWN == architecture && Processor.UNKNOWN != (processor = getProcessor())) {
            String name = System.getProperty("os.name").toLowerCase();
            if (name.indexOf("nix") >= 0 || name.indexOf("nux") >= 0) {
                if (Processor.INTEL_32 == processor) {
                    architecture = Architecture.LINUX_32;
                } else if (Processor.INTEL_64 == processor) {
                    architecture = Architecture.LINUX_64;
                }
            } else if (name.indexOf("win") >= 0) {
                if (Processor.INTEL_32 == processor) {
                    architecture = Architecture.WINDOWS_32;
                } else if (Processor.INTEL_64 == processor) {
                    architecture = Architecture.WINDOWS_64;
                }
            } else if (name.indexOf("mac") >= 0) {
                if (Processor.INTEL_32 == processor) {
                    architecture = Architecture.OSX_32;
                } else if (Processor.INTEL_64 == processor) {
                    architecture = Architecture.OSX_64;
                } else if (Processor.PPC == processor) {
                    architecture = Architecture.OSX_PPC;
                }
            }
        }
        LOGGER.log(Level.FINE, "architecture is " + architecture + " os.name is " + System.getProperty("os.name").toLowerCase());
        return architecture;
    }

    private static Processor getProcessor() {
        Processor processor = Processor.UNKNOWN;
        String arch = System.getProperty("os.arch").toLowerCase();
        if (arch.indexOf("ppc") >= 0) {
            processor = Processor.PPC;
        } else if (arch.indexOf("86") >= 0 || arch.indexOf("amd") >= 0) {
            int bits = 32;
            if (arch.indexOf("64") >= 0) {
                bits = 64;
            }
            processor = 32 == bits ? Processor.INTEL_32 : Processor.INTEL_64;
        }
        LOGGER.log(Level.FINE, "processor is " + processor + " os.arch is " + System.getProperty("os.arch").toLowerCase());
        return processor;
    }

    public static String getPlatformLibraryPath() {
        String path = "META-INF/lib/" + getArchitecture().name().toLowerCase() + DELIM;
        LOGGER.log(Level.FINE, "platform specific path is " + path);
        return path;
    }

    public static String getPlatformLibraryName(String libName) {
        String name = null;
        switch (getArchitecture()) {
            case LINUX_32:
            case LINUX_64:
                name = libName + ".so";
                break;
            case WINDOWS_32:
            case WINDOWS_64:
                name = libName + ".dll";
                break;
            case OSX_32:
            case OSX_64:
                name = "lib" + libName + ".dylib";
                break;
        }
        LOGGER.log(Level.FINE, "native library name " + name);
        return name;
    }

    public static String getVersionedLibraryName(Class libraryJarClass, String libName) {
        String version = libraryJarClass.getPackage().getImplementationVersion();
        if (null != version && version.length() > 0) {
            libName = libName + "-" + version;
        }
        return libName;
    }

    public static boolean loadVersionedNativeLibrary(Class libraryJarClass, String libName) {
        return loadNativeLibrary(libraryJarClass, getVersionedLibraryName(libraryJarClass, libName));
    }

    public static boolean loadNativeLibrary(Class libraryJarClass, String libName) {
        boolean success = false;
        if (Architecture.UNKNOWN == getArchitecture()) {
            LOGGER.log(Level.WARNING, "No native library available for this platform.");
        } else {
            try {
                String tmpDirectory = System.getProperty(JAVA_TMPDIR);
                JniExtractor jniExtractor = new DefaultJniExtractor(libraryJarClass, tmpDirectory);
                File extractedFile = jniExtractor.extractJni(getPlatformLibraryPath(), libName);
                System.load(extractedFile.getPath());
                success = true;
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "IOException creating DefaultJniExtractor", (Throwable) e);
            } catch (SecurityException e2) {
                LOGGER.log(Level.WARNING, "Can't load dynamic library", (Throwable) e2);
            } catch (UnsatisfiedLinkError e3) {
                LOGGER.log(Level.WARNING, "Problem with library", (Throwable) e3);
            }
        }
        return success;
    }
}
