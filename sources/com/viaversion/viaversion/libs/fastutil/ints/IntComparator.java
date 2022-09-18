package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Comparator;

/*  JADX ERROR: NullPointerException in pass: ClassModifier
    java.lang.NullPointerException: Cannot invoke "java.util.List.forEach(java.util.function.Consumer)" because "blocks" is null
    	at jadx.core.utils.BlockUtils.collectAllInsns(BlockUtils.java:987)
    	at jadx.core.dex.visitors.ClassModifier.removeBridgeMethod(ClassModifier.java:229)
    	at jadx.core.dex.visitors.ClassModifier.removeSyntheticMethods(ClassModifier.java:151)
    	at java.base/java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.ClassModifier.visit(ClassModifier.java:61)
    */
/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.nodes.MethodNode.getBasicBlocks()" is null
    	at jadx.core.dex.visitors.kotlin.ProcessKotlinInternals.processMth(ProcessKotlinInternals.java:92)
    	at jadx.core.dex.visitors.kotlin.ProcessKotlinInternals.visit(ProcessKotlinInternals.java:83)
    */
@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntComparator.class */
public interface IntComparator extends Comparator<Integer> {
    int compare(int i, int i2);

    /*  JADX ERROR: Failed to decode insn: 0x0002: INVOKE_CUSTOM r0, r1, method: com.viaversion.viaversion.libs.fastutil.ints.IntComparator.thenComparing(com.viaversion.viaversion.libs.fastutil.ints.IntComparator):com.viaversion.viaversion.libs.fastutil.ints.IntComparator
        jadx.core.utils.exceptions.JadxRuntimeException: 'invoke-custom' instruction processing error: Failed to process invoke-custom instruction: CallSite{[{ENCODED_METHOD_HANDLE: INVOKE_STATIC: Ljava/lang/invoke/LambdaMetafactory;->altMetafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;}, compare, {ENCODED_METHOD_TYPE: (Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;, Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;)Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_METHOD_HANDLE: INVOKE_DIRECT: Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;->lambda$thenComparing$931d6fed$1(Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;II)I}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_INT: 5}, {ENCODED_INT: 0}]}
        	at jadx.core.dex.instructions.InvokeCustomBuilder.build(InvokeCustomBuilder.java:34)
        	at jadx.core.dex.instructions.InsnDecoder.invokeCustom(InsnDecoder.java:581)
        	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:440)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:82)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:45)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:144)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
        	at jadx.core.ProcessClass.process(ProcessClass.java:67)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Failed to process invoke-custom instruction: CallSite{[{ENCODED_METHOD_HANDLE: INVOKE_STATIC: Ljava/lang/invoke/LambdaMetafactory;->altMetafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;}, compare, {ENCODED_METHOD_TYPE: (Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;, Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;)Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_METHOD_HANDLE: INVOKE_DIRECT: Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;->lambda$thenComparing$931d6fed$1(Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;II)I}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_INT: 5}, {ENCODED_INT: 0}]}
        	at jadx.core.dex.instructions.InvokeCustomBuilder.build(InvokeCustomBuilder.java:32)
        	... 11 more
        */
    default com.viaversion.viaversion.libs.fastutil.ints.IntComparator thenComparing(com.viaversion.viaversion.libs.fastutil.ints.IntComparator r4) {
        /*
            r3 = this;
            r0 = r3
            r1 = r4
            // decode failed: 'invoke-custom' instruction processing error: Failed to process invoke-custom instruction: CallSite{[{ENCODED_METHOD_HANDLE: INVOKE_STATIC: Ljava/lang/invoke/LambdaMetafactory;->altMetafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;}, compare, {ENCODED_METHOD_TYPE: (Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;, Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;)Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_METHOD_HANDLE: INVOKE_DIRECT: Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;->lambda$thenComparing$931d6fed$1(Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;II)I}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_INT: 5}, {ENCODED_INT: 0}]}
            java.io.Serializable r0 = (java.io.Serializable) r0
            com.viaversion.viaversion.libs.fastutil.ints.IntComparator r0 = (com.viaversion.viaversion.libs.fastutil.ints.IntComparator) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.fastutil.ints.IntComparator.thenComparing(com.viaversion.viaversion.libs.fastutil.ints.IntComparator):com.viaversion.viaversion.libs.fastutil.ints.IntComparator");
    }

    /*  JADX ERROR: Failed to decode insn: 0x0091: INVOKE_CUSTOM r0, r1, method: com.viaversion.viaversion.libs.fastutil.ints.IntComparator.$deserializeLambda$(java.lang.invoke.SerializedLambda):java.lang.Object
        jadx.core.utils.exceptions.JadxRuntimeException: 'invoke-custom' instruction processing error: Failed to process invoke-custom instruction: CallSite{[{ENCODED_METHOD_HANDLE: INVOKE_STATIC: Ljava/lang/invoke/LambdaMetafactory;->altMetafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;}, compare, {ENCODED_METHOD_TYPE: (Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;, Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;)Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_METHOD_HANDLE: INVOKE_DIRECT: Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;->lambda$thenComparing$931d6fed$1(Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;II)I}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_INT: 5}, {ENCODED_INT: 0}]}
        	at jadx.core.dex.instructions.InvokeCustomBuilder.build(InvokeCustomBuilder.java:34)
        	at jadx.core.dex.instructions.InsnDecoder.invokeCustom(InsnDecoder.java:581)
        	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:440)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:82)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:45)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:144)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:403)
        	at jadx.core.ProcessClass.process(ProcessClass.java:67)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:115)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:381)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:307)
        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Failed to process invoke-custom instruction: CallSite{[{ENCODED_METHOD_HANDLE: INVOKE_STATIC: Ljava/lang/invoke/LambdaMetafactory;->altMetafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;}, compare, {ENCODED_METHOD_TYPE: (Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;, Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;)Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_METHOD_HANDLE: INVOKE_DIRECT: Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;->lambda$thenComparing$931d6fed$1(Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;II)I}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_INT: 5}, {ENCODED_INT: 0}]}
        	at jadx.core.dex.instructions.InvokeCustomBuilder.build(InvokeCustomBuilder.java:32)
        	... 11 more
        */
    private static /* synthetic */ java.lang.Object $deserializeLambda$(java.lang.invoke.SerializedLambda r4) {
        /*
            r0 = r4
            java.lang.String r0 = r0.getImplMethodName()
            r5 = r0
            r0 = -1
            r6 = r0
            r0 = r5
            int r0 = r0.hashCode()
            switch(r0) {
                case -1554871547: goto L1c;
                default: goto L27;
            }
            r0 = r5
            java.lang.String r1 = "lambda$thenComparing$931d6fed$1"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L27
            r0 = 0
            r6 = r0
            r0 = r6
            switch(r0) {
                case 0: goto L3c;
                default: goto L97;
            }
            r0 = r4
            int r0 = r0.getImplMethodKind()
            r1 = 7
            if (r0 != r1) goto L97
            r0 = r4
            java.lang.String r0 = r0.getFunctionalInterfaceClass()
            java.lang.String r1 = "com/viaversion/viaversion/libs/fastutil/ints/IntComparator"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L97
            r0 = r4
            java.lang.String r0 = r0.getFunctionalInterfaceMethodName()
            java.lang.String r1 = "compare"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L97
            r0 = r4
            java.lang.String r0 = r0.getFunctionalInterfaceMethodSignature()
            java.lang.String r1 = "(II)I"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L97
            r0 = r4
            java.lang.String r0 = r0.getImplClass()
            java.lang.String r1 = "com/viaversion/viaversion/libs/fastutil/ints/IntComparator"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L97
            r0 = r4
            java.lang.String r0 = r0.getImplMethodSignature()
            java.lang.String r1 = "(Lit/unimi/dsi/fastutil/ints/IntComparator;II)I"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L97
            r0 = r4
            r1 = 0
            java.lang.Object r0 = r0.getCapturedArg(r1)
            com.viaversion.viaversion.libs.fastutil.ints.IntComparator r0 = (com.viaversion.viaversion.libs.fastutil.ints.IntComparator) r0
            r1 = r4
            r2 = 1
            java.lang.Object r1 = r1.getCapturedArg(r2)
            com.viaversion.viaversion.libs.fastutil.ints.IntComparator r1 = (com.viaversion.viaversion.libs.fastutil.ints.IntComparator) r1
            // decode failed: 'invoke-custom' instruction processing error: Failed to process invoke-custom instruction: CallSite{[{ENCODED_METHOD_HANDLE: INVOKE_STATIC: Ljava/lang/invoke/LambdaMetafactory;->altMetafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;}, compare, {ENCODED_METHOD_TYPE: (Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;, Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;)Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_METHOD_HANDLE: INVOKE_DIRECT: Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;->lambda$thenComparing$931d6fed$1(Lcom/viaversion/viaversion/libs/fastutil/ints/IntComparator;II)I}, {ENCODED_METHOD_TYPE: (I, I)I}, {ENCODED_INT: 5}, {ENCODED_INT: 0}]}
            return r0
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r1 = r0
            java.lang.String r2 = "Invalid lambda deserialization"
            r1.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.fastutil.ints.IntComparator.$deserializeLambda$(java.lang.invoke.SerializedLambda):java.lang.Object");
    }

    @Override // java.util.Comparator
    /* renamed from: reversed */
    default Comparator<Integer> reversed2() {
        return IntComparators.oppositeComparator(this);
    }

    @Deprecated
    default int compare(Integer ok1, Integer ok2) {
        return compare(ok1.intValue(), ok2.intValue());
    }

    private /* synthetic */ default int lambda$thenComparing$931d6fed$1(IntComparator second, int k1, int k2) {
        int comp = compare(k1, k2);
        return comp == 0 ? second.compare(k1, k2) : comp;
    }

    @Override // java.util.Comparator
    default Comparator<Integer> thenComparing(Comparator<? super Integer> second) {
        if (second instanceof IntComparator) {
            return thenComparing((IntComparator) second);
        }
        return super.thenComparing(second);
    }
}
