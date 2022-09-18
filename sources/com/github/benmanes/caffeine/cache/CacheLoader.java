package com.github.benmanes.caffeine.cache;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/CacheLoader.class */
public interface CacheLoader<K, V> extends AsyncCacheLoader<K, V> {
    V load(K k) throws Exception;

    default Map<K, V> loadAll(Iterable<? extends K> keys) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncCacheLoader
    default CompletableFuture<V> asyncLoad(K key, Executor executor) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(executor);
        return CompletableFuture.supplyAsync(()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0015: RETURN  
              (wrap: java.util.concurrent.CompletableFuture<V> : 0x0012: INVOKE  (r0v6 java.util.concurrent.CompletableFuture<V> A[REMOVE]) = 
              (wrap: java.util.function.Supplier : 0x000c: INVOKE_CUSTOM (r0v5 java.util.function.Supplier A[REMOVE]) = 
              (r3v0 'this' com.github.benmanes.caffeine.cache.CacheLoader<K, V> A[D('this' com.github.benmanes.caffeine.cache.CacheLoader<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r4v0 'key' K A[D('key' K), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.util.function.Supplier.get():java.lang.Object
             call insn: ?: INVOKE  (r0 I:com.github.benmanes.caffeine.cache.CacheLoader), (r1 I:java.lang.Object) type: DIRECT call: com.github.benmanes.caffeine.cache.CacheLoader.lambda$asyncLoad$0(java.lang.Object):java.lang.Object)
              (r5v0 'executor' java.util.concurrent.Executor A[D('executor' java.util.concurrent.Executor)])
             type: STATIC call: java.util.concurrent.CompletableFuture.supplyAsync(java.util.function.Supplier, java.util.concurrent.Executor):java.util.concurrent.CompletableFuture)
             in method: com.github.benmanes.caffeine.cache.CacheLoader.asyncLoad(K, java.util.concurrent.Executor):java.util.concurrent.CompletableFuture<V>, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/CacheLoader.class
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
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:345)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
            	... 15 more
            */
        /*
            this = this;
            r0 = r4
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r5
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r3
            r1 = r4
            java.util.concurrent.CompletableFuture<V> r0 = () -> { // java.util.function.Supplier.get():java.lang.Object
                return r0.lambda$asyncLoad$0(r1);
            }
            r1 = r5
            java.util.concurrent.CompletableFuture r0 = java.util.concurrent.CompletableFuture.supplyAsync(r0, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.CacheLoader.asyncLoad(java.lang.Object, java.util.concurrent.Executor):java.util.concurrent.CompletableFuture");
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncCacheLoader
    default CompletableFuture<Map<K, V>> asyncLoadAll(Iterable<? extends K> keys, Executor executor) {
        Objects.requireNonNull(keys);
        Objects.requireNonNull(executor);
        return CompletableFuture.supplyAsync(()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0015: RETURN  
              (wrap: java.util.concurrent.CompletableFuture<java.util.Map<K, V>> : 0x0012: INVOKE  (r0v6 java.util.concurrent.CompletableFuture<java.util.Map<K, V>> A[REMOVE]) = 
              (wrap: java.util.function.Supplier : 0x000c: INVOKE_CUSTOM (r0v5 java.util.function.Supplier A[REMOVE]) = 
              (r3v0 'this' com.github.benmanes.caffeine.cache.CacheLoader<K, V> A[D('this' com.github.benmanes.caffeine.cache.CacheLoader<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r4v0 'keys' java.lang.Iterable<? extends K> A[D('keys' java.lang.Iterable<? extends K>), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.util.function.Supplier.get():java.lang.Object
             call insn: ?: INVOKE  (r0 I:com.github.benmanes.caffeine.cache.CacheLoader), (r1 I:java.lang.Iterable) type: DIRECT call: com.github.benmanes.caffeine.cache.CacheLoader.lambda$asyncLoadAll$1(java.lang.Iterable):java.util.Map)
              (r5v0 'executor' java.util.concurrent.Executor A[D('executor' java.util.concurrent.Executor)])
             type: STATIC call: java.util.concurrent.CompletableFuture.supplyAsync(java.util.function.Supplier, java.util.concurrent.Executor):java.util.concurrent.CompletableFuture)
             in method: com.github.benmanes.caffeine.cache.CacheLoader.asyncLoadAll(java.lang.Iterable<? extends K>, java.util.concurrent.Executor):java.util.concurrent.CompletableFuture<java.util.Map<K, V>>, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/CacheLoader.class
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
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:345)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
            	... 15 more
            */
        /*
            this = this;
            r0 = r4
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r5
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r3
            r1 = r4
            java.util.concurrent.CompletableFuture<java.util.Map<K, V>> r0 = () -> { // java.util.function.Supplier.get():java.lang.Object
                return r0.lambda$asyncLoadAll$1(r1);
            }
            r1 = r5
            java.util.concurrent.CompletableFuture r0 = java.util.concurrent.CompletableFuture.supplyAsync(r0, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.CacheLoader.asyncLoadAll(java.lang.Iterable, java.util.concurrent.Executor):java.util.concurrent.CompletableFuture");
    }

    default V reload(K key, V oldValue) throws Exception {
        return load(key);
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncCacheLoader
    default CompletableFuture<V> asyncReload(K key, V oldValue, Executor executor) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(executor);
        return CompletableFuture.supplyAsync(()
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0016: RETURN  
              (wrap: java.util.concurrent.CompletableFuture<V> : 0x0013: INVOKE  (r0v6 java.util.concurrent.CompletableFuture<V> A[REMOVE]) = 
              (wrap: java.util.function.Supplier : 0x000d: INVOKE_CUSTOM (r0v5 java.util.function.Supplier A[REMOVE]) = 
              (r4v0 'this' com.github.benmanes.caffeine.cache.CacheLoader<K, V> A[D('this' com.github.benmanes.caffeine.cache.CacheLoader<K, V>), DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r5v0 'key' K A[D('key' K), DONT_INLINE])
              (r6v0 'oldValue' V A[D('oldValue' V), DONT_INLINE])
            
             handle type: INVOKE_DIRECT
             lambda: java.util.function.Supplier.get():java.lang.Object
             call insn: ?: INVOKE  (r0 I:com.github.benmanes.caffeine.cache.CacheLoader), (r1 I:java.lang.Object), (r2 I:java.lang.Object) type: DIRECT call: com.github.benmanes.caffeine.cache.CacheLoader.lambda$asyncReload$2(java.lang.Object, java.lang.Object):java.lang.Object)
              (r7v0 'executor' java.util.concurrent.Executor A[D('executor' java.util.concurrent.Executor)])
             type: STATIC call: java.util.concurrent.CompletableFuture.supplyAsync(java.util.function.Supplier, java.util.concurrent.Executor):java.util.concurrent.CompletableFuture)
             in method: com.github.benmanes.caffeine.cache.CacheLoader.asyncReload(K, V, java.util.concurrent.Executor):java.util.concurrent.CompletableFuture<V>, file: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/CacheLoader.class
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
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:830)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:345)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
            	... 15 more
            */
        /*
            this = this;
            r0 = r5
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r7
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r4
            r1 = r5
            r2 = r6
            java.util.concurrent.CompletableFuture<V> r0 = () -> { // java.util.function.Supplier.get():java.lang.Object
                return r0.lambda$asyncReload$2(r1, r2);
            }
            r1 = r7
            java.util.concurrent.CompletableFuture r0 = java.util.concurrent.CompletableFuture.supplyAsync(r0, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.CacheLoader.asyncReload(java.lang.Object, java.lang.Object, java.util.concurrent.Executor):java.util.concurrent.CompletableFuture");
    }
}
