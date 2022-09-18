package net.ccbluex.liquidbounce.features.module;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: ModuleCategory.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n��\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0001\u0018��2\b\u0012\u0004\u0012\u00020��0\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "", "displayName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "COMBAT", "PLAYER", "MOVEMENT", "RENDER", "WORLD", "MISC", "EXPLOIT", "COLOR", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/ModuleCategory.class */
public enum ModuleCategory {
    COMBAT("Combat"),
    PLAYER("Player"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    WORLD("World"),
    MISC("Misc"),
    EXPLOIT("Exploit"),
    COLOR("Color");
    
    @NotNull
    private final String displayName;

    ModuleCategory(String displayName) {
        this.displayName = displayName;
    }

    @NotNull
    public final String getDisplayName() {
        return this.displayName;
    }
}
