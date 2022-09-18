package com.viaversion.viaversion.libs.kyori.adventure.identity;

import com.viaversion.viaversion.libs.kyori.adventure.Adventure;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/identity/Identity.class */
public interface Identity extends Examinable {
    public static final Pointer<String> NAME = Pointer.pointer(String.class, Key.key(Adventure.NAMESPACE, "name"));
    public static final Pointer<UUID> UUID = Pointer.pointer(UUID.class, Key.key(Adventure.NAMESPACE, "uuid"));
    public static final Pointer<Component> DISPLAY_NAME = Pointer.pointer(Component.class, Key.key(Adventure.NAMESPACE, "display_name"));
    public static final Pointer<Locale> LOCALE = Pointer.pointer(Locale.class, Key.key(Adventure.NAMESPACE, "locale"));

    @NotNull
    UUID uuid();

    @NotNull
    static Identity nil() {
        return Identities.NIL;
    }

    @NotNull
    static Identity identity(@NotNull final UUID uuid) {
        return uuid.equals(Identities.NIL.uuid()) ? Identities.NIL : new IdentityImpl(uuid);
    }

    @Override // com.viaversion.viaversion.libs.kyori.examination.Examinable
    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.m91of("uuid", uuid()));
    }
}
