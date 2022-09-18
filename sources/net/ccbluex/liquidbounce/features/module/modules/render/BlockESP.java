package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "BlockESP", spacedName = "Block ESP", description = "Allows you to see a selected block through walls.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/BlockESP.class */
public class BlockESP extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "2D"}, "Box");
    private final BlockValue blockValue = new BlockValue("Block", 168);
    private final IntegerValue radiusValue = new IntegerValue("Radius", 40, 5, 120);
    private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 179, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 72, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    private final MSTimer searchTimer = new MSTimer();
    private final List<BlockPos> posList = new ArrayList();
    private Thread thread;

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (this.searchTimer.hasTimePassed(1000L)) {
            if (this.thread == null || !this.thread.isAlive()) {
                int radius = this.radiusValue.get().intValue();
                Block selectedBlock = Block.func_149729_e(this.blockValue.get().intValue());
                if (selectedBlock == null || selectedBlock == Blocks.field_150350_a) {
                    return;
                }
                this.thread = new Thread(()
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x005b: IPUT  
                      (wrap: java.lang.Thread : 0x0058: CONSTRUCTOR  (r1v2 java.lang.Thread A[REMOVE]) = 
                      (wrap: java.lang.Runnable : 0x0051: INVOKE_CUSTOM (r3v1 java.lang.Runnable A[REMOVE]) = 
                      (r7v0 'this' net.ccbluex.liquidbounce.features.module.modules.render.BlockESP A[D('this' net.ccbluex.liquidbounce.features.module.modules.render.BlockESP), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                      (r0v9 'radius' int A[D('radius' int), DONT_INLINE])
                      (r0v15 'selectedBlock' net.minecraft.block.Block A[D('selectedBlock' net.minecraft.block.Block), DONT_INLINE])
                    
                     handle type: INVOKE_DIRECT
                     lambda: java.lang.Runnable.run():void
                     call insn: ?: INVOKE  (r3 I:net.ccbluex.liquidbounce.features.module.modules.render.BlockESP), (r4 I:int), (r5 I:net.minecraft.block.Block) type: DIRECT call: net.ccbluex.liquidbounce.features.module.modules.render.BlockESP.lambda$onUpdate$0(int, net.minecraft.block.Block):void)
                      ("BlockESP-BlockFinder")
                     call: java.lang.Thread.<init>(java.lang.Runnable, java.lang.String):void type: CONSTRUCTOR)
                      (r7v0 'this' net.ccbluex.liquidbounce.features.module.modules.render.BlockESP A[D('this' net.ccbluex.liquidbounce.features.module.modules.render.BlockESP), IMMUTABLE_TYPE, THIS])
                     net.ccbluex.liquidbounce.features.module.modules.render.BlockESP.thread java.lang.Thread in method: net.ccbluex.liquidbounce.features.module.modules.render.BlockESP.onUpdate(net.ccbluex.liquidbounce.event.UpdateEvent):void, file: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/BlockESP.class
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:123)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:123)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
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
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:464)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                    	... 35 more
                    */
                /*
                    this = this;
                    r0 = r7
                    net.ccbluex.liquidbounce.utils.timer.MSTimer r0 = r0.searchTimer
                    r1 = 1000(0x3e8, double:4.94E-321)
                    boolean r0 = r0.hasTimePassed(r1)
                    if (r0 == 0) goto L65
                    r0 = r7
                    java.lang.Thread r0 = r0.thread
                    if (r0 == 0) goto L1e
                    r0 = r7
                    java.lang.Thread r0 = r0.thread
                    boolean r0 = r0.isAlive()
                    if (r0 != 0) goto L65
                L1e:
                    r0 = r7
                    net.ccbluex.liquidbounce.value.IntegerValue r0 = r0.radiusValue
                    java.lang.Object r0 = r0.get()
                    java.lang.Integer r0 = (java.lang.Integer) r0
                    int r0 = r0.intValue()
                    r9 = r0
                    r0 = r7
                    net.ccbluex.liquidbounce.value.BlockValue r0 = r0.blockValue
                    java.lang.Object r0 = r0.get()
                    java.lang.Integer r0 = (java.lang.Integer) r0
                    int r0 = r0.intValue()
                    net.minecraft.block.Block r0 = net.minecraft.block.Block.func_149729_e(r0)
                    r10 = r0
                    r0 = r10
                    if (r0 == 0) goto L48
                    r0 = r10
                    net.minecraft.block.Block r1 = net.minecraft.init.Blocks.field_150350_a
                    if (r0 != r1) goto L49
                L48:
                    return
                L49:
                    r0 = r7
                    java.lang.Thread r1 = new java.lang.Thread
                    r2 = r1
                    r3 = r7
                    r4 = r9
                    r5 = r10
                    void r3 = () -> { // java.lang.Runnable.run():void
                        r3.lambda$onUpdate$0(r4, r5);
                    }
                    java.lang.String r4 = "BlockESP-BlockFinder"
                    r2.<init>(r3, r4)
                    r0.thread = r1
                    r0 = r7
                    java.lang.Thread r0 = r0.thread
                    r0.start()
                L65:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.render.BlockESP.onUpdate(net.ccbluex.liquidbounce.event.UpdateEvent):void");
            }

            @EventTarget
            public void onRender3D(Render3DEvent event) {
                synchronized (this.posList) {
                    Color color = this.colorRainbow.get().booleanValue() ? ColorUtils.rainbow() : new Color(this.colorRedValue.get().intValue(), this.colorGreenValue.get().intValue(), this.colorBlueValue.get().intValue());
                    for (BlockPos blockPos : this.posList) {
                        String lowerCase = this.modeValue.get().toLowerCase();
                        boolean z = true;
                        switch (lowerCase.hashCode()) {
                            case 1650:
                                if (lowerCase.equals("2d")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case 97739:
                                if (lowerCase.equals("box")) {
                                    z = false;
                                    break;
                                }
                                break;
                        }
                        switch (z) {
                            case false:
                                RenderUtils.drawBlockBox(blockPos, color, true);
                                break;
                            case true:
                                RenderUtils.draw2D(blockPos, color.getRGB(), Color.BLACK.getRGB());
                                break;
                        }
                    }
                }
            }

            @Override // net.ccbluex.liquidbounce.features.module.Module
            public String getTag() {
                return BlockUtils.getBlockName(this.blockValue.get().intValue());
            }
        }
