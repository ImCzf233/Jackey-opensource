package jdk.nashorn.internal.codegen;

import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.scripts.C1653JD;
import jdk.nashorn.internal.scripts.C1654JO;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/SpillObjectCreator.class */
public final class SpillObjectCreator extends ObjectCreator<Expression> {
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !SpillObjectCreator.class.desiredAssertionStatus();
    }

    public SpillObjectCreator(CodeGenerator codegen, List<MapTuple<Expression>> tuples) {
        super(codegen, tuples, false, false);
        makeMap();
    }

    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    public void createObject(MethodEmitter method) {
        Object constantValue;
        if ($assertionsDisabled || !isScope()) {
            int length = this.tuples.size();
            boolean dualFields = this.codegen.useDualFields();
            int spillLength = ScriptObject.spillAllocationLength(length);
            long[] jpresetValues = dualFields ? new long[spillLength] : null;
            Object[] opresetValues = new Object[spillLength];
            Class<?> objectClass = getAllocatorClass();
            ArrayData arrayData = ArrayData.allocate(ScriptRuntime.EMPTY_ARRAY);
            int pos = 0;
            Iterator it = this.tuples.iterator();
            while (it.hasNext()) {
                MapTuple<Expression> tuple = (MapTuple) it.next();
                String key = tuple.key;
                Expression value = (Expression) tuple.value;
                method.invalidateSpecialName(tuple.key);
                if (value != null && (constantValue = LiteralNode.objectAsConstant(value)) != LiteralNode.POSTSET_MARKER) {
                    Property property = this.propertyMap.findProperty(key);
                    if (property != null) {
                        property.setType(dualFields ? JSType.unboxedFieldType(constantValue) : Object.class);
                        int slot = property.getSlot();
                        if (dualFields && (constantValue instanceof Number)) {
                            jpresetValues[slot] = ObjectClassGenerator.pack((Number) constantValue);
                        } else {
                            opresetValues[slot] = constantValue;
                        }
                    } else {
                        long oldLength = arrayData.length();
                        int index = ArrayIndex.getArrayIndex(key);
                        long longIndex = ArrayIndex.toLongIndex(index);
                        if (!$assertionsDisabled && !ArrayIndex.isValidArrayIndex(index)) {
                            throw new AssertionError();
                        }
                        if (longIndex >= oldLength) {
                            arrayData = arrayData.ensure(longIndex);
                        }
                        if (constantValue instanceof Integer) {
                            arrayData = arrayData.set(index, ((Integer) constantValue).intValue(), false);
                        } else if (constantValue instanceof Double) {
                            arrayData = arrayData.set(index, ((Double) constantValue).doubleValue(), false);
                        } else {
                            arrayData = arrayData.set(index, constantValue, false);
                        }
                        if (longIndex > oldLength) {
                            arrayData = arrayData.delete(oldLength, longIndex - 1);
                        }
                    }
                }
                pos++;
            }
            method._new(objectClass).dup();
            this.codegen.loadConstant(this.propertyMap);
            if (dualFields) {
                this.codegen.loadConstant(jpresetValues);
            } else {
                method.loadNull();
            }
            this.codegen.loadConstant(opresetValues);
            method.invoke(CompilerConstants.constructorNoLookup(objectClass, PropertyMap.class, long[].class, Object[].class));
            if (arrayData.length() > 0) {
                method.dup();
                this.codegen.loadConstant(arrayData);
                method.invoke(CompilerConstants.virtualCallNoLookup(ScriptObject.class, "setArray", Void.TYPE, ArrayData.class));
                return;
            }
            return;
        }
        throw new AssertionError("spill scope objects are not currently supported");
    }

    @Override // jdk.nashorn.internal.codegen.CodeGenerator.SplitLiteralCreator
    public void populateRange(MethodEmitter method, Type objectType, int objectSlot, int start, int end) {
        int callSiteFlags = this.codegen.getCallSiteFlags();
        method.load(objectType, objectSlot);
        for (int i = start; i < end; i++) {
            MapTuple<Expression> tuple = (MapTuple) this.tuples.get(i);
            if (!LiteralNode.isConstant(tuple.value)) {
                Property property = this.propertyMap.findProperty(tuple.key);
                if (property == null) {
                    int index = ArrayIndex.getArrayIndex(tuple.key);
                    if (!$assertionsDisabled && !ArrayIndex.isValidArrayIndex(index)) {
                        throw new AssertionError();
                    }
                    method.dup();
                    loadIndex(method, ArrayIndex.toLongIndex(index));
                    loadTuple(method, tuple, false);
                    method.dynamicSetIndex(callSiteFlags);
                } else {
                    method.dup();
                    loadTuple(method, tuple, false);
                    method.dynamicSet(property.getKey(), this.codegen.getCallSiteFlags(), false);
                }
            }
        }
    }

    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    protected PropertyMap makeMap() {
        if ($assertionsDisabled || this.propertyMap == null) {
            Class<? extends ScriptObject> clazz = getAllocatorClass();
            this.propertyMap = new MapCreator(clazz, this.tuples).makeSpillMap(false, this.codegen.useDualFields());
            return this.propertyMap;
        }
        throw new AssertionError("property map already initialized");
    }

    public void loadValue(Expression expr, Type type) {
        this.codegen.loadExpressionAsType(expr, Type.generic(type));
    }

    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    public Class<? extends ScriptObject> getAllocatorClass() {
        return this.codegen.useDualFields() ? C1653JD.class : C1654JO.class;
    }
}
