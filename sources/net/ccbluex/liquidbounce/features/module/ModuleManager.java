package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aimbot;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiFireBall;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoBow;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoLeave;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoPot;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoSoup;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoWeapon;
import net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot;
import net.ccbluex.liquidbounce.features.module.modules.combat.ComboOneHit;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.FastBow;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitBox;
import net.ccbluex.liquidbounce.features.module.modules.combat.Ignite;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.combat.SuperKnockback;
import net.ccbluex.liquidbounce.features.module.modules.combat.TNTBlock;
import net.ccbluex.liquidbounce.features.module.modules.combat.TeleportAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AntiHunger;
import net.ccbluex.liquidbounce.features.module.modules.exploit.BedGodMode;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Clip;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ConsoleSpammer;
import net.ccbluex.liquidbounce.features.module.modules.exploit.CustomDisabler;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Damage;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Disabler;
import net.ccbluex.liquidbounce.features.module.modules.exploit.FakeLag;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Ghost;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GhostHand;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GodMode;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ItemTeleport;
import net.ccbluex.liquidbounce.features.module.modules.exploit.KeepContainer;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Kick;
import net.ccbluex.liquidbounce.features.module.modules.exploit.MultiActions;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PacketFixer;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Phase;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PingSpoof;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Plugins;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PortalMenu;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ServerCrasher;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Teleport;
import net.ccbluex.liquidbounce.features.module.modules.exploit.VehicleOneHit;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBan;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiDesync;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiExploit;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiVanish;
import net.ccbluex.liquidbounce.features.module.modules.misc.AuthBypass;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoDisable;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoHypixel;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoKit;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoLogin;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoPlay;
import net.ccbluex.liquidbounce.features.module.modules.misc.BanChecker;
import net.ccbluex.liquidbounce.features.module.modules.misc.HoverDetails;
import net.ccbluex.liquidbounce.features.module.modules.misc.MCF;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.misc.NoInvClose;
import net.ccbluex.liquidbounce.features.module.modules.misc.NoRotateSet;
import net.ccbluex.liquidbounce.features.module.modules.misc.PackSpoofer;
import net.ccbluex.liquidbounce.features.module.modules.misc.Patcher;
import net.ccbluex.liquidbounce.features.module.modules.misc.Spammer;
import net.ccbluex.liquidbounce.features.module.modules.misc.SpinBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.features.module.modules.movement.AirJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.AirLadder;
import net.ccbluex.liquidbounce.features.module.modules.movement.AntiFall;
import net.ccbluex.liquidbounce.features.module.modules.movement.AutoWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.BlockWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.BowJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.BufferSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastClimb;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastStairs;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.movement.Freeze;
import net.ccbluex.liquidbounce.features.module.modules.movement.HighJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.IceSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.InvMove;
import net.ccbluex.liquidbounce.features.module.modules.movement.KeepSprint;
import net.ccbluex.liquidbounce.features.module.modules.movement.LadderJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.LongJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoClip;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoJumpDelay;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoWeb;
import net.ccbluex.liquidbounce.features.module.modules.movement.Parkour;
import net.ccbluex.liquidbounce.features.module.modules.movement.PerfectHorseJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.ReverseStep;
import net.ccbluex.liquidbounce.features.module.modules.movement.SafeWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.SlimeJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sneak;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.movement.Step;
import net.ccbluex.liquidbounce.features.module.modules.movement.Strafe;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.features.module.modules.movement.WallClimb;
import net.ccbluex.liquidbounce.features.module.modules.movement.WaterSpeed;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiAFK;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiCactus;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiVoid;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoFish;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoRespawn;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.player.Eagle;
import net.ccbluex.liquidbounce.features.module.modules.player.FastUse;
import net.ccbluex.liquidbounce.features.module.modules.player.Gapple;
import net.ccbluex.liquidbounce.features.module.modules.player.Heal;
import net.ccbluex.liquidbounce.features.module.modules.player.InvManager;
import net.ccbluex.liquidbounce.features.module.modules.player.KeepAlive;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.player.PotionSaver;
import net.ccbluex.liquidbounce.features.module.modules.player.Reach;
import net.ccbluex.liquidbounce.features.module.modules.player.Regen;
import net.ccbluex.liquidbounce.features.module.modules.player.Zoot;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.AsianHat;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockESP;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockOverlay;
import net.ccbluex.liquidbounce.features.module.modules.render.Breadcrumbs;
import net.ccbluex.liquidbounce.features.module.modules.render.CameraClip;
import net.ccbluex.liquidbounce.features.module.modules.render.Cape;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.features.module.modules.render.Crosshair;
import net.ccbluex.liquidbounce.features.module.modules.render.DamageParticle;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP2D;
import net.ccbluex.liquidbounce.features.module.modules.render.EnchantEffect;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.features.module.modules.render.Fullbright;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.render.ItemESP;
import net.ccbluex.liquidbounce.features.module.modules.render.ItemPhysics;
import net.ccbluex.liquidbounce.features.module.modules.render.NameTags;
import net.ccbluex.liquidbounce.features.module.modules.render.NoAchievements;
import net.ccbluex.liquidbounce.features.module.modules.render.NoFOV;
import net.ccbluex.liquidbounce.features.module.modules.render.NoHurtCam;
import net.ccbluex.liquidbounce.features.module.modules.render.NoRender;
import net.ccbluex.liquidbounce.features.module.modules.render.PointerESP;
import net.ccbluex.liquidbounce.features.module.modules.render.PostProcessing;
import net.ccbluex.liquidbounce.features.module.modules.render.Projectiles;
import net.ccbluex.liquidbounce.features.module.modules.render.ProphuntESP;
import net.ccbluex.liquidbounce.features.module.modules.render.Rotations;
import net.ccbluex.liquidbounce.features.module.modules.render.Skeletal;
import net.ccbluex.liquidbounce.features.module.modules.render.StorageESP;
import net.ccbluex.liquidbounce.features.module.modules.render.SuperheroFX;
import net.ccbluex.liquidbounce.features.module.modules.render.TNTESP;
import net.ccbluex.liquidbounce.features.module.modules.render.TargetMark;
import net.ccbluex.liquidbounce.features.module.modules.render.Tracers;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.features.module.modules.world.Ambience;
import net.ccbluex.liquidbounce.features.module.modules.world.AutoBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer;
import net.ccbluex.liquidbounce.features.module.modules.world.CivBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.FastBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.FastPlace;
import net.ccbluex.liquidbounce.features.module.modules.world.Fucker;
import net.ccbluex.liquidbounce.features.module.modules.world.Lightning;
import net.ccbluex.liquidbounce.features.module.modules.world.Liquids;
import net.ccbluex.liquidbounce.features.module.modules.world.NoSlowBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.Nuker;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.world.Timer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

