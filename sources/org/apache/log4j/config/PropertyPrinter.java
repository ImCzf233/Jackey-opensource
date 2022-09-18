package org.apache.log4j.config;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.config.PropertyGetter;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/config/PropertyPrinter.class */
public class PropertyPrinter implements PropertyGetter.PropertyCallback {
    protected int numAppenders;
    protected Hashtable appenderNames;
    protected Hashtable layoutNames;
    protected PrintWriter out;
    protected boolean doCapitalize;

    public PropertyPrinter(PrintWriter out) {
        this(out, false);
    }

    public PropertyPrinter(PrintWriter out, boolean doCapitalize) {
        this.numAppenders = 0;
        this.appenderNames = new Hashtable();
        this.layoutNames = new Hashtable();
        this.out = out;
        this.doCapitalize = doCapitalize;
        print(out);
        out.flush();
    }

    protected String genAppName() {
        StringBuffer append = new StringBuffer().append("A");
        int i = this.numAppenders;
        this.numAppenders = i + 1;
        return append.append(i).toString();
    }

    protected boolean isGenAppName(String name) {
        if (name.length() < 2 || name.charAt(0) != 'A') {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) < '0' || name.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public void print(PrintWriter out) {
        printOptions(out, Logger.getRootLogger());
        Enumeration cats = LogManager.getCurrentLoggers();
        while (cats.hasMoreElements()) {
            printOptions(out, (Logger) cats.nextElement());
        }
    }

    protected void printOptions(PrintWriter out, Category cat) {
        String appenderString;
        Enumeration appenders = cat.getAllAppenders();
        Level prio = cat.getLevel();
        String level = prio == null ? "" : prio.toString();
        while (true) {
            appenderString = level;
            if (!appenders.hasMoreElements()) {
                break;
            }
            Appender app = (Appender) appenders.nextElement();
            String str = (String) this.appenderNames.get(app);
            String name = str;
            if (str == null) {
                String name2 = app.getName();
                name = name2;
                if (name2 == null || isGenAppName(name)) {
                    name = genAppName();
                }
                this.appenderNames.put(app, name);
                printOptions(out, app, new StringBuffer().append("log4j.appender.").append(name).toString());
                if (app.getLayout() != null) {
                    printOptions(out, app.getLayout(), new StringBuffer().append("log4j.appender.").append(name).append(".layout").toString());
                }
            }
            level = new StringBuffer().append(appenderString).append(", ").append(name).toString();
        }
        String catKey = cat == Logger.getRootLogger() ? "log4j.rootLogger" : new StringBuffer().append("log4j.logger.").append(cat.getName()).toString();
        if (appenderString != "") {
            out.println(new StringBuffer().append(catKey).append("=").append(appenderString).toString());
        }
        if (!cat.getAdditivity() && cat != Logger.getRootLogger()) {
            out.println(new StringBuffer().append("log4j.additivity.").append(cat.getName()).append("=false").toString());
        }
    }

    protected void printOptions(PrintWriter out, Logger cat) {
        printOptions(out, (Category) cat);
    }

    protected void printOptions(PrintWriter out, Object obj, String fullname) {
        out.println(new StringBuffer().append(fullname).append("=").append(obj.getClass().getName()).toString());
        PropertyGetter.getProperties(obj, this, new StringBuffer().append(fullname).append(".").toString());
    }

    @Override // org.apache.log4j.config.PropertyGetter.PropertyCallback
    public void foundProperty(Object obj, String prefix, String name, Object value) {
        if ((obj instanceof Appender) && "name".equals(name)) {
            return;
        }
        if (this.doCapitalize) {
            name = capitalize(name);
        }
        this.out.println(new StringBuffer().append(prefix).append(name).append("=").append(value.toString()).toString());
    }

    public static String capitalize(String name) {
        if (Character.isLowerCase(name.charAt(0)) && (name.length() == 1 || Character.isLowerCase(name.charAt(1)))) {
            StringBuffer newname = new StringBuffer(name);
            newname.setCharAt(0, Character.toUpperCase(name.charAt(0)));
            return newname.toString();
        }
        return name;
    }

    public static void main(String[] args) {
        new PropertyPrinter(new PrintWriter(System.out));
    }
}
