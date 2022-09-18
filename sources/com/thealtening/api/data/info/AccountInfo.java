package com.thealtening.api.data.info;

import com.google.gson.annotations.SerializedName;

/* loaded from: Jackey Client b2.jar:com/thealtening/api/data/info/AccountInfo.class */
public class AccountInfo {
    @SerializedName("hypixel.lvl")
    private int hypixelLevel;
    @SerializedName("hypixel.rank")
    private String hypixelRank;
    @SerializedName("mineplex.lvl")
    private int mineplexLevel;
    @SerializedName("mineplex.rank")
    private String mineplexRank;
    @SerializedName("labymod.cape")
    private boolean labymodCape;
    @SerializedName("5zig.cape")
    private boolean fiveZigCape;

    public int getHypixelLevel() {
        return this.hypixelLevel;
    }

    public String getHypixelRank() {
        return this.hypixelRank;
    }

    public int getMineplexLevel() {
        return this.mineplexLevel;
    }

    public String getMineplexRank() {
        return this.mineplexRank;
    }

    public boolean hasLabyModCape() {
        return this.labymodCape;
    }

    public boolean hasFiveZigCape() {
        return this.fiveZigCape;
    }

    public String toString() {
        return String.format("AccountInfo[%s:%s:%s:%s:%s:%s]", Integer.valueOf(this.hypixelLevel), this.hypixelRank, Integer.valueOf(this.mineplexLevel), this.mineplexRank, Boolean.valueOf(this.labymodCape), Boolean.valueOf(this.fiveZigCape));
    }
}
