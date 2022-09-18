package com.viaversion.viaversion.libs.kyori.adventure.permission;

import com.viaversion.viaversion.libs.kyori.adventure.util.TriState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/permission/PermissionCheckers.class */
final class PermissionCheckers {
    static final PermissionChecker NOT_SET = new Always(TriState.NOT_SET);
    static final PermissionChecker FALSE = new Always(TriState.FALSE);
    static final PermissionChecker TRUE = new Always(TriState.TRUE);

    private PermissionCheckers() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/permission/PermissionCheckers$Always.class */
    private static final class Always implements PermissionChecker {
        private final TriState value;

        private Always(final TriState value) {
            this.value = value;
        }

        @Override // com.viaversion.viaversion.libs.kyori.adventure.permission.PermissionChecker
        @NotNull
        public TriState value(final String permission) {
            return this.value;
        }

        public String toString() {
            return PermissionChecker.class.getSimpleName() + ".always(" + this.value + ")";
        }

        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Always always = (Always) other;
            return this.value == always.value;
        }

        public int hashCode() {
            return this.value.hashCode();
        }
    }
}
