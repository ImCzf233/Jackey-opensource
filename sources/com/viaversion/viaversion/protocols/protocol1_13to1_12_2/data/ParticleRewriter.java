package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/ParticleRewriter.class */
public class ParticleRewriter {
    private static final List<NewParticle> particles = new ArrayList();

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/ParticleRewriter$ParticleDataHandler.class */
    public interface ParticleDataHandler {
        Particle handler(Particle particle, Integer[] numArr);
    }

    static {
        add(34);
        add(19);
        add(18);
        add(21);
        add(4);
        add(43);
        add(22);
        add(42);
        add(42);
        add(6);
        add(14);
        add(37);
        add(30);
        add(12);
        add(26);
        add(17);
        add(0);
        add(44);
        add(10);
        add(9);
        add(1);
        add(24);
        add(32);
        add(33);
        add(35);
        add(15);
        add(23);
        add(31);
        add(-1);
        add(5);
        add(11, reddustHandler());
        add(29);
        add(34);
        add(28);
        add(25);
        add(2);
        add(27, iconcrackHandler());
        add(3, blockHandler());
        add(3, blockHandler());
        add(36);
        add(-1);
        add(13);
        add(8);
        add(16);
        add(7);
        add(40);
        add(20, blockHandler());
        add(41);
        add(38);
    }

    public static Particle rewriteParticle(int particleId, Integer[] data) {
        if (particleId >= particles.size()) {
            Via.getPlatform().getLogger().severe("Failed to transform particles with id " + particleId + " and data " + Arrays.toString(data));
            return null;
        }
        NewParticle rewrite = particles.get(particleId);
        return rewrite.handle(new Particle(rewrite.getId()), data);
    }

    private static void add(int newId) {
        particles.add(new NewParticle(newId, null));
    }

    private static void add(int newId, ParticleDataHandler dataHandler) {
        particles.add(new NewParticle(newId, dataHandler));
    }

    private static ParticleDataHandler reddustHandler() {
        return new ParticleDataHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.1
            @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.ParticleDataHandler
            public Particle handler(Particle particle, Integer[] data) {
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(ParticleRewriter.randomBool() ? 1.0f : 0.0f)));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(0.0f)));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(ParticleRewriter.randomBool() ? 1.0f : 0.0f)));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(1.0f)));
                return particle;
            }
        };
    }

    public static boolean randomBool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static ParticleDataHandler iconcrackHandler() {
        return new ParticleDataHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.2
            @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.ParticleDataHandler
            public Particle handler(Particle particle, Integer[] data) {
                Item item;
                if (data.length == 1) {
                    item = new DataItem(data[0].intValue(), (byte) 1, (short) 0, null);
                } else if (data.length == 2) {
                    item = new DataItem(data[0].intValue(), (byte) 1, data[1].shortValue(), null);
                } else {
                    return particle;
                }
                ((Protocol1_13To1_12_2) Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class)).getItemRewriter().handleItemToClient(item);
                particle.getArguments().add(new Particle.ParticleData(Type.FLAT_ITEM, item));
                return particle;
            }
        };
    }

    private static ParticleDataHandler blockHandler() {
        return new ParticleDataHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.3
            @Override // com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter.ParticleDataHandler
            public Particle handler(Particle particle, Integer[] data) {
                int value = data[0].intValue();
                int combined = ((value & 4095) << 4) | ((value >> 12) & 15);
                int newId = WorldPackets.toNewId(combined);
                particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Integer.valueOf(newId)));
                return particle;
            }
        };
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/data/ParticleRewriter$NewParticle.class */
    public static class NewParticle {

        /* renamed from: id */
        private final int f203id;
        private final ParticleDataHandler handler;

        public NewParticle(int id, ParticleDataHandler handler) {
            this.f203id = id;
            this.handler = handler;
        }

        public Particle handle(Particle particle, Integer[] data) {
            if (this.handler != null) {
                return this.handler.handler(particle, data);
            }
            return particle;
        }

        public int getId() {
            return this.f203id;
        }

        public ParticleDataHandler getHandler() {
            return this.handler;
        }
    }
}
