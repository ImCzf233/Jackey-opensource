package com.viaversion.viaversion.configuration;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocol.BlockedProtocolVersionsImpl;
import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/configuration/AbstractViaConfig.class */
public abstract class AbstractViaConfig extends Config implements ViaVersionConfig {
    private boolean checkForUpdates;
    private boolean preventCollision;
    private boolean useNewEffectIndicator;
    private boolean useNewDeathmessages;
    private boolean suppressMetadataErrors;
    private boolean shieldBlocking;
    private boolean noDelayShieldBlocking;
    private boolean showShieldWhenSwordInHand;
    private boolean hologramPatch;
    private boolean pistonAnimationPatch;
    private boolean bossbarPatch;
    private boolean bossbarAntiFlicker;
    private double hologramOffset;
    private int maxPPS;
    private String maxPPSKickMessage;
    private int trackingPeriod;
    private int warningPPS;
    private int maxPPSWarnings;
    private String maxPPSWarningsKickMessage;
    private boolean sendSupportedVersions;
    private boolean simulatePlayerTick;
    private boolean itemCache;
    private boolean nmsPlayerTicking;
    private boolean replacePistons;
    private int pistonReplacementId;
    private boolean chunkBorderFix;
    private boolean autoTeam;
    private boolean forceJsonTransform;
    private boolean nbtArrayFix;
    private BlockedProtocolVersions blockedProtocolVersions;
    private String blockedDisconnectMessage;
    private String reloadDisconnectMessage;
    private boolean suppressConversionWarnings;
    private boolean disable1_13TabComplete;
    private boolean minimizeCooldown;
    private boolean teamColourFix;
    private boolean serversideBlockConnections;
    private boolean reduceBlockStorageMemory;
    private boolean flowerStemWhenBlockAbove;
    private boolean vineClimbFix;
    private boolean snowCollisionFix;
    private boolean infestedBlocksFix;
    private int tabCompleteDelay;
    private boolean truncate1_14Books;
    private boolean leftHandedHandling;
    private boolean fullBlockLightFix;
    private boolean healthNaNFix;
    private boolean instantRespawn;
    private boolean ignoreLongChannelNames;
    private boolean forcedUse1_17ResourcePack;
    private JsonElement resourcePack1_17PromptMessage;
    private WorldIdentifiers map1_16WorldNames;
    private boolean cache1_17Light;

    public AbstractViaConfig(File configFile) {
        super(configFile);
    }

    @Override // com.viaversion.viaversion.util.Config, com.viaversion.viaversion.api.configuration.ConfigurationProvider
    public void reloadConfig() {
        super.reloadConfig();
        loadFields();
    }

