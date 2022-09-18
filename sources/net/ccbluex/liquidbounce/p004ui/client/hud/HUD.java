package net.ccbluex.liquidbounce.p004ui.client.hud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Armor;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Arraylist;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Effects;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Graph;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Image;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Inventory;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Model;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Notifications;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.PlayerList;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Radar;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.ScoreboardElement;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.SpeedGraph;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.TabGUI;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Target;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Text;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.util.Constants;

/* compiled from: HUD.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n��\n\u0002\u0010\b\n\u0002\b\r\b\u0016\u0018�� &2\u00020\u0001:\u0001&B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020��2\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nJ\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u001e\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001aJ\u0016\u0010\u001f\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001aJ\u0006\u0010 \u001a\u00020\u0012J\u000e\u0010!\u001a\u00020��2\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\"\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nJ\u000e\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u000fJ\u0006\u0010%\u001a\u00020\u0012R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\b\b\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004¢\u0006\b\n��\u001a\u0004\b\u000b\u0010\u0007¨\u0006'"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "elements", "", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getElements", "()Ljava/util/List;", "elements$1", "notifications", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "getNotifications", "addElement", "element", "addNotification", "", "notification", "clearElements", "", "handleDamage", "ent", "Lnet/minecraft/entity/player/EntityPlayer;", "handleKey", "c", "", "keyCode", "", "handleMouseClick", "mouseX", "mouseY", "button", "handleMouseMove", "handleMouseReleased", "removeElement", "removeNotification", "render", "designer", "update", "Companion", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.HUD */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/HUD.class */
public class HUD extends MinecraftInstance {
    @NotNull
    private final List<Element> elements$1 = new ArrayList();
    @NotNull
    private final List<Notification> notifications = new ArrayList();
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final Class<? extends Element>[] elements = {Armor.class, Arraylist.class, Effects.class, Image.class, Model.class, Notifications.class, TabGUI.class, Text.class, ScoreboardElement.class, Target.class, Inventory.class, SpeedGraph.class, PlayerList.class, Radar.class, Graph.class};

    @JvmStatic
    @NotNull
    public static final HUD createDefault() {
        return Companion.createDefault();
    }

    @NotNull
    public final List<Element> getElements() {
        return this.elements$1;
    }

    @NotNull
    public final List<Notification> getNotifications() {
        return this.notifications;
    }

