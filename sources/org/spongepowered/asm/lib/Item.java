package org.spongepowered.asm.lib;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/Item.class */
final class Item {
    int index;
    int type;
    int intVal;
    long longVal;
    String strVal1;
    String strVal2;
    String strVal3;
    int hashCode;
    Item next;

    public Item() {
    }

    public Item(int index) {
        this.index = index;
    }

    public Item(int index, Item i) {
        this.index = index;
        this.type = i.type;
        this.intVal = i.intVal;
        this.longVal = i.longVal;
        this.strVal1 = i.strVal1;
        this.strVal2 = i.strVal2;
        this.strVal3 = i.strVal3;
        this.hashCode = i.hashCode;
    }

    public void set(int intVal) {
        this.type = 3;
        this.intVal = intVal;
        this.hashCode = Integer.MAX_VALUE & (this.type + intVal);
    }

    public void set(long longVal) {
        this.type = 5;
        this.longVal = longVal;
        this.hashCode = Integer.MAX_VALUE & (this.type + ((int) longVal));
    }

    public void set(float floatVal) {
        this.type = 4;
        this.intVal = Float.floatToRawIntBits(floatVal);
        this.hashCode = Integer.MAX_VALUE & (this.type + ((int) floatVal));
    }

    public void set(double doubleVal) {
        this.type = 6;
        this.longVal = Double.doubleToRawLongBits(doubleVal);
        this.hashCode = Integer.MAX_VALUE & (this.type + ((int) doubleVal));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void set(int type, String strVal1, String strVal2, String strVal3) {
        this.type = type;
        this.strVal1 = strVal1;
        this.strVal2 = strVal2;
        this.strVal3 = strVal3;
        switch (type) {
            case 1:
            case 8:
            case 16:
            case 30:
                break;
            case 7:
                this.intVal = 0;
                break;
            case 12:
                this.hashCode = Integer.MAX_VALUE & (type + (strVal1.hashCode() * strVal2.hashCode()));
                return;
            default:
                this.hashCode = Integer.MAX_VALUE & (type + (strVal1.hashCode() * strVal2.hashCode() * strVal3.hashCode()));
                return;
        }
        this.hashCode = Integer.MAX_VALUE & (type + strVal1.hashCode());
    }

    public void set(String name, String desc, int bsmIndex) {
        this.type = 18;
        this.longVal = bsmIndex;
        this.strVal1 = name;
        this.strVal2 = desc;
        this.hashCode = Integer.MAX_VALUE & (18 + (bsmIndex * this.strVal1.hashCode() * this.strVal2.hashCode()));
    }

    public void set(int position, int hashCode) {
        this.type = 33;
        this.intVal = position;
        this.hashCode = hashCode;
    }

    public boolean isEqualTo(Item i) {
        switch (this.type) {
            case 1:
            case 7:
            case 8:
            case 16:
            case 30:
                return i.strVal1.equals(this.strVal1);
            case 2:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            default:
                return i.strVal1.equals(this.strVal1) && i.strVal2.equals(this.strVal2) && i.strVal3.equals(this.strVal3);
            case 3:
            case 4:
                return i.intVal == this.intVal;
            case 5:
            case 6:
            case 32:
                return i.longVal == this.longVal;
            case 12:
                return i.strVal1.equals(this.strVal1) && i.strVal2.equals(this.strVal2);
            case 18:
                return i.longVal == this.longVal && i.strVal1.equals(this.strVal1) && i.strVal2.equals(this.strVal2);
            case 31:
                return i.intVal == this.intVal && i.strVal1.equals(this.strVal1);
        }
    }
}
