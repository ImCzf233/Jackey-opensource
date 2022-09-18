package jdk.nashorn.internal.codegen;

import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/FieldObjectCreator.class */
public abstract class FieldObjectCreator<T> extends ObjectCreator<T> {
    private String fieldObjectClassName;
    private Class<? extends ScriptObject> fieldObjectClass;
    private int fieldCount;
    private int paddedFieldCount;
    private int paramCount;
    private final int callSiteFlags;
    private final boolean evalCode;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !FieldObjectCreator.class.desiredAssertionStatus();
    }

    public FieldObjectCreator(CodeGenerator codegen, List<MapTuple<T>> tuples) {
        this(codegen, tuples, false, false);
    }

    public FieldObjectCreator(CodeGenerator codegen, List<MapTuple<T>> tuples, boolean isScope, boolean hasArguments) {
        super(codegen, tuples, isScope, hasArguments);
        this.callSiteFlags = codegen.getCallSiteFlags();
        this.evalCode = codegen.isEvalCode();
        countFields();
        findClass();
    }

    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    public void createObject(MethodEmitter method) {
        makeMap();
        String className = getClassName();
        if ($assertionsDisabled || this.fieldObjectClass != null) {
            method._new(this.fieldObjectClass).dup();
            loadMap(method);
            if (isScope()) {
                loadScope(method);
                if (hasArguments()) {
                    method.loadCompilerConstant(CompilerConstants.ARGUMENTS);
                    method.invoke(CompilerConstants.constructorNoLookup(className, PropertyMap.class, ScriptObject.class, CompilerConstants.ARGUMENTS.type()));
                    return;
                }
                method.invoke(CompilerConstants.constructorNoLookup(className, PropertyMap.class, ScriptObject.class));
                return;
            }
            method.invoke(CompilerConstants.constructorNoLookup(className, PropertyMap.class));
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.CodeGenerator.SplitLiteralCreator
    public void populateRange(MethodEmitter method, Type objectType, int objectSlot, int start, int end) {
        method.load(objectType, objectSlot);
        for (int i = start; i < end; i++) {
            MapTuple<T> tuple = this.tuples.get(i);
            if (tuple.symbol != null && tuple.value != null) {
                int index = ArrayIndex.getArrayIndex(tuple.key);
                method.dup();
                if (!ArrayIndex.isValidArrayIndex(index)) {
                    putField(method, tuple.key, tuple.symbol.getFieldIndex(), tuple);
                } else {
                    putSlot(method, ArrayIndex.toLongIndex(index), tuple);
                }
                method.invalidateSpecialName(tuple.key);
            }
        }
    }

    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    protected PropertyMap makeMap() {
        if ($assertionsDisabled || this.propertyMap == null) {
            this.propertyMap = newMapCreator(this.fieldObjectClass).makeFieldMap(hasArguments(), this.codegen.useDualFields(), this.fieldCount, this.paddedFieldCount, this.evalCode);
            return this.propertyMap;
        }
        throw new AssertionError("property map already initialized");
    }

    private void putField(MethodEmitter method, String key, int fieldIndex, MapTuple<T> tuple) {
        Type fieldType = (!this.codegen.useDualFields() || !tuple.isPrimitive()) ? Type.OBJECT : ObjectClassGenerator.PRIMITIVE_FIELD_TYPE;
        String fieldClass = getClassName();
        String fieldName = ObjectClassGenerator.getFieldName(fieldIndex, fieldType);
        String fieldDesc = CompilerConstants.typeDescriptor(fieldType.getTypeClass());
        if ($assertionsDisabled || fieldName.equals(ObjectClassGenerator.getFieldName(fieldIndex, ObjectClassGenerator.PRIMITIVE_FIELD_TYPE)) || fieldType.isObject()) {
            if (!$assertionsDisabled && !fieldName.equals(ObjectClassGenerator.getFieldName(fieldIndex, Type.OBJECT)) && !fieldType.isPrimitive()) {
                throw new AssertionError(key + " primitive keys must store to J*-fields");
            }
            loadTuple(method, tuple, true);
            method.putField(fieldClass, fieldName, fieldDesc);
            return;
        }
        throw new AssertionError(key + " object keys must store to L*-fields");
    }

    private void putSlot(MethodEmitter method, long index, MapTuple<T> tuple) {
        loadIndex(method, index);
        loadTuple(method, tuple, false);
        method.dynamicSetIndex(this.callSiteFlags);
    }

    private void findClass() {
        String str;
        if (isScope()) {
            str = ObjectClassGenerator.getClassName(this.fieldCount, this.paramCount, this.codegen.useDualFields());
        } else {
            str = ObjectClassGenerator.getClassName(this.paddedFieldCount, this.codegen.useDualFields());
        }
        this.fieldObjectClassName = str;
        try {
            this.fieldObjectClass = Context.forStructureClass(Compiler.binaryName(this.fieldObjectClassName));
        } catch (ClassNotFoundException e) {
            throw new AssertionError("Nashorn has encountered an internal error.  Structure can not be created.");
        }
    }

    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    public Class<? extends ScriptObject> getAllocatorClass() {
        return this.fieldObjectClass;
    }

    String getClassName() {
        return this.fieldObjectClassName;
    }

    private void countFields() {
        for (MapTuple<T> tuple : this.tuples) {
            Symbol symbol = tuple.symbol;
            if (symbol != null) {
                if (hasArguments() && symbol.isParam()) {
                    int i = this.paramCount;
                    this.paramCount = i + 1;
                    symbol.setFieldIndex(i);
                } else if (!ArrayIndex.isValidArrayIndex(ArrayIndex.getArrayIndex(tuple.key))) {
                    int i2 = this.fieldCount;
                    this.fieldCount = i2 + 1;
                    symbol.setFieldIndex(i2);
                }
            }
        }
        this.paddedFieldCount = ObjectClassGenerator.getPaddedFieldCount(this.fieldCount);
    }
}
