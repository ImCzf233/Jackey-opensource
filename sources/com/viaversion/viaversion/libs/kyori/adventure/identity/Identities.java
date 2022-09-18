package com.viaversion.viaversion.libs.kyori.adventure.identity;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/identity/Identities.class */
public final class Identities {
    static final Identity NIL = new Identity() { // from class: com.viaversion.viaversion.libs.kyori.adventure.identity.Identities.1
        private final UUID uuid = new UUID(0, 0);

        @Override // com.viaversion.viaversion.libs.kyori.adventure.identity.Identity
        @NotNull
        public UUID uuid() {
            return this.uuid;
        }

        public String toString() {
            return "Identity.nil()";
        }

        public boolean equals(final Object that) {
            return this == that;
        }

        public int hashCode() {
            return 0;
        }
    };

    private Identities() {
    }
}
