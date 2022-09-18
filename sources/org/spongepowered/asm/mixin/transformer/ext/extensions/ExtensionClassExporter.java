package org.spongepowered.asm.mixin.transformer.ext.extensions;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.spi.LocationInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.Marker;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ext/extensions/ExtensionClassExporter.class */
public class ExtensionClassExporter implements IExtension {
    private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
    private static final String EXPORT_CLASS_DIR = "class";
    private static final String EXPORT_JAVA_DIR = "java";
    private static final Logger logger = LogManager.getLogger("mixin");
    private final File classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, EXPORT_CLASS_DIR);
    private final IDecompiler decompiler;

    public ExtensionClassExporter(MixinEnvironment env) {
        this.decompiler = initDecompiler(env, new File(Constants.DEBUG_OUTPUT_DIR, EXPORT_JAVA_DIR));
        try {
            FileUtils.deleteDirectory(this.classExportDir);
        } catch (IOException ex) {
            logger.warn("Error cleaning class output directory: {}", new Object[]{ex.getMessage()});
        }
    }

    public boolean isDecompilerActive() {
        return this.decompiler != null;
    }

    private IDecompiler initDecompiler(MixinEnvironment env, File outputPath) {
        if (!env.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
            return null;
        }
        try {
            boolean as = env.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
            Logger logger2 = logger;
            Object[] objArr = new Object[1];
            objArr[0] = as ? " (Threaded mode)" : "";
            logger2.info("Attempting to load Fernflower decompiler{}", objArr);
            String className = DECOMPILER_CLASS + (as ? "Async" : "");
            IDecompiler decompiler = (IDecompiler) Class.forName(className).getDeclaredConstructor(File.class).newInstance(outputPath);
            Logger logger3 = logger;
            Object[] objArr2 = new Object[1];
            objArr2[0] = as ? " in a separate thread" : "";
            logger3.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", objArr2);
            return decompiler;
        } catch (Throwable th) {
            logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[]{th.getClass().getSimpleName(), th.getMessage()});
            return null;
        }
    }

    private String prepareFilter(String filter) {
        return ("^\\Q" + filter.replace("**", "\u0081").replace(Marker.ANY_MARKER, "\u0082").replace(LocationInfo.f402NA, "\u0083") + "\\E$").replace("\u0081", "\\E.*\\Q").replace("\u0082", "\\E[^\\.]+\\Q").replace("\u0083", "\\E.\\Q").replace("\\Q\\E", "");
    }

    private boolean applyFilter(String filter, String subject) {
        return Pattern.compile(prepareFilter(filter), 2).matcher(subject).matches();
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public boolean checkActive(MixinEnvironment environment) {
        return true;
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public void preApply(ITargetClassContext context) {
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public void postApply(ITargetClassContext context) {
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public void export(MixinEnvironment env, String name, boolean force, byte[] bytes) {
        if (force || env.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
            String filter = env.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
            if (force || filter == null || applyFilter(filter, name)) {
                Profiler.Section exportTimer = MixinEnvironment.getProfiler().begin("debug.export");
                File outputFile = dumpClass(name.replace('.', '/'), bytes);
                if (this.decompiler != null) {
                    this.decompiler.decompile(outputFile);
                }
                exportTimer.end();
            }
        }
    }

    public File dumpClass(String fileName, byte[] bytes) {
        File outputFile = new File(this.classExportDir, fileName + ".class");
        try {
            FileUtils.writeByteArrayToFile(outputFile, bytes);
        } catch (IOException e) {
        }
        return outputFile;
    }
}