/* compiled from: ModuleManager.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0015\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0006H��¢\u0006\u0002\b!J\u0017\u0010\"\u001a\u0004\u0018\u00010\u00062\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0086\u0002J\u0014\u0010$\u001a\u0004\u0018\u00010\u00062\n\u0010%\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0012\u0010$\u001a\u0004\u0018\u00010\u00062\b\u0010&\u001a\u0004\u0018\u00010'J\b\u0010(\u001a\u00020\rH\u0016J\u0010\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020+H\u0003J\u0018\u0010,\u001a\u00020\u001f2\u000e\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0002J\u000e\u0010,\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0006J\u0006\u0010-\u001a\u00020\u001fJ1\u0010-\u001a\u00020\u001f2\"\u0010\b\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050.\"\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0007¢\u0006\u0002\u0010/J\u000e\u00100\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0006R2\u0010\u0003\u001a&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n��R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t¢\u0006\b\n��\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u00061"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "moduleClassMap", "Ljava/util/HashMap;", Constants.CLASS, "Lnet/ccbluex/liquidbounce/features/module/Module;", "Lkotlin/collections/HashMap;", "modules", "Ljava/util/TreeSet;", "getModules", "()Ljava/util/TreeSet;", "shouldNotify", "", "getShouldNotify", "()Z", "setShouldNotify", "(Z)V", "toggleSoundMode", "", "getToggleSoundMode", "()I", "setToggleSoundMode", "(I)V", "toggleVolume", "", "getToggleVolume", "()F", "setToggleVolume", "(F)V", "generateCommand", "", "module", "generateCommand$LiquidBounce", PropertyDescriptor.GET, "clazz", "getModule", "moduleClass", "moduleName", "", "handleEvents", "onKey", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "registerModule", "registerModules", "", "([Ljava/lang/Class;)V", "unregisterModule", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/ModuleManager.class */
public final class ModuleManager implements Listenable {
    @NotNull
    private final TreeSet<Module> modules = new TreeSet<>(ModuleManager::m2766modules$lambda0);
    @NotNull
    private final HashMap<Class<?>, Module> moduleClassMap = new HashMap<>();
    private boolean shouldNotify;
    private int toggleSoundMode;
    private float toggleVolume;

