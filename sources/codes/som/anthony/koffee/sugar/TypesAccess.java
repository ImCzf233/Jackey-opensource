package codes.som.anthony.koffee.sugar;

import codes.som.anthony.koffee.types.Types;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

/* compiled from: TypesAccess.kt */
@Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n��\bf\u0018��2\u00020\u0001J\u0014\u0010\u0017\u001a\u00020\u00032\n\u0010\u0018\u001a\u00060\u0001j\u0002`\u0019H\u0016R\u001c\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u001c\u0010\t\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u001c\u0010\u000b\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006R\u001c\u0010\r\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006R\u001c\u0010\u000f\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006R\u001c\u0010\u0011\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0006R\u001c\u0010\u0013\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0006R\u001c\u0010\u0015\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0006¨\u0006\u001a"}, m53d2 = {"Lcodes/som/anthony/koffee/sugar/TypesAccess;", "", "boolean", "Lorg/objectweb/asm/Type;", "kotlin.jvm.PlatformType", "getBoolean", "()Lorg/objectweb/asm/Type;", "byte", "getByte", "char", "getChar", "double", "getDouble", "float", "getFloat", "int", "getInt", "long", "getLong", "short", "getShort", "void", "getVoid", "coerceType", "value", "Lcodes/som/anthony/koffee/types/TypeLike;", "koffee"})
/* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/sugar/TypesAccess.class */
public interface TypesAccess {
    Type getVoid();

    Type getChar();

    Type getByte();

    Type getShort();

    Type getInt();

    Type getFloat();

    Type getLong();

    Type getDouble();

    Type getBoolean();

    @NotNull
    Type coerceType(@NotNull Object obj);

    /* compiled from: TypesAccess.kt */
    @Metadata(m51mv = {1, 1, 15}, m55bv = {1, 0, 3}, m52k = 3)
    /* loaded from: Jackey Client b2.jar:codes/som/anthony/koffee/sugar/TypesAccess$DefaultImpls.class */
    public static final class DefaultImpls {
        public static Type getVoid(TypesAccess $this) {
            return Types.getVoid();
        }

        public static Type getChar(TypesAccess $this) {
            return Types.getChar();
        }

        public static Type getByte(TypesAccess $this) {
            return Types.getByte();
        }

        public static Type getShort(TypesAccess $this) {
            return Types.getShort();
        }

        public static Type getInt(TypesAccess $this) {
            return Types.getInt();
        }

        public static Type getFloat(TypesAccess $this) {
            return Types.getFloat();
        }

        public static Type getLong(TypesAccess $this) {
            return Types.getLong();
        }

        public static Type getDouble(TypesAccess $this) {
            return Types.getDouble();
        }

        public static Type getBoolean(TypesAccess $this) {
            return Types.getBoolean();
        }

        @NotNull
        public static Type coerceType(TypesAccess $this, @NotNull Object value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            return Types.coerceType(value);
        }
    }
}
