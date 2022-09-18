package org.spongepowered.asm.mixin.refmap;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/refmap/ReferenceMapper.class */
public final class ReferenceMapper implements IReferenceMapper, Serializable {
    private static final long serialVersionUID = 2;
    public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
    public static final ReferenceMapper DEFAULT_MAPPER = new ReferenceMapper(true, "invalid");
    private final Map<String, Map<String, String>> mappings;
    private final Map<String, Map<String, Map<String, String>>> data;
    private final transient boolean readOnly;
    private transient String context;
    private transient String resource;

    public ReferenceMapper() {
        this(false, DEFAULT_RESOURCE);
    }

    private ReferenceMapper(boolean readOnly, String resource) {
        this.mappings = Maps.newHashMap();
        this.data = Maps.newHashMap();
        this.context = null;
        this.readOnly = readOnly;
        this.resource = resource;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public boolean isDefault() {
        return this.readOnly;
    }

    private void setResourceName(String resource) {
        if (!this.readOnly) {
            this.resource = resource != null ? resource : "<unknown resource>";
        }
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String getResourceName() {
        return this.resource;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String getStatus() {
        return isDefault() ? "No refMap loaded." : "Using refmap " + getResourceName();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String getContext() {
        return this.context;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public void setContext(String context) {
        this.context = context;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String remap(String className, String reference) {
        return remapWithContext(this.context, className, reference);
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String remapWithContext(String context, String className, String reference) {
        Map<String, Map<String, String>> mappings = this.mappings;
        if (context != null) {
            mappings = this.data.get(context);
            if (mappings == null) {
                mappings = this.mappings;
            }
        }
        return remap(mappings, className, reference);
    }

    private String remap(Map<String, Map<String, String>> mappings, String className, String reference) {
        if (className == null) {
            for (Map<String, String> mapping : mappings.values()) {
                if (mapping.containsKey(reference)) {
                    return mapping.get(reference);
                }
            }
        }
        Map<String, String> classMappings = mappings.get(className);
        if (classMappings == null) {
            return reference;
        }
        String remappedReference = classMappings.get(reference);
        return remappedReference != null ? remappedReference : reference;
    }

    public String addMapping(String context, String className, String reference, String newReference) {
        if (this.readOnly || reference == null || newReference == null || reference.equals(newReference)) {
            return null;
        }
        Map<String, Map<String, String>> mappings = this.mappings;
        if (context != null) {
            mappings = this.data.get(context);
            if (mappings == null) {
                mappings = Maps.newHashMap();
                this.data.put(context, mappings);
            }
        }
        Map<String, String> classMappings = mappings.get(className);
        if (classMappings == null) {
            classMappings = new HashMap<>();
            mappings.put(className, classMappings);
        }
        return classMappings.put(reference, newReference);
    }

    public void write(Appendable writer) {
        new GsonBuilder().setPrettyPrinting().create().toJson(this, writer);
    }

    public static ReferenceMapper read(String resourcePath) {
        InputStream resource;
        Logger logger = LogManager.getLogger("mixin");
        InputStreamReader inputStreamReader = null;
        try {
            try {
                IMixinService service = MixinService.getService();
                resource = service.getResourceAsStream(resourcePath);
            } catch (Exception ex) {
                logger.error("Failed reading REFMAP JSON from " + resourcePath + ": " + ex.getClass().getName() + " " + ex.getMessage());
                IOUtils.closeQuietly(inputStreamReader);
            } catch (JsonParseException ex2) {
                logger.error("Invalid REFMAP JSON in " + resourcePath + ": " + ex2.getClass().getName() + " " + ex2.getMessage());
                IOUtils.closeQuietly(inputStreamReader);
            }
            if (resource == null) {
                IOUtils.closeQuietly((Reader) null);
                return DEFAULT_MAPPER;
            }
            inputStreamReader = new InputStreamReader(resource);
            ReferenceMapper mapper = readJson(inputStreamReader);
            mapper.setResourceName(resourcePath);
            IOUtils.closeQuietly(inputStreamReader);
            return mapper;
        } catch (Throwable th) {
            IOUtils.closeQuietly(inputStreamReader);
            throw th;
        }
    }

    public static ReferenceMapper read(Reader reader, String name) {
        try {
            ReferenceMapper mapper = readJson(reader);
            mapper.setResourceName(name);
            return mapper;
        } catch (Exception e) {
            return DEFAULT_MAPPER;
        }
    }

    private static ReferenceMapper readJson(Reader reader) {
        return (ReferenceMapper) new Gson().fromJson(reader, ReferenceMapper.class);
    }
}
