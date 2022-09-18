package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/remapper/PacketRemapper.class */
public abstract class PacketRemapper {
    private final List<PacketHandler> valueRemappers = new ArrayList();

    public abstract void registerMap();

    public PacketRemapper() {
        registerMap();
    }

    public void map(Type type) {
        handler(wrapper -> {
            wrapper.write(type, wrapper.read(type));
        });
    }

    public void map(Type oldType, Type newType) {
        handler(wrapper -> {
            wrapper.write(newType, wrapper.read(oldType));
        });
    }

    public <T1, T2> void map(Type<T1> oldType, Type<T2> newType, final Function<T1, T2> transformer) {
        map(oldType, new ValueTransformer<T1, T2>(newType) { // from class: com.viaversion.viaversion.api.protocol.remapper.PacketRemapper.1
            /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Object, T2] */
            @Override // com.viaversion.viaversion.api.protocol.remapper.ValueTransformer
            public T2 transform(PacketWrapper wrapper, T1 inputValue) throws Exception {
                return transformer.apply(inputValue);
            }
        });
    }

    public <T1, T2> void map(ValueTransformer<T1, T2> transformer) {
        if (transformer.getInputType() == null) {
            throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
        }
        map(transformer.getInputType(), transformer);
    }

    public <T1, T2> void map(Type<T1> oldType, ValueTransformer<T1, T2> transformer) {
        map(new TypeRemapper(oldType), transformer);
    }

    public <T> void map(ValueReader<T> inputReader, ValueWriter<T> outputWriter) {
        handler(wrapper -> {
            outputWriter.write(wrapper, inputReader.read(wrapper));
        });
    }

    public void handler(PacketHandler handler) {
        this.valueRemappers.add(handler);
    }

    public <T> void create(Type<T> type, T value) {
        handler(wrapper -> {
            wrapper.write(type, value);
        });
    }

    public void read(Type type) {
        handler(wrapper -> {
            wrapper.read(type);
        });
    }

    public void remap(PacketWrapper packetWrapper) throws Exception {
        try {
            for (PacketHandler handler : this.valueRemappers) {
                handler.handle(packetWrapper);
            }
        } catch (CancelException e) {
            throw e;
        } catch (InformativeException e2) {
            e2.addSource(getClass());
            throw e2;
        } catch (Exception e3) {
            InformativeException ex = new InformativeException(e3);
            ex.addSource(getClass());
            throw ex;
        }
    }
}