    public void loadFields() {
        this.checkForUpdates = getBoolean("checkforupdates", true);
        this.preventCollision = getBoolean("prevent-collision", true);
        this.useNewEffectIndicator = getBoolean("use-new-effect-indicator", true);
        this.useNewDeathmessages = getBoolean("use-new-deathmessages", true);
        this.suppressMetadataErrors = getBoolean("suppress-metadata-errors", false);
        this.shieldBlocking = getBoolean("shield-blocking", true);
        this.noDelayShieldBlocking = getBoolean("no-delay-shield-blocking", false);
        this.showShieldWhenSwordInHand = getBoolean("show-shield-when-sword-in-hand", false);
        this.hologramPatch = getBoolean("hologram-patch", false);
        this.pistonAnimationPatch = getBoolean("piston-animation-patch", false);
        this.bossbarPatch = getBoolean("bossbar-patch", true);
        this.bossbarAntiFlicker = getBoolean("bossbar-anti-flicker", false);
        this.hologramOffset = getDouble("hologram-y", -0.96d);
        this.maxPPS = getInt("max-pps", 800);
        this.maxPPSKickMessage = getString("max-pps-kick-msg", "Sending packets too fast? lag?");
        this.trackingPeriod = getInt("tracking-period", 6);
        this.warningPPS = getInt("tracking-warning-pps", 120);
        this.maxPPSWarnings = getInt("tracking-max-warnings", 3);
        this.maxPPSWarningsKickMessage = getString("tracking-max-kick-msg", "You are sending too many packets, :(");
        this.sendSupportedVersions = getBoolean("send-supported-versions", false);
        this.simulatePlayerTick = getBoolean("simulate-pt", true);
        this.itemCache = getBoolean("item-cache", true);
        this.nmsPlayerTicking = getBoolean("nms-player-ticking", true);
        this.replacePistons = getBoolean("replace-pistons", false);
        this.pistonReplacementId = getInt("replacement-piston-id", 0);
        this.chunkBorderFix = getBoolean("chunk-border-fix", false);
        this.autoTeam = getBoolean("auto-team", true);
        this.forceJsonTransform = getBoolean("force-json-transform", false);
        this.nbtArrayFix = getBoolean("chat-nbt-fix", true);
        this.blockedProtocolVersions = loadBlockedProtocolVersions();
        this.blockedDisconnectMessage = getString("block-disconnect-msg", "You are using an unsupported Minecraft version!");
        this.reloadDisconnectMessage = getString("reload-disconnect-msg", "Server reload, please rejoin!");
        this.minimizeCooldown = getBoolean("minimize-cooldown", true);
        this.teamColourFix = getBoolean("team-colour-fix", true);
        this.suppressConversionWarnings = getBoolean("suppress-conversion-warnings", false);
        this.disable1_13TabComplete = getBoolean("disable-1_13-auto-complete", false);
        this.serversideBlockConnections = getBoolean("serverside-blockconnections", true);
        this.reduceBlockStorageMemory = getBoolean("reduce-blockstorage-memory", false);
        this.flowerStemWhenBlockAbove = getBoolean("flowerstem-when-block-above", false);
        this.vineClimbFix = getBoolean("vine-climb-fix", false);
        this.snowCollisionFix = getBoolean("fix-low-snow-collision", false);
        this.infestedBlocksFix = getBoolean("fix-infested-block-breaking", true);
        this.tabCompleteDelay = getInt("1_13-tab-complete-delay", 0);
        this.truncate1_14Books = getBoolean("truncate-1_14-books", false);
        this.leftHandedHandling = getBoolean("left-handed-handling", true);
        this.fullBlockLightFix = getBoolean("fix-non-full-blocklight", false);
        this.healthNaNFix = getBoolean("fix-1_14-health-nan", true);
        this.instantRespawn = getBoolean("use-1_15-instant-respawn", false);
        this.ignoreLongChannelNames = getBoolean("ignore-long-1_16-channel-names", true);
        this.forcedUse1_17ResourcePack = getBoolean("forced-use-1_17-resource-pack", false);
        this.resourcePack1_17PromptMessage = getSerializedComponent("resource-pack-1_17-prompt");
        Map<String, String> worlds = (Map) get("map-1_16-world-names", Map.class, new HashMap());
        this.map1_16WorldNames = new WorldIdentifiers(worlds.getOrDefault("overworld", WorldIdentifiers.OVERWORLD_DEFAULT), worlds.getOrDefault("nether", WorldIdentifiers.NETHER_DEFAULT), worlds.getOrDefault(AsmConstants.END, WorldIdentifiers.END_DEFAULT));
        this.cache1_17Light = getBoolean("cache-1_17-light", true);
    }

    private BlockedProtocolVersions loadBlockedProtocolVersions() {
        IntSet blockedProtocols = new IntOpenHashSet(getIntegerList("block-protocols"));
        int lowerBound = -1;
        int upperBound = -1;
        for (String s : getStringList("block-versions")) {
            if (!s.isEmpty()) {
                char c = s.charAt(0);
                if (c == '<' || c == '>') {
                    ProtocolVersion protocolVersion = protocolVersion(s.substring(1));
                    if (protocolVersion != null) {
                        if (c == '<') {
                            if (lowerBound != -1) {
                                Via.getPlatform().getLogger().warning("Already set lower bound " + lowerBound + " overridden by " + protocolVersion.getName());
                            }
                            lowerBound = protocolVersion.getVersion();
                        } else {
                            if (upperBound != -1) {
                                Via.getPlatform().getLogger().warning("Already set upper bound " + upperBound + " overridden by " + protocolVersion.getName());
                            }
                            upperBound = protocolVersion.getVersion();
                        }
                    }
                } else {
                    ProtocolVersion protocolVersion2 = protocolVersion(s);
                    if (protocolVersion2 != null && !blockedProtocols.add(protocolVersion2.getVersion())) {
                        Via.getPlatform().getLogger().warning("Duplicated blocked protocol version " + protocolVersion2.getName() + "/" + protocolVersion2.getVersion());
                    }
                }
            }
        }
        if (lowerBound != -1 || upperBound != -1) {
            int finalLowerBound = lowerBound;
            int finalUpperBound = upperBound;
            blockedProtocols.removeIf(version -> {
                if ((finalLowerBound != -1 && version < finalLowerBound) || (finalUpperBound != -1 && version > finalUpperBound)) {
                    ProtocolVersion protocolVersion3 = ProtocolVersion.getProtocol(version);
                    Via.getPlatform().getLogger().warning("Blocked protocol version " + protocolVersion3.getName() + "/" + protocolVersion3.getVersion() + " already covered by upper or lower bound");
                    return true;
                }
                return false;
            });
        }
        return new BlockedProtocolVersionsImpl(blockedProtocols, lowerBound, upperBound);
    }

