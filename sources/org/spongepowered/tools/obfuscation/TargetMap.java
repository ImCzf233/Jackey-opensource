package org.spongepowered.tools.obfuscation;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/TargetMap.class */
public final class TargetMap extends HashMap<TypeReference, Set<TypeReference>> {
    private static final long serialVersionUID = 1;
    private final String sessionId;

    private TargetMap() {
        this(String.valueOf(System.currentTimeMillis()));
    }

    private TargetMap(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void registerTargets(AnnotatedMixin mixin) {
        registerTargets(mixin.getTargets(), mixin.getHandle());
    }

    public void registerTargets(List<TypeHandle> targets, TypeHandle mixin) {
        for (TypeHandle target : targets) {
            addMixin(target, mixin);
        }
    }

    public void addMixin(TypeHandle target, TypeHandle mixin) {
        addMixin(target.getReference(), mixin.getReference());
    }

    public void addMixin(String target, String mixin) {
        addMixin(new TypeReference(target), new TypeReference(mixin));
    }

    public void addMixin(TypeReference target, TypeReference mixin) {
        Set<TypeReference> mixins = getMixinsFor(target);
        mixins.add(mixin);
    }

    public Collection<TypeReference> getMixinsTargeting(TypeElement target) {
        return getMixinsTargeting(new TypeHandle(target));
    }

    public Collection<TypeReference> getMixinsTargeting(TypeHandle target) {
        return getMixinsTargeting(target.getReference());
    }

    public Collection<TypeReference> getMixinsTargeting(TypeReference target) {
        return Collections.unmodifiableCollection(getMixinsFor(target));
    }

    private Set<TypeReference> getMixinsFor(TypeReference target) {
        HashSet mixins = get(target);
        if (mixins == null) {
            mixins = new HashSet();
            put(target, mixins);
        }
        return mixins;
    }

    public void readImports(File file) throws IOException {
        if (!file.isFile()) {
            return;
        }
        for (String line : Files.readLines(file, Charset.defaultCharset())) {
            String[] parts = line.split("\t");
            if (parts.length == 2) {
                addMixin(parts[1], parts[0]);
            }
        }
    }

    public void write(boolean temp) {
        ObjectOutputStream oos = null;
        try {
            try {
                File sessionFile = getSessionFile(this.sessionId);
                if (temp) {
                    sessionFile.deleteOnExit();
                }
                FileOutputStream fout = new FileOutputStream(sessionFile, true);
                oos = new ObjectOutputStream(fout);
                oos.writeObject(this);
                if (oos == null) {
                    return;
                }
                try {
                    oos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (Throwable th) {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException ex2) {
                        ex2.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception ex3) {
            ex3.printStackTrace();
            if (oos == null) {
                return;
            }
            try {
                oos.close();
            } catch (IOException ex4) {
                ex4.printStackTrace();
            }
        }
    }

    private static TargetMap read(File sessionFile) {
        ObjectInputStream objectinputstream = null;
        try {
            try {
                FileInputStream streamIn = new FileInputStream(sessionFile);
                objectinputstream = new ObjectInputStream(streamIn);
                TargetMap targetMap = (TargetMap) objectinputstream.readObject();
                if (objectinputstream != null) {
                    try {
                        objectinputstream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                return targetMap;
            } catch (Exception e) {
                e.printStackTrace();
                if (objectinputstream == null) {
                    return null;
                }
                try {
                    objectinputstream.close();
                    return null;
                } catch (IOException ex2) {
                    ex2.printStackTrace();
                    return null;
                }
            }
        } catch (Throwable th) {
            if (objectinputstream != null) {
                try {
                    objectinputstream.close();
                } catch (IOException ex3) {
                    ex3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static TargetMap create(String sessionId) {
        TargetMap map;
        if (sessionId != null) {
            File sessionFile = getSessionFile(sessionId);
            if (sessionFile.exists() && (map = read(sessionFile)) != null) {
                return map;
            }
        }
        return new TargetMap();
    }

    private static File getSessionFile(String sessionId) {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        return new File(tempDir, String.format("mixin-targetdb-%s.tmp", sessionId));
    }
}
