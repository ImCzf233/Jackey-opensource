package de.enzaxd.viaforge.platform;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import de.enzaxd.viaforge.ViaForge;
import de.enzaxd.viaforge.util.FutureTaskId;
import de.enzaxd.viaforge.util.JLoggerToLog4j;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.File;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

/* loaded from: Jackey Client b2.jar:de/enzaxd/viaforge/platform/Platform.class */
public class Platform implements ViaPlatform<UUID> {
    private final ViaConfig config;
    private final File dataFolder;
    private final Logger logger = new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
    private final com.viaversion.viaversion.api.ViaAPI api = new ViaAPI();

    public Platform(File dataFolder) {
        Path configDir = dataFolder.toPath().resolve("ViaVersion");
        this.config = new ViaConfig(configDir.resolve("viaversion.yml").toFile());
        this.dataFolder = configDir.toFile();
    }

    public static String legacyToJson(String legacy) {
        return GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacySection().deserialize(legacy));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public Logger getLogger() {
        return this.logger;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformName() {
        return "ViaForge";
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformVersion() {
        return "47";
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPluginVersion() {
        return "4.0.0";
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public FutureTaskId runAsync(Runnable runnable) {
        return new FutureTaskId(CompletableFuture.runAsync(runnable, ViaForge.getInstance().getAsyncExecutor()).exceptionally(throwable -> {
            if (!(throwable instanceof CancellationException)) {
                throwable.printStackTrace();
                return null;
            }
            return null;
        }));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public FutureTaskId runSync(Runnable runnable) {
        return new FutureTaskId(ViaForge.getInstance().getEventLoop().submit(runnable).addListener(errorLogger()));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runSync(Runnable runnable, long ticks) {
        return new FutureTaskId(ViaForge.getInstance().getEventLoop().schedule(()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x002a: RETURN  
              (wrap: de.enzaxd.viaforge.util.FutureTaskId : 0x0027: CONSTRUCTOR  (r0v0 de.enzaxd.viaforge.util.FutureTaskId A[REMOVE]) = 
              (wrap: io.netty.util.concurrent.Future : 0x0022: INVOKE  (r2v3 io.netty.util.concurrent.Future A[REMOVE]) = 
              (wrap: io.netty.util.concurrent.ScheduledFuture : 0x0019: INVOKE  (r2v2 io.netty.util.concurrent.ScheduledFuture A[REMOVE]) = 
              (wrap: io.netty.channel.EventLoop : 0x0007: INVOKE  (r2v1 io.netty.channel.EventLoop A[REMOVE]) = 
              (wrap: de.enzaxd.viaforge.ViaForge : 0x0004: INVOKE  (r2v0 de.enzaxd.viaforge.ViaForge A[REMOVE]) =  type: STATIC call: de.enzaxd.viaforge.ViaForge.getInstance():de.enzaxd.viaforge.ViaForge)
             type: VIRTUAL call: de.enzaxd.viaforge.ViaForge.getEventLoop():io.netty.channel.EventLoop)
              (wrap: java.util.concurrent.Callable : 0x000c: INVOKE_CUSTOM (r3v1 java.util.concurrent.Callable A[REMOVE]) = 
              (r9v0 'this' de.enzaxd.viaforge.platform.Platform A[D('this' de.enzaxd.viaforge.platform.Platform), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r10v0 'runnable' java.lang.Runnable A[D('runnable' java.lang.Runnable), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.util.concurrent.Callable.call():java.lang.Object
             call insn: ?: INVOKE  (r3 I:de.enzaxd.viaforge.platform.Platform), (r4 I:java.lang.Runnable) type: DIRECT call: de.enzaxd.viaforge.platform.Platform.lambda$runSync$1(java.lang.Runnable):de.enzaxd.viaforge.util.FutureTaskId)
              (wrap: long : 0x0015: ARITH  (r4v2 long A[REMOVE]) = (r11v0 'ticks' long A[D('ticks' long)]) * (50 long))
              (wrap: java.util.concurrent.TimeUnit : 0x0016: SGET  (r5v1 java.util.concurrent.TimeUnit A[REMOVE]) =  java.util.concurrent.TimeUnit.MILLISECONDS java.util.concurrent.TimeUnit)
             type: INTERFACE call: io.netty.channel.EventLoop.schedule(java.util.concurrent.Callable, long, java.util.concurrent.TimeUnit):io.netty.util.concurrent.ScheduledFuture)
              (wrap: io.netty.util.concurrent.GenericFutureListener : 0x001f: INVOKE  (r3v3 io.netty.util.concurrent.GenericFutureListener A[REMOVE]) = 
              (r9v0 'this' de.enzaxd.viaforge.platform.Platform A[D('this' de.enzaxd.viaforge.platform.Platform), IMMUTABLE_TYPE, THIS])
             type: DIRECT call: de.enzaxd.viaforge.platform.Platform.errorLogger():io.netty.util.concurrent.GenericFutureListener)
             type: INTERFACE call: io.netty.util.concurrent.ScheduledFuture.addListener(io.netty.util.concurrent.GenericFutureListener):io.netty.util.concurrent.Future)
             call: de.enzaxd.viaforge.util.FutureTaskId.<init>(java.util.concurrent.Future):void type: CONSTRUCTOR)
             in method: de.enzaxd.viaforge.platform.Platform.runSync(java.lang.Runnable, long):com.viaversion.viaversion.api.platform.PlatformTask, file: Jackey Client b2.jar:de/enzaxd/viaforge/platform/Platform.class
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            Caused by: java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
            	at java.base/java.util.Objects.checkIndex(Unknown Source)
            	at java.base/java.util.ArrayList.get(Unknown Source)
            	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
            	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:830)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:93)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:804)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:745)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:395)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:345)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
            	... 15 more
            */
        /*
            this = this;
            de.enzaxd.viaforge.util.FutureTaskId r0 = new de.enzaxd.viaforge.util.FutureTaskId
            r1 = r0
            de.enzaxd.viaforge.ViaForge r2 = de.enzaxd.viaforge.ViaForge.getInstance()
            io.netty.channel.EventLoop r2 = r2.getEventLoop()
            r3 = r9
            r4 = r10
            com.viaversion.viaversion.api.platform.PlatformTask r3 = () -> { // java.util.concurrent.Callable.call():java.lang.Object
                return r3.lambda$runSync$1(r4);
            }
            r4 = r11
            r5 = 50
            long r4 = r4 * r5
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.MILLISECONDS
            io.netty.util.concurrent.ScheduledFuture r2 = r2.schedule(r3, r4, r5)
            r3 = r9
            io.netty.util.concurrent.GenericFutureListener r3 = r3.errorLogger()
            io.netty.util.concurrent.Future r2 = r2.addListener(r3)
            r1.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: de.enzaxd.viaforge.platform.Platform.runSync(java.lang.Runnable, long):com.viaversion.viaversion.api.platform.PlatformTask");
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runRepeatingSync(Runnable runnable, long ticks) {
        return new FutureTaskId(ViaForge.getInstance().getEventLoop().scheduleAtFixedRate(()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x002b: RETURN  
              (wrap: de.enzaxd.viaforge.util.FutureTaskId : 0x0028: CONSTRUCTOR  (r0v0 de.enzaxd.viaforge.util.FutureTaskId A[REMOVE]) = 
              (wrap: io.netty.util.concurrent.Future : 0x0023: INVOKE  (r2v3 io.netty.util.concurrent.Future A[REMOVE]) = 
              (wrap: io.netty.util.concurrent.ScheduledFuture : 0x001a: INVOKE  (r2v2 io.netty.util.concurrent.ScheduledFuture A[REMOVE]) = 
              (wrap: io.netty.channel.EventLoop : 0x0007: INVOKE  (r2v1 io.netty.channel.EventLoop A[REMOVE]) = 
              (wrap: de.enzaxd.viaforge.ViaForge : 0x0004: INVOKE  (r2v0 de.enzaxd.viaforge.ViaForge A[REMOVE]) =  type: STATIC call: de.enzaxd.viaforge.ViaForge.getInstance():de.enzaxd.viaforge.ViaForge)
             type: VIRTUAL call: de.enzaxd.viaforge.ViaForge.getEventLoop():io.netty.channel.EventLoop)
              (wrap: java.lang.Runnable : 0x000c: INVOKE_CUSTOM (r3v1 java.lang.Runnable A[REMOVE]) = 
              (r11v0 'this' de.enzaxd.viaforge.platform.Platform A[D('this' de.enzaxd.viaforge.platform.Platform), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r12v0 'runnable' java.lang.Runnable A[D('runnable' java.lang.Runnable), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.lang.Runnable.run():void
             call insn: ?: INVOKE  (r3 I:de.enzaxd.viaforge.platform.Platform), (r4 I:java.lang.Runnable) type: DIRECT call: de.enzaxd.viaforge.platform.Platform.lambda$runRepeatingSync$2(java.lang.Runnable):void)
              (0 long)
              (wrap: long : 0x0016: ARITH  (r5v1 long A[REMOVE]) = (r13v0 'ticks' long A[D('ticks' long)]) * (50 long))
              (wrap: java.util.concurrent.TimeUnit : 0x0017: SGET  (r6v1 java.util.concurrent.TimeUnit A[REMOVE]) =  java.util.concurrent.TimeUnit.MILLISECONDS java.util.concurrent.TimeUnit)
             type: INTERFACE call: io.netty.channel.EventLoop.scheduleAtFixedRate(java.lang.Runnable, long, long, java.util.concurrent.TimeUnit):io.netty.util.concurrent.ScheduledFuture)
              (wrap: io.netty.util.concurrent.GenericFutureListener : 0x0020: INVOKE  (r3v3 io.netty.util.concurrent.GenericFutureListener A[REMOVE]) = 
              (r11v0 'this' de.enzaxd.viaforge.platform.Platform A[D('this' de.enzaxd.viaforge.platform.Platform), IMMUTABLE_TYPE, THIS])
             type: DIRECT call: de.enzaxd.viaforge.platform.Platform.errorLogger():io.netty.util.concurrent.GenericFutureListener)
             type: INTERFACE call: io.netty.util.concurrent.ScheduledFuture.addListener(io.netty.util.concurrent.GenericFutureListener):io.netty.util.concurrent.Future)
             call: de.enzaxd.viaforge.util.FutureTaskId.<init>(java.util.concurrent.Future):void type: CONSTRUCTOR)
             in method: de.enzaxd.viaforge.platform.Platform.runRepeatingSync(java.lang.Runnable, long):com.viaversion.viaversion.api.platform.PlatformTask, file: Jackey Client b2.jar:de/enzaxd/viaforge/platform/Platform.class
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
            	at java.base/java.util.ArrayList.forEach(Unknown Source)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
            Caused by: java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
            	at java.base/java.util.Objects.checkIndex(Unknown Source)
            	at java.base/java.util.ArrayList.get(Unknown Source)
            	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
            	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:830)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:93)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:804)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:745)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:395)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:345)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
            	... 15 more
            */
        /*
            this = this;
            de.enzaxd.viaforge.util.FutureTaskId r0 = new de.enzaxd.viaforge.util.FutureTaskId
            r1 = r0
            de.enzaxd.viaforge.ViaForge r2 = de.enzaxd.viaforge.ViaForge.getInstance()
            io.netty.channel.EventLoop r2 = r2.getEventLoop()
            r3 = r11
            r4 = r12
            com.viaversion.viaversion.api.platform.PlatformTask r3 = () -> { // java.lang.Runnable.run():void
                r3.lambda$runRepeatingSync$2(r4);
            }
            r4 = 0
            r5 = r13
            r6 = 50
            long r5 = r5 * r6
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MILLISECONDS
            io.netty.util.concurrent.ScheduledFuture r2 = r2.scheduleAtFixedRate(r3, r4, r5, r6)
            r3 = r11
            io.netty.util.concurrent.GenericFutureListener r3 = r3.errorLogger()
            io.netty.util.concurrent.Future r2 = r2.addListener(r3)
            r1.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: de.enzaxd.viaforge.platform.Platform.runRepeatingSync(java.lang.Runnable, long):com.viaversion.viaversion.api.platform.PlatformTask");
    }

    private <T extends Future<?>> GenericFutureListener<T> errorLogger() {
        return future -> {
            if (!future.isCancelled() && future.cause() != null) {
                future.cause().printStackTrace();
            }
        };
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ViaCommandSender[] getOnlinePlayers() {
        return new ViaCommandSender[1337];
    }

    private ViaCommandSender[] getServerPlayers() {
        return new ViaCommandSender[1337];
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public void sendMessage(UUID uuid, String s) {
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean kickPlayer(UUID uuid, String s) {
        return false;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isPluginEnabled() {
        return true;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public com.viaversion.viaversion.api.ViaAPI<UUID> getApi() {
        return this.api;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ViaVersionConfig getConf() {
        return this.config;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ConfigurationProvider getConfigurationProvider() {
        return this.config;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public File getDataFolder() {
        return this.dataFolder;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public void onReload() {
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public JsonObject getDump() {
        JsonObject platformSpecific = new JsonObject();
        return platformSpecific;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isOldClientsAllowed() {
        return true;
    }
}
