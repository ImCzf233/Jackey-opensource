package org.spongepowered.tools.obfuscation.mapping.mcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.common.MappingWriter;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/mapping/mcp/MappingWriterSrg.class */
public class MappingWriterSrg extends MappingWriter {
    public MappingWriterSrg(Messager messager, Filer filer) {
        super(messager, filer);
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingWriter
    public void write(String output, ObfuscationType type, IMappingConsumer.MappingSet<MappingField> fields, IMappingConsumer.MappingSet<MappingMethod> methods) {
        if (output == null) {
            return;
        }
        PrintWriter writer = null;
        try {
            try {
                writer = openFileWriter(output, type + " output SRGs");
                writeFieldMappings(writer, fields);
                writeMethodMappings(writer, methods);
                if (writer == null) {
                    return;
                }
                try {
                    writer.close();
                } catch (Exception e) {
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                if (writer == null) {
                    return;
                }
                try {
                    writer.close();
                } catch (Exception e2) {
                }
            }
        } catch (Throwable th) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
    }

    protected void writeFieldMappings(PrintWriter writer, IMappingConsumer.MappingSet<MappingField> fields) {
        Iterator it = fields.iterator();
        while (it.hasNext()) {
            IMappingConsumer.MappingSet.Pair<MappingField> field = (IMappingConsumer.MappingSet.Pair) it.next();
            writer.println(formatFieldMapping(field));
        }
    }

    protected void writeMethodMappings(PrintWriter writer, IMappingConsumer.MappingSet<MappingMethod> methods) {
        Iterator it = methods.iterator();
        while (it.hasNext()) {
            IMappingConsumer.MappingSet.Pair<MappingMethod> method = (IMappingConsumer.MappingSet.Pair) it.next();
            writer.println(formatMethodMapping(method));
        }
    }

    protected String formatFieldMapping(IMappingConsumer.MappingSet.Pair<MappingField> mapping) {
        return String.format("FD: %s/%s %s/%s", mapping.from.getOwner(), mapping.from.getName(), mapping.f455to.getOwner(), mapping.f455to.getName());
    }

    protected String formatMethodMapping(IMappingConsumer.MappingSet.Pair<MappingMethod> mapping) {
        return String.format("MD: %s %s %s %s", mapping.from.getName(), mapping.from.getDesc(), mapping.f455to.getName(), mapping.f455to.getDesc());
    }
}
