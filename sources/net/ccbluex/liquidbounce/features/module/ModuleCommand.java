package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/* compiled from: ModuleCommand.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018��2\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\b\u0002\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\u0002\u0010\u0007J\u001b\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016¢\u0006\u0002\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\b\u0010\tR\u001b\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\b\n��\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "(Lnet/ccbluex/liquidbounce/features/module/Module;Ljava/util/List;)V", "getModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "getValues", "()Ljava/util/List;", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/ModuleCommand.class */
public final class ModuleCommand extends Command {
    @NotNull
    private final Module module;
    @NotNull
    private final List<Value<?>> values;

    public /* synthetic */ ModuleCommand(Module module, List<Value<?>> list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(module, (i & 2) != 0 ? module.getValues() : list);
    }

    @NotNull
    public final Module getModule() {
        return this.module;
    }

    @NotNull
    public final List<Value<?>> getValues() {
        return this.values;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ModuleCommand(@org.jetbrains.annotations.NotNull net.ccbluex.liquidbounce.features.module.Module r5, @org.jetbrains.annotations.NotNull java.util.List<? extends net.ccbluex.liquidbounce.value.Value<?>> r6) {
        /*
            r4 = this;
            r0 = r5
            java.lang.String r1 = "module"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r0 = r6
            java.lang.String r1 = "values"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r0 = r4
            r1 = r5
            java.lang.String r1 = r1.getName()
            java.lang.String r1 = r1.toLowerCase()
            r8 = r1
            r1 = r8
            java.lang.String r2 = "this as java.lang.String).toLowerCase()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            r1 = r8
            r2 = 0
            r7 = r2
            r2 = 0
            java.lang.String[] r2 = new java.lang.String[r2]
            r0.<init>(r1, r2)
            r0 = r4
            r1 = r5
            r0.module = r1
            r0 = r4
            r1 = r6
            r0.values = r1
            r0 = r4
            java.util.List<net.ccbluex.liquidbounce.value.Value<?>> r0 = r0.values
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L49
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r1 = r0
            java.lang.String r2 = "Values are empty!"
            r1.<init>(r2)
            throw r0
        L49:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.ModuleCommand.<init>(net.ccbluex.liquidbounce.features.module.Module, java.util.List):void");
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        int id;
        Intrinsics.checkNotNullParameter(args, "args");
        Iterable $this$filter$iv = this.values;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Value it = (Value) element$iv$iv;
            if (!(it instanceof FontValue)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        String valueNames = CollectionsKt.joinToString$default((List) destination$iv$iv, "/", null, null, 0, null, ModuleCommand$execute$valueNames$2.INSTANCE, 30, null);
        String moduleName = this.module.getName().toLowerCase();
        Intrinsics.checkNotNullExpressionValue(moduleName, "this as java.lang.String).toLowerCase()");
        if (args.length < 2) {
            chatSyntax(this.values.size() == 1 ? moduleName + ' ' + valueNames + " <value>" : moduleName + " <" + valueNames + '>');
            return;
        }
        Value value = this.module.getValue(args[1]);
        if (value == null) {
            chatSyntax(moduleName + " <" + valueNames + '>');
        } else if (value instanceof BoolValue) {
            boolean newValue = !((BoolValue) value).get().booleanValue();
            ((BoolValue) value).set(Boolean.valueOf(newValue));
            chat("§7" + this.module.getName() + " §8" + args[1] + "§7 was toggled " + (newValue ? "§8on§7" : "§8off§7."));
            playEdit();
        } else if (args.length < 3) {
            if ((value instanceof IntegerValue) || (value instanceof FloatValue) || (value instanceof TextValue)) {
                StringBuilder append = new StringBuilder().append(moduleName).append(' ');
                String lowerCase = args[1].toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                chatSyntax(append.append(lowerCase).append(" <value>").toString());
            } else if (value instanceof ListValue) {
                StringBuilder append2 = new StringBuilder().append(moduleName).append(' ');
                String lowerCase2 = args[1].toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                StringBuilder append3 = append2.append(lowerCase2).append(" <");
                String lowerCase3 = ArraysKt.joinToString$default(((ListValue) value).getValues(), "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null).toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase3, "this as java.lang.String).toLowerCase()");
                chatSyntax(append3.append(lowerCase3).append('>').toString());
            }
        } else {
            try {
                if (value instanceof BlockValue) {
                    try {
                        id = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        id = Block.func_149682_b(Block.func_149684_b(args[2]));
                        if (id <= 0) {
                            chat("§7Block §8" + args[2] + "§7 does not exist!");
                            return;
                        }
                    }
                    ((BlockValue) value).set((BlockValue) Integer.valueOf(id));
                    StringBuilder append4 = new StringBuilder().append("§7").append(this.module.getName()).append(" §8");
                    String lowerCase4 = args[1].toLowerCase();
                    Intrinsics.checkNotNullExpressionValue(lowerCase4, "this as java.lang.String).toLowerCase()");
                    chat(append4.append(lowerCase4).append("§7 was set to §8").append(BlockUtils.getBlockName(id)).append("§7.").toString());
                    playEdit();
                    return;
                }
                if (value instanceof IntegerValue) {
                    ((IntegerValue) value).set((IntegerValue) Integer.valueOf(Integer.parseInt(args[2])));
                } else if (value instanceof FloatValue) {
                    ((FloatValue) value).set((FloatValue) Float.valueOf(Float.parseFloat(args[2])));
                } else if (value instanceof ListValue) {
                    if (!((ListValue) value).contains(args[2])) {
                        StringBuilder append5 = new StringBuilder().append(moduleName).append(' ');
                        String lowerCase5 = args[1].toLowerCase();
                        Intrinsics.checkNotNullExpressionValue(lowerCase5, "this as java.lang.String).toLowerCase()");
                        StringBuilder append6 = append5.append(lowerCase5).append(" <");
                        String lowerCase6 = ArraysKt.joinToString$default(((ListValue) value).getValues(), "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null).toLowerCase();
                        Intrinsics.checkNotNullExpressionValue(lowerCase6, "this as java.lang.String).toLowerCase()");
                        chatSyntax(append6.append(lowerCase6).append('>').toString());
                        return;
                    }
                    ((ListValue) value).set(args[2]);
                } else if (value instanceof TextValue) {
                    String completeString = StringUtils.toCompleteString(args, 2);
                    Intrinsics.checkNotNullExpressionValue(completeString, "toCompleteString(args, 2)");
                    ((TextValue) value).set(completeString);
                }
                chat("§7" + this.module.getName() + " §8" + args[1] + "§7 was set to §8" + value.get() + "§7.");
                playEdit();
            } catch (NumberFormatException e2) {
                chat("§8" + args[2] + "§7 cannot be converted to number!");
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List<String> tabComplete(@NotNull String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        switch (args.length) {
            case 1:
                Iterable $this$filter$iv = this.values;
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    Value it = (Value) element$iv$iv;
                    if (!(it instanceof FontValue) && StringsKt.startsWith(it.getName(), args[0], true)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                Iterable $this$map$iv = (List) destination$iv$iv;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    Value it2 = (Value) item$iv$iv;
                    String lowerCase = it2.getName().toLowerCase();
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
                    destination$iv$iv2.add(lowerCase);
                }
                return (List) destination$iv$iv2;
            case 2:
                Value<?> value = this.module.getValue(args[0]);
                if (value instanceof BlockValue) {
                    Iterable func_148742_b = Block.field_149771_c.func_148742_b();
                    Intrinsics.checkNotNullExpressionValue(func_148742_b, "blockRegistry.keys");
                    Iterable $this$map$iv2 = func_148742_b;
                    Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
                    for (Object item$iv$iv2 : $this$map$iv2) {
                        ResourceLocation it3 = (ResourceLocation) item$iv$iv2;
                        String func_110623_a = it3.func_110623_a();
                        Intrinsics.checkNotNullExpressionValue(func_110623_a, "it.resourcePath");
                        String lowerCase2 = func_110623_a.toLowerCase();
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "this as java.lang.String).toLowerCase()");
                        destination$iv$iv3.add(lowerCase2);
                    }
                    Iterable $this$filter$iv2 = (List) destination$iv$iv3;
                    Collection destination$iv$iv4 = new ArrayList();
                    for (Object element$iv$iv2 : $this$filter$iv2) {
                        String it4 = (String) element$iv$iv2;
                        if (StringsKt.startsWith(it4, args[1], true)) {
                            destination$iv$iv4.add(element$iv$iv2);
                        }
                    }
                    return (List) destination$iv$iv4;
                } else if (value instanceof ListValue) {
                    Iterable $this$forEach$iv = this.values;
                    for (Object element$iv : $this$forEach$iv) {
                        Value value2 = (Value) element$iv;
                        if (StringsKt.equals(value2.getName(), args[0], true) && (value2 instanceof ListValue)) {
                            String[] values = ((ListValue) value2).getValues();
                            Collection destination$iv$iv5 = new ArrayList();
                            int i = 0;
                            int length = values.length;
                            while (i < length) {
                                String str = values[i];
                                i++;
                                if (StringsKt.startsWith(str, args[1], true)) {
                                    destination$iv$iv5.add(str);
                                }
                            }
                            return (List) destination$iv$iv5;
                        }
                    }
                    return CollectionsKt.emptyList();
                } else {
                    return CollectionsKt.emptyList();
                }
            default:
                return CollectionsKt.emptyList();
        }
    }
}
