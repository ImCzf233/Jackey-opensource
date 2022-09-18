package com.viaversion.viaversion.libs.kyori.adventure.identity;

import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/identity/IdentityImpl.class */
final class IdentityImpl implements Examinable, Identity {
    private final UUID uuid;

    public IdentityImpl(final UUID uuid) {
        this.uuid = uuid;
    }

    @Override // com.viaversion.viaversion.libs.kyori.adventure.identity.Identity
    @NotNull
    public UUID uuid() {
        return this.uuid;
    }

    public String toString() {
        return (String) examine(StringExaminer.simpleEscaping());
    }

    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Identity)) {
            return false;
        }
        Identity that = (Identity) other;
        return this.uuid.equals(that.uuid());
    }

    public int hashCode() {
        return this.uuid.hashCode();
    }
}
