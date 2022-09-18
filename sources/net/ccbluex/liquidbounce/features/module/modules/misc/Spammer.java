package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Random;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.apache.log4j.Level;

@ModuleInfo(name = "Spammer", description = "Spams the chat with a given message.", category = ModuleCategory.MISC)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/Spammer.class */
public class Spammer extends Module {
    private final IntegerValue maxDelayValue = new IntegerValue("MaxDelay", 1000, 0, Level.TRACE_INT, "ms") { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.Spammer.1
        public void onChanged(Integer oldValue, Integer newValue) {
            int minDelayValueObject = Spammer.this.minDelayValue.get().intValue();
            if (minDelayValueObject > newValue.intValue()) {
                set((C16661) Integer.valueOf(minDelayValueObject));
            }
            Spammer.this.delay = TimeUtils.randomDelay(Spammer.this.minDelayValue.get().intValue(), Spammer.this.maxDelayValue.get().intValue());
        }
    };
    private final IntegerValue minDelayValue = new IntegerValue("MinDelay", 500, 0, Level.TRACE_INT, "ms") { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.Spammer.2
        public void onChanged(Integer oldValue, Integer newValue) {
            int maxDelayValueObject = Spammer.this.maxDelayValue.get().intValue();
            if (maxDelayValueObject < newValue.intValue()) {
                set((C16672) Integer.valueOf(maxDelayValueObject));
            }
            Spammer.this.delay = TimeUtils.randomDelay(Spammer.this.minDelayValue.get().intValue(), Spammer.this.maxDelayValue.get().intValue());
        }
    };
    private final TextValue messageValue = new TextValue("Message", "Example text");
    private final BoolValue customValue = new BoolValue("Custom", false);
    private final TextValue blankText = new TextValue("Placeholder guide", "", () -> {
        return this.customValue.get();
    });
    private final TextValue guideFloat = new TextValue("%f", "Random float", () -> {
        return this.customValue.get();
    });
    private final TextValue guideInt = new TextValue("%i", "Random integer (max length 10000)", () -> {
        return this.customValue.get();
    });
    private final TextValue guideString = new TextValue("%s", "Random string (max length 9)", () -> {
        return this.customValue.get();
    });
    private final TextValue guideShortString = new TextValue("%ss", "Random short string (max length 5)", () -> {
        return this.customValue.get();
    });
    private final TextValue guideLongString = new TextValue("%ls", "Random long string (max length 16)", () -> {
        return this.customValue.get();
    });
    private final MSTimer msTimer = new MSTimer();
    private long delay = TimeUtils.randomDelay(this.minDelayValue.get().intValue(), this.maxDelayValue.get().intValue());

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (this.msTimer.hasTimePassed(this.delay)) {
            f362mc.field_71439_g.func_71165_d(this.customValue.get().booleanValue() ? replace(this.messageValue.get()) : this.messageValue.get() + " >" + RandomUtils.randomString(5 + new Random().nextInt(5)) + "<");
            this.msTimer.reset();
            this.delay = TimeUtils.randomDelay(this.minDelayValue.get().intValue(), this.maxDelayValue.get().intValue());
        }
    }

    private String replace(String object) {
        Random r = new Random();
        while (object.contains("%f")) {
            object = object.substring(0, object.indexOf("%f")) + r.nextFloat() + object.substring(object.indexOf("%f") + "%f".length());
        }
        while (object.contains("%i")) {
            object = object.substring(0, object.indexOf("%i")) + r.nextInt(10000) + object.substring(object.indexOf("%i") + "%i".length());
        }
        while (object.contains("%s")) {
            object = object.substring(0, object.indexOf("%s")) + RandomUtils.randomString(r.nextInt(8) + 1) + object.substring(object.indexOf("%s") + "%s".length());
        }
        while (object.contains("%ss")) {
            object = object.substring(0, object.indexOf("%ss")) + RandomUtils.randomString(r.nextInt(4) + 1) + object.substring(object.indexOf("%ss") + "%ss".length());
        }
        while (object.contains("%ls")) {
            object = object.substring(0, object.indexOf("%ls")) + RandomUtils.randomString(r.nextInt(15) + 1) + object.substring(object.indexOf("%ls") + "%ls".length());
        }
        return object;
    }
}
