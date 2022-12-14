package com.thealtening.api.data;

import com.google.gson.annotations.SerializedName;

/* loaded from: Jackey Client b2.jar:com/thealtening/api/data/LicenseData.class */
public class LicenseData {
    private String username;
    private boolean premium;
    @SerializedName("premium_name")
    private String premiumName;
    @SerializedName("expires")
    private String expiryDate;

    public String getUsername() {
        return this.username;
    }

    public boolean isPremium() {
        return this.premium;
    }

    public String getPremiumName() {
        return this.premiumName;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String toString() {
        return String.format("LicenseData[%s:%s:%s:%s]", this.username, Boolean.valueOf(this.premium), this.premiumName, this.expiryDate);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LicenseData)) {
            return false;
        }
        LicenseData castedLicenseInfo = (LicenseData) obj;
        return castedLicenseInfo.getExpiryDate().equals(getExpiryDate()) && castedLicenseInfo.getPremiumName().equals(getPremiumName()) && castedLicenseInfo.isPremium() == isPremium() && castedLicenseInfo.getUsername().equals(getUsername());
    }
}
