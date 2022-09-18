package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;
import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import de.gerrygames.viarewind.replacement.Replacement;
import de.gerrygames.viarewind.replacement.ReplacementRegistry;
import de.gerrygames.viarewind.storage.BlockState;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/items/ReplacementRegistry1_7_6_10to1_8.class */
public class ReplacementRegistry1_7_6_10to1_8 {
    private static final ReplacementRegistry registry = new ReplacementRegistry();

    static {
        registry.registerBlock(176, new Replacement(63));
        registry.registerBlock(177, new Replacement(68));
        registry.registerBlock(193, new Replacement(64));
        registry.registerBlock(194, new Replacement(64));
        registry.registerBlock(195, new Replacement(64));
        registry.registerBlock(Opcode.WIDE, new Replacement(64));
        registry.registerBlock(197, new Replacement(64));
        registry.registerBlock(77, 5, new Replacement(69, 6));
        registry.registerBlock(77, 13, new Replacement(69, 14));
        registry.registerBlock(77, 0, new Replacement(69, 0));
        registry.registerBlock(77, 8, new Replacement(69, 8));
        registry.registerBlock(143, 5, new Replacement(69, 6));
        registry.registerBlock(143, 13, new Replacement(69, 14));
        registry.registerBlock(143, 0, new Replacement(69, 0));
        registry.registerBlock(143, 8, new Replacement(69, 8));
        registry.registerBlock(178, new Replacement(151));
        registry.registerBlock(182, 0, new Replacement(44, 1));
        registry.registerBlock(182, 8, new Replacement(44, 9));
        registry.registerItem(425, new Replacement((int) TokenId.INSTANCEOF, "Banner"));
        registry.registerItem(409, new Replacement((int) TokenId.StringL, "Prismarine Shard"));
        registry.registerItem(TokenId.TRUE, new Replacement((int) TokenId.StringL, "Prismarine Crystal"));
        registry.registerItem(416, new Replacement(280, "Armor Stand"));
        registry.registerItem(423, new Replacement((int) TokenId.MINUSMINUS, "Raw Mutton"));
        registry.registerItem(424, new Replacement((int) TokenId.LSHIFT, "Cooked Mutton"));
        registry.registerItem(TokenId.FALSE, new Replacement((int) TokenId.LSHIFT_E, "Raw Rabbit"));
        registry.registerItem(TokenId.NULL, new Replacement((int) TokenId.RSHIFT, "Cooked Rabbit"));
        registry.registerItem(413, new Replacement(282, "Rabbit Stew"));
        registry.registerItem(414, new Replacement(375, "Rabbit's Foot"));
        registry.registerItem(415, new Replacement((int) TokenId.SHORT, "Rabbit Hide"));
        registry.registerItem(373, 8203, new Replacement(373, 0, "Potion of Leaping"));
        registry.registerItem(373, 8235, new Replacement(373, 0, "Potion of Leaping"));
        registry.registerItem(373, 8267, new Replacement(373, 0, "Potion of Leaping"));
        registry.registerItem(373, 16395, new Replacement(373, 0, "Splash Potion of Leaping"));
        registry.registerItem(373, 16427, new Replacement(373, 0, "Splash Potion of Leaping"));
        registry.registerItem(373, 16459, new Replacement(373, 0, "Splash Potion of Leaping"));
        registry.registerItem(383, 30, new Replacement(383, "Spawn ArmorStand"));
        registry.registerItem(383, 67, new Replacement(383, "Spawn Endermite"));
        registry.registerItem(383, 68, new Replacement(383, "Spawn Guardian"));
        registry.registerItem(383, 101, new Replacement(383, "Spawn Rabbit"));
        registry.registerItem(19, 1, new Replacement(19, 0, "Wet Sponge"));
        registry.registerItem(182, new Replacement(44, 1, "Red Sandstone Slab"));
        registry.registerItemBlock(166, new Replacement(20, "Barrier"));
        registry.registerItemBlock(167, new Replacement(96, "Iron Trapdoor"));
        registry.registerItemBlock(1, 1, new Replacement(1, 0, "Granite"));
        registry.registerItemBlock(1, 2, new Replacement(1, 0, "Polished Granite"));
        registry.registerItemBlock(1, 3, new Replacement(1, 0, "Diorite"));
        registry.registerItemBlock(1, 4, new Replacement(1, 0, "Polished Diorite"));
        registry.registerItemBlock(1, 5, new Replacement(1, 0, "Andesite"));
        registry.registerItemBlock(1, 6, new Replacement(1, 0, "Polished Andesite"));
        registry.registerItemBlock(168, 0, new Replacement(1, 0, "Prismarine"));
        registry.registerItemBlock(168, 1, new Replacement(98, 0, "Prismarine Bricks"));
        registry.registerItemBlock(168, 2, new Replacement(98, 1, "Dark Prismarine"));
        registry.registerItemBlock(169, new Replacement(89, "Sea Lantern"));
        registry.registerItemBlock(165, new Replacement(95, 5, "Slime Block"));
        registry.registerItemBlock(179, 0, new Replacement(24, "Red Sandstone"));
        registry.registerItemBlock(179, 1, new Replacement(24, "Chiseled Red Sandstone"));
        registry.registerItemBlock(179, 2, new Replacement(24, "Smooth Sandstone"));
        registry.registerItemBlock(181, new Replacement(43, 1, "Double Red Sandstone Slab"));
        registry.registerItemBlock(180, new Replacement(128, "Red Sandstone Stairs"));
        registry.registerItemBlock(188, new Replacement(85, "Spruce Fence"));
        registry.registerItemBlock(189, new Replacement(85, "Birch Fence"));
        registry.registerItemBlock(190, new Replacement(85, "Jungle Fence"));
        registry.registerItemBlock(191, new Replacement(85, "Dark Oak Fence"));
        registry.registerItemBlock(192, new Replacement(85, "Acacia Fence"));
        registry.registerItemBlock(183, new Replacement(107, "Spruce Fence Gate"));
        registry.registerItemBlock(184, new Replacement(107, "Birch Fence Gate"));
        registry.registerItemBlock(185, new Replacement(107, "Jungle Fence Gate"));
        registry.registerItemBlock(186, new Replacement(107, "Dark Oak Fence Gate"));
        registry.registerItemBlock(187, new Replacement(107, "Acacia Fence Gate"));
        registry.registerItemBlock(427, new Replacement((int) TokenId.INT, "Spruce Door"));
        registry.registerItemBlock(428, new Replacement((int) TokenId.INT, "Birch Door"));
        registry.registerItemBlock(429, new Replacement((int) TokenId.INT, "Jungle Door"));
        registry.registerItemBlock(430, new Replacement((int) TokenId.INT, "Dark Oak Door"));
        registry.registerItemBlock(431, new Replacement((int) TokenId.INT, "Acacia Door"));
        registry.registerItemBlock(157, new Replacement(28, "Activator Rail"));
    }

    public static Item replace(Item item) {
        return registry.replace(item);
    }

    public static int replace(int raw) {
        int data = BlockState.extractData(raw);
        Replacement replace = registry.replace(BlockState.extractId(raw), data);
        return replace != null ? BlockState.stateToRaw(replace.getId(), replace.replaceData(data)) : raw;
    }

    public static Replacement getReplacement(int id, int data) {
        return registry.replace(id, data);
    }
}
