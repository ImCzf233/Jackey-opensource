package net.vitox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;

/* loaded from: Jackey Client b2.jar:net/vitox/ParticleGenerator.class */
public class ParticleGenerator {
    private final List<Particle> particles = new ArrayList();
    private final int amount;
    private int prevWidth;
    private int prevHeight;

    public ParticleGenerator(int amount) {
        this.amount = amount;
    }

    public void draw(int mouseX, int mouseY) {
        if (this.particles.isEmpty() || this.prevWidth != Minecraft.func_71410_x().field_71443_c || this.prevHeight != Minecraft.func_71410_x().field_71440_d) {
            this.particles.clear();
            create();
        }
        this.prevWidth = Minecraft.func_71410_x().field_71443_c;
        this.prevHeight = Minecraft.func_71410_x().field_71440_d;
        for (Particle particle : this.particles) {
            particle.fall();
            particle.interpolation();
            boolean mouseOver = ((float) mouseX) >= particle.f380x - ((float) 50) && ((float) mouseY) >= particle.f381y - ((float) 50) && ((float) mouseX) <= particle.f380x + ((float) 50) && ((float) mouseY) <= particle.f381y + ((float) 50);
            if (mouseOver) {
                this.particles.stream().filter(part
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x00d9: INVOKE  
                      (wrap: java.util.stream.Stream<net.vitox.Particle> : 0x00cd: INVOKE  (r0v26 java.util.stream.Stream<net.vitox.Particle> A[REMOVE]) = 
                      (wrap: java.util.stream.Stream<net.vitox.Particle> : 0x00bf: INVOKE  (r0v25 java.util.stream.Stream<net.vitox.Particle> A[REMOVE]) = 
                      (wrap: java.util.List<net.vitox.Particle> : 0x00bc: IGET  (r0v24 java.util.List<net.vitox.Particle> A[REMOVE]) = (r5v0 'this' net.vitox.ParticleGenerator A[D('this' net.vitox.ParticleGenerator), IMMUTABLE_TYPE, THIS]) net.vitox.ParticleGenerator.particles java.util.List)
                     type: INTERFACE call: java.util.List.stream():java.util.stream.Stream)
                      (wrap: java.util.function.Predicate<? super net.vitox.Particle> : 0x00c8: INVOKE_CUSTOM (r1v10 java.util.function.Predicate<? super net.vitox.Particle> A[REMOVE]) = (r0v12 'particle' net.vitox.Particle A[D('particle' net.vitox.Particle), DONT_INLINE]), (50 int)
                     handle type: INVOKE_STATIC
                     lambda: java.util.function.Predicate.test(java.lang.Object):boolean
                     call insn: ?: INVOKE  (r1 I:net.vitox.Particle), (r2 I:int), (v2 net.vitox.Particle) type: STATIC call: net.vitox.ParticleGenerator.lambda$draw$0(net.vitox.Particle, int, net.vitox.Particle):boolean)
                     type: INTERFACE call: java.util.stream.Stream.filter(java.util.function.Predicate):java.util.stream.Stream)
                      (wrap: java.util.function.Consumer<? super net.vitox.Particle> : 0x00d4: INVOKE_CUSTOM (r1v12 java.util.function.Consumer<? super net.vitox.Particle> A[REMOVE]) = (r0v12 'particle' net.vitox.Particle A[D('particle' net.vitox.Particle), DONT_INLINE])
                     handle type: INVOKE_STATIC
                     lambda: java.util.function.Consumer.accept(java.lang.Object):void
                     call insn: ?: INVOKE  (r1 I:net.vitox.Particle), (v1 net.vitox.Particle) type: STATIC call: net.vitox.ParticleGenerator.lambda$draw$1(net.vitox.Particle, net.vitox.Particle):void)
                     type: INTERFACE call: java.util.stream.Stream.forEach(java.util.function.Consumer):void in method: net.vitox.ParticleGenerator.draw(int, int):void, file: Jackey Client b2.jar:net/vitox/ParticleGenerator.class
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:123)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                    	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:207)
                    	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:167)
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
                    Caused by: java.lang.ClassCastException: class jadx.core.dex.instructions.args.LiteralArg cannot be cast to class jadx.core.dex.instructions.args.RegisterArg (jadx.core.dex.instructions.args.LiteralArg and jadx.core.dex.instructions.args.RegisterArg are in unnamed module of loader 'app')
                    	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:951)
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
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                    	... 27 more
                    */
                /*
                    Method dump skipped, instructions count: 245
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: net.vitox.ParticleGenerator.draw(int, int):void");
            }

            private void create() {
                Random random = new Random();
                for (int i = 0; i < this.amount; i++) {
                    this.particles.add(new Particle(random.nextInt(Minecraft.func_71410_x().field_71443_c), random.nextInt(Minecraft.func_71410_x().field_71440_d)));
                }
            }
        }
