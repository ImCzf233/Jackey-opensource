package net.ccbluex.liquidbounce.features.module.modules.combat;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import de.enzaxd.viaforge.ViaForge;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.progressionUtil;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EntityMovementEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Disabler;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtension;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: KillAura.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0007\n\u0002\b\u0011\n\u0002\u0010!\n\u0002\b \n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0083\u0001\u001a\u00030\u0084\u00012\u0007\u0010\u0085\u0001\u001a\u00020$H\u0002J\u0013\u0010\u0086\u0001\u001a\u00020C2\b\u0010\u0085\u0001\u001a\u00030\u0087\u0001H\u0002J\u0016\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0089\u00012\b\u0010\u0085\u0001\u001a\u00030\u0087\u0001H\u0002J\u0012\u0010\u008a\u0001\u001a\u00020\u00132\u0007\u0010\u0085\u0001\u001a\u00020$H\u0002J\u0013\u0010\u008b\u0001\u001a\u00020\u00132\n\u0010\u0085\u0001\u001a\u0005\u0018\u00010\u0087\u0001J\n\u0010\u008c\u0001\u001a\u00030\u0084\u0001H\u0016J\n\u0010\u008d\u0001\u001a\u00030\u0084\u0001H\u0016J\u0014\u0010\u008e\u0001\u001a\u00030\u0084\u00012\b\u0010\u008f\u0001\u001a\u00030\u0090\u0001H\u0007J\u0014\u0010\u0091\u0001\u001a\u00030\u0084\u00012\b\u0010\u008f\u0001\u001a\u00030\u0092\u0001H\u0007J\u0014\u0010\u0093\u0001\u001a\u00030\u0084\u00012\b\u0010\u008f\u0001\u001a\u00030\u0094\u0001H\u0007J\u0014\u0010\u0095\u0001\u001a\u00030\u0084\u00012\b\u0010\u008f\u0001\u001a\u00030\u0096\u0001H\u0007J\u0014\u0010\u0097\u0001\u001a\u00030\u0084\u00012\b\u0010\u008f\u0001\u001a\u00030\u0098\u0001H\u0007J\u0014\u0010\u0099\u0001\u001a\u00030\u0084\u00012\b\u0010\u008f\u0001\u001a\u00030\u009a\u0001H\u0007J\n\u0010\u009b\u0001\u001a\u00030\u0084\u0001H\u0002J\u001d\u0010\u009c\u0001\u001a\u00030\u0084\u00012\b\u0010\u009d\u0001\u001a\u00030\u0087\u00012\u0007\u0010\u009e\u0001\u001a\u00020\u0013H\u0002J\n\u0010\u009f\u0001\u001a\u00030\u0084\u0001H\u0002J\b\u0010 \u0001\u001a\u00030\u0084\u0001J\n\u0010¡\u0001\u001a\u00030\u0084\u0001H\u0002J\n\u0010¢\u0001\u001a\u00030\u0084\u0001H\u0002J\u0013\u0010£\u0001\u001a\u00020\u00132\b\u0010\u0085\u0001\u001a\u00030\u0087\u0001H\u0002J\n\u0010¤\u0001\u001a\u00030\u0084\u0001H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010\u0019\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0015R\u0014\u0010\u001b\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0015R\u0014\u0010\u001d\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0015R\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\"\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n��R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u000e\u0010)\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010*\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010+\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010-\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b.\u0010\u0015\"\u0004\b/\u0010\u0017R\u000e\u00100\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00101\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00102\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00103\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u001a\u00104\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b5\u0010\u0015\"\u0004\b6\u0010\u0017R\u000e\u00107\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00108\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u00109\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010:\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010;\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010<\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0010\u0010=\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010>\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010?\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010@\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010A\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010B\u001a\u00020C8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u000e\u0010F\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010G\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010H\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010I\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010J\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010K\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010L\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010M\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010N\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010O\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010P\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010Q\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010R\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010S\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u0014\u0010T\u001a\b\u0012\u0004\u0012\u00020!0UX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010V\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010W\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010X\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010Y\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010Z\u001a\u00020,¢\u0006\b\n��\u001a\u0004\b[\u0010\\R\u000e\u0010]\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010^\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010_\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u0011\u0010`\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\ba\u0010bR\u000e\u0010c\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010d\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010g\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010h\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010i\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010j\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\bk\u0010\u0015\"\u0004\bl\u0010\u0017R\u000e\u0010m\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u001a\u0010n\u001a\u00020CX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\bo\u0010E\"\u0004\bp\u0010qR\u000e\u0010r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010s\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n��R\u0016\u0010u\u001a\u0004\u0018\u00010v8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bw\u0010xR\u001c\u0010y\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\bz\u0010&\"\u0004\b{\u0010(R\u0011\u0010|\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\b}\u0010bR\u000e\u0010~\u001a\u00020,X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u007f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n��R\u001d\u0010\u0080\u0001\u001a\u00020\u0013X\u0086\u000e¢\u0006\u0010\n��\u001a\u0005\b\u0081\u0001\u0010\u0015\"\u0005\b\u0082\u0001\u0010\u0017¨\u0006¥\u0001"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "abThruWallValue", "accuracyValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "afterTickPatchValue", "alpha", "attackDelay", "", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoBlockModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "blinkCheck", "blockRate", "blockingStatus", "", "getBlockingStatus", "()Z", "setBlockingStatus", "(Z)V", "blue", "canBlock", "getCanBlock", "canSmartBlock", "getCanSmartBlock", "cancelRun", "getCancelRun", "circleValue", "clicks", "", "containerOpen", "currentTarget", "Lnet/minecraft/entity/EntityLivingBase;", "getCurrentTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setCurrentTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "debugValue", "displayAutoBlockSettings", "failRateValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "fakeBlock", "getFakeBlock", "setFakeBlock", "fakeSharpValue", "fakeSwingValue", "fovValue", "green", "hitable", "getHitable", "setHitable", "hurtTimeValue", "interactAutoBlockValue", "keepSprintValue", "lastHitTick", "limitedMultiTargetsValue", "livingRaycastValue", "markEntity", "markTimer", "maxCPS", "maxPredictSize", "maxRand", "maxRange", "", "getMaxRange", "()F", "maxSpinSpeed", "maxTurnSpeed", "minCPS", "minPredictSize", "minRand", "minSpinSpeed", "minTurnSpeed", "noHitCheck", "noInventoryAttackValue", "noInventoryDelayValue", "noScaffValue", "noSendRot", "outborderValue", "predictValue", "prevTargetEntities", "", "priorityValue", "randomCenterNewValue", "randomCenterValue", "rangeSprintReducementValue", "rangeValue", "getRangeValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "raycastIgnoredValue", "raycastValue", "red", "rotationStrafeValue", "getRotationStrafeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "rotations", "silentRotationValue", "smartABFacingValue", "smartABItemValue", "smartABRangeValue", "smartABTolerationValue", "smartAutoBlockValue", "smartBlocking", "getSmartBlocking", "setSmartBlocking", "spinHurtTimeValue", "spinYaw", "getSpinYaw", "setSpinYaw", "(F)V", "swingOrderValue", "swingValue", "switchDelayValue", "tag", "", "getTag", "()Ljava/lang/String;", "target", "getTarget", "setTarget", "targetModeValue", "getTargetModeValue", "throughWallsRangeValue", "verusAutoBlockValue", "verusBlocking", "getVerusBlocking", "setVerusBlocking", "attackEntity", "", "entity", "getRange", "Lnet/minecraft/entity/Entity;", "getTargetRotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "isAlive", "isEnemy", "onDisable", "onEnable", "onEntityMove", "event", "Lnet/ccbluex/liquidbounce/event/EntityMovementEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runAttack", "startBlocking", "interactEntity", "interact", "stopBlocking", "update", "updateHitable", "updateKA", "updateRotations", "updateTarget", "LiquidBounce"})
@ModuleInfo(name = "KillAura", spacedName = "Kill Aura", description = "Automatically attacks targets around you.", category = ModuleCategory.COMBAT, keyBind = 19)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/combat/KillAura.class */
public final class KillAura extends Module {
    @Nullable
    private EntityLivingBase target;
    @Nullable
    private EntityLivingBase currentTarget;
    private boolean hitable;
    @Nullable
    private EntityLivingBase markEntity;
    private long attackDelay;
    private int clicks;
    private int lastHitTick;
    private boolean blockingStatus;
    private boolean verusBlocking;
    private boolean fakeBlock;
    private boolean smartBlocking;
    private float spinYaw;
    @NotNull
    private final IntegerValue maxCPS = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$maxCPS$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("MaxCPS", 8, 1, 20);
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            IntegerValue integerValue2;
            integerValue = KillAura.this.minCPS;
            int i = integerValue.get().intValue();
            if (i > newValue) {
                set((KillAura$maxCPS$1) Integer.valueOf(i));
            }
            KillAura killAura = KillAura.this;
            integerValue2 = KillAura.this.minCPS;
            killAura.attackDelay = TimeUtils.randomClickDelay(integerValue2.get().intValue(), get().intValue());
        }
    };
    @NotNull
    private final IntegerValue minCPS = new IntegerValue() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$minCPS$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("MinCPS", 5, 1, 20);
        }

        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Integer num, Integer num2) {
            onChanged(num.intValue(), num2.intValue());
        }

        protected void onChanged(int oldValue, int newValue) {
            IntegerValue integerValue;
            IntegerValue integerValue2;
            integerValue = KillAura.this.maxCPS;
            int i = integerValue.get().intValue();
            if (i < newValue) {
                set((KillAura$minCPS$1) Integer.valueOf(i));
            }
            KillAura killAura = KillAura.this;
            int intValue = get().intValue();
            integerValue2 = KillAura.this.maxCPS;
            killAura.attackDelay = TimeUtils.randomClickDelay(intValue, integerValue2.get().intValue());
        }
    };
    @NotNull
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    @NotNull
    private final FloatValue rangeValue = new FloatValue("Range", 3.7f, 1.0f, 8.0f, "m");
    @NotNull
    private final FloatValue throughWallsRangeValue = new FloatValue("ThroughWallsRange", 3.0f, 0.0f, 8.0f, "m");
    @NotNull
    private final FloatValue rangeSprintReducementValue = new FloatValue("RangeSprintReducement", 0.0f, 0.0f, 0.4f, "m");
    @NotNull
    private final ListValue rotations = new ListValue("RotationMode", new String[]{"Vanilla", "BackTrack", "Spin", "None"}, "BackTrack");
    @NotNull
    private final IntegerValue spinHurtTimeValue = new IntegerValue("Spin-HitHurtTime", 10, 0, 10, new KillAura$spinHurtTimeValue$1(this));
    @NotNull
    private final FloatValue maxSpinSpeed = new FloatValue(new KillAura$maxSpinSpeed$2(this)) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$maxSpinSpeed$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            FloatValue floatValue;
            floatValue = KillAura.this.minSpinSpeed;
            float v = floatValue.get().floatValue();
            if (v > newValue) {
                set((KillAura$maxSpinSpeed$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final FloatValue minSpinSpeed = new FloatValue(new KillAura$minSpinSpeed$2(this)) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$minSpinSpeed$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            FloatValue floatValue;
            floatValue = KillAura.this.maxSpinSpeed;
            float v = floatValue.get().floatValue();
            if (v < newValue) {
                set((KillAura$minSpinSpeed$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final FloatValue maxTurnSpeed = new FloatValue(new KillAura$maxTurnSpeed$2(this)) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$maxTurnSpeed$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            FloatValue floatValue;
            floatValue = KillAura.this.minTurnSpeed;
            float v = floatValue.get().floatValue();
            if (v > newValue) {
                set((KillAura$maxTurnSpeed$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final FloatValue minTurnSpeed = new FloatValue(new KillAura$minTurnSpeed$2(this)) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$minTurnSpeed$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            FloatValue floatValue;
            floatValue = KillAura.this.maxTurnSpeed;
            float v = floatValue.get().floatValue();
            if (v < newValue) {
                set((KillAura$minTurnSpeed$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final BoolValue noSendRot = new BoolValue("NoSendRotation", true, new KillAura$noSendRot$1(this));
    @NotNull
    private final BoolValue noHitCheck = new BoolValue("NoHitCheck", false, new KillAura$noHitCheck$1(this));
    @NotNull
    private final BoolValue blinkCheck = new BoolValue("BlinkCheck", true);
    @NotNull
    private final ListValue priorityValue = new ListValue("Priority", new String[]{"Health", "Distance", "Direction", "LivingTime", "Armor", "HurtResistance", "HurtTime", "HealthAbsorption", "RegenAmplifier"}, "Distance");
    @NotNull
    private final ListValue targetModeValue = new ListValue("TargetMode", new String[]{"Single", "Switch", "Multi"}, "Switch");
    @NotNull
    private final IntegerValue switchDelayValue = new IntegerValue("SwitchDelay", 1000, 1, 2000, "ms", new KillAura$switchDelayValue$1(this));
    @NotNull
    private final BoolValue swingValue = new BoolValue("Swing", true);
    @NotNull
    private final BoolValue swingOrderValue = new BoolValue("1.9OrderCheck", true, new KillAura$swingOrderValue$1(this));
    @NotNull
    private final BoolValue keepSprintValue = new BoolValue("KeepSprint", true);
    @NotNull
    private final ListValue autoBlockModeValue = new ListValue("AutoBlock", new String[]{"None", "Packet", "AfterTick", "NCP", "Vulcan"}, "None");
    @NotNull
    private final BoolValue displayAutoBlockSettings = new BoolValue("Open-AutoBlock-Settings", false, new KillAura$displayAutoBlockSettings$1(this));
    @NotNull
    private final BoolValue interactAutoBlockValue = new BoolValue("InteractAutoBlock", true, new KillAura$interactAutoBlockValue$1(this));
    @NotNull
    private final BoolValue verusAutoBlockValue = new BoolValue("VerusAutoBlock", false, new KillAura$verusAutoBlockValue$1(this));
    @NotNull
    private final BoolValue abThruWallValue = new BoolValue("AutoBlockThroughWalls", false, new KillAura$abThruWallValue$1(this));
    @NotNull
    private final BoolValue smartAutoBlockValue = new BoolValue("SmartAutoBlock", false, new KillAura$smartAutoBlockValue$1(this));
    @NotNull
    private final BoolValue smartABItemValue = new BoolValue("SmartAutoBlock-ItemCheck", true, new KillAura$smartABItemValue$1(this));
    @NotNull
    private final BoolValue smartABFacingValue = new BoolValue("SmartAutoBlock-FacingCheck", true, new KillAura$smartABFacingValue$1(this));
    @NotNull
    private final FloatValue smartABRangeValue = new FloatValue("SmartAB-Range", 3.5f, 3.0f, 8.0f, "m", new KillAura$smartABRangeValue$1(this));
    @NotNull
    private final FloatValue smartABTolerationValue = new FloatValue("SmartAB-Toleration", 0.0f, 0.0f, 2.0f, new KillAura$smartABTolerationValue$1(this));
    @NotNull
    private final BoolValue afterTickPatchValue = new BoolValue("AfterTickPatch", true, new KillAura$afterTickPatchValue$1(this));
    @NotNull
    private final IntegerValue blockRate = new IntegerValue("BlockRate", 100, 1, 100, "%", new KillAura$blockRate$1(this));
    @NotNull
    private final BoolValue raycastValue = new BoolValue("RayCast", true);
    @NotNull
    private final BoolValue raycastIgnoredValue = new BoolValue("RayCastIgnored", false);
    @NotNull
    private final BoolValue livingRaycastValue = new BoolValue("LivingRayCast", true);
    @NotNull
    private final BoolValue aacValue = new BoolValue("AAC", false);
    @NotNull
    private final BoolValue silentRotationValue = new BoolValue("SilentRotation", true, new KillAura$silentRotationValue$1(this));
    @NotNull
    private final ListValue rotationStrafeValue = new ListValue("Strafe", new String[]{"Off", "Strict", "Silent"}, "Off");
    @NotNull
    private final FloatValue fovValue = new FloatValue("FOV", 180.0f, 0.0f, 180.0f);
    @NotNull
    private final BoolValue predictValue = new BoolValue("Predict", true);
    @NotNull
    private final FloatValue maxPredictSize = new FloatValue(new KillAura$maxPredictSize$2(this)) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$maxPredictSize$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            FloatValue floatValue;
            floatValue = KillAura.this.minPredictSize;
            float v = floatValue.get().floatValue();
            if (v > newValue) {
                set((KillAura$maxPredictSize$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final FloatValue minPredictSize = new FloatValue(new KillAura$minPredictSize$2(this)) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$minPredictSize$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            FloatValue floatValue;
            floatValue = KillAura.this.maxPredictSize;
            float v = floatValue.get().floatValue();
            if (v < newValue) {
                set((KillAura$minPredictSize$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final BoolValue randomCenterValue = new BoolValue("RandomCenter", false, new KillAura$randomCenterValue$1(this));
    @NotNull
    private final BoolValue randomCenterNewValue = new BoolValue("NewCalc", true, new KillAura$randomCenterNewValue$1(this));
    @NotNull
    private final FloatValue minRand = new FloatValue(new KillAura$minRand$2(this)) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$minRand$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            FloatValue floatValue;
            floatValue = KillAura.this.maxRand;
            float v = floatValue.get().floatValue();
            if (v < newValue) {
                set((KillAura$minRand$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final FloatValue maxRand = new FloatValue(new KillAura$maxRand$2(this)) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$maxRand$1
        @Override // net.ccbluex.liquidbounce.value.Value
        public /* bridge */ /* synthetic */ void onChanged(Float f, Float f2) {
            onChanged(f.floatValue(), f2.floatValue());
        }

        protected void onChanged(float oldValue, float newValue) {
            FloatValue floatValue;
            floatValue = KillAura.this.minRand;
            float v = floatValue.get().floatValue();
            if (v > newValue) {
                set((KillAura$maxRand$1) Float.valueOf(v));
            }
        }
    };
    @NotNull
    private final BoolValue outborderValue = new BoolValue("Outborder", false);
    @NotNull
    private final FloatValue failRateValue = new FloatValue("FailRate", 0.0f, 0.0f, 100.0f);
    @NotNull
    private final BoolValue fakeSwingValue = new BoolValue("FakeSwing", true);
    @NotNull
    private final BoolValue noInventoryAttackValue = new BoolValue("NoInvAttack", false);
    @NotNull
    private final IntegerValue noInventoryDelayValue = new IntegerValue("NoInvDelay", 200, 0, 500, "ms", new KillAura$noInventoryDelayValue$1(this));
    @NotNull
    private final IntegerValue limitedMultiTargetsValue = new IntegerValue("LimitedMultiTargets", 0, 0, 50, new KillAura$limitedMultiTargetsValue$1(this));
    @NotNull
    private final BoolValue noScaffValue = new BoolValue("NoScaffold", true);
    @NotNull
    private final BoolValue debugValue = new BoolValue("Debug", false);
    @NotNull
    private final BoolValue circleValue = new BoolValue("Circle", true);
    @NotNull
    private final IntegerValue accuracyValue = new IntegerValue("Accuracy", 59, 0, 59, new KillAura$accuracyValue$1(this));
    @NotNull
    private final BoolValue fakeSharpValue = new BoolValue("FakeSharp", true);
    @NotNull
    private final IntegerValue red = new IntegerValue("Red", 0, 0, 255, new KillAura$red$1(this));
    @NotNull
    private final IntegerValue green = new IntegerValue("Green", 0, 0, 255, new KillAura$green$1(this));
    @NotNull
    private final IntegerValue blue = new IntegerValue("Blue", 0, 0, 255, new KillAura$blue$1(this));
    @NotNull
    private final IntegerValue alpha = new IntegerValue("Alpha", 0, 0, 255, new KillAura$alpha$1(this));
    @NotNull
    private final List<Integer> prevTargetEntities = new ArrayList();
    @NotNull
    private final MSTimer markTimer = new MSTimer();
    @NotNull
    private final MSTimer attackTimer = new MSTimer();
    private long containerOpen = -1;

    @NotNull
    public final FloatValue getRangeValue() {
        return this.rangeValue;
    }

    @NotNull
    public final ListValue getTargetModeValue() {
        return this.targetModeValue;
    }

    @NotNull
    public final ListValue getRotationStrafeValue() {
        return this.rotationStrafeValue;
    }

    @Nullable
    public final EntityLivingBase getTarget() {
        return this.target;
    }

    public final void setTarget(@Nullable EntityLivingBase entityLivingBase) {
        this.target = entityLivingBase;
    }

    @Nullable
    public final EntityLivingBase getCurrentTarget() {
        return this.currentTarget;
    }

    public final void setCurrentTarget(@Nullable EntityLivingBase entityLivingBase) {
        this.currentTarget = entityLivingBase;
    }

    public final boolean getHitable() {
        return this.hitable;
    }

    public final void setHitable(boolean z) {
        this.hitable = z;
    }

    public final boolean getBlockingStatus() {
        return this.blockingStatus;
    }

    public final void setBlockingStatus(boolean z) {
        this.blockingStatus = z;
    }

    public final boolean getVerusBlocking() {
        return this.verusBlocking;
    }

    public final void setVerusBlocking(boolean z) {
        this.verusBlocking = z;
    }

    public final boolean getFakeBlock() {
        return this.fakeBlock;
    }

    public final void setFakeBlock(boolean z) {
        this.fakeBlock = z;
    }

    public final boolean getSmartBlocking() {
        return this.smartBlocking;
    }

    public final void setSmartBlocking(boolean z) {
        this.smartBlocking = z;
    }

    private final boolean getCanSmartBlock() {
        return !this.smartAutoBlockValue.get().booleanValue() || this.smartBlocking;
    }

    public final float getSpinYaw() {
        return this.spinYaw;
    }

    public final void setSpinYaw(float f) {
        this.spinYaw = f;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        if (MinecraftInstance.f362mc.field_71439_g == null || MinecraftInstance.f362mc.field_71441_e == null) {
            return;
        }
        updateTarget();
        this.verusBlocking = false;
        this.smartBlocking = false;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onDisable() {
        this.target = null;
        this.currentTarget = null;
        this.hitable = false;
        this.prevTargetEntities.clear();
        this.attackTimer.reset();
        this.clicks = 0;
        stopBlocking();
        if (this.verusBlocking && !this.blockingStatus && !MinecraftInstance.f362mc.field_71439_g.func_70632_aY()) {
            this.verusBlocking = false;
            if (this.verusAutoBlockValue.get().booleanValue()) {
                PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
            }
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() == EventState.POST) {
            if (this.target == null || this.currentTarget == null) {
                return;
            }
            updateHitable();
            if (StringsKt.equals(this.autoBlockModeValue.get(), "AfterTick", true) && getCanBlock()) {
                EntityLivingBase entityLivingBase = this.currentTarget;
                Intrinsics.checkNotNull(entityLivingBase);
                startBlocking((Entity) entityLivingBase, this.hitable);
            }
        }
        if (StringsKt.equals(this.rotationStrafeValue.get(), "Off", true)) {
            update();
        }
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(TargetStrafe.class);
        Intrinsics.checkNotNull(module);
        TargetStrafe targetStrafe = (TargetStrafe) module;
        if (StringsKt.equals(this.rotationStrafeValue.get(), "Off", true) && !targetStrafe.getState()) {
            return;
        }
        update();
        if (this.currentTarget != null && RotationUtils.targetRotation != null) {
            if (targetStrafe.getCanStrafe()) {
                Float[] strafingData = targetStrafe.getData();
                MovementUtils.strafeCustom(MovementUtils.getSpeed(), strafingData[0].floatValue(), strafingData[1].floatValue(), strafingData[2].floatValue());
                event.cancelEvent();
                return;
            }
            String lowerCase = this.rotationStrafeValue.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            if (Intrinsics.areEqual(lowerCase, "strict")) {
                Rotation rotation = RotationUtils.targetRotation;
                if (rotation == null) {
                    return;
                }
                float yaw = rotation.component1();
                float strafe = event.getStrafe();
                float forward = event.getForward();
                float friction = event.getFriction();
                float f = (strafe * strafe) + (forward * forward);
                if (f >= 1.0E-4f) {
                    float f2 = MathHelper.func_76129_c(f);
                    if (f2 < 1.0f) {
                        f2 = 1.0f;
                    }
                    float f3 = friction / f2;
                    float strafe2 = strafe * f3;
                    float forward2 = forward * f3;
                    float yawSin = MathHelper.func_76126_a((float) ((yaw * 3.141592653589793d) / 180.0f));
                    float yawCos = MathHelper.func_76134_b((float) ((yaw * 3.141592653589793d) / 180.0f));
                    MinecraftInstance.f362mc.field_71439_g.field_70159_w += (strafe2 * yawCos) - (forward2 * yawSin);
                    MinecraftInstance.f362mc.field_71439_g.field_70179_y += (forward2 * yawCos) + (strafe2 * yawSin);
                }
                event.cancelEvent();
            } else if (Intrinsics.areEqual(lowerCase, "silent")) {
                update();
                RotationUtils.targetRotation.applyStrafeToPlayer(event);
                event.cancelEvent();
            }
        }
    }

    public final void update() {
        if (!getCancelRun()) {
            if (this.noInventoryAttackValue.get().booleanValue() && ((MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer) || System.currentTimeMillis() - this.containerOpen < this.noInventoryDelayValue.get().intValue())) {
                return;
            }
            updateTarget();
            if (this.target == null) {
                stopBlocking();
                return;
            }
            this.currentTarget = this.target;
            if (!StringsKt.equals(this.targetModeValue.get(), "Switch", true) && isEnemy((Entity) this.currentTarget)) {
                this.target = this.currentTarget;
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        C07PacketPlayerDigging packet = event.getPacket();
        if (this.verusBlocking && ((((packet instanceof C07PacketPlayerDigging) && packet.func_180762_c() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) || (packet instanceof C08PacketPlayerBlockPlacement)) && this.verusAutoBlockValue.get().booleanValue())) {
            event.cancelEvent();
        }
        if (packet instanceof C09PacketHeldItemChange) {
            this.verusBlocking = false;
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        updateKA();
        this.smartBlocking = false;
        if (this.smartAutoBlockValue.get().booleanValue() && this.target != null) {
            Entity entity = this.target;
            Intrinsics.checkNotNull(entity);
            if (!this.smartABItemValue.get().booleanValue() || (entity.func_70694_bm() != null && entity.func_70694_bm().func_77973_b() != null && ((entity.func_70694_bm().func_77973_b() instanceof ItemSword) || (entity.func_70694_bm().func_77973_b() instanceof ItemAxe)))) {
                Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
                if (PlayerExtension.getDistanceToEntityBox(entity2, entity) < this.smartABRangeValue.get().floatValue()) {
                    if (this.smartABFacingValue.get().booleanValue()) {
                        if (entity.func_174822_a(this.smartABRangeValue.get().floatValue(), 1.0f).field_72313_a == MovingObjectPosition.MovingObjectType.MISS) {
                            Vec3 eyesVec = entity.func_174824_e(1.0f);
                            Vec3 lookVec = entity.func_70676_i(1.0f);
                            Vec3 pointingVec = eyesVec.func_72441_c(lookVec.field_72450_a * this.smartABRangeValue.get().doubleValue(), lookVec.field_72448_b * this.smartABRangeValue.get().doubleValue(), lookVec.field_72449_c * this.smartABRangeValue.get().doubleValue());
                            float border = MinecraftInstance.f362mc.field_71439_g.func_70111_Y() + this.smartABTolerationValue.get().floatValue();
                            AxisAlignedBB bb = MinecraftInstance.f362mc.field_71439_g.func_174813_aQ().func_72314_b(border, border, border);
                            this.smartBlocking = bb.func_72327_a(eyesVec, pointingVec) != null || bb.func_72326_a(entity.func_174813_aQ());
                        }
                    } else {
                        this.smartBlocking = true;
                    }
                }
            }
        }
        if (this.blockingStatus || MinecraftInstance.f362mc.field_71439_g.func_70632_aY()) {
            this.verusBlocking = true;
        } else if (this.verusBlocking) {
            this.verusBlocking = false;
            if (this.verusAutoBlockValue.get().booleanValue()) {
                PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
            }
        }
    }

    private final void updateKA() {
        if (getCancelRun()) {
            this.target = null;
            this.currentTarget = null;
            this.hitable = false;
            stopBlocking();
        } else if (this.noInventoryAttackValue.get().booleanValue() && ((MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer) || System.currentTimeMillis() - this.containerOpen < this.noInventoryDelayValue.get().intValue())) {
            this.target = null;
            this.currentTarget = null;
            this.hitable = false;
            if (MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer) {
                this.containerOpen = System.currentTimeMillis();
            }
        } else if (this.target != null && this.currentTarget != null) {
            while (this.clicks > 0) {
                runAttack();
                this.clicks--;
            }
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        int i;
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.circleValue.get().booleanValue()) {
            GL11.glPushMatrix();
            GL11.glTranslated((MinecraftInstance.f362mc.field_71439_g.field_70142_S + ((MinecraftInstance.f362mc.field_71439_g.field_70165_t - MinecraftInstance.f362mc.field_71439_g.field_70142_S) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - MinecraftInstance.f362mc.func_175598_ae().field_78725_b, (MinecraftInstance.f362mc.field_71439_g.field_70137_T + ((MinecraftInstance.f362mc.field_71439_g.field_70163_u - MinecraftInstance.f362mc.field_71439_g.field_70137_T) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - MinecraftInstance.f362mc.func_175598_ae().field_78726_c, (MinecraftInstance.f362mc.field_71439_g.field_70136_U + ((MinecraftInstance.f362mc.field_71439_g.field_70161_v - MinecraftInstance.f362mc.field_71439_g.field_70136_U) * MinecraftInstance.f362mc.field_71428_T.field_74281_c)) - MinecraftInstance.f362mc.func_175598_ae().field_78723_d);
            GL11.glEnable(3042);
            GL11.glEnable(2848);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.0f);
            GL11.glColor4f(this.red.get().intValue() / 255.0f, this.green.get().intValue() / 255.0f, this.blue.get().intValue() / 255.0f, this.alpha.get().intValue() / 255.0f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glBegin(3);
            int intValue = 60 - this.accuracyValue.get().intValue();
            if (intValue <= 0) {
                throw new IllegalArgumentException("Step must be positive, was: " + intValue + '.');
            }
            int i2 = 0;
            int progressionLastElement = progressionUtil.getProgressionLastElement(0, (int) TokenId.EXOR_E, intValue);
            if (0 <= progressionLastElement) {
                do {
                    i = i2;
                    i2 += intValue;
                    GL11.glVertex2f(((float) Math.cos((i * 3.141592653589793d) / 180.0d)) * this.rangeValue.get().floatValue(), ((float) Math.sin((i * 3.141592653589793d) / 180.0d)) * this.rangeValue.get().floatValue());
                } while (i != progressionLastElement);
                GL11.glEnd();
                GL11.glDisable(3042);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDisable(2848);
                GL11.glPopMatrix();
            } else {
                GL11.glEnd();
                GL11.glDisable(3042);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDisable(2848);
                GL11.glPopMatrix();
            }
        }
        if (getCancelRun()) {
            this.target = null;
            this.currentTarget = null;
            this.hitable = false;
            stopBlocking();
        } else if (this.noInventoryAttackValue.get().booleanValue() && ((MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer) || System.currentTimeMillis() - this.containerOpen < this.noInventoryDelayValue.get().intValue())) {
            this.target = null;
            this.currentTarget = null;
            this.hitable = false;
            if (MinecraftInstance.f362mc.field_71462_r instanceof GuiContainer) {
                this.containerOpen = System.currentTimeMillis();
            }
        } else if (this.target != null && this.currentTarget != null && this.attackTimer.hasTimePassed(this.attackDelay)) {
            EntityLivingBase entityLivingBase = this.currentTarget;
            Intrinsics.checkNotNull(entityLivingBase);
            if (entityLivingBase.field_70737_aN <= this.hurtTimeValue.get().intValue()) {
                this.clicks++;
                this.attackTimer.reset();
                this.attackDelay = TimeUtils.randomClickDelay(this.minCPS.get().intValue(), this.maxCPS.get().intValue());
            }
        }
    }

    @EventTarget
    public final void onEntityMove(@NotNull EntityMovementEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Entity movedEntity = event.getMovedEntity();
        if (this.target == null || !Intrinsics.areEqual(movedEntity, this.currentTarget)) {
            return;
        }
        updateHitable();
    }

    private final void runAttack() {
        int i;
        int i2;
        if (this.target == null || this.currentTarget == null) {
            return;
        }
        float failRate = this.failRateValue.get().floatValue();
        boolean swing = this.swingValue.get().booleanValue();
        boolean multi = StringsKt.equals(this.targetModeValue.get(), "Multi", true);
        boolean openInventory = this.aacValue.get().booleanValue() && (MinecraftInstance.f362mc.field_71462_r instanceof GuiInventory);
        boolean failHit = failRate > 0.0f && ((float) new Random().nextInt(100)) <= failRate;
        if (openInventory) {
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C0DPacketCloseWindow());
        }
        if (!this.hitable || failHit) {
            if (swing && (this.fakeSwingValue.get().booleanValue() || failHit)) {
                MinecraftInstance.f362mc.field_71439_g.func_71038_i();
            }
        } else {
            if (!multi) {
                EntityLivingBase entityLivingBase = this.currentTarget;
                Intrinsics.checkNotNull(entityLivingBase);
                attackEntity(entityLivingBase);
            } else {
                int targets = 0;
                for (Entity entity : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
                    Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
                    Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
                    Intrinsics.checkNotNullExpressionValue(entity, "entity");
                    double distance = PlayerExtension.getDistanceToEntityBox(entity2, entity);
                    if ((entity instanceof EntityLivingBase) && isEnemy(entity) && distance <= getRange(entity)) {
                        attackEntity((EntityLivingBase) entity);
                        targets++;
                        if (this.limitedMultiTargetsValue.get().intValue() != 0 && this.limitedMultiTargetsValue.get().intValue() <= targets) {
                            break;
                        }
                    }
                }
            }
            List<Integer> list = this.prevTargetEntities;
            if (this.aacValue.get().booleanValue()) {
                EntityLivingBase entityLivingBase2 = this.target;
                Intrinsics.checkNotNull(entityLivingBase2);
                i2 = entityLivingBase2.func_145782_y();
            } else {
                EntityLivingBase entityLivingBase3 = this.currentTarget;
                Intrinsics.checkNotNull(entityLivingBase3);
                i2 = entityLivingBase3.func_145782_y();
            }
            list.add(Integer.valueOf(i2));
            if (Intrinsics.areEqual(this.target, this.currentTarget)) {
                this.target = null;
            }
        }
        if (StringsKt.equals(this.targetModeValue.get(), "Switch", true) && this.attackTimer.hasTimePassed(this.switchDelayValue.get().intValue()) && this.switchDelayValue.get().intValue() != 0) {
            List<Integer> list2 = this.prevTargetEntities;
            if (this.aacValue.get().booleanValue()) {
                EntityLivingBase entityLivingBase4 = this.target;
                Intrinsics.checkNotNull(entityLivingBase4);
                i = entityLivingBase4.func_145782_y();
            } else {
                EntityLivingBase entityLivingBase5 = this.currentTarget;
                Intrinsics.checkNotNull(entityLivingBase5);
                i = entityLivingBase5.func_145782_y();
            }
            list2.add(Integer.valueOf(i));
            this.attackTimer.reset();
        }
        if (openInventory) {
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        }
    }

    private final void updateTarget() {
        int hurtTime = this.hurtTimeValue.get().intValue();
        float fov = this.fovValue.get().floatValue();
        boolean switchMode = StringsKt.equals(this.targetModeValue.get(), "Switch", true);
        List targets = new ArrayList();
        new ArrayList();
        for (EntityLivingBase entityLivingBase : MinecraftInstance.f362mc.field_71441_e.field_72996_f) {
            if ((entityLivingBase instanceof EntityLivingBase) && isEnemy(entityLivingBase) && (!switchMode || !this.prevTargetEntities.contains(Integer.valueOf(entityLivingBase.func_145782_y())))) {
                Entity entity = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNullExpressionValue(entity, "mc.thePlayer");
                double distance = PlayerExtension.getDistanceToEntityBox(entity, entityLivingBase);
                double entityFov = RotationUtils.getRotationDifference((Entity) entityLivingBase);
                if (distance <= getMaxRange()) {
                    if ((fov == 180.0f) || entityFov <= fov) {
                        if (entityLivingBase.field_70737_aN <= hurtTime) {
                            targets.add(entityLivingBase);
                        }
                    }
                }
            }
        }
        String lowerCase = this.priorityValue.get().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1221262756:
                if (lowerCase.equals("health") && targets.size() > 1) {
                    CollectionsKt.sortWith(targets, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$2
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            EntityLivingBase it = (EntityLivingBase) t;
                            EntityLivingBase it2 = (EntityLivingBase) t2;
                            return ComparisonsKt.compareValues(Float.valueOf(it.func_110143_aJ()), Float.valueOf(it2.func_110143_aJ()));
                        }
                    });
                    break;
                }
                break;
            case -962590849:
                if (lowerCase.equals("direction") && targets.size() > 1) {
                    CollectionsKt.sortWith(targets, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$3
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            return ComparisonsKt.compareValues(Double.valueOf(RotationUtils.getRotationDifference((EntityLivingBase) t)), Double.valueOf(RotationUtils.getRotationDifference((EntityLivingBase) t2)));
                        }
                    });
                    break;
                }
                break;
            case -930933036:
                if (lowerCase.equals("regenamplifier") && targets.size() > 1) {
                    CollectionsKt.sortWith(targets, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$8
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            EntityLivingBase it = (EntityLivingBase) t;
                            EntityLivingBase it2 = (EntityLivingBase) t2;
                            return ComparisonsKt.compareValues(Integer.valueOf(it.func_70644_a(Potion.field_76428_l) ? it.func_70660_b(Potion.field_76428_l).func_76458_c() : -1), Integer.valueOf(it2.func_70644_a(Potion.field_76428_l) ? it2.func_70660_b(Potion.field_76428_l).func_76458_c() : -1));
                        }
                    });
                    break;
                }
                break;
            case -109771221:
                if (lowerCase.equals("healthabsorption") && targets.size() > 1) {
                    CollectionsKt.sortWith(targets, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$7
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            EntityLivingBase it = (EntityLivingBase) t;
                            EntityLivingBase it2 = (EntityLivingBase) t2;
                            return ComparisonsKt.compareValues(Float.valueOf(it.func_110143_aJ() + it.func_110139_bj()), Float.valueOf(it2.func_110143_aJ() + it2.func_110139_bj()));
                        }
                    });
                    break;
                }
                break;
            case 288459765:
                if (lowerCase.equals("distance") && targets.size() > 1) {
                    CollectionsKt.sortWith(targets, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$1
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            Minecraft minecraft;
                            Minecraft minecraft2;
                            minecraft = MinecraftInstance.f362mc;
                            Entity entity2 = minecraft.field_71439_g;
                            Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
                            Double valueOf = Double.valueOf(PlayerExtension.getDistanceToEntityBox(entity2, (EntityLivingBase) t));
                            minecraft2 = MinecraftInstance.f362mc;
                            Entity entity3 = minecraft2.field_71439_g;
                            Intrinsics.checkNotNullExpressionValue(entity3, "mc.thePlayer");
                            return ComparisonsKt.compareValues(valueOf, Double.valueOf(PlayerExtension.getDistanceToEntityBox(entity3, (EntityLivingBase) t2)));
                        }
                    });
                    break;
                }
                break;
            case 399498632:
                if (lowerCase.equals("hurtresistance") && targets.size() > 1) {
                    CollectionsKt.sortWith(targets, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$5
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            EntityLivingBase it = (EntityLivingBase) t;
                            EntityLivingBase it2 = (EntityLivingBase) t2;
                            return ComparisonsKt.compareValues(Integer.valueOf(it.field_70172_ad), Integer.valueOf(it2.field_70172_ad));
                        }
                    });
                    break;
                }
                break;
            case 701808476:
                if (lowerCase.equals("hurttime") && targets.size() > 1) {
                    CollectionsKt.sortWith(targets, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$6
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            EntityLivingBase it = (EntityLivingBase) t;
                            EntityLivingBase it2 = (EntityLivingBase) t2;
                            return ComparisonsKt.compareValues(Integer.valueOf(it.field_70737_aN), Integer.valueOf(it2.field_70737_aN));
                        }
                    });
                    break;
                }
                break;
            case 886905078:
                if (lowerCase.equals("livingtime") && targets.size() > 1) {
                    CollectionsKt.sortWith(targets, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$4
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            EntityLivingBase it = (EntityLivingBase) t;
                            EntityLivingBase it2 = (EntityLivingBase) t2;
                            return ComparisonsKt.compareValues(Integer.valueOf(-it.field_70173_aa), Integer.valueOf(-it2.field_70173_aa));
                        }
                    });
                    break;
                }
                break;
        }
        boolean found = false;
        Iterator it = targets.iterator();
        while (true) {
            if (it.hasNext()) {
                EntityLivingBase entity2 = (EntityLivingBase) it.next();
                if (updateRotations((Entity) entity2)) {
                    this.target = entity2;
                    found = true;
                }
            }
        }
        if (found) {
            if (StringsKt.equals(this.rotations.get(), "spin", true)) {
                this.spinYaw += RandomUtils.nextFloat(this.minSpinSpeed.get().floatValue(), this.maxSpinSpeed.get().floatValue());
                this.spinYaw = MathHelper.func_76142_g(this.spinYaw);
                Rotation rot = new Rotation(this.spinYaw, 90.0f);
                RotationUtils.setTargetRotation(rot, 0);
                return;
            }
            return;
        }
        this.target = null;
        if (!this.prevTargetEntities.isEmpty()) {
            this.prevTargetEntities.clear();
            updateTarget();
        }
    }

    public final boolean isEnemy(@Nullable Entity entity) {
        if (entity instanceof EntityLivingBase) {
            if ((EntityUtils.targetDead || isAlive((EntityLivingBase) entity)) && !Intrinsics.areEqual(entity, MinecraftInstance.f362mc.field_71439_g)) {
                if (!EntityUtils.targetInvisible && ((EntityLivingBase) entity).func_82150_aj()) {
                    return false;
                }
                if (!EntityUtils.targetPlayer || !(entity instanceof EntityPlayer)) {
                    return (EntityUtils.targetMobs && EntityUtils.isMob(entity)) || (EntityUtils.targetAnimals && EntityUtils.isAnimal(entity));
                } else if (((EntityPlayer) entity).func_175149_v() || AntiBot.isBot((EntityLivingBase) entity)) {
                    return false;
                } else {
                    if (EntityUtils.isFriend(entity)) {
                        Module module = LiquidBounce.INSTANCE.getModuleManager().get(NoFriends.class);
                        Intrinsics.checkNotNull(module);
                        if (!module.getState()) {
                            return false;
                        }
                    }
                    Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(Teams.class);
                    if (module2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.Teams");
                    }
                    Teams teams = (Teams) module2;
                    return !teams.getState() || !teams.isInYourTeam((EntityLivingBase) entity);
                }
            }
            return false;
        }
        return false;
    }

    private final void attackEntity(EntityLivingBase entity) {
        if (MinecraftInstance.f362mc.field_71439_g.func_70632_aY() || this.blockingStatus) {
            stopBlocking();
        }
        LiquidBounce.INSTANCE.getEventManager().callEvent(new AttackEvent((Entity) entity));
        this.markEntity = entity;
        if (StringsKt.equals(this.rotations.get(), "spin", true) && !this.noSendRot.get().booleanValue()) {
            Rotation targetedRotation = getTargetRotation((Entity) entity);
            if (targetedRotation == null) {
                return;
            }
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C03PacketPlayer.C05PacketPlayerLook(targetedRotation.getYaw(), targetedRotation.getPitch(), MinecraftInstance.f362mc.field_71439_g.field_70122_E));
            if (this.debugValue.get().booleanValue()) {
                ClientUtils.displayChatMessage("[KillAura] Silent rotation change.");
            }
        }
        if (this.swingValue.get().booleanValue() && (!this.swingOrderValue.get().booleanValue() || ViaForge.getInstance().getVersion() <= 47)) {
            MinecraftInstance.f362mc.field_71439_g.func_71038_i();
        }
        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C02PacketUseEntity((Entity) entity, C02PacketUseEntity.Action.ATTACK));
        if (this.swingValue.get().booleanValue() && this.swingOrderValue.get().booleanValue() && ViaForge.getInstance().getVersion() > 47) {
            MinecraftInstance.f362mc.field_71439_g.func_71038_i();
        }
        if (this.keepSprintValue.get().booleanValue()) {
            if (MinecraftInstance.f362mc.field_71439_g.field_70143_R > 0.0f && !MinecraftInstance.f362mc.field_71439_g.field_70122_E && !MinecraftInstance.f362mc.field_71439_g.func_70617_f_() && !MinecraftInstance.f362mc.field_71439_g.func_70090_H() && !MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76440_q) && !MinecraftInstance.f362mc.field_71439_g.func_70115_ae()) {
                MinecraftInstance.f362mc.field_71439_g.func_71009_b((Entity) entity);
            }
            if (EnchantmentHelper.func_152377_a(MinecraftInstance.f362mc.field_71439_g.func_70694_bm(), entity.func_70668_bt()) > 0.0f) {
                MinecraftInstance.f362mc.field_71439_g.func_71047_c((Entity) entity);
            }
        } else if (MinecraftInstance.f362mc.field_71442_b.field_78779_k != WorldSettings.GameType.SPECTATOR) {
            MinecraftInstance.f362mc.field_71439_g.func_71059_n((Entity) entity);
        }
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Criticals.class);
        if (module == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.Criticals");
        }
        Criticals criticals = (Criticals) module;
        int i = 0;
        while (i < 3) {
            i++;
            if ((MinecraftInstance.f362mc.field_71439_g.field_70143_R > 0.0f && !MinecraftInstance.f362mc.field_71439_g.field_70122_E && !MinecraftInstance.f362mc.field_71439_g.func_70617_f_() && !MinecraftInstance.f362mc.field_71439_g.func_70090_H() && !MinecraftInstance.f362mc.field_71439_g.func_70644_a(Potion.field_76440_q) && MinecraftInstance.f362mc.field_71439_g.field_70154_o == null) || (criticals.getState() && criticals.getMsTimer().hasTimePassed(criticals.getDelayValue().get().intValue()) && !MinecraftInstance.f362mc.field_71439_g.func_70090_H() && !MinecraftInstance.f362mc.field_71439_g.func_180799_ab() && !MinecraftInstance.f362mc.field_71439_g.field_70134_J)) {
                MinecraftInstance.f362mc.field_71439_g.func_71009_b(this.target);
            }
            ItemStack func_70694_bm = MinecraftInstance.f362mc.field_71439_g.func_70694_bm();
            EntityLivingBase entityLivingBase = this.target;
            Intrinsics.checkNotNull(entityLivingBase);
            if (EnchantmentHelper.func_152377_a(func_70694_bm, entityLivingBase.func_70668_bt()) > 0.0f || this.fakeSharpValue.get().booleanValue()) {
                MinecraftInstance.f362mc.field_71439_g.func_71047_c(this.target);
            }
        }
        if (this.afterTickPatchValue.get().booleanValue() && StringsKt.equals(this.autoBlockModeValue.get(), "AfterTick", true)) {
            return;
        }
        if (MinecraftInstance.f362mc.field_71439_g.func_70632_aY() || getCanBlock()) {
            startBlocking((Entity) entity, this.interactAutoBlockValue.get().booleanValue());
        }
    }

    private final boolean updateRotations(Entity entity) {
        if (StringsKt.equals(this.rotations.get(), "none", true)) {
            return true;
        }
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(Disabler.class);
        Intrinsics.checkNotNull(module);
        Disabler disabler = (Disabler) module;
        boolean modify = disabler.getCanModifyRotation();
        if (modify) {
            return true;
        }
        Rotation defRotation = getTargetRotation(entity);
        if (defRotation == null) {
            return false;
        }
        if (this.silentRotationValue.get().booleanValue()) {
            RotationUtils.setTargetRotation(defRotation, (!this.aacValue.get().booleanValue() || StringsKt.equals(this.rotations.get(), "Spin", true)) ? 0 : 15);
            return true;
        }
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNull(entityPlayerSP);
        defRotation.toPlayer((EntityPlayer) entityPlayerSP);
        return true;
    }

    private final Rotation getTargetRotation(Entity entity) {
        AxisAlignedBB boundingBox = entity.func_174813_aQ();
        if (StringsKt.equals(this.rotations.get(), "Vanilla", true)) {
            if (this.maxTurnSpeed.get().floatValue() <= 0.0f) {
                return RotationUtils.serverRotation;
            }
            if (this.predictValue.get().booleanValue()) {
                boundingBox = boundingBox.func_72317_d((entity.field_70165_t - entity.field_70169_q) * RandomUtils.nextFloat(this.minPredictSize.get().floatValue(), this.maxPredictSize.get().floatValue()), (entity.field_70163_u - entity.field_70167_r) * RandomUtils.nextFloat(this.minPredictSize.get().floatValue(), this.maxPredictSize.get().floatValue()), (entity.field_70161_v - entity.field_70166_s) * RandomUtils.nextFloat(this.minPredictSize.get().floatValue(), this.maxPredictSize.get().floatValue()));
            }
            AxisAlignedBB axisAlignedBB = boundingBox;
            boolean z = this.outborderValue.get().booleanValue() && !this.attackTimer.hasTimePassed(this.attackDelay / ((long) 2));
            boolean booleanValue = this.randomCenterValue.get().booleanValue();
            boolean booleanValue2 = this.predictValue.get().booleanValue();
            Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entity2);
            VecRotation searchCenter = RotationUtils.searchCenter(axisAlignedBB, z, booleanValue, booleanValue2, PlayerExtension.getDistanceToEntityBox(entity2, entity) < ((double) this.throughWallsRangeValue.get().floatValue()), getMaxRange(), RandomUtils.nextFloat(this.minRand.get().floatValue(), this.maxRand.get().floatValue()), this.randomCenterNewValue.get().booleanValue());
            if (searchCenter != null) {
                Rotation rotation = searchCenter.component2();
                Rotation limitedRotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, rotation, (float) ((Math.random() * (this.maxTurnSpeed.get().floatValue() - this.minTurnSpeed.get().floatValue())) + this.minTurnSpeed.get().doubleValue()));
                Intrinsics.checkNotNullExpressionValue(limitedRotation, "limitAngleChange(Rotatio…rnSpeed.get()).toFloat())");
                return limitedRotation;
            }
            return null;
        } else if (StringsKt.equals(this.rotations.get(), "Spin", true)) {
            if (this.maxTurnSpeed.get().floatValue() <= 0.0f) {
                return RotationUtils.serverRotation;
            }
            if (this.predictValue.get().booleanValue()) {
                boundingBox = boundingBox.func_72317_d((entity.field_70165_t - entity.field_70169_q) * RandomUtils.nextFloat(this.minPredictSize.get().floatValue(), this.maxPredictSize.get().floatValue()), (entity.field_70163_u - entity.field_70167_r) * RandomUtils.nextFloat(this.minPredictSize.get().floatValue(), this.maxPredictSize.get().floatValue()), (entity.field_70161_v - entity.field_70166_s) * RandomUtils.nextFloat(this.minPredictSize.get().floatValue(), this.maxPredictSize.get().floatValue()));
            }
            AxisAlignedBB axisAlignedBB2 = boundingBox;
            Entity entity3 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entity3);
            VecRotation searchCenter2 = RotationUtils.searchCenter(axisAlignedBB2, false, false, false, PlayerExtension.getDistanceToEntityBox(entity3, entity) < ((double) this.throughWallsRangeValue.get().floatValue()), getMaxRange());
            if (searchCenter2 != null) {
                Rotation rotation2 = searchCenter2.component2();
                return rotation2;
            }
            return null;
        } else if (StringsKt.equals(this.rotations.get(), "BackTrack", true)) {
            if (this.predictValue.get().booleanValue()) {
                boundingBox = boundingBox.func_72317_d((entity.field_70165_t - entity.field_70169_q) * RandomUtils.nextFloat(this.minPredictSize.get().floatValue(), this.maxPredictSize.get().floatValue()), (entity.field_70163_u - entity.field_70167_r) * RandomUtils.nextFloat(this.minPredictSize.get().floatValue(), this.maxPredictSize.get().floatValue()), (entity.field_70161_v - entity.field_70166_s) * RandomUtils.nextFloat(this.minPredictSize.get().floatValue(), this.maxPredictSize.get().floatValue()));
            }
            Rotation rotation3 = RotationUtils.serverRotation;
            AxisAlignedBB axisAlignedBB3 = boundingBox;
            Vec3 center = RotationUtils.getCenter(entity.func_174813_aQ());
            boolean booleanValue3 = this.predictValue.get().booleanValue();
            Entity entity4 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entity4);
            Rotation limitedRotation2 = RotationUtils.limitAngleChange(rotation3, RotationUtils.OtherRotation(axisAlignedBB3, center, booleanValue3, PlayerExtension.getDistanceToEntityBox(entity4, entity) < ((double) this.throughWallsRangeValue.get().floatValue()), getMaxRange()), (float) ((Math.random() * (this.maxTurnSpeed.get().floatValue() - this.minTurnSpeed.get().floatValue())) + this.minTurnSpeed.get().doubleValue()));
            Intrinsics.checkNotNullExpressionValue(limitedRotation2, "limitAngleChange(Rotatio…rnSpeed.get()).toFloat())");
            return limitedRotation2;
        } else {
            return RotationUtils.serverRotation;
        }
    }

    private final void updateHitable() {
        if (StringsKt.equals(this.rotations.get(), "none", true)) {
            this.hitable = true;
            return;
        }
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(Disabler.class);
        Intrinsics.checkNotNull(module);
        Disabler disabler = (Disabler) module;
        if (StringsKt.equals(this.rotations.get(), "spin", true)) {
            EntityLivingBase entityLivingBase = this.target;
            Intrinsics.checkNotNull(entityLivingBase);
            this.hitable = entityLivingBase.field_70737_aN <= this.spinHurtTimeValue.get().intValue();
        } else if (this.maxTurnSpeed.get().floatValue() <= 0.0f || this.noHitCheck.get().booleanValue() || disabler.getCanModifyRotation()) {
            this.hitable = true;
        } else {
            Entity entity = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entity, "mc.thePlayer");
            Entity entity2 = this.target;
            Intrinsics.checkNotNull(entity2);
            double reach = Math.min(getMaxRange(), PlayerExtension.getDistanceToEntityBox(entity, entity2)) + 1;
            if (this.raycastValue.get().booleanValue()) {
                EntityLivingBase raycastEntity = RaycastUtils.raycastEntity(reach, (v1) -> {
                    return m2769updateHitable$lambda8(r1, v1);
                });
                if (this.raycastValue.get().booleanValue() && (raycastEntity instanceof EntityLivingBase)) {
                    Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(NoFriends.class);
                    Intrinsics.checkNotNull(module2);
                    if (module2.getState() || !EntityUtils.isFriend(raycastEntity)) {
                        this.currentTarget = raycastEntity;
                    }
                }
                this.hitable = this.maxTurnSpeed.get().floatValue() > 0.0f ? Intrinsics.areEqual(this.currentTarget, raycastEntity) : true;
                return;
            }
            this.hitable = RotationUtils.isFaced(this.currentTarget, reach);
        }
    }

    /* renamed from: updateHitable$lambda-8 */
    private static final boolean m2769updateHitable$lambda8(KillAura this$0, Entity it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!this$0.livingRaycastValue.get().booleanValue() || ((it instanceof EntityLivingBase) && !(it instanceof EntityArmorStand))) {
            if (!this$0.isEnemy(it) && !this$0.raycastIgnoredValue.get().booleanValue()) {
                if (this$0.aacValue.get().booleanValue()) {
                    List func_72839_b = MinecraftInstance.f362mc.field_71441_e.func_72839_b(it, it.func_174813_aQ());
                    Intrinsics.checkNotNullExpressionValue(func_72839_b, "mc.theWorld.getEntitiesW…it, it.entityBoundingBox)");
                    if (!func_72839_b.isEmpty()) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    private final void startBlocking(Entity interactEntity, boolean interact) {
        if (!getCanSmartBlock() || StringsKt.equals(this.autoBlockModeValue.get(), "none", true) || this.blockRate.get().intValue() <= 0 || new Random().nextInt(100) > this.blockRate.get().intValue()) {
            return;
        }
        if (!this.abThruWallValue.get().booleanValue() && (interactEntity instanceof EntityLivingBase)) {
            EntityLivingBase entityLB = (EntityLivingBase) interactEntity;
            Entity entity = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entity);
            if (!entityLB.func_70685_l(entity)) {
                this.fakeBlock = true;
                return;
            }
        }
        if (StringsKt.equals(this.autoBlockModeValue.get(), "ncp", true)) {
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, (ItemStack) null, 0.0f, 0.0f, 0.0f));
            this.blockingStatus = true;
            return;
        }
        if (interact) {
            Entity func_175606_aa = MinecraftInstance.f362mc.func_175606_aa();
            Vec3 positionEye = func_175606_aa == null ? null : func_175606_aa.func_174824_e(1.0f);
            double expandSize = interactEntity.func_70111_Y();
            AxisAlignedBB boundingBox = interactEntity.func_174813_aQ().func_72314_b(expandSize, expandSize, expandSize);
            Rotation rotation = RotationUtils.targetRotation;
            if (rotation == null) {
                EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP);
                float f = entityPlayerSP.field_70177_z;
                EntityPlayerSP entityPlayerSP2 = MinecraftInstance.f362mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP2);
                rotation = new Rotation(f, entityPlayerSP2.field_70125_A);
            }
            Rotation rotation2 = rotation;
            float yaw = rotation2.component1();
            float pitch = rotation2.component2();
            float yawCos = (float) Math.cos(((-yaw) * 0.017453292f) - 3.1415927f);
            float yawSin = (float) Math.sin(((-yaw) * 0.017453292f) - 3.1415927f);
            float pitchCos = -((float) Math.cos((-pitch) * 0.017453292f));
            float pitchSin = (float) Math.sin((-pitch) * 0.017453292f);
            Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
            Intrinsics.checkNotNull(entity2);
            double range = Math.min(getMaxRange(), PlayerExtension.getDistanceToEntityBox(entity2, interactEntity)) + 1;
            Intrinsics.checkNotNull(positionEye);
            Vec3 lookAt = positionEye.func_72441_c(yawSin * pitchCos * range, pitchSin * range, yawCos * pitchCos * range);
            MovingObjectPosition movingObject = boundingBox.func_72327_a(positionEye, lookAt);
            if (movingObject == null) {
                return;
            }
            Vec3 hitVec = movingObject.field_72307_f;
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C02PacketUseEntity(interactEntity, new Vec3(hitVec.field_72450_a - interactEntity.field_70165_t, hitVec.field_72448_b - interactEntity.field_70163_u, hitVec.field_72449_c - interactEntity.field_70161_v)));
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C02PacketUseEntity(interactEntity, C02PacketUseEntity.Action.INTERACT));
        }
        MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C08PacketPlayerBlockPlacement(MinecraftInstance.f362mc.field_71439_g.field_71071_by.func_70448_g()));
        this.blockingStatus = true;
    }

    private final void stopBlocking() {
        this.fakeBlock = false;
        if (this.blockingStatus) {
            MinecraftInstance.f362mc.func_147114_u().func_147297_a(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
            this.blockingStatus = false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0048, code lost:
        if (r0.getState() == false) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean getCancelRun() {
        /*
            r4 = this;
            net.minecraft.client.Minecraft r0 = net.ccbluex.liquidbounce.utils.MinecraftInstance.f362mc
            net.minecraft.client.entity.EntityPlayerSP r0 = r0.field_71439_g
            boolean r0 = r0.func_175149_v()
            if (r0 != 0) goto L87
            r0 = r4
            net.minecraft.client.Minecraft r1 = net.ccbluex.liquidbounce.utils.MinecraftInstance.f362mc
            net.minecraft.client.entity.EntityPlayerSP r1 = r1.field_71439_g
            r5 = r1
            r1 = r5
            java.lang.String r2 = "mc.thePlayer"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            r1 = r5
            net.minecraft.entity.EntityLivingBase r1 = (net.minecraft.entity.EntityLivingBase) r1
            boolean r0 = r0.isAlive(r1)
            if (r0 == 0) goto L87
            r0 = r4
            net.ccbluex.liquidbounce.value.BoolValue r0 = r0.blinkCheck
            java.lang.Object r0 = r0.get()
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L4b
            net.ccbluex.liquidbounce.LiquidBounce r0 = net.ccbluex.liquidbounce.LiquidBounce.INSTANCE
            net.ccbluex.liquidbounce.features.module.ModuleManager r0 = r0.getModuleManager()
            java.lang.Class<net.ccbluex.liquidbounce.features.module.modules.player.Blink> r1 = net.ccbluex.liquidbounce.features.module.modules.player.Blink.class
            net.ccbluex.liquidbounce.features.module.Module r0 = r0.get(r1)
            r1 = r0
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            boolean r0 = r0.getState()
            if (r0 != 0) goto L87
        L4b:
            net.ccbluex.liquidbounce.LiquidBounce r0 = net.ccbluex.liquidbounce.LiquidBounce.INSTANCE
            net.ccbluex.liquidbounce.features.module.ModuleManager r0 = r0.getModuleManager()
            java.lang.Class<net.ccbluex.liquidbounce.features.module.modules.render.FreeCam> r1 = net.ccbluex.liquidbounce.features.module.modules.render.FreeCam.class
            net.ccbluex.liquidbounce.features.module.Module r0 = r0.get(r1)
            r1 = r0
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            boolean r0 = r0.getState()
            if (r0 != 0) goto L87
            r0 = r4
            net.ccbluex.liquidbounce.value.BoolValue r0 = r0.noScaffValue
            java.lang.Object r0 = r0.get()
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L8b
            net.ccbluex.liquidbounce.LiquidBounce r0 = net.ccbluex.liquidbounce.LiquidBounce.INSTANCE
            net.ccbluex.liquidbounce.features.module.ModuleManager r0 = r0.getModuleManager()
            java.lang.Class<net.ccbluex.liquidbounce.features.module.modules.world.Scaffold> r1 = net.ccbluex.liquidbounce.features.module.modules.world.Scaffold.class
            net.ccbluex.liquidbounce.features.module.Module r0 = r0.get(r1)
            r1 = r0
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            boolean r0 = r0.getState()
            if (r0 == 0) goto L8b
        L87:
            r0 = 1
            goto L8c
        L8b:
            r0 = 0
        L8c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura.getCancelRun():boolean");
    }

    private final boolean isAlive(EntityLivingBase entity) {
        return (entity.func_70089_S() && entity.func_110143_aJ() > 0.0f) || (this.aacValue.get().booleanValue() && entity.field_70737_aN > 5);
    }

    private final boolean getCanBlock() {
        return MinecraftInstance.f362mc.field_71439_g.func_70694_bm() != null && (MinecraftInstance.f362mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword);
    }

    private final float getMaxRange() {
        return Math.max(this.rangeValue.get().floatValue(), this.throughWallsRangeValue.get().floatValue());
    }

    private final float getRange(Entity entity) {
        Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
        return (PlayerExtension.getDistanceToEntityBox(entity2, entity) >= ((double) this.throughWallsRangeValue.get().floatValue()) ? this.rangeValue.get().floatValue() : this.throughWallsRangeValue.get().floatValue()) - (MinecraftInstance.f362mc.field_71439_g.func_70051_ag() ? this.rangeSprintReducementValue.get().floatValue() : 0.0f);
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    @Nullable
    public String getTag() {
        return this.targetModeValue.get();
    }
}
