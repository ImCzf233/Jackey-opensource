package com.viaversion.viaversion.libs.kyori.adventure.permission;

import com.viaversion.viaversion.libs.kyori.adventure.Adventure;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
import com.viaversion.viaversion.libs.kyori.adventure.util.TriState;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/permission/PermissionChecker.class */
public interface PermissionChecker extends Predicate<String> {
    public static final Pointer<PermissionChecker> POINTER = Pointer.pointer(PermissionChecker.class, Key.key(Adventure.NAMESPACE, "permission"));

    @NotNull
    TriState value(final String permission);

    @NotNull
    static PermissionChecker always(final TriState state) {
        return state == TriState.TRUE ? PermissionCheckers.TRUE : state == TriState.FALSE ? PermissionCheckers.FALSE : PermissionCheckers.NOT_SET;
    }

    default boolean test(final String permission) {
        return value(permission) == TriState.TRUE;
    }
}
