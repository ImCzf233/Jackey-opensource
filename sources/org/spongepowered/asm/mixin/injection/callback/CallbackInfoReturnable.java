package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.util.Constants;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable.class */
public class CallbackInfoReturnable<R> extends CallbackInfo {
    private R returnValue;

    public CallbackInfoReturnable(String name, boolean cancellable) {
        super(name, cancellable);
        this.returnValue = null;
    }

    public CallbackInfoReturnable(String name, boolean cancellable, R returnValue) {
        super(name, cancellable);
        this.returnValue = returnValue;
    }

    public CallbackInfoReturnable(String name, boolean cancellable, byte returnValue) {
        super(name, cancellable);
        this.returnValue = (R) Byte.valueOf(returnValue);
    }

    public CallbackInfoReturnable(String name, boolean cancellable, char returnValue) {
        super(name, cancellable);
        this.returnValue = (R) Character.valueOf(returnValue);
    }

    public CallbackInfoReturnable(String name, boolean cancellable, double returnValue) {
        super(name, cancellable);
        this.returnValue = (R) Double.valueOf(returnValue);
    }

    public CallbackInfoReturnable(String name, boolean cancellable, float returnValue) {
        super(name, cancellable);
        this.returnValue = (R) Float.valueOf(returnValue);
    }

    public CallbackInfoReturnable(String name, boolean cancellable, int returnValue) {
        super(name, cancellable);
        this.returnValue = (R) Integer.valueOf(returnValue);
    }

    public CallbackInfoReturnable(String name, boolean cancellable, long returnValue) {
        super(name, cancellable);
        this.returnValue = (R) Long.valueOf(returnValue);
    }

    public CallbackInfoReturnable(String name, boolean cancellable, short returnValue) {
        super(name, cancellable);
        this.returnValue = (R) Short.valueOf(returnValue);
    }

    public CallbackInfoReturnable(String name, boolean cancellable, boolean returnValue) {
        super(name, cancellable);
        this.returnValue = (R) Boolean.valueOf(returnValue);
    }

    public void setReturnValue(R returnValue) throws CancellationException {
        super.cancel();
        this.returnValue = returnValue;
    }

    public R getReturnValue() {
        return this.returnValue;
    }

    public byte getReturnValueB() {
        if (this.returnValue == null) {
            return (byte) 0;
        }
        return ((Byte) this.returnValue).byteValue();
    }

    public char getReturnValueC() {
        if (this.returnValue == null) {
            return (char) 0;
        }
        return ((Character) this.returnValue).charValue();
    }

    public double getReturnValueD() {
        if (this.returnValue == null) {
            return 0.0d;
        }
        return ((Double) this.returnValue).doubleValue();
    }

    public float getReturnValueF() {
        if (this.returnValue == null) {
            return 0.0f;
        }
        return ((Float) this.returnValue).floatValue();
    }

    public int getReturnValueI() {
        if (this.returnValue == null) {
            return 0;
        }
        return ((Integer) this.returnValue).intValue();
    }

    public long getReturnValueJ() {
        if (this.returnValue == null) {
            return 0L;
        }
        return ((Long) this.returnValue).longValue();
    }

    public short getReturnValueS() {
        if (this.returnValue == null) {
            return (short) 0;
        }
        return ((Short) this.returnValue).shortValue();
    }

    public boolean getReturnValueZ() {
        if (this.returnValue == null) {
            return false;
        }
        return ((Boolean) this.returnValue).booleanValue();
    }

    public static String getReturnAccessor(Type returnType) {
        if (returnType.getSort() == 10 || returnType.getSort() == 9) {
            return "getReturnValue";
        }
        return String.format("getReturnValue%s", returnType.getDescriptor());
    }

    public static String getReturnDescriptor(Type returnType) {
        if (returnType.getSort() == 10 || returnType.getSort() == 9) {
            return String.format("()%s", Constants.OBJECT);
        }
        return String.format("()%s", returnType.getDescriptor());
    }
}
