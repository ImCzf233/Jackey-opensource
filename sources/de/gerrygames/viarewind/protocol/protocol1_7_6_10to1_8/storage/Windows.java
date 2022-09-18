package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.HashMap;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Windows.class */
public class Windows extends StoredObject {
    public HashMap<Short, Short> types = new HashMap<>();
    public HashMap<Short, Furnace> furnace = new HashMap<>();
    public short levelCost = 0;
    public short anvilId = -1;

    public Windows(UserConnection user) {
        super(user);
    }

    public short get(short windowId) {
        return this.types.getOrDefault(Short.valueOf(windowId), (short) -1).shortValue();
    }

    public void remove(short windowId) {
        this.types.remove(Short.valueOf(windowId));
        this.furnace.remove(Short.valueOf(windowId));
    }

    public static int getInventoryType(String name) {
        boolean z = true;
        switch (name.hashCode()) {
            case -1879003021:
                if (name.equals("minecraft:villager")) {
                    z = true;
                    break;
                }
                break;
            case -1719356277:
                if (name.equals("minecraft:furnace")) {
                    z = true;
                    break;
                }
                break;
            case -1366784614:
                if (name.equals("EntityHorse")) {
                    z = true;
                    break;
                }
                break;
            case -1293651279:
                if (name.equals("minecraft:beacon")) {
                    z = true;
                    break;
                }
                break;
            case -1150744385:
                if (name.equals("minecraft:anvil")) {
                    z = true;
                    break;
                }
                break;
            case -1149092108:
                if (name.equals("minecraft:chest")) {
                    z = true;
                    break;
                }
                break;
            case -1124126594:
                if (name.equals("minecraft:crafting_table")) {
                    z = true;
                    break;
                }
                break;
            case -1112182111:
                if (name.equals("minecraft:hopper")) {
                    z = true;
                    break;
                }
                break;
            case 319164197:
                if (name.equals("minecraft:enchanting_table")) {
                    z = true;
                    break;
                }
                break;
            case 712019713:
                if (name.equals("minecraft:dropper")) {
                    z = true;
                    break;
                }
                break;
            case 1438413556:
                if (name.equals("minecraft:container")) {
                    z = false;
                    break;
                }
                break;
            case 1649065834:
                if (name.equals("minecraft:brewing_stand")) {
                    z = true;
                    break;
                }
                break;
            case 2090881320:
                if (name.equals("minecraft:dispenser")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return 0;
            case true:
                return 0;
            case true:
                return 1;
            case true:
                return 2;
            case true:
                return 3;
            case true:
                return 4;
            case true:
                return 5;
            case true:
                return 6;
            case true:
                return 7;
            case true:
                return 8;
            case true:
                return 9;
            case true:
                return 10;
            case true:
                return 11;
            default:
                throw new IllegalArgumentException("Unknown type " + name);
        }
    }

    /* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/storage/Windows$Furnace.class */
    public static class Furnace {
        private short fuelLeft = 0;
        private short maxFuel = 0;
        private short progress = 0;
        private short maxProgress = 200;

        public short getFuelLeft() {
            return this.fuelLeft;
        }

        public short getMaxFuel() {
            return this.maxFuel;
        }

        public short getProgress() {
            return this.progress;
        }

        public short getMaxProgress() {
            return this.maxProgress;
        }

        public void setFuelLeft(short fuelLeft) {
            this.fuelLeft = fuelLeft;
        }

        public void setMaxFuel(short maxFuel) {
            this.maxFuel = maxFuel;
        }

        public void setProgress(short progress) {
            this.progress = progress;
        }

        public void setMaxProgress(short maxProgress) {
            this.maxProgress = maxProgress;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Furnace)) {
                return false;
            }
            Furnace other = (Furnace) o;
            return other.canEqual(this) && getFuelLeft() == other.getFuelLeft() && getMaxFuel() == other.getMaxFuel() && getProgress() == other.getProgress() && getMaxProgress() == other.getMaxProgress();
        }

        protected boolean canEqual(Object other) {
            return other instanceof Furnace;
        }

        public int hashCode() {
            int result = (1 * 59) + getFuelLeft();
            return (((((result * 59) + getMaxFuel()) * 59) + getProgress()) * 59) + getMaxProgress();
        }

        public String toString() {
            return "Windows.Furnace(fuelLeft=" + ((int) getFuelLeft()) + ", maxFuel=" + ((int) getMaxFuel()) + ", progress=" + ((int) getProgress()) + ", maxProgress=" + ((int) getMaxProgress()) + ")";
        }
    }
}