    private ProtocolVersion protocolVersion(String s) {
        ProtocolVersion protocolVersion = ProtocolVersion.getClosest(s);
        if (protocolVersion == null) {
            Via.getPlatform().getLogger().warning("Unknown protocol version in block-versions: " + s);
            return null;
        }
        return protocolVersion;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isCheckForUpdates() {
        return this.checkForUpdates;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public void setCheckForUpdates(boolean checkForUpdates) {
        this.checkForUpdates = checkForUpdates;
        set("checkforupdates", Boolean.valueOf(checkForUpdates));
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isPreventCollision() {
        return this.preventCollision;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isNewEffectIndicator() {
        return this.useNewEffectIndicator;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isShowNewDeathMessages() {
        return this.useNewDeathmessages;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isSuppressMetadataErrors() {
        return this.suppressMetadataErrors;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isShieldBlocking() {
        return this.shieldBlocking;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isNoDelayShieldBlocking() {
        return this.noDelayShieldBlocking;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isShowShieldWhenSwordInHand() {
        return this.showShieldWhenSwordInHand;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isHologramPatch() {
        return this.hologramPatch;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isPistonAnimationPatch() {
        return this.pistonAnimationPatch;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isBossbarPatch() {
        return this.bossbarPatch;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isBossbarAntiflicker() {
        return this.bossbarAntiFlicker;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public double getHologramYOffset() {
        return this.hologramOffset;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public int getMaxPPS() {
        return this.maxPPS;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public String getMaxPPSKickMessage() {
        return this.maxPPSKickMessage;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public int getTrackingPeriod() {
        return this.trackingPeriod;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public int getWarningPPS() {
        return this.warningPPS;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public int getMaxWarnings() {
        return this.maxPPSWarnings;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public String getMaxWarningsKickMessage() {
        return this.maxPPSWarningsKickMessage;
    }

    public boolean isAntiXRay() {
        return false;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isSendSupportedVersions() {
        return this.sendSupportedVersions;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isSimulatePlayerTick() {
        return this.simulatePlayerTick;
    }

    public boolean isItemCache() {
        return this.itemCache;
    }

    public boolean isNMSPlayerTicking() {
        return this.nmsPlayerTicking;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isReplacePistons() {
        return this.replacePistons;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public int getPistonReplacementId() {
        return this.pistonReplacementId;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isChunkBorderFix() {
        return this.chunkBorderFix;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isAutoTeam() {
        return this.preventCollision && this.autoTeam;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isForceJsonTransform() {
        return this.forceJsonTransform;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_12NBTArrayFix() {
        return this.nbtArrayFix;
    }

    public boolean is1_12QuickMoveActionFix() {
        return false;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public BlockedProtocolVersions blockedProtocolVersions() {
        return this.blockedProtocolVersions;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public String getBlockedDisconnectMsg() {
        return this.blockedDisconnectMessage;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public String getReloadDisconnectMsg() {
        return this.reloadDisconnectMessage;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isMinimizeCooldown() {
        return this.minimizeCooldown;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_13TeamColourFix() {
        return this.teamColourFix;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isSuppressConversionWarnings() {
        return this.suppressConversionWarnings;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isDisable1_13AutoComplete() {
        return this.disable1_13TabComplete;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isServersideBlockConnections() {
        return this.serversideBlockConnections;
    }

    public String getBlockConnectionMethod() {
        return "packet";
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isReduceBlockStorageMemory() {
        return this.reduceBlockStorageMemory;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isStemWhenBlockAbove() {
        return this.flowerStemWhenBlockAbove;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isVineClimbFix() {
        return this.vineClimbFix;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isSnowCollisionFix() {
        return this.snowCollisionFix;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isInfestedBlocksFix() {
        return this.infestedBlocksFix;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public int get1_13TabCompleteDelay() {
        return this.tabCompleteDelay;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isTruncate1_14Books() {
        return this.truncate1_14Books;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isLeftHandedHandling() {
        return this.leftHandedHandling;
    }

    public boolean is1_9HitboxFix() {
        return false;
    }

    public boolean is1_14HitboxFix() {
        return false;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isNonFullBlockLightFix() {
        return this.fullBlockLightFix;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_14HealthNaNFix() {
        return this.healthNaNFix;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean is1_15InstantRespawn() {
        return this.instantRespawn;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isIgnoreLong1_16ChannelNames() {
        return this.ignoreLongChannelNames;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean isForcedUse1_17ResourcePack() {
        return this.forcedUse1_17ResourcePack;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public JsonElement get1_17ResourcePackPrompt() {
        return this.resourcePack1_17PromptMessage;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public WorldIdentifiers get1_16WorldNamesMap() {
        return this.map1_16WorldNames;
    }

    @Override // com.viaversion.viaversion.api.configuration.ViaVersionConfig
    public boolean cache1_17Light() {
        return this.cache1_17Light;
    }
}
