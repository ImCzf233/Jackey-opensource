package com.viaversion.viaversion.libs.kyori.adventure.audience;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/audience/Audiences.class */
final class Audiences {
    static final Collector<? super Audience, ?, ForwardingAudience> COLLECTOR = Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), audiences -> {
        return Audience.audience(Collections.unmodifiableCollection(audiences));
    });

    private Audiences() {
    }
}
