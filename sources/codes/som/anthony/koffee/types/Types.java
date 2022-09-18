package codes.som.anthony.koffee.types;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMapping;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 2, m54d1 = {"��\u0016\n��\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010��\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0015\u001a\u00020\u00012\n\u0010\u0016\u001a\u00060\u0017j\u0002`\u0018\"\u0019\u0010��\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0019\u0010\u0005\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0019\u0010\u0007\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004\"\u0019\u0010\t\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0004\"\u0019\u0010\u000b\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0004\"\u0019\u0010\r\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0004\"\u0019\u0010\u000f\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0004\"\u0019\u0010\u0011\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0004\"\u0019\u0010\u0013\u001a\n \u0002*\u0004\u0018\u00010\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0004*\n\u0010\u0019\"\u00020\u00172\u00020\u0017¨\u0006\u001a"}, m53d2 = {"boolean", "Lorg/objectweb/asm/Type;", "kotlin.jvm.PlatformType", "getBoolean", "()Lorg/objectweb/asm/Type;", "byte", "getByte", "char", "getChar", "double", "getDouble", "float", "getFloat", "int", "getInt", "long", "getLong", "short", "getShort", "void", "getVoid", "coerceType", "value", "", "Lcodes/som/anthony/koffee/types/TypeLike;", "TypeLike", "koffee"})
/* renamed from: codes.som.anthony.koffee.types.TypesKt */
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/types/TypesKt.class */
public final class Types {
    public static final Type getVoid() {
        return Type.VOID_TYPE;
    }

    public static final Type getChar() {
        return Type.CHAR_TYPE;
    }

    public static final Type getByte() {
        return Type.BYTE_TYPE;
    }

    public static final Type getShort() {
        return Type.SHORT_TYPE;
    }

    public static final Type getInt() {
        return Type.INT_TYPE;
    }

    public static final Type getFloat() {
        return Type.FLOAT_TYPE;
    }

    public static final Type getLong() {
        return Type.LONG_TYPE;
    }

    public static final Type getDouble() {
        return Type.DOUBLE_TYPE;
    }

    public static final Type getBoolean() {
        return Type.BOOLEAN_TYPE;
    }

    @NotNull
    public static final Type coerceType(@NotNull Object value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        if (value instanceof Type) {
            return (Type) value;
        }
        if (value instanceof KClass) {
            Type type = Type.getType(JvmClassMapping.getJavaClass((KClass) value));
            Intrinsics.checkExpressionValueIsNotNull(type, "Type.getType(value.java)");
            return type;
        } else if (value instanceof Class) {
            Type type2 = Type.getType((Class) value);
            Intrinsics.checkExpressionValueIsNotNull(type2, "Type.getType(value)");
            return type2;
        } else if (value instanceof String) {
            Type objectType = Type.getObjectType((String) value);
            Intrinsics.checkExpressionValueIsNotNull(objectType, "Type.getObjectType(value)");
            return objectType;
        } else if (value instanceof ClassNode) {
            Type objectType2 = Type.getObjectType(((ClassNode) value).name);
            Intrinsics.checkExpressionValueIsNotNull(objectType2, "Type.getObjectType(value.name)");
            return objectType2;
        } else {
            throw new IllegalStateException("Non type-like object passed to coerceType()".toString());
        }
    }
}
