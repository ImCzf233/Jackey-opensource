package net.ccbluex.liquidbounce.utils.misc;

import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/MiscUtils.class */
public final class MiscUtils extends MinecraftInstance {
    public static void showErrorPopup(String title, String message) {
        JOptionPane.showMessageDialog((Component) null, message, title, 0);
    }

    public static void showURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static File openFileChooser() {
        if (f362mc.func_71372_G()) {
            f362mc.func_71352_k();
        }
        JFileChooser fileChooser = new JFileChooser();
        JFrame frame = new JFrame();
        fileChooser.setFileSelectionMode(0);
        frame.setVisible(true);
        frame.toFront();
        frame.setVisible(false);
        int action = fileChooser.showOpenDialog(frame);
        frame.dispose();
        if (action == 0) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static File saveFileChooser() {
        if (f362mc.func_71372_G()) {
            f362mc.func_71352_k();
        }
        JFileChooser fileChooser = new JFileChooser();
        JFrame frame = new JFrame();
        fileChooser.setFileSelectionMode(0);
        frame.setVisible(true);
        frame.toFront();
        frame.setVisible(false);
        int action = fileChooser.showSaveDialog(frame);
        frame.dispose();
        if (action == 0) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
