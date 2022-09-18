package org.spongepowered.asm.mixin.transformer.debug;

import java.io.File;
import java.lang.Thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/debug/RuntimeDecompilerAsync.class */
public class RuntimeDecompilerAsync extends RuntimeDecompiler implements Runnable, Thread.UncaughtExceptionHandler {
    private final BlockingQueue<File> queue = new LinkedBlockingQueue();
    private boolean run = true;
    private final Thread thread = new Thread(this, "Decompiler thread");

    public RuntimeDecompilerAsync(File outputPath) {
        super(outputPath);
        this.thread.setDaemon(true);
        this.thread.setPriority(1);
        this.thread.setUncaughtExceptionHandler(this);
        this.thread.start();
    }

    @Override // org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler, org.spongepowered.asm.mixin.transformer.ext.IDecompiler
    public void decompile(File file) {
        if (this.run) {
            this.queue.offer(file);
        } else {
            super.decompile(file);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (this.run) {
            try {
                File file = this.queue.take();
                super.decompile(file);
            } catch (InterruptedException e) {
                this.run = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable ex) {
        this.logger.error("Async decompiler encountered an error and will terminate. Further decompile requests will be handled synchronously. {} {}", new Object[]{ex.getClass().getName(), ex.getMessage()});
        flush();
    }

    private void flush() {
        this.run = false;
        while (true) {
            File file = this.queue.poll();
            if (file != null) {
                decompile(file);
            } else {
                return;
            }
        }
    }
}
