package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.JvmPlatformAnnotations;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\bÂ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n��¨\u0006\u0005"}, m53d2 = {"Lkotlin/text/ScreenFloatValueRegEx;", "", "()V", "value", "Lkotlin/text/Regex;", "kotlin-stdlib"})
/* renamed from: kotlin.text.ScreenFloatValueRegEx */
/* loaded from: Jackey Client b2.jar:kotlin/text/ScreenFloatValueRegEx.class */
final class StringNumberConversionsJVM {
    @NotNull
    public static final StringNumberConversionsJVM INSTANCE = new StringNumberConversionsJVM();
    @JvmPlatformAnnotations
    @NotNull
    public static final Regex value;

    private StringNumberConversionsJVM() {
    }

    static {
        StringNumberConversionsJVM stringNumberConversionsJVM = INSTANCE;
        String Exp = Intrinsics.stringPlus("[eE][+-]?", "(\\p{Digit}+)");
        String HexString = "(0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+))";
        String Number = "((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)(" + Exp + ")?)|(\\.((\\p{Digit}+))(" + Exp + ")?)|((" + HexString + ")[pP][+-]?(\\p{Digit}+))";
        String fpRegex = "[\\x00-\\x20]*[+-]?(NaN|Infinity|((" + Number + ")[fFdD]?))[\\x00-\\x20]*";
        value = new Regex(fpRegex);
    }
}
