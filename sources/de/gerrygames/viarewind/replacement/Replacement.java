package de.gerrygames.viarewind.replacement;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/replacement/Replacement.class */
public class Replacement {

    /* renamed from: id */
    private int f215id;
    private int data;
    private String name;
    private String resetName;
    private String bracketName;

    public Replacement(int id) {
        this(id, -1);
    }

    public Replacement(int id, int data) {
        this(id, data, null);
    }

    public Replacement(int id, String name) {
        this(id, -1, name);
    }

    public Replacement(int id, int data, String name) {
        this.f215id = id;
        this.data = data;
        this.name = name;
        if (name != null) {
            this.resetName = "§r" + name;
            this.bracketName = " §r§7(" + name + "§r§7)";
        }
    }

    public int getId() {
        return this.f215id;
    }

    public int getData() {
        return this.data;
    }

    public String getName() {
        return this.name;
    }

    public Item replace(Item item) {
        item.setIdentifier(this.f215id);
        if (this.data != -1) {
            item.setData((short) this.data);
        }
        if (this.name != null) {
            CompoundTag compoundTag = item.tag() == null ? new CompoundTag() : item.tag();
            if (!compoundTag.contains("display")) {
                compoundTag.put("display", new CompoundTag());
            }
            CompoundTag display = (CompoundTag) compoundTag.get("display");
            if (display.contains("Name")) {
                StringTag name = (StringTag) display.get("Name");
                if (!name.getValue().equals(this.resetName) && !name.getValue().endsWith(this.bracketName)) {
                    name.setValue(name.getValue() + this.bracketName);
                }
            } else {
                display.put("Name", new StringTag(this.resetName));
            }
            item.setTag(compoundTag);
        }
        return item;
    }

    public int replaceData(int data) {
        return this.data == -1 ? data : this.data;
    }
}
