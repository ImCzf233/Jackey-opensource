package jdk.nashorn.internal.codegen;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.options.Options;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/OptimisticTypesPersistence.class */
public final class OptimisticTypesPersistence {
    private static final int DEFAULT_MAX_FILES = 0;
    private static final int UNLIMITED_FILES = -1;
    private static final int DEFAULT_CLEANUP_DELAY = 20;
    private static final String DEFAULT_CACHE_SUBDIR_NAME = "com.oracle.java.NashornTypeInfo";
    private static final Object[] locks;
    private static final long ERROR_REPORT_THRESHOLD = 60000;
    private static volatile long lastReportedError;
    private static final AtomicBoolean scheduledCleanup;
    private static final Timer cleanupTimer;
    private static final int MAX_FILES = getMaxFiles();
    private static final int CLEANUP_DELAY = Math.max(0, Options.getIntProperty("nashorn.typeInfo.cleanupDelaySeconds", 20));
    private static final File baseCacheDir = createBaseCacheDir();
    private static final File cacheDir = createCacheDir(baseCacheDir);

    static {
        locks = cacheDir == null ? null : createLockArray();
        if (baseCacheDir == null || MAX_FILES == -1) {
            scheduledCleanup = null;
            cleanupTimer = null;
            return;
        }
        scheduledCleanup = new AtomicBoolean();
        cleanupTimer = new Timer(true);
    }

