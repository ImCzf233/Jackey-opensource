package com.viaversion.viaversion.util;

import java.util.concurrent.ConcurrentSkipListMap;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/YamlConstructor.class */
public class YamlConstructor extends SafeConstructor {
    public YamlConstructor() {
        this.yamlClassConstructors.put(NodeId.mapping, new SafeConstructor.ConstructYamlMap());
        this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap());
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/YamlConstructor$Map.class */
    class Map extends SafeConstructor.ConstructYamlMap {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        Map() {
            super();
            YamlConstructor.this = this$0;
        }

        @Override // org.yaml.snakeyaml.constructor.SafeConstructor.ConstructYamlMap, org.yaml.snakeyaml.constructor.Construct
        public Object construct(Node node) {
            Object o = super.construct(node);
            if ((o instanceof Map) && !(o instanceof ConcurrentSkipListMap)) {
                return new ConcurrentSkipListMap((java.util.Map) o);
            }
            return o;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/YamlConstructor$ConstructYamlOmap.class */
    public class ConstructYamlOmap extends SafeConstructor.ConstructYamlOmap {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ConstructYamlOmap() {
            super();
            YamlConstructor.this = this$0;
        }

        @Override // org.yaml.snakeyaml.constructor.SafeConstructor.ConstructYamlOmap, org.yaml.snakeyaml.constructor.Construct
        public Object construct(Node node) {
            Object o = super.construct(node);
            if ((o instanceof Map) && !(o instanceof ConcurrentSkipListMap)) {
                return new ConcurrentSkipListMap((java.util.Map) o);
            }
            return o;
        }
    }
}
