package kotlin.p002io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReadWrite.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��X\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n��\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0017\u0010��\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010��\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0087\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\u0087\b\u001a8\u0010\u001b\u001a\u0002H\u001c\"\u0004\b��\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\u0086\bø\u0001��ø\u0001\u0001¢\u0006\u0002\u0010\u001f\u0082\u0002\u000f\n\u0006\b\u0011(\u001e0\u0001\n\u0005\b\u009920\u0001¨\u0006 "}, m53d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"})
@JvmName(name = "TextStreamsKt")
/* renamed from: kotlin.io.TextStreamsKt */
/* loaded from: Jackey Client b2.jar:kotlin/io/TextStreamsKt.class */
public final class TextStreamsKt {
    static /* synthetic */ BufferedReader buffered$default(Reader $this$buffered_u24default, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            bufferSize = 8192;
        }
        Intrinsics.checkNotNullParameter($this$buffered_u24default, "<this>");
        return $this$buffered_u24default instanceof BufferedReader ? (BufferedReader) $this$buffered_u24default : new BufferedReader($this$buffered_u24default, bufferSize);
    }

    @InlineOnly
    private static final BufferedReader buffered(Reader $this$buffered, int bufferSize) {
        Intrinsics.checkNotNullParameter($this$buffered, "<this>");
        return $this$buffered instanceof BufferedReader ? (BufferedReader) $this$buffered : new BufferedReader($this$buffered, bufferSize);
    }

    static /* synthetic */ BufferedWriter buffered$default(Writer $this$buffered_u24default, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            bufferSize = 8192;
        }
        Intrinsics.checkNotNullParameter($this$buffered_u24default, "<this>");
        return $this$buffered_u24default instanceof BufferedWriter ? (BufferedWriter) $this$buffered_u24default : new BufferedWriter($this$buffered_u24default, bufferSize);
    }

    @InlineOnly
    private static final BufferedWriter buffered(Writer $this$buffered, int bufferSize) {
        Intrinsics.checkNotNullParameter($this$buffered, "<this>");
        return $this$buffered instanceof BufferedWriter ? (BufferedWriter) $this$buffered : new BufferedWriter($this$buffered, bufferSize);
    }

    public static final void forEachLine(@NotNull Reader $this$forEachLine, @NotNull Function1<? super String, Unit> action) {
        Intrinsics.checkNotNullParameter($this$forEachLine, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        BufferedReader bufferedReader = $this$forEachLine instanceof BufferedReader ? (BufferedReader) $this$forEachLine : new BufferedReader($this$forEachLine, 8192);
        try {
            BufferedReader it$iv = bufferedReader;
            Sequence it = lineSequence(it$iv);
            for (Object element$iv : it) {
                action.invoke(element$iv);
            }
            Unit unit = Unit.INSTANCE;
            Closeable.closeFinally(bufferedReader, null);
        } finally {
        }
    }

    @NotNull
    public static final List<String> readLines(@NotNull Reader $this$readLines) {
        Intrinsics.checkNotNullParameter($this$readLines, "<this>");
        ArrayList result = new ArrayList();
        forEachLine($this$readLines, new TextStreamsKt$readLines$1(result));
        return result;
    }

    public static final <T> T useLines(@NotNull Reader $this$useLines, @NotNull Function1<? super Sequence<String>, ? extends T> block) {
        Intrinsics.checkNotNullParameter($this$useLines, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        BufferedReader bufferedReader = $this$useLines instanceof BufferedReader ? (BufferedReader) $this$useLines : new BufferedReader($this$useLines, 8192);
        try {
            BufferedReader it = bufferedReader;
            T invoke = block.invoke(lineSequence(it));
            InlineMarker.finallyStart(1);
            Closeable.closeFinally(bufferedReader, null);
            InlineMarker.finallyEnd(1);
            return invoke;
        } finally {
        }
    }

    @InlineOnly
    private static final StringReader reader(String $this$reader) {
        Intrinsics.checkNotNullParameter($this$reader, "<this>");
        return new StringReader($this$reader);
    }

    @NotNull
    public static final Sequence<String> lineSequence(@NotNull BufferedReader $this$lineSequence) {
        Intrinsics.checkNotNullParameter($this$lineSequence, "<this>");
        return SequencesKt.constrainOnce(new ReadWrite($this$lineSequence));
    }

    @NotNull
    public static final String readText(@NotNull Reader $this$readText) {
        Intrinsics.checkNotNullParameter($this$readText, "<this>");
        StringWriter buffer = new StringWriter();
        copyTo$default($this$readText, buffer, 0, 2, null);
        String stringWriter = buffer.toString();
        Intrinsics.checkNotNullExpressionValue(stringWriter, "buffer.toString()");
        return stringWriter;
    }

    public static /* synthetic */ long copyTo$default(Reader reader, Writer writer, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        return copyTo(reader, writer, i);
    }

    public static final long copyTo(@NotNull Reader $this$copyTo, @NotNull Writer out, int bufferSize) {
        Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
        Intrinsics.checkNotNullParameter(out, "out");
        long charsCopied = 0;
        char[] buffer = new char[bufferSize];
        int read = $this$copyTo.read(buffer);
        while (true) {
            int chars = read;
            if (chars >= 0) {
                out.write(buffer, 0, chars);
                charsCopied += chars;
                read = $this$copyTo.read(buffer);
            } else {
                return charsCopied;
            }
        }
    }

    @InlineOnly
    private static final String readText(URL $this$readText, Charset charset) {
        Intrinsics.checkNotNullParameter($this$readText, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new String(readBytes($this$readText), charset);
    }

    static /* synthetic */ String readText$default(URL $this$readText_u24default, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter($this$readText_u24default, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new String(readBytes($this$readText_u24default), charset);
    }

    @NotNull
    public static final byte[] readBytes(@NotNull URL $this$readBytes) {
        Intrinsics.checkNotNullParameter($this$readBytes, "<this>");
        InputStream openStream = $this$readBytes.openStream();
        try {
            InputStream it = openStream;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            byte[] readBytes = IOStreams.readBytes(it);
            Closeable.closeFinally(openStream, null);
            return readBytes;
        } finally {
        }
    }
}
