package net.ccbluex.liquidbounce.features.command.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;

/* compiled from: ThemeCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/command/commands/ThemeCommand$loadTheme$thread$1.class */
public final class ThemeCommand$loadTheme$thread$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ ThemeCommand this$0;
    final /* synthetic */ boolean $useCached;
    final /* synthetic */ Function1<List<String>, Unit> $callback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ThemeCommand$loadTheme$thread$1(ThemeCommand $receiver, boolean $useCached, Function1<? super List<String>, Unit> function1) {
        super(0);
        this.this$0 = $receiver;
        this.$useCached = $useCached;
        this.$callback = function1;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        Object obj;
        List list;
        List<String> list2;
        obj = this.this$0.loadingLock;
        boolean z = this.$useCached;
        ThemeCommand themeCommand = this.this$0;
        Function1<List<String>, Unit> function1 = this.$callback;
        synchronized (obj) {
            if (z) {
                list = themeCommand.onlineThemes;
                if (list != null) {
                    list2 = themeCommand.onlineThemes;
                    Intrinsics.checkNotNull(list2);
                    function1.invoke(list2);
                    return;
                }
            }
            try {
                JsonArray parse = new JsonParser().parse(HttpUtils.get("https://api.github.com/repos/WYSI-Foundation/LiquidCloud/contents/LiquidBounce/themes"));
                List loadingTheme = new ArrayList();
                if (parse instanceof JsonArray) {
                    Iterator it = parse.iterator();
                    while (it.hasNext()) {
                        JsonElement theme = (JsonElement) it.next();
                        String asString = theme.getAsJsonObject().get("name").getAsString();
                        Intrinsics.checkNotNullExpressionValue(asString, "theme.asJsonObject[\"name\"].asString");
                        loadingTheme.add(asString);
                    }
                }
                function1.invoke(loadingTheme);
                themeCommand.onlineThemes = loadingTheme;
            } catch (Exception e) {
                themeCommand.chat("Failed to fetch theme list.");
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}
