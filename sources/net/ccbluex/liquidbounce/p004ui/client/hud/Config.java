package net.ccbluex.liquidbounce.p004ui.client.hud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

/* compiled from: Config.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018��2\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\n\u001a\u00020\u0006J\u0006\u0010\u000b\u001a\u00020\u0003R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n��¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/Config;", "", "config", "", "(Ljava/lang/String;)V", "hud", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;)V", "jsonArray", "Lcom/google/gson/JsonArray;", "toHUD", "toJson", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.Config */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/Config.class */
public final class Config {
    @NotNull
    private JsonArray jsonArray;

    public Config(@NotNull String config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.jsonArray = new JsonArray();
        Object fromJson = new Gson().fromJson(config, JsonArray.class);
        Intrinsics.checkNotNullExpressionValue(fromJson, "Gson().fromJson(config, JsonArray::class.java)");
        this.jsonArray = (JsonArray) fromJson;
    }

    public Config(@NotNull HUD hud) {
        Intrinsics.checkNotNullParameter(hud, "hud");
        this.jsonArray = new JsonArray();
        for (Element element : hud.getElements()) {
            JsonElement jsonObject = new JsonObject();
            jsonObject.addProperty("Type", element.getName());
            jsonObject.addProperty("X", Double.valueOf(element.getX()));
            jsonObject.addProperty("Y", Double.valueOf(element.getY()));
            jsonObject.addProperty("Scale", Float.valueOf(element.getScale()));
            jsonObject.addProperty("HorizontalFacing", element.getSide().getHorizontal().getSideName());
            jsonObject.addProperty("VerticalFacing", element.getSide().getVertical().getSideName());
            for (Value value : element.getValues()) {
                jsonObject.add(value.getName(), value.toJson());
            }
            this.jsonArray.add(jsonObject);
        }
    }

    @NotNull
    public final String toJson() {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(this.jsonArray);
        Intrinsics.checkNotNullExpressionValue(json, "GsonBuilder().setPrettyP…reate().toJson(jsonArray)");
        return json;
    }

    @NotNull
    public final HUD toHUD() {
        boolean z;
        Object obj;
        HUD hud = new HUD();
        try {
            Iterator it = this.jsonArray.iterator();
            while (it.hasNext()) {
                JsonObject jsonObject = (JsonElement) it.next();
                try {
                    if ((jsonObject instanceof JsonObject) && jsonObject.has("Type")) {
                        String type = jsonObject.get("Type").getAsString();
                        Class[] elements = HUD.Companion.getElements();
                        int i = 0;
                        int length = elements.length;
                        while (true) {
                            if (i < length) {
                                Class elementClass = elements[i];
                                i++;
                                String classType = ((ElementInfo) elementClass.getAnnotation(ElementInfo.class)).name();
                                if (Intrinsics.areEqual(classType, type)) {
                                    Element element = elementClass.newInstance();
                                    element.setX(jsonObject.get("X").getAsInt());
                                    element.setY(jsonObject.get("Y").getAsInt());
                                    element.setScale(jsonObject.get("Scale").getAsFloat());
                                    Side.Horizontal.Companion companion = Side.Horizontal.Companion;
                                    String asString = jsonObject.get("HorizontalFacing").getAsString();
                                    Intrinsics.checkNotNullExpressionValue(asString, "jsonObject[\"HorizontalFacing\"].asString");
                                    Side.Horizontal byName = companion.getByName(asString);
                                    Intrinsics.checkNotNull(byName);
                                    Side.Vertical.Companion companion2 = Side.Vertical.Companion;
                                    String asString2 = jsonObject.get("VerticalFacing").getAsString();
                                    Intrinsics.checkNotNullExpressionValue(asString2, "jsonObject[\"VerticalFacing\"].asString");
                                    Side.Vertical byName2 = companion2.getByName(asString2);
                                    Intrinsics.checkNotNull(byName2);
                                    element.setSide(new Side(byName, byName2));
                                    for (Value value : element.getValues()) {
                                        if (jsonObject.has(value.getName())) {
                                            JsonElement jsonElement = jsonObject.get(value.getName());
                                            Intrinsics.checkNotNullExpressionValue(jsonElement, "jsonObject[value.name]");
                                            value.fromJson(jsonElement);
                                        }
                                    }
                                    if (jsonObject.has("font")) {
                                        Iterator<T> it2 = element.getValues().iterator();
                                        while (true) {
                                            if (!it2.hasNext()) {
                                                obj = null;
                                                break;
                                            }
                                            Object next = it2.next();
                                            Value it3 = (Value) next;
                                            if (it3 instanceof FontValue) {
                                                obj = next;
                                                break;
                                            }
                                        }
                                        Value value2 = (Value) obj;
                                        if (value2 != null) {
                                            JsonElement jsonElement2 = jsonObject.get("font");
                                            Intrinsics.checkNotNullExpressionValue(jsonElement2, "jsonObject[\"font\"]");
                                            value2.fromJson(jsonElement2);
                                        }
                                    }
                                    Intrinsics.checkNotNullExpressionValue(element, "element");
                                    hud.addElement(element);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    ClientUtils.getLogger().error("Error while loading custom hud element from config.", e);
                }
            }
            Class[] elements2 = HUD.Companion.getElements();
            int i2 = 0;
            int length2 = elements2.length;
            while (i2 < length2) {
                Class elementClass2 = elements2[i2];
                i2++;
                if (((ElementInfo) elementClass2.getAnnotation(ElementInfo.class)).force()) {
                    Iterable $this$none$iv = hud.getElements();
                    if (!($this$none$iv instanceof Collection) || !((Collection) $this$none$iv).isEmpty()) {
                        Iterator<T> it4 = $this$none$iv.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                z = true;
                                break;
                            }
                            Object element$iv = it4.next();
                            Element it5 = (Element) element$iv;
                            if (Intrinsics.areEqual(it5.getClass(), elementClass2)) {
                                z = false;
                                break;
                            }
                        }
                    } else {
                        z = true;
                    }
                    if (z) {
                        Element newInstance = elementClass2.newInstance();
                        Intrinsics.checkNotNullExpressionValue(newInstance, "elementClass.newInstance()");
                        hud.addElement(newInstance);
                    }
                }
            }
            return hud;
        } catch (Exception e2) {
            ClientUtils.getLogger().error("Error while loading custom hud config.", e2);
            return HUD.Companion.createDefault();
        }
    }
}
