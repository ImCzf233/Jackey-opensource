package com.jagrosh.discordipc.entities;

import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/jagrosh/discordipc/entities/Callback.class */
public class Callback {
    private final Consumer<Packet> success;
    private final Consumer<String> failure;

    public Callback() {
        this((Consumer<Packet>) null, (Consumer<String>) null);
    }

    public Callback(Consumer<Packet> success) {
        this(success, (Consumer<String>) null);
    }

    public Callback(Consumer<Packet> success, Consumer<String> failure) {
        this.success = success;
        this.failure = failure;
    }

    @Deprecated
    public Callback(Runnable success, Consumer<String> failure) {
        this(p -> {
            success.run();
        }, failure);
    }

    @Deprecated
    public Callback(Runnable success) {
        this(p -> {
            success.run();
        }, (Consumer<String>) null);
    }

    public boolean isEmpty() {
        return this.success == null && this.failure == null;
    }

    public void succeed(Packet packet) {
        if (this.success != null) {
            this.success.accept(packet);
        }
    }

    public void fail(String message) {
        if (this.failure != null) {
            this.failure.accept(message);
        }
    }
}