    /* compiled from: HUD.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0007R!\u0010\u0003\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/HUD$Companion;", "", "()V", "elements", "", Constants.CLASS, "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getElements", "()[Ljava/lang/Class;", "[Ljava/lang/Class;", "createDefault", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.HUD$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/HUD$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final Class<? extends Element>[] getElements() {
            return HUD.elements;
        }

        @JvmStatic
        @NotNull
        public final HUD createDefault() {
            return new HUD().addElement(Text.Companion.defaultClient()).addElement(new Arraylist(0.0d, 0.0d, 0.0f, null, 15, null)).addElement(new ScoreboardElement(0.0d, 0.0d, 0.0f, null, 15, null)).addElement(new Armor(0.0d, 0.0d, 0.0f, null, 15, null)).addElement(new Effects(0.0d, 0.0d, 0.0f, null, 15, null)).addElement(new Notifications(0.0d, 0.0d, 0.0f, null, 15, null)).addElement(new SpeedGraph(0.0d, 0.0d, 0.0f, null, 15, null));
        }
    }

    public final void render(boolean designer) {
        Iterable $this$forEach$iv = this.elements$1;
        for (Object element$iv : $this$forEach$iv) {
            Element element = (Element) element$iv;
            GL11.glPushMatrix();
            if (!element.getInfo().disableScale()) {
                if (!(element.getScale() == 1.0f)) {
                    GL11.glScalef(element.getScale(), element.getScale(), element.getScale());
                }
            }
            GL11.glTranslated(element.getRenderX(), element.getRenderY(), 0.0d);
            try {
                element.setBorder(element.drawElement());
                if (designer) {
                    Border border = element.getBorder();
                    if (border != null) {
                        border.draw();
                    }
                }
            } catch (Exception ex) {
                ClientUtils.getLogger().error("Something went wrong while drawing " + element.getName() + " element in HUD.", ex);
            }
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }

    public final void update() {
        for (Element element : this.elements$1) {
            element.updateElement();
        }
    }

    public final void handleDamage(@NotNull EntityPlayer ent) {
        Intrinsics.checkNotNullParameter(ent, "ent");
        for (Element element : this.elements$1) {
            if (element.getInfo().retrieveDamage()) {
                element.handleDamage(ent);
            }
        }
    }

    public final void handleMouseClick(int mouseX, int mouseY, int button) {
        for (Element element : this.elements$1) {
            element.handleMouseClick((mouseX / element.getScale()) - element.getRenderX(), (mouseY / element.getScale()) - element.getRenderY(), button);
        }
        if (button == 0) {
            for (Element element2 : CollectionsKt.reversed(this.elements$1)) {
                if (element2.isInBorder((mouseX / element2.getScale()) - element2.getRenderX(), (mouseY / element2.getScale()) - element2.getRenderY())) {
                    element2.setDrag(true);
                    this.elements$1.remove(element2);
                    this.elements$1.add(element2);
                    List $this$sortBy$iv = this.elements$1;
                    if ($this$sortBy$iv.size() <= 1) {
                        return;
                    }
                    CollectionsKt.sortWith($this$sortBy$iv, new Comparator() { // from class: net.ccbluex.liquidbounce.ui.client.hud.HUD$handleMouseClick$$inlined$sortBy$1
                        @Override // java.util.Comparator
                        public final int compare(T t, T t2) {
                            Element it = (Element) t;
                            Element it2 = (Element) t2;
                            return ComparisonsKt.compareValues(Integer.valueOf(-it.getInfo().priority()), Integer.valueOf(-it2.getInfo().priority()));
                        }
                    });
                    return;
                }
            }
        }
    }

    public final void handleMouseReleased() {
        for (Element element : this.elements$1) {
            element.setDrag(false);
        }
    }

    public final void handleMouseMove(int mouseX, int mouseY) {
        if (!(MinecraftInstance.f362mc.field_71462_r instanceof GuiHudDesigner)) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(MinecraftInstance.f362mc);
        for (Element element : this.elements$1) {
            float scaledX = mouseX / element.getScale();
            float scaledY = mouseY / element.getScale();
            float prevMouseX = element.getPrevMouseX();
            float prevMouseY = element.getPrevMouseY();
            element.setPrevMouseX(scaledX);
            element.setPrevMouseY(scaledY);
            if (element.getDrag()) {
                float moveX = scaledX - prevMouseX;
                float moveY = scaledY - prevMouseY;
                if (moveX == 0.0f) {
                    if (moveY == 0.0f) {
                    }
                }
                Border border = element.getBorder();
                if (border != null) {
                    float minX = Math.min(border.getX(), border.getX2()) + 1;
                    float minY = Math.min(border.getY(), border.getY2()) + 1;
                    float maxX = Math.max(border.getX(), border.getX2()) - 1;
                    float maxY = Math.max(border.getY(), border.getY2()) - 1;
                    float width = scaledResolution.func_78326_a() / element.getScale();
                    float height = scaledResolution.func_78328_b() / element.getScale();
                    if ((element.getRenderX() + minX + moveX >= 0.0d || moveX > 0.0f) && (element.getRenderX() + maxX + moveX <= width || moveX < 0.0f)) {
                        element.setRenderX(moveX);
                    }
                    if (element.getRenderY() + minY + moveY >= 0.0d || moveY > 0.0f) {
                        if (element.getRenderY() + maxY + moveY <= height || moveY < 0.0f) {
                            element.setRenderY(moveY);
                        }
                    }
                }
            }
        }
    }

    public final void handleKey(char c, int keyCode) {
        for (Element element : this.elements$1) {
            element.handleKey(c, keyCode);
        }
    }

    @NotNull
    public final HUD addElement(@NotNull Element element) {
        Intrinsics.checkNotNullParameter(element, "element");
        this.elements$1.add(element);
        List $this$sortBy$iv = this.elements$1;
        if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new Comparator() { // from class: net.ccbluex.liquidbounce.ui.client.hud.HUD$addElement$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Element it = (Element) t;
                    Element it2 = (Element) t2;
                    return ComparisonsKt.compareValues(Integer.valueOf(-it.getInfo().priority()), Integer.valueOf(-it2.getInfo().priority()));
                }
            });
        }
        element.updateElement();
        return this;
    }

    @NotNull
    public final HUD removeElement(@NotNull Element element) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.destroyElement();
        this.elements$1.remove(element);
        List $this$sortBy$iv = this.elements$1;
        if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new Comparator() { // from class: net.ccbluex.liquidbounce.ui.client.hud.HUD$removeElement$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Element it = (Element) t;
                    Element it2 = (Element) t2;
                    return ComparisonsKt.compareValues(Integer.valueOf(-it.getInfo().priority()), Integer.valueOf(-it2.getInfo().priority()));
                }
            });
        }
        return this;
    }

    public final void clearElements() {
        for (Element element : this.elements$1) {
            element.destroyElement();
        }
        this.elements$1.clear();
    }

    public final boolean addNotification(@NotNull Notification notification) {
        boolean z;
        Intrinsics.checkNotNullParameter(notification, "notification");
        Iterable $this$any$iv = this.elements$1;
        if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
            Iterator<T> it = $this$any$iv.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    Element it2 = (Element) element$iv;
                    if (it2 instanceof Notifications) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
        } else {
            z = false;
        }
        return z && this.notifications.add(notification);
    }

    public final boolean removeNotification(@NotNull Notification notification) {
        Intrinsics.checkNotNullParameter(notification, "notification");
        return this.notifications.remove(notification);
    }
}