    public ModuleManager() {
        LiquidBounce.INSTANCE.getEventManager().registerListener(this);
    }

    @NotNull
    public final TreeSet<Module> getModules() {
        return this.modules;
    }

    /* renamed from: modules$lambda-0 */
    private static final int m2766modules$lambda0(Module module1, Module module2) {
        return module1.getName().compareTo(module2.getName());
    }

    public final boolean getShouldNotify() {
        return this.shouldNotify;
    }

    public final void setShouldNotify(boolean z) {
        this.shouldNotify = z;
    }

    public final int getToggleSoundMode() {
        return this.toggleSoundMode;
    }

    public final void setToggleSoundMode(int i) {
        this.toggleSoundMode = i;
    }

    public final float getToggleVolume() {
        return this.toggleVolume;
    }

    public final void setToggleVolume(float f) {
        this.toggleVolume = f;
    }

    public final void registerModules() {
        ClientUtils.getLogger().info("[ModuleManager] Loading modules...");
        registerModules(AntiFall.class, AntiExploit.class, Patcher.class, AutoWeapon.class, BowAimbot.class, Aimbot.class, AutoBow.class, AutoSoup.class, FastBow.class, Criticals.class, KillAura.class, Velocity.class, Fly.class, ClickGUI.class, HighJump.class, InvMove.class, NoSlow.class, LiquidWalk.class, Strafe.class, Sprint.class, Teams.class, NoRotateSet.class, AntiBot.class, ChestStealer.class, Scaffold.class, FastBreak.class, FastPlace.class, ESP.class, Sneak.class, Speed.class, Tracers.class, NameTags.class, FastUse.class, Fullbright.class, ItemESP.class, StorageESP.class, Projectiles.class, PingSpoof.class, Step.class, AutoRespawn.class, AutoTool.class, NoWeb.class, Spammer.class, Regen.class, NoFall.class, Blink.class, NameProtect.class, NoHurtCam.class, MCF.class, XRay.class, Timer.class, FreeCam.class, HitBox.class, Plugins.class, LongJump.class, AutoClicker.class, BlockOverlay.class, NoFriends.class, BlockESP.class, Chams.class, Clip.class, Phase.class, ServerCrasher.class, NoFOV.class, Animations.class, ReverseStep.class, TNTBlock.class, InvManager.class, TrueSight.class, AntiBlind.class, Breadcrumbs.class, AbortBreaking.class, PotionSaver.class, CameraClip.class, WaterSpeed.class, SuperKnockback.class, Reach.class, Rotations.class, NoJumpDelay.class, HUD.class, TNTESP.class, PackSpoofer.class, NoSlowBreak.class, PortalMenu.class, Ambience.class, EnchantEffect.class, Cape.class, NoRender.class, DamageParticle.class, AntiVanish.class, Lightning.class, Skeletal.class, ItemPhysics.class, AutoLogin.class, Heal.class, AuthBypass.class, Gapple.class, ColorMixer.class, Disabler.class, CustomDisabler.class, AutoDisable.class, Crosshair.class, VehicleOneHit.class, SpinBot.class, MultiActions.class, AntiVoid.class, AutoHypixel.class, TargetStrafe.class, ESP2D.class, BanChecker.class, TargetMark.class, AntiFireBall.class, KeepSprint.class, ItemTeleport.class, Teleport.class, AsianHat.class, BowJump.class, ConsoleSpammer.class, PointerESP.class, SafeWalk.class, NoAchievements.class, GhostHand.class, AntiHunger.class, AirJump.class, Freeze.class, AntiCactus.class, Eagle.class, FastClimb.class, FastStairs.class, SlimeJump.class, Parkour.class, WallClimb.class, AntiDesync.class, FakeLag.class, PacketFixer.class, AutoPlay.class, AutoKit.class, AntiBan.class, NoInvClose.class, TeleportAura.class, AutoPot.class, Ignite.class, AntiAFK.class, AutoFish.class, ComboOneHit.class, AutoLeave.class, BedGodMode.class, Damage.class, Ghost.class, GodMode.class, KeepContainer.class, Kick.class, AirLadder.class, AutoWalk.class, BlockWalk.class, BufferSpeed.class, IceSpeed.class, LadderJump.class, NoClip.class, PerfectHorseJump.class, KeepAlive.class, Zoot.class, ProphuntESP.class, Liquids.class, HoverDetails.class, AutoBreak.class, CivBreak.class, Nuker.class, SuperheroFX.class, PostProcessing.class);
        registerModule(Fucker.INSTANCE);
        registerModule(ChestAura.INSTANCE);
        ClientUtils.getLogger().info("[ModuleManager] Successfully loaded " + this.modules.size() + " modules.");
    }

