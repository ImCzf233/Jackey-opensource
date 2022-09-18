package org.apache.log4j.spi;

import java.io.PrintWriter;
import java.util.Vector;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/spi/VectorWriter.class */
class VectorWriter extends PrintWriter {

    /* renamed from: v */
    private Vector f403v = new Vector();

    VectorWriter() {
        super(new NullWriter());
    }

    @Override // java.io.PrintWriter
    public void print(Object o) {
        this.f403v.addElement(String.valueOf(o));
    }

    @Override // java.io.PrintWriter
    public void print(char[] chars) {
        this.f403v.addElement(new String(chars));
    }

    @Override // java.io.PrintWriter
    public void print(String s) {
        this.f403v.addElement(s);
    }

    @Override // java.io.PrintWriter
    public void println(Object o) {
        this.f403v.addElement(String.valueOf(o));
    }

    @Override // java.io.PrintWriter
    public void println(char[] chars) {
        this.f403v.addElement(new String(chars));
    }

    @Override // java.io.PrintWriter
    public void println(String s) {
        this.f403v.addElement(s);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] chars) {
        this.f403v.addElement(new String(chars));
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] chars, int off, int len) {
        this.f403v.addElement(new String(chars, off, len));
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String s, int off, int len) {
        this.f403v.addElement(s.substring(off, off + len));
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String s) {
        this.f403v.addElement(s);
    }

    public String[] toStringArray() {
        int len = this.f403v.size();
        String[] sa = new String[len];
        for (int i = 0; i < len; i++) {
            sa[i] = (String) this.f403v.elementAt(i);
        }
        return sa;
    }
}
