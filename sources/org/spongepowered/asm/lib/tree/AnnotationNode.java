package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Opcodes;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/AnnotationNode.class */
public class AnnotationNode extends AnnotationVisitor {
    public String desc;
    public List<Object> values;

    public AnnotationNode(String desc) {
        this(Opcodes.ASM5, desc);
        if (getClass() != AnnotationNode.class) {
            throw new IllegalStateException();
        }
    }

    public AnnotationNode(int api, String desc) {
        super(api);
        this.desc = desc;
    }

    public AnnotationNode(List<Object> values) {
        super(Opcodes.ASM5);
        this.values = values;
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visit(String name, Object value) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        if (value instanceof byte[]) {
            byte[] v = (byte[]) value;
            ArrayList<Byte> l = new ArrayList<>(v.length);
            for (byte b : v) {
                l.add(Byte.valueOf(b));
            }
            this.values.add(l);
        } else if (value instanceof boolean[]) {
            boolean[] v2 = (boolean[]) value;
            ArrayList<Boolean> l2 = new ArrayList<>(v2.length);
            for (boolean b2 : v2) {
                l2.add(Boolean.valueOf(b2));
            }
            this.values.add(l2);
        } else if (value instanceof short[]) {
            short[] v3 = (short[]) value;
            ArrayList<Short> l3 = new ArrayList<>(v3.length);
            for (short s : v3) {
                l3.add(Short.valueOf(s));
            }
            this.values.add(l3);
        } else if (value instanceof char[]) {
            char[] v4 = (char[]) value;
            ArrayList<Character> l4 = new ArrayList<>(v4.length);
            for (char c : v4) {
                l4.add(Character.valueOf(c));
            }
            this.values.add(l4);
        } else if (value instanceof int[]) {
            int[] v5 = (int[]) value;
            ArrayList<Integer> l5 = new ArrayList<>(v5.length);
            for (int i : v5) {
                l5.add(Integer.valueOf(i));
            }
            this.values.add(l5);
        } else if (value instanceof long[]) {
            long[] v6 = (long[]) value;
            ArrayList<Long> l6 = new ArrayList<>(v6.length);
            for (long lng : v6) {
                l6.add(Long.valueOf(lng));
            }
            this.values.add(l6);
        } else if (value instanceof float[]) {
            float[] v7 = (float[]) value;
            ArrayList<Float> l7 = new ArrayList<>(v7.length);
            for (float f : v7) {
                l7.add(Float.valueOf(f));
            }
            this.values.add(l7);
        } else if (value instanceof double[]) {
            double[] v8 = (double[]) value;
            ArrayList<Double> l8 = new ArrayList<>(v8.length);
            for (double d : v8) {
                l8.add(Double.valueOf(d));
            }
            this.values.add(l8);
        } else {
            this.values.add(value);
        }
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visitEnum(String name, String desc, String value) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        this.values.add(new String[]{desc, value});
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        AnnotationNode annotation = new AnnotationNode(desc);
        this.values.add(annotation);
        return annotation;
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitArray(String name) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        List<Object> array = new ArrayList<>();
        this.values.add(array);
        return new AnnotationNode(array);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visitEnd() {
    }

    public void check(int api) {
    }

    public void accept(AnnotationVisitor av) {
        if (av != null) {
            if (this.values != null) {
                for (int i = 0; i < this.values.size(); i += 2) {
                    String name = (String) this.values.get(i);
                    Object value = this.values.get(i + 1);
                    accept(av, name, value);
                }
            }
            av.visitEnd();
        }
    }

    public static void accept(AnnotationVisitor av, String name, Object value) {
        if (av != null) {
            if (value instanceof String[]) {
                String[] typeconst = (String[]) value;
                av.visitEnum(name, typeconst[0], typeconst[1]);
            } else if (value instanceof AnnotationNode) {
                AnnotationNode an = (AnnotationNode) value;
                an.accept(av.visitAnnotation(name, an.desc));
            } else if (value instanceof List) {
                AnnotationVisitor v = av.visitArray(name);
                if (v != null) {
                    List<?> array = (List) value;
                    for (int j = 0; j < array.size(); j++) {
                        accept(v, null, array.get(j));
                    }
                    v.visitEnd();
                }
            } else {
                av.visit(name, value);
            }
        }
    }
}
