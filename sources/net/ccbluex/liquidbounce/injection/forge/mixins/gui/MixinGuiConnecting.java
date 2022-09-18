package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.concurrent.atomic.AtomicInteger;
import jdk.internal.dynalink.CallSiteDescriptor;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.NetworkManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiConnecting.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiConnecting.class */
public abstract class MixinGuiConnecting extends GuiScreen {
    @Shadow
    private NetworkManager field_146371_g;
    @Shadow
    @Final
    private static Logger field_146370_f;
    @Shadow
    private boolean field_146373_h;
    @Shadow
    @Final
    private GuiScreen field_146374_i;
    @Shadow
    @Final
    private static AtomicInteger field_146372_a;

    @Inject(method = {"connect"}, m23at = {@AbstractC1790At("HEAD")})
    private void headConnect(String ip, int port, CallbackInfo callbackInfo) {
        ServerUtils.serverData = new ServerData("", ip + CallSiteDescriptor.TOKEN_DELIMITER + port, false);
    }

    @Overwrite
    private void func_146367_a(String ip, int port) {
        field_146370_f.info("Connecting to " + ip + ", " + port);
        new Thread(()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x004b: INVOKE  
              (wrap: java.lang.Thread : 0x0048: CONSTRUCTOR  (r0v1 java.lang.Thread A[REMOVE]) = 
              (wrap: java.lang.Runnable : 0x002b: INVOKE_CUSTOM (r2v6 java.lang.Runnable A[REMOVE]) = 
              (r6v0 'this' net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiConnecting A[D('this' net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiConnecting), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r7v0 'ip' java.lang.String A[D('ip' java.lang.String), DONT_INLINE])
              (r8v0 'port' int A[D('port' int), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.lang.Runnable.run():void
             call insn: ?: INVOKE  (r2 I:net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiConnecting), (r3 I:java.lang.String), (r4 I:int) type: DIRECT call: net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiConnecting.lambda$connect$0(java.lang.String, int):void)
              (wrap: java.lang.String : ?: STR_CONCAT  
              ("Server Connector #")
              (wrap: int : 0x003f: INVOKE  
              (wrap: java.util.concurrent.atomic.AtomicInteger : 0x003c: SGET   net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiConnecting.field_146372_a java.util.concurrent.atomic.AtomicInteger)
             type: VIRTUAL call: java.util.concurrent.atomic.AtomicInteger.incrementAndGet():int)
            )
             call: java.lang.Thread.<init>(java.lang.Runnable, java.lang.String):void type: CONSTRUCTOR)
             type: VIRTUAL call: java.lang.Thread.start():void in method: net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiConnecting.func_146367_a(java.lang.String, int):void, file: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiConnecting.class
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
            Caused by: java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 2
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
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:745)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:395)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:93)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:804)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
            	... 15 more
            */
        /*
            this = this;
            org.apache.logging.log4j.Logger r0 = net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiConnecting.field_146370_f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r2 = r1
            r2.<init>()
            java.lang.String r2 = "Connecting to "
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r7
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ", "
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r8
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.info(r1)
            java.lang.Thread r0 = new java.lang.Thread
            r1 = r0
            r2 = r6
            r3 = r7
            r4 = r8
            void r2 = () -> { // java.lang.Runnable.run():void
                r2.lambda$connect$0(r3, r4);
            }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r4 = r3
            r4.<init>()
            java.lang.String r4 = "Server Connector #"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.util.concurrent.atomic.AtomicInteger r4 = net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiConnecting.field_146372_a
            int r4 = r4.incrementAndGet()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r2, r3)
            r0.start()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiConnecting.func_146367_a(java.lang.String, int):void");
    }

    @Overwrite
    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
        func_146276_q_();
        String ip = "Unknown";
        ServerData serverData = this.field_146297_k.func_147104_D();
        if (serverData != null) {
            ip = serverData.field_78845_b;
        }
        Fonts.font40.drawCenteredString("Connecting to", scaledResolution.func_78326_a() / 2, (scaledResolution.func_78328_b() / 4) + 110, 16777215, true);
        Fonts.font35.drawCenteredString(ip, scaledResolution.func_78326_a() / 2, (scaledResolution.func_78328_b() / 4) + 120, 5407227, true);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
}
