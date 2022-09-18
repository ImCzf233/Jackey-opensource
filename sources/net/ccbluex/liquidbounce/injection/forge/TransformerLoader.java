package net.ccbluex.liquidbounce.injection.forge;

import java.util.Map;
import net.ccbluex.liquidbounce.injection.transformers.ForgeNetworkTransformer;
import net.ccbluex.liquidbounce.injection.transformers.OptimizeTransformer;
import net.ccbluex.liquidbounce.script.remapper.injection.transformers.AbstractJavaLinkerTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/TransformerLoader.class */
public class TransformerLoader implements IFMLLoadingPlugin {
    public String[] getASMTransformerClass() {
        return new String[]{ForgeNetworkTransformer.class.getName(), OptimizeTransformer.class.getName(), AbstractJavaLinkerTransformer.class.getName()};
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
    }

    public String getAccessTransformerClass() {
        return null;
    }
}
