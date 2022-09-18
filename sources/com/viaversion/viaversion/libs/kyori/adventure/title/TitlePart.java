package com.viaversion.viaversion.libs.kyori.adventure.title;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.title.Title;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/title/TitlePart.class */
public interface TitlePart<T> {
    public static final TitlePart<Component> TITLE = new TitlePart<Component>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart.1
        public String toString() {
            return "TitlePart.TITLE";
        }
    };
    public static final TitlePart<Component> SUBTITLE = new TitlePart<Component>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart.2
        public String toString() {
            return "TitlePart.SUBTITLE";
        }
    };
    public static final TitlePart<Title.Times> TIMES = new TitlePart<Title.Times>() { // from class: com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart.3
        public String toString() {
            return "TitlePart.TIMES";
        }
    };
}
