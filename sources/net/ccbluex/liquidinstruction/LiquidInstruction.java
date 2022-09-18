package net.ccbluex.liquidinstruction;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JLabel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.TextStreamsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u001a\u0006\u0010��\u001a\u00020\u0001¨\u0006\u0002"}, m53d2 = {"main", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidinstruction.LiquidInstructionKt */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidinstruction/LiquidInstructionKt.class */
public final class LiquidInstruction {
    public static final void main() {
        JFrame frame = new JFrame("Install LiquidBounce+");
        frame.setDefaultCloseOperation(3);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        InputStream resourceAsStream = LiquidBounce.class.getResourceAsStream("/instructions.html");
        Intrinsics.checkNotNullExpressionValue(resourceAsStream, "LiquidBounce::class.java…eam(\"/instructions.html\")");
        String readText = TextStreamsKt.readText(new InputStreamReader(resourceAsStream, Charsets.UTF_8));
        String url = LiquidBounce.INSTANCE.getClass().getClassLoader().getResource("assets").toString();
        Intrinsics.checkNotNullExpressionValue(url, "LiquidBounce.javaClass.c…urce(\"assets\").toString()");
        frame.add(new JLabel(StringsKt.replace$default(readText, "{assets}", url, false, 4, (Object) null)), "Center");
        frame.pack();
        frame.setLocationRelativeTo((Component) null);
        frame.setVisible(true);
    }
}
