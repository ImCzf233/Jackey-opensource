package jdk.nashorn.internal.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.function.Supplier;
import jdk.nashorn.internal.codegen.CompileUnit;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "time")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Timing.class */
public final class Timing implements Loggable {
    private DebugLogger log;
    private TimeSupplier timeSupplier;
    private final boolean isEnabled;
    private final long startTime = System.nanoTime();
    private static final String LOGGER_NAME;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Timing.class.desiredAssertionStatus();
        LOGGER_NAME = ((Logger) Timing.class.getAnnotation(Logger.class)).name();
    }

    public Timing(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getLogInfo() {
        if ($assertionsDisabled || isEnabled()) {
            return this.timeSupplier.get();
        }
        throw new AssertionError();
    }

    public String[] getLogInfoLines() {
        if ($assertionsDisabled || isEnabled()) {
            return this.timeSupplier.getStrings();
        }
        throw new AssertionError();
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void accumulateTime(String module, long durationNano) {
        if (isEnabled()) {
            ensureInitialized(Context.getContextTrusted());
            this.timeSupplier.accumulateTime(module, durationNano);
        }
    }

    private DebugLogger ensureInitialized(Context context) {
        if (isEnabled() && this.log == null) {
            this.log = initLogger(context);
            if (this.log.isEnabled()) {
                this.timeSupplier = new TimeSupplier();
                Runtime.getRuntime().addShutdownHook(new Thread() { // from class: jdk.nashorn.internal.runtime.Timing.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        String[] strings;
                        StringBuilder sb = new StringBuilder();
                        for (String str : Timing.this.timeSupplier.getStrings()) {
                            sb.append('[').append(Timing.getLoggerName()).append("] ").append(str).append('\n');
                        }
                        System.err.print(sb);
                    }
                });
            }
        }
        return this.log;
    }

    public static String getLoggerName() {
        return LOGGER_NAME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    public static String toMillisPrint(long durationNano) {
        return Long.toString(TimeUnit.NANOSECONDS.toMillis(durationNano));
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/Timing$TimeSupplier.class */
    public final class TimeSupplier implements Supplier<String> {
        private final Map<String, LongAdder> timings = new ConcurrentHashMap();
        private final LinkedBlockingQueue<String> orderedTimingNames = new LinkedBlockingQueue<>();
        private final Function<String, LongAdder> newTimingCreator = new Function<String, LongAdder>() { // from class: jdk.nashorn.internal.runtime.Timing.TimeSupplier.1
            public LongAdder apply(String s) {
                TimeSupplier.this.orderedTimingNames.add(s);
                return new LongAdder();
            }
        };

        TimeSupplier() {
            Timing.this = this$0;
        }

        String[] getStrings() {
            List<String> strs = new ArrayList<>();
            BufferedReader br = new BufferedReader(new StringReader(get()));
            while (true) {
                try {
                    String line = br.readLine();
                    if (line != null) {
                        strs.add(line);
                    } else {
                        return (String[]) strs.toArray(new String[strs.size()]);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override // java.util.function.Supplier
        public String get() {
            long t = System.nanoTime();
            long knownTime = 0;
            int maxKeyLength = 0;
            int maxValueLength = 0;
            for (Map.Entry<String, LongAdder> entry : this.timings.entrySet()) {
                maxKeyLength = Math.max(maxKeyLength, entry.getKey().length());
                maxValueLength = Math.max(maxValueLength, Timing.toMillisPrint(entry.getValue().longValue()).length());
            }
            int maxKeyLength2 = maxKeyLength + 1;
            StringBuilder sb = new StringBuilder();
            sb.append("Accumulated compilation phase timings:\n\n");
            Iterator<String> it = this.orderedTimingNames.iterator();
            while (it.hasNext()) {
                String timingName = it.next();
                int len = sb.length();
                sb.append(timingName);
                int len2 = sb.length() - len;
                while (true) {
                    int i = len2;
                    len2++;
                    if (i >= maxKeyLength2) {
                        break;
                    }
                    sb.append(' ');
                }
                long duration = this.timings.get(timingName).longValue();
                String strDuration = Timing.toMillisPrint(duration);
                int len3 = strDuration.length();
                for (int i2 = 0; i2 < maxValueLength - len3; i2++) {
                    sb.append(' ');
                }
                sb.append(strDuration).append(" ms\n");
                knownTime += duration;
            }
            long total = t - Timing.this.startTime;
            sb.append('\n');
            sb.append("Total runtime: ").append(Timing.toMillisPrint(total)).append(" ms (Non-runtime: ").append(Timing.toMillisPrint(knownTime)).append(" ms [").append((int) ((knownTime * 100.0d) / total)).append("%])");
            sb.append("\n\nEmitted compile units: ").append(CompileUnit.getEmittedUnitCount());
            return sb.toString();
        }

        public void accumulateTime(String module, long duration) {
            this.timings.computeIfAbsent(module, this.newTimingCreator).add(duration);
        }
    }
}
