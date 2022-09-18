package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ArmorType;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/listeners/protocol1_9to1_8/ArmorListener.class */
public class ArmorListener extends ViaBukkitListener {
    private static final UUID ARMOR_ATTRIBUTE = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");

    public ArmorListener(Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }

    public void sendArmorUpdate(Player player) {
        ItemStack[] armorContents;
        if (!isOnPipe(player)) {
            return;
        }
        int armor = 0;
        for (ItemStack stack : player.getInventory().getArmorContents()) {
            armor += ArmorType.findById(stack.getTypeId()).getArmorPoints();
        }
        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_PROPERTIES, (ByteBuf) null, getUserConnection(player));
        try {
            wrapper.write(Type.VAR_INT, Integer.valueOf(player.getEntityId()));
            wrapper.write(Type.INT, 1);
            wrapper.write(Type.STRING, "generic.armor");
            wrapper.write(Type.DOUBLE, Double.valueOf(0.0d));
            wrapper.write(Type.VAR_INT, 1);
            wrapper.write(Type.UUID, ARMOR_ATTRIBUTE);
            wrapper.write(Type.DOUBLE, Double.valueOf(armor));
            wrapper.write(Type.BYTE, (byte) 0);
            wrapper.scheduleSend(Protocol1_9To1_8.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        HumanEntity human = e.getWhoClicked();
        if ((human instanceof Player) && (e.getInventory() instanceof CraftingInventory)) {
            Player player = (Player) human;
            if (e.getCurrentItem() != null && ArmorType.isArmor(e.getCurrentItem().getTypeId())) {
                sendDelayedArmorUpdate(player);
            } else if (e.getRawSlot() >= 5 && e.getRawSlot() <= 8) {
                sendDelayedArmorUpdate(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() != null) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player player = e.getPlayer();
                Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), ()
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0031: INVOKE  
                      (wrap: org.bukkit.scheduler.BukkitScheduler : 0x0020: INVOKE  (r0v6 org.bukkit.scheduler.BukkitScheduler A[REMOVE]) =  type: STATIC call: org.bukkit.Bukkit.getScheduler():org.bukkit.scheduler.BukkitScheduler)
                      (wrap: org.bukkit.plugin.Plugin : 0x0024: INVOKE  (r1v2 org.bukkit.plugin.Plugin A[REMOVE]) = 
                      (r6v0 'this' com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener A[D('this' com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener), IMMUTABLE_TYPE, THIS])
                     type: VIRTUAL call: com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener.getPlugin():org.bukkit.plugin.Plugin)
                      (wrap: java.lang.Runnable : 0x0029: INVOKE_CUSTOM (r2v1 java.lang.Runnable A[REMOVE]) = 
                      (r6v0 'this' com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener A[D('this' com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                      (r0v5 'player' org.bukkit.entity.Player A[D('player' org.bukkit.entity.Player), DONT_INLINE])
                    
                     handle type: INVOKE_DIRECT
                     lambda: java.lang.Runnable.run():void
                     call insn: ?: INVOKE  (r2 I:com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener), (r3 I:org.bukkit.entity.Player) type: DIRECT call: com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener.lambda$onInteract$0(org.bukkit.entity.Player):void)
                      (3 long)
                     type: INTERFACE call: org.bukkit.scheduler.BukkitScheduler.scheduleSyncDelayedTask(org.bukkit.plugin.Plugin, java.lang.Runnable, long):int in method: com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener.onInteract(org.bukkit.event.player.PlayerInteractEvent):void, file: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/listeners/protocol1_9to1_8/ArmorListener.class
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:123)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:123)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                    	at java.base/java.util.ArrayList.forEach(Unknown Source)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                    Caused by: java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
                    	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
                    	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
                    	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
                    	at java.base/java.util.Objects.checkIndex(Unknown Source)
                    	at java.base/java.util.ArrayList.get(Unknown Source)
                    	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
                    	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:830)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                    	... 31 more
                    */
                /*
                    this = this;
                    r0 = r7
                    org.bukkit.inventory.ItemStack r0 = r0.getItem()
                    if (r0 == 0) goto L37
                    r0 = r7
                    org.bukkit.event.block.Action r0 = r0.getAction()
                    org.bukkit.event.block.Action r1 = org.bukkit.event.block.Action.RIGHT_CLICK_AIR
                    if (r0 == r1) goto L1b
                    r0 = r7
                    org.bukkit.event.block.Action r0 = r0.getAction()
                    org.bukkit.event.block.Action r1 = org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK
                    if (r0 != r1) goto L37
                L1b:
                    r0 = r7
                    org.bukkit.entity.Player r0 = r0.getPlayer()
                    r8 = r0
                    org.bukkit.scheduler.BukkitScheduler r0 = org.bukkit.Bukkit.getScheduler()
                    r1 = r6
                    org.bukkit.plugin.Plugin r1 = r1.getPlugin()
                    r2 = r6
                    r3 = r8
                    void r2 = () -> { // java.lang.Runnable.run():void
                        r2.lambda$onInteract$0(r3);
                    }
                    r3 = 3
                    int r0 = r0.scheduleSyncDelayedTask(r1, r2, r3)
                L37:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener.onInteract(org.bukkit.event.player.PlayerInteractEvent):void");
            }

            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void onItemBreak(PlayerItemBreakEvent e) {
                sendDelayedArmorUpdate(e.getPlayer());
            }

            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void onJoin(PlayerJoinEvent e) {
                sendDelayedArmorUpdate(e.getPlayer());
            }

            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void onRespawn(PlayerRespawnEvent e) {
                sendDelayedArmorUpdate(e.getPlayer());
            }

            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void onWorldChange(PlayerChangedWorldEvent e) {
                sendArmorUpdate(e.getPlayer());
            }

            public void sendDelayedArmorUpdate(Player player) {
                if (!isOnPipe(player)) {
                    return;
                }
                Via.getPlatform().runSync(()
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0013: INVOKE  
                      (wrap: com.viaversion.viaversion.api.platform.ViaPlatform : 0x0009: INVOKE  (r0v2 com.viaversion.viaversion.api.platform.ViaPlatform A[REMOVE]) =  type: STATIC call: com.viaversion.viaversion.api.Via.getPlatform():com.viaversion.viaversion.api.platform.ViaPlatform)
                      (wrap: java.lang.Runnable : 0x000e: INVOKE_CUSTOM (r1v2 java.lang.Runnable A[REMOVE]) = 
                      (r4v0 'this' com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener A[D('this' com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener), DONT_INLINE, IMMUTABLE_TYPE, THIS])
                      (r5v0 'player' org.bukkit.entity.Player A[D('player' org.bukkit.entity.Player), DONT_INLINE])
                    
                     handle type: INVOKE_DIRECT
                     lambda: java.lang.Runnable.run():void
                     call insn: ?: INVOKE  (r1 I:com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener), (r2 I:org.bukkit.entity.Player) type: DIRECT call: com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener.lambda$sendDelayedArmorUpdate$1(org.bukkit.entity.Player):void)
                     type: INTERFACE call: com.viaversion.viaversion.api.platform.ViaPlatform.runSync(java.lang.Runnable):com.viaversion.viaversion.api.platform.PlatformTask in method: com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener.sendDelayedArmorUpdate(org.bukkit.entity.Player):void, file: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/listeners/protocol1_9to1_8/ArmorListener.class
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:287)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:286)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:265)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:369)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                    	at java.base/java.util.ArrayList.forEach(Unknown Source)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                    Caused by: java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
                    	at java.base/jdk.internal.util.Preconditions.outOfBounds(Unknown Source)
                    	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Unknown Source)
                    	at java.base/jdk.internal.util.Preconditions.checkIndex(Unknown Source)
                    	at java.base/java.util.Objects.checkIndex(Unknown Source)
                    	at java.base/java.util.ArrayList.get(Unknown Source)
                    	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:952)
                    	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:857)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:791)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:141)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:117)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1029)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:830)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:399)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:280)
                    	... 19 more
                    */
                /*
                    this = this;
                    r0 = r4
                    r1 = r5
                    boolean r0 = r0.isOnPipe(r1)
                    if (r0 != 0) goto L9
                    return
                L9:
                    com.viaversion.viaversion.api.platform.ViaPlatform r0 = com.viaversion.viaversion.api.Via.getPlatform()
                    r1 = r4
                    r2 = r5
                    void r1 = () -> { // java.lang.Runnable.run():void
                        r1.lambda$sendDelayedArmorUpdate$1(r2);
                    }
                    com.viaversion.viaversion.api.platform.PlatformTask r0 = r0.runSync(r1)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener.sendDelayedArmorUpdate(org.bukkit.entity.Player):void");
            }
        }
