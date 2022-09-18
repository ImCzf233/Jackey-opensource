package com.jagrosh.discordipc.entities;

/* loaded from: Jackey Client b2.jar:com/jagrosh/discordipc/entities/User.class */
public class User {
    private final String name;
    private final String discriminator;

    /* renamed from: id */
    private final long f6id;
    private final String avatar;

    public User(String name, String discriminator, long id, String avatar) {
        this.name = name;
        this.discriminator = discriminator;
        this.f6id = id;
        this.avatar = avatar;
    }

    public String getName() {
        return this.name;
    }

    public String getDiscriminator() {
        return this.discriminator;
    }

    public long getIdLong() {
        return this.f6id;
    }

    public String getId() {
        return Long.toString(this.f6id);
    }

    public String getAvatarId() {
        return this.avatar;
    }

    public String getAvatarUrl() {
        if (getAvatarId() == null) {
            return null;
        }
        return "https://cdn.discordapp.com/avatars/" + getId() + "/" + getAvatarId() + (getAvatarId().startsWith("a_") ? ".gif" : ".png");
    }

    public String getDefaultAvatarId() {
        return DefaultAvatar.values()[Integer.parseInt(getDiscriminator()) % DefaultAvatar.values().length].toString();
    }

    public String getDefaultAvatarUrl() {
        return "https://discordapp.com/assets/" + getDefaultAvatarId() + ".png";
    }

    public String getEffectiveAvatarUrl() {
        return getAvatarUrl() == null ? getDefaultAvatarUrl() : getAvatarUrl();
    }

    public boolean isBot() {
        return false;
    }

    public String getAsMention() {
        return "<@" + this.f6id + '>';
    }

    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        User oUser = (User) o;
        return this == oUser || this.f6id == oUser.f6id;
    }

    public int hashCode() {
        return Long.hashCode(this.f6id);
    }

    public String toString() {
        return "U:" + getName() + '(' + this.f6id + ')';
    }

    /* loaded from: Jackey Client b2.jar:com/jagrosh/discordipc/entities/User$DefaultAvatar.class */
    public enum DefaultAvatar {
        BLURPLE("6debd47ed13483642cf09e832ed0bc1b"),
        GREY("322c936a8c8be1b803cd94861bdfa868"),
        GREEN("dd4dbc0016779df1378e7812eabaa04d"),
        ORANGE("0e291f67c9274a1abdddeb3fd919cbaa"),
        RED("1cbd08c76f8af6dddce02c5138971129");
        
        private final String text;

        DefaultAvatar(String text) {
            this.text = text;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.text;
        }
    }
}
