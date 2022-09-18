package com.viaversion.viaversion.libs.kyori.examination;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/examination/ExaminableProperty.class */
public abstract class ExaminableProperty {
    @NotNull
    public abstract String name();

    @NotNull
    public abstract <R> R examine(@NotNull final Examiner<? extends R> examiner);

    private ExaminableProperty() {
    }

    public String toString() {
        return "ExaminableProperty{" + name() + "}";
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m91of(@NotNull final String name, @Nullable final Object value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m90of(@NotNull final String name, @Nullable final String value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m88of(@NotNull final String name, final boolean value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m80of(@NotNull final String name, final boolean[] value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m97of(@NotNull final String name, final byte value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m87of(@NotNull final String name, final byte[] value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m96of(@NotNull final String name, final char value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.7
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m86of(@NotNull final String name, final char[] value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.8
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m95of(@NotNull final String name, final double value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.9
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m85of(@NotNull final String name, final double[] value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.10
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m94of(@NotNull final String name, final float value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.11
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m84of(@NotNull final String name, final float[] value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.12
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m93of(@NotNull final String name, final int value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.13
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m83of(@NotNull final String name, final int[] value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.14
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m92of(@NotNull final String name, final long value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.15
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m82of(@NotNull final String name, final long[] value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.16
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m89of(@NotNull final String name, final short value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.17
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }

    @NotNull
    /* renamed from: of */
    public static ExaminableProperty m81of(@NotNull final String name, final short[] value) {
        return new ExaminableProperty() { // from class: com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty.18
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public String name() {
                return name;
            }

            @Override // com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty
            @NotNull
            public <R> R examine(@NotNull final Examiner<? extends R> examiner) {
                return examiner.examine(value);
            }
        };
    }
}
