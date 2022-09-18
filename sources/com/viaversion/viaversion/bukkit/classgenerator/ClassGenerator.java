package com.viaversion.viaversion.bukkit.classgenerator;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.bukkit.handlers.BukkitDecodeHandler;
import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.classgenerator.generated.BasicHandlerConstructor;
import com.viaversion.viaversion.classgenerator.generated.HandlerConstructor;
import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.CtField;
import com.viaversion.viaversion.libs.javassist.CtMethod;
import com.viaversion.viaversion.libs.javassist.CtNewConstructor;
import com.viaversion.viaversion.libs.javassist.CtNewMethod;
import com.viaversion.viaversion.libs.javassist.LoaderClassPath;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.expr.ConstructorCall;
import com.viaversion.viaversion.libs.javassist.expr.ExprEditor;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/classgenerator/ClassGenerator.class */
public final class ClassGenerator {
    private static final boolean useModules = hasModuleMethod();
    private static HandlerConstructor constructor = new BasicHandlerConstructor();
    private static String psPackage;
    private static Class psConnectListener;

    public static HandlerConstructor getConstructor() {
        return constructor;
    }

    public static void generate() {
        Plugin[] plugins;
        if (ViaVersionPlugin.getInstance().isCompatSpigotBuild() || ViaVersionPlugin.getInstance().isProtocolSupport()) {
            try {
                ClassPool pool = ClassPool.getDefault();
                pool.insertClassPath(new LoaderClassPath(Bukkit.class.getClassLoader()));
                for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
                    pool.insertClassPath(new LoaderClassPath(p.getClass().getClassLoader()));
                }
                if (ViaVersionPlugin.getInstance().isCompatSpigotBuild()) {
                    Class decodeSuper = NMSUtil.nms("PacketDecoder", "net.minecraft.network.PacketDecoder");
                    Class encodeSuper = NMSUtil.nms("PacketEncoder", "net.minecraft.network.PacketEncoder");
                    addSpigotCompatibility(pool, BukkitDecodeHandler.class, decodeSuper);
                    addSpigotCompatibility(pool, BukkitEncodeHandler.class, encodeSuper);
                } else if (isMultiplatformPS()) {
                    psConnectListener = makePSConnectListener(pool);
                    return;
                } else {
                    String psPackage2 = getOldPSPackage();
                    Class decodeSuper2 = Class.forName(psPackage2.equals("unknown") ? "protocolsupport.protocol.pipeline.common.PacketDecoder" : psPackage2 + ".wrapped.WrappedDecoder");
                    Class encodeSuper2 = Class.forName(psPackage2.equals("unknown") ? "protocolsupport.protocol.pipeline.common.PacketEncoder" : psPackage2 + ".wrapped.WrappedEncoder");
                    addPSCompatibility(pool, BukkitDecodeHandler.class, decodeSuper2);
                    addPSCompatibility(pool, BukkitEncodeHandler.class, encodeSuper2);
                }
                CtClass generated = pool.makeClass("com.viaversion.viaversion.classgenerator.generated.GeneratedConstructor");
                CtClass handlerInterface = pool.get(HandlerConstructor.class.getName());
                generated.setInterfaces(new CtClass[]{handlerInterface});
                pool.importPackage("com.viaversion.viaversion.classgenerator.generated");
                pool.importPackage("com.viaversion.viaversion.classgenerator");
                pool.importPackage("com.viaversion.viaversion.api.connection");
                pool.importPackage("io.netty.handler.codec");
                generated.addMethod(CtMethod.make("public MessageToByteEncoder newEncodeHandler(UserConnection info, MessageToByteEncoder minecraftEncoder) {\n        return new BukkitEncodeHandler(info, minecraftEncoder);\n    }", generated));
                generated.addMethod(CtMethod.make("public ByteToMessageDecoder newDecodeHandler(UserConnection info, ByteToMessageDecoder minecraftDecoder) {\n        return new BukkitDecodeHandler(info, minecraftDecoder);\n    }", generated));
                constructor = (HandlerConstructor) toClass(generated).getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (CannotCompileException | NotFoundException | ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addSpigotCompatibility(ClassPool pool, Class input, Class superclass) {
        String newName = "com.viaversion.viaversion.classgenerator.generated." + input.getSimpleName();
        try {
            CtClass generated = pool.getAndRename(input.getName(), newName);
            if (superclass != null) {
                CtClass toExtend = pool.get(superclass.getName());
                generated.setSuperclass(toExtend);
                if (superclass.getName().startsWith("net.minecraft") && generated.getConstructors().length != 0) {
                    generated.getConstructors()[0].instrument(new ExprEditor() { // from class: com.viaversion.viaversion.bukkit.classgenerator.ClassGenerator.1
                        @Override // com.viaversion.viaversion.libs.javassist.expr.ExprEditor
                        public void edit(ConstructorCall c) throws CannotCompileException {
                            if (c.isSuper()) {
                                c.replace("super(null);");
                            }
                            super.edit(c);
                        }
                    });
                }
            }
            toClass(generated);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private static void addPSCompatibility(ClassPool pool, Class input, Class superclass) {
        boolean newPS = getOldPSPackage().equals("unknown");
        String newName = "com.viaversion.viaversion.classgenerator.generated." + input.getSimpleName();
        try {
            CtClass generated = pool.getAndRename(input.getName(), newName);
            if (superclass != null) {
                CtClass toExtend = pool.get(superclass.getName());
                generated.setSuperclass(toExtend);
                if (!newPS) {
                    pool.importPackage(getOldPSPackage());
                    pool.importPackage(getOldPSPackage() + ".wrapped");
                    if (superclass.getName().endsWith("Decoder")) {
                        generated.addMethod(CtMethod.make("public void setRealDecoder(IPacketDecoder dec) {\n        ((WrappedDecoder) this.minecraftDecoder).setRealDecoder(dec);\n    }", generated));
                    } else {
                        pool.importPackage("protocolsupport.api");
                        pool.importPackage("java.lang.reflect");
                        generated.addMethod(CtMethod.make("public void setRealEncoder(IPacketEncoder enc) {\n         try {\n             Field field = enc.getClass().getDeclaredField(\"version\");\n             field.setAccessible(true);\n             ProtocolVersion version = (ProtocolVersion) field.get(enc);\n             if (version == ProtocolVersion.MINECRAFT_FUTURE) enc = enc.getClass().getConstructor(\n                 new Class[]{ProtocolVersion.class}).newInstance(new Object[] {ProtocolVersion.getLatest()});\n         } catch (Exception e) {\n         }\n        ((WrappedEncoder) this.minecraftEncoder).setRealEncoder(enc);\n    }", generated));
                    }
                }
            }
            toClass(generated);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private static Class makePSConnectListener(ClassPool pool) {
        HandshakeProtocolType type = handshakeVersionMethod();
        try {
            CtClass toExtend = pool.get("protocolsupport.api.Connection$PacketListener");
            CtClass connectListenerClazz = pool.makeClass("com.viaversion.viaversion.classgenerator.generated.ProtocolSupportConnectListener");
            connectListenerClazz.setSuperclass(toExtend);
            pool.importPackage("java.util.Arrays");
            pool.importPackage("protocolsupport.api.ProtocolVersion");
            pool.importPackage("protocolsupport.api.ProtocolType");
            pool.importPackage("protocolsupport.api.Connection");
            pool.importPackage("protocolsupport.api.Connection.PacketListener");
            pool.importPackage("protocolsupport.api.Connection.PacketListener.PacketEvent");
            pool.importPackage("protocolsupport.protocol.ConnectionImpl");
            pool.importPackage(NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol").getName());
            connectListenerClazz.addField(CtField.make("private ConnectionImpl connection;", connectListenerClazz));
            connectListenerClazz.addConstructor(CtNewConstructor.make("public ProtocolSupportConnectListener (ConnectionImpl connection) {\n    this.connection = connection;\n}", connectListenerClazz));
            connectListenerClazz.addMethod(CtNewMethod.make("public void onPacketReceiving(protocolsupport.api.Connection.PacketListener.PacketEvent event) {\n    if (event.getPacket() instanceof PacketHandshakingInSetProtocol) {\n        PacketHandshakingInSetProtocol packet = (PacketHandshakingInSetProtocol) event.getPacket();\n        int protoVersion = packet." + type.methodName() + "();\n        if (connection.getVersion() == ProtocolVersion.MINECRAFT_FUTURE && protoVersion == com.viaversion.viaversion.api.Via.getAPI().getServerVersion().lowestSupportedVersion()) {\n            connection.setVersion(ProtocolVersion.getLatest(ProtocolType.PC));\n        }\n    }\n    connection.removePacketListener(this);\n}", connectListenerClazz));
            return toClass(connectListenerClazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void registerPSConnectListener(ViaVersionPlugin plugin) {
        if (psConnectListener != null) {
            try {
                Bukkit.getPluginManager().registerEvent(Class.forName("protocolsupport.api.events.ConnectionOpenEvent"), new Listener() { // from class: com.viaversion.viaversion.bukkit.classgenerator.ClassGenerator.2
                }, EventPriority.HIGH, new EventExecutor() { // from class: com.viaversion.viaversion.bukkit.classgenerator.ClassGenerator.3
                    public void execute(Listener listener, Event event) throws EventException {
                        try {
                            Object connection = event.getClass().getMethod("getConnection", new Class[0]).invoke(event, new Object[0]);
                            Object connectListener = ClassGenerator.psConnectListener.getConstructor(connection.getClass()).newInstance(connection);
                            Method addConnectListener = connection.getClass().getMethod("addPacketListener", Class.forName("protocolsupport.api.Connection$PacketListener"));
                            addConnectListener.invoke(connection, connectListener);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, plugin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Class getPSConnectListener() {
        return psConnectListener;
    }

    public static String getOldPSPackage() {
        if (psPackage == null) {
            try {
                Class.forName("protocolsupport.protocol.core.IPacketDecoder");
                psPackage = "protocolsupport.protocol.core";
            } catch (ClassNotFoundException e) {
                try {
                    Class.forName("protocolsupport.protocol.pipeline.IPacketDecoder");
                    psPackage = "protocolsupport.protocol.pipeline";
                } catch (ClassNotFoundException e2) {
                    psPackage = "unknown";
                }
            }
        }
        return psPackage;
    }

    public static boolean isMultiplatformPS() {
        try {
            Class.forName("protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static HandshakeProtocolType handshakeVersionMethod() {
        Class<?> clazz = null;
        try {
            clazz = NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol");
            clazz.getMethod("getProtocolVersion", new Class[0]);
            return HandshakeProtocolType.MAPPED;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e2) {
            try {
                if (clazz.getMethod("b", new Class[0]).getReturnType() == Integer.TYPE) {
                    return HandshakeProtocolType.OBFUSCATED_OLD;
                }
                if (clazz.getMethod("c", new Class[0]).getReturnType() == Integer.TYPE) {
                    return HandshakeProtocolType.OBFUSCATED_NEW;
                }
                throw new UnsupportedOperationException("Protocol version method not found in " + clazz.getSimpleName());
            } catch (ReflectiveOperationException e3) {
                throw new RuntimeException(e3);
            }
        }
    }

    private static Class<?> toClass(CtClass ctClass) throws CannotCompileException {
        return useModules ? ctClass.toClass(HandlerConstructor.class) : ctClass.toClass(HandlerConstructor.class.getClassLoader());
    }

    private static boolean hasModuleMethod() {
        try {
            Class.class.getDeclaredMethod("getModule", new Class[0]);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/classgenerator/ClassGenerator$HandshakeProtocolType.class */
    public enum HandshakeProtocolType {
        MAPPED("getProtocolVersion"),
        OBFUSCATED_OLD("b"),
        OBFUSCATED_NEW("c");
        
        private final String methodName;

        HandshakeProtocolType(String methodName) {
            this.methodName = methodName;
        }

        public String methodName() {
            return this.methodName;
        }
    }
}