    public final void registerModule(@NotNull Module module) {
        Intrinsics.checkNotNullParameter(module, "module");
        this.modules.add(module);
        this.moduleClassMap.put(module.getClass(), module);
        module.onInitialize();
        generateCommand$LiquidBounce(module);
        LiquidBounce.INSTANCE.getEventManager().registerListener(module);
    }

    private final void registerModule(Class<? extends Module> cls) {
        try {
            Module newInstance = cls.newInstance();
            Intrinsics.checkNotNullExpressionValue(newInstance, "moduleClass.newInstance()");
            registerModule(newInstance);
        } catch (Throwable e) {
            ClientUtils.getLogger().error("Failed to load module: " + ((Object) cls.getName()) + " (" + ((Object) e.getClass().getName()) + ": " + ((Object) e.getMessage()) + ')');
        }
    }

    @SafeVarargs
    public final void registerModules(@NotNull Class<? extends Module>... modules) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        int i = 0;
        int length = modules.length;
        while (i < length) {
            i++;
            registerModule(modules[i]);
        }
    }

    public final void unregisterModule(@NotNull Module module) {
        Intrinsics.checkNotNullParameter(module, "module");
        this.modules.remove(module);
        this.moduleClassMap.remove(module.getClass());
        LiquidBounce.INSTANCE.getEventManager().unregisterListener(module);
    }

    public final void generateCommand$LiquidBounce(@NotNull Module module) {
        Intrinsics.checkNotNullParameter(module, "module");
        List values = module.getValues();
        if (values.isEmpty()) {
            return;
        }
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(new ModuleCommand(module, values));
    }

    @Nullable
    public final Module getModule(@NotNull Class<?> moduleClass) {
        Intrinsics.checkNotNullParameter(moduleClass, "moduleClass");
        return this.moduleClassMap.get(moduleClass);
    }

    @Nullable
    public final Module get(@NotNull Class<?> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return getModule(clazz);
    }

    @Nullable
    public final Module getModule(@Nullable String moduleName) {
        Object obj;
        Iterator<T> it = this.modules.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            Module it2 = (Module) next;
            if (StringsKt.equals(it2.getName(), moduleName, true)) {
                obj = next;
                break;
            }
        }
        return (Module) obj;
    }

    @EventTarget
    private final void onKey(KeyEvent event) {
        Iterable $this$filter$iv = this.modules;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Module it = (Module) element$iv$iv;
            if (it.getKeyBind() == event.getKey()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$forEach$iv = (List) destination$iv$iv;
        for (Object element$iv : $this$forEach$iv) {
            Module it2 = (Module) element$iv;
            it2.toggle();
        }
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return true;
    }
}
