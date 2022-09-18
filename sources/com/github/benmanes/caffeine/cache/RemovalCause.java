package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/RemovalCause.class */
public enum RemovalCause {
    EXPLICIT { // from class: com.github.benmanes.caffeine.cache.RemovalCause.1
        @Override // com.github.benmanes.caffeine.cache.RemovalCause
        public boolean wasEvicted() {
            return false;
        }
    },
    REPLACED { // from class: com.github.benmanes.caffeine.cache.RemovalCause.2
        @Override // com.github.benmanes.caffeine.cache.RemovalCause
        public boolean wasEvicted() {
            return false;
        }
    },
    COLLECTED { // from class: com.github.benmanes.caffeine.cache.RemovalCause.3
        @Override // com.github.benmanes.caffeine.cache.RemovalCause
        public boolean wasEvicted() {
            return true;
        }
    },
    EXPIRED { // from class: com.github.benmanes.caffeine.cache.RemovalCause.4
        @Override // com.github.benmanes.caffeine.cache.RemovalCause
        public boolean wasEvicted() {
            return true;
        }
    },
    SIZE { // from class: com.github.benmanes.caffeine.cache.RemovalCause.5
        @Override // com.github.benmanes.caffeine.cache.RemovalCause
        public boolean wasEvicted() {
            return true;
        }
    };

    public abstract boolean wasEvicted();
}
