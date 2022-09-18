package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.InformativeException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/remapper/ValueTransformer.class */
public abstract class ValueTransformer<T1, T2> implements ValueWriter<T1> {
    private final Type<T1> inputType;
    private final Type<T2> outputType;

    public abstract T2 transform(PacketWrapper packetWrapper, T1 t1) throws Exception;

    public ValueTransformer(Type<T1> inputType, Type<T2> outputType) {
        this.inputType = inputType;
        this.outputType = outputType;
    }

    public ValueTransformer(Type<T2> outputType) {
        this(null, outputType);
    }

    @Override // com.viaversion.viaversion.api.protocol.remapper.ValueWriter
    public void write(PacketWrapper writer, T1 inputValue) throws Exception {
        try {
            writer.write(this.outputType, transform(writer, inputValue));
        } catch (InformativeException e) {
            e.addSource(getClass());
            throw e;
        }
    }

    public Type<T1> getInputType() {
        return this.inputType;
    }

    public Type<T2> getOutputType() {
        return this.outputType;
    }
}
