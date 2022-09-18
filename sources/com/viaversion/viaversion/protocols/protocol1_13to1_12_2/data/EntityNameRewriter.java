package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import java.util.HashMap;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/EntityNameRewriter.class */
public class EntityNameRewriter {
    private static final Map<String, String> entityNames = new HashMap();

    static {
        reg("commandblock_minecart", "command_block_minecart");
        reg("ender_crystal", "end_crystal");
        reg("evocation_fangs", "evoker_fangs");
        reg("evocation_illager", "evoker");
        reg("eye_of_ender_signal", "eye_of_ender");
        reg("fireworks_rocket", "firework_rocket");
        reg("illusion_illager", "illusioner");
        reg("snowman", "snow_golem");
        reg("villager_golem", "iron_golem");
        reg("vindication_illager", "vindicator");
        reg("xp_bottle", "experience_bottle");
        reg("xp_orb", "experience_orb");
    }

    private static void reg(String past, String future) {
        entityNames.put("minecraft:" + past, "minecraft:" + future);
    }

    public static String rewrite(String entName) {
        String entityName = entityNames.get(entName);
        if (entityName != null) {
            return entityName;
        }
        String entityName2 = entityNames.get("minecraft:" + entName);
        if (entityName2 != null) {
            return entityName2;
        }
        return entName;
    }
}
