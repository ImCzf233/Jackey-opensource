package de.gerrygames.viarewind.replacement;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.List;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/replacement/EntityReplacement.class */
public interface EntityReplacement {
    int getEntityId();

    void setLocation(double d, double d2, double d3);

    void relMove(double d, double d2, double d3);

    void setYawPitch(float f, float f2);

    void setHeadYaw(float f);

    void spawn();

    void despawn();

    void updateMetadata(List<Metadata> list);
}