    public static Object getLocationDescriptor(Source source, int functionId, Type[] paramTypes) {
        if (cacheDir == null) {
            return null;
        }
        StringBuilder b = new StringBuilder(48);
        b.append(source.getDigest()).append('-').append(functionId);
        if (paramTypes != null && paramTypes.length > 0) {
            b.append('-');
            for (Type t : paramTypes) {
                b.append(Type.getShortSignatureDescriptor(t));
            }
        }
        return new LocationDescriptor(new File(cacheDir, b.toString()));
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/OptimisticTypesPersistence$LocationDescriptor.class */
    public static final class LocationDescriptor {
        private final File file;

        LocationDescriptor(File file) {
            this.file = file;
        }
    }

    public static void store(Object locationDescriptor, final Map<Integer, Type> optimisticTypes) {
        if (locationDescriptor != null && !optimisticTypes.isEmpty()) {
            final File file = ((LocationDescriptor) locationDescriptor).file;
            AccessController.doPrivileged(new PrivilegedAction<Void>() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.1
                @Override // java.security.PrivilegedAction
                public Void run() {
                    synchronized (OptimisticTypesPersistence.getFileLock(file)) {
                        if (!file.exists()) {
                            OptimisticTypesPersistence.scheduleCleanup();
                        }
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            Throwable th = null;
                            try {
                                out.getChannel().lock();
                                DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(out));
                                Type.writeTypeMap(optimisticTypes, dout);
                                dout.flush();
                                if (out != null) {
                                    if (0 != 0) {
                                        try {
                                            out.close();
                                        } catch (Throwable th2) {
                                            th.addSuppressed(th2);
                                        }
                                    } else {
                                        out.close();
                                    }
                                }
                            } finally {
                            }
                        } catch (Exception e) {
                            OptimisticTypesPersistence.reportError("write", file, e);
                        }
                    }
                    return null;
                }
            });
        }
    }

    public static Map<Integer, Type> load(Object locationDescriptor) {
        if (locationDescriptor != null) {
            final File file = ((LocationDescriptor) locationDescriptor).file;
            return (Map) AccessController.doPrivileged(new PrivilegedAction<Map<Integer, Type>>() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.2
                @Override // java.security.PrivilegedAction
                public Map<Integer, Type> run() {
                    Map<Integer, Type> readTypeMap;
                    try {
                        if (file.isFile()) {
                            synchronized (OptimisticTypesPersistence.getFileLock(file)) {
                                FileInputStream in = new FileInputStream(file);
                                Throwable th = null;
                                try {
                                    in.getChannel().lock(0L, LongCompanionObject.MAX_VALUE, true);
                                    DataInputStream din = new DataInputStream(new BufferedInputStream(in));
                                    readTypeMap = Type.readTypeMap(din);
                                    if (in != null) {
                                        if (0 != 0) {
                                            try {
                                                in.close();
                                            } catch (Throwable th2) {
                                                th.addSuppressed(th2);
                                            }
                                        } else {
                                            in.close();
                                        }
                                    }
                                } finally {
                                }
                            }
                            return readTypeMap;
                        }
                        return null;
                    } catch (Exception e) {
                        OptimisticTypesPersistence.reportError("read", file, e);
                        return null;
                    }
                }
            });
        }
        return null;
    }

    public static void reportError(String msg, File file, Exception e) {
        long now = System.currentTimeMillis();
        if (now - lastReportedError > 60000) {
            reportError(String.format("Failed to %s %s", msg, file), e);
            lastReportedError = now;
        }
    }

    public static void reportError(String msg, Exception e) {
        getLogger().warning(msg, "\n", exceptionToString(e));
    }

    private static String exceptionToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter((Writer) sw, false);
        e.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    private static File createBaseCacheDir() {
        if (MAX_FILES == 0 || Options.getBooleanProperty("nashorn.typeInfo.disabled")) {
            return null;
        }
        try {
            return createBaseCacheDirPrivileged();
        } catch (Exception e) {
            reportError("Failed to create cache dir", e);
            return null;
        }
    }

    private static File createBaseCacheDirPrivileged() {
        return (File) AccessController.doPrivileged(new PrivilegedAction<File>() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.3
            @Override // java.security.PrivilegedAction
            public File run() {
                File dir;
                String explicitDir = System.getProperty("nashorn.typeInfo.cacheDir");
                if (explicitDir == null) {
                    File systemCacheDir = OptimisticTypesPersistence.getSystemCacheDir();
                    dir = new File(systemCacheDir, OptimisticTypesPersistence.DEFAULT_CACHE_SUBDIR_NAME);
                    if (OptimisticTypesPersistence.isSymbolicLink(dir)) {
                        return null;
                    }
                } else {
                    dir = new File(explicitDir);
                }
                return dir;
            }
        });
    }

    private static File createCacheDir(File baseDir) {
        if (baseDir == null) {
            return null;
        }
        try {
            return createCacheDirPrivileged(baseDir);
        } catch (Exception e) {
            reportError("Failed to create cache dir", e);
            return null;
        }
    }

    private static File createCacheDirPrivileged(final File baseDir) {
        return (File) AccessController.doPrivileged(new PrivilegedAction<File>() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.4
            @Override // java.security.PrivilegedAction
            public File run() {
                try {
                    String versionDirName = OptimisticTypesPersistence.getVersionDirName();
                    File versionDir = new File(baseDir, versionDirName);
                    if (OptimisticTypesPersistence.isSymbolicLink(versionDir)) {
                        return null;
                    }
                    versionDir.mkdirs();
                    if (versionDir.isDirectory()) {
                        OptimisticTypesPersistence.getLogger().info("Optimistic type persistence directory is " + versionDir);
                        return versionDir;
                    }
                    OptimisticTypesPersistence.getLogger().warning("Could not create optimistic type persistence directory " + versionDir);
                    return null;
                } catch (Exception e) {
                    OptimisticTypesPersistence.reportError("Failed to calculate version dir name", e);
                    return null;
                }
            }
        });
    }

    public static File getSystemCacheDir() {
        String os = System.getProperty("os.name", "generic");
        if ("Mac OS X".equals(os)) {
            return new File(new File(System.getProperty("user.home"), "Library"), "Caches");
        }
        if (os.startsWith("Windows")) {
            return new File(System.getProperty("java.io.tmpdir"));
        }
        return new File(System.getProperty("user.home"), ".cache");
    }

    public static String getVersionDirName() throws Exception {
        URL url = OptimisticTypesPersistence.class.getResource("anchor.properties");
        String protocol = url.getProtocol();
        if (protocol.equals("jar")) {
            String jarUrlFile = url.getFile();
            String filePath = jarUrlFile.substring(0, jarUrlFile.indexOf(33));
            URL file = new URL(filePath);
            InputStream in = file.openStream();
            Throwable th = null;
            try {
                byte[] buf = new byte[131072];
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                while (true) {
                    int l = in.read(buf);
                    if (l == -1) {
                        break;
                    }
                    digest.update(buf, 0, l);
                }
                String encodeToString = Base64.getUrlEncoder().withoutPadding().encodeToString(digest.digest());
                if (in != null) {
                    if (0 != 0) {
                        try {
                            in.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    } else {
                        in.close();
                    }
                }
                return encodeToString;
            } catch (Throwable th3) {
                if (in != null) {
                    if (0 != 0) {
                        try {
                            in.close();
                        } catch (Throwable th4) {
                            th.addSuppressed(th4);
                        }
                    } else {
                        in.close();
                    }
                }
                throw th3;
            }
        } else if (protocol.equals("file")) {
            String fileStr = url.getFile();
            String className = OptimisticTypesPersistence.class.getName();
            int packageNameLen = className.lastIndexOf(46);
            String dirStr = fileStr.substring(0, (fileStr.length() - packageNameLen) - 1);
            File dir = new File(dirStr);
            return "dev-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(getLastModifiedClassFile(dir, 0L)));
        } else {
            throw new AssertionError();
        }
    }

    private static long getLastModifiedClassFile(File dir, long max) {
        File[] listFiles;
        long currentMax = max;
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith(".class")) {
                long lastModified = f.lastModified();
                if (lastModified > currentMax) {
                    currentMax = lastModified;
                }
            } else if (f.isDirectory()) {
                long lastModified2 = getLastModifiedClassFile(f, currentMax);
                if (lastModified2 > currentMax) {
                    currentMax = lastModified2;
                }
            }
        }
        return currentMax;
    }

    public static boolean isSymbolicLink(File file) {
        if (Files.isSymbolicLink(file.toPath())) {
            getLogger().warning("Directory " + file + " is a symlink");
            return true;
        }
        return false;
    }

    private static Object[] createLockArray() {
        Object[] lockArray = new Object[Runtime.getRuntime().availableProcessors() * 2];
        for (int i = 0; i < lockArray.length; i++) {
            lockArray[i] = new Object();
        }
        return lockArray;
    }

    public static Object getFileLock(File file) {
        return locks[(file.hashCode() & Integer.MAX_VALUE) % locks.length];
    }

    public static DebugLogger getLogger() {
        try {
            return Context.getContext().getLogger(RecompilableScriptFunctionData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return DebugLogger.DISABLED_LOGGER;
        }
    }

    public static void scheduleCleanup() {
        if (MAX_FILES != -1 && scheduledCleanup.compareAndSet(false, true)) {
            cleanupTimer.schedule(new TimerTask() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.5
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    OptimisticTypesPersistence.scheduledCleanup.set(false);
                    try {
                        OptimisticTypesPersistence.doCleanup();
                    } catch (IOException e) {
                    }
                }
            }, TimeUnit.SECONDS.toMillis(CLEANUP_DELAY));
        }
    }

    public static void doCleanup() throws IOException {
        Path[] files = getAllRegularFilesInLastModifiedOrder();
        int nFiles = files.length;
        int filesToDelete = Math.max(0, nFiles - MAX_FILES);
        int filesDeleted = 0;
        for (int i = 0; i < nFiles && filesDeleted < filesToDelete; i++) {
            try {
                Files.deleteIfExists(files[i]);
                filesDeleted++;
            } catch (Exception e) {
            }
            files[i] = null;
        }
    }

    private static Path[] getAllRegularFilesInLastModifiedOrder() throws IOException {
        Stream<Path> filesStream = Files.walk(baseCacheDir.toPath(), new FileVisitOption[0]);
        Throwable th = null;
        try {
            Path[] pathArr = (Path[]) filesStream.filter(new Predicate<Path>() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.9
                public boolean test(Path path) {
                    return !Files.isDirectory(path, new LinkOption[0]);
                }
            }).map(new Function<Path, PathAndTime>() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.8
                public PathAndTime apply(Path path) {
                    return new PathAndTime(path);
                }
            }).sorted().map(new Function<PathAndTime, Path>() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.7
                public Path apply(PathAndTime pathAndTime) {
                    return pathAndTime.path;
                }
            }).toArray(new IntFunction<Path[]>() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.6
                @Override // java.util.function.IntFunction
                public Path[] apply(int length) {
                    return new Path[length];
                }
            });
            if (filesStream != null) {
                if (0 != 0) {
                    try {
                        filesStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                } else {
                    filesStream.close();
                }
            }
            return pathArr;
        } finally {
        }
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/OptimisticTypesPersistence$PathAndTime.class */
    public static class PathAndTime implements Comparable<PathAndTime> {
        private final Path path;
        private final long time;

        PathAndTime(Path path) {
            this.path = path;
            this.time = getTime(path);
        }

        public int compareTo(PathAndTime other) {
            return Long.compare(this.time, other.time);
        }

        private static long getTime(Path path) {
            try {
                return Files.getLastModifiedTime(path, new LinkOption[0]).toMillis();
            } catch (IOException e) {
                return -1L;
            }
        }
    }

    private static int getMaxFiles() {
        String str = Options.getStringProperty("nashorn.typeInfo.maxFiles", null);
        if (str == null) {
            return 0;
        }
        if ("unlimited".equals(str)) {
            return -1;
        }
        return Math.max(0, Integer.parseInt(str));
    }
}
