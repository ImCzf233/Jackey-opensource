package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.spi.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter.class */
public class PrettyPrinter {
    private final HorizontalRule horizontalRule;
    private final List<Object> lines;
    private Table table;
    private boolean recalcWidth;
    protected int width;
    protected int wrapWidth;
    protected int kvKeyWidth;
    protected String kvFormat;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$Alignment.class */
    public enum Alignment {
        LEFT,
        RIGHT
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$IPrettyPrintable.class */
    public interface IPrettyPrintable {
        void print(PrettyPrinter prettyPrinter);
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$ISpecialEntry.class */
    public interface ISpecialEntry {
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$IVariableWidthEntry.class */
    public interface IVariableWidthEntry {
        int getWidth();
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$KeyValue.class */
    public class KeyValue implements IVariableWidthEntry {
        private final String key;
        private final Object value;

        public KeyValue(String key, Object value) {
            PrettyPrinter.this = this$0;
            this.key = key;
            this.value = value;
        }

        public String toString() {
            return String.format(PrettyPrinter.this.kvFormat, this.key, this.value);
        }

        @Override // org.spongepowered.asm.util.PrettyPrinter.IVariableWidthEntry
        public int getWidth() {
            return toString().length();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$HorizontalRule.class */
    public class HorizontalRule implements ISpecialEntry {
        private final char[] hrChars;

        public HorizontalRule(char... hrChars) {
            PrettyPrinter.this = this$0;
            this.hrChars = hrChars;
        }

        public String toString() {
            return Strings.repeat(new String(this.hrChars), PrettyPrinter.this.width + 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$CentredText.class */
    public class CentredText {
        private final Object centred;

        public CentredText(Object centred) {
            PrettyPrinter.this = this$0;
            this.centred = centred;
        }

        public String toString() {
            String text = this.centred.toString();
            return String.format("%" + (((PrettyPrinter.this.width - text.length()) / 2) + text.length()) + "s", text);
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$Table.class */
    public static class Table implements IVariableWidthEntry {
        final List<Column> columns = new ArrayList();
        final List<Row> rows = new ArrayList();
        String format = "%s";
        int colSpacing = 2;
        boolean addHeader = true;

        Table() {
        }

        void headerAdded() {
            this.addHeader = false;
        }

        void setColSpacing(int spacing) {
            this.colSpacing = Math.max(0, spacing);
            updateFormat();
        }

        Table grow(int size) {
            while (this.columns.size() < size) {
                this.columns.add(new Column(this));
            }
            updateFormat();
            return this;
        }

        Column add(Column column) {
            this.columns.add(column);
            return column;
        }

        Row add(Row row) {
            this.rows.add(row);
            return row;
        }

        Column addColumn(String title) {
            return add(new Column(this, title));
        }

        Column addColumn(Alignment align, int size, String title) {
            return add(new Column(this, align, size, title));
        }

        Row addRow(Object... args) {
            return add(new Row(this, args));
        }

        void updateFormat() {
            String spacing = Strings.repeat(" ", this.colSpacing);
            StringBuilder format = new StringBuilder();
            boolean addSpacing = false;
            for (Column column : this.columns) {
                if (addSpacing) {
                    format.append(spacing);
                }
                addSpacing = true;
                format.append(column.getFormat());
            }
            this.format = format.toString();
        }

        String getFormat() {
            return this.format;
        }

        Object[] getTitles() {
            List<Object> titles = new ArrayList<>();
            for (Column column : this.columns) {
                titles.add(column.getTitle());
            }
            return titles.toArray();
        }

        public String toString() {
            boolean nonEmpty = false;
            String[] titles = new String[this.columns.size()];
            for (int col = 0; col < this.columns.size(); col++) {
                titles[col] = this.columns.get(col).toString();
                nonEmpty |= !titles[col].isEmpty();
            }
            if (nonEmpty) {
                return String.format(this.format, titles);
            }
            return null;
        }

        @Override // org.spongepowered.asm.util.PrettyPrinter.IVariableWidthEntry
        public int getWidth() {
            String str = toString();
            if (str != null) {
                return str.length();
            }
            return 0;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$Column.class */
    public static class Column {
        private final Table table;
        private Alignment align;
        private int minWidth;
        private int maxWidth;
        private int size;
        private String title;
        private String format;

        Column(Table table) {
            this.align = Alignment.LEFT;
            this.minWidth = 1;
            this.maxWidth = Integer.MAX_VALUE;
            this.size = 0;
            this.title = "";
            this.format = "%s";
            this.table = table;
        }

        Column(Table table, String title) {
            this(table);
            this.title = title;
            this.minWidth = title.length();
            updateFormat();
        }

        Column(Table table, Alignment align, int size, String title) {
            this(table, title);
            this.align = align;
            this.size = size;
        }

        void setAlignment(Alignment align) {
            this.align = align;
            updateFormat();
        }

        void setWidth(int width) {
            if (width > this.size) {
                this.size = width;
                updateFormat();
            }
        }

        void setMinWidth(int width) {
            if (width > this.minWidth) {
                this.minWidth = width;
                updateFormat();
            }
        }

        void setMaxWidth(int width) {
            this.size = Math.min(this.size, this.maxWidth);
            this.maxWidth = Math.max(1, width);
            updateFormat();
        }

        void setTitle(String title) {
            this.title = title;
            setWidth(title.length());
        }

        private void updateFormat() {
            int width = Math.min(this.maxWidth, this.size == 0 ? this.minWidth : this.size);
            this.format = "%" + (this.align == Alignment.RIGHT ? "" : "-") + width + "s";
            this.table.updateFormat();
        }

        int getMaxWidth() {
            return this.maxWidth;
        }

        String getTitle() {
            return this.title;
        }

        String getFormat() {
            return this.format;
        }

        public String toString() {
            if (this.title.length() > this.maxWidth) {
                return this.title.substring(0, this.maxWidth);
            }
            return this.title;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/PrettyPrinter$Row.class */
    public static class Row implements IVariableWidthEntry {
        final Table table;
        final String[] args;

        public Row(Table table, Object... args) {
            this.table = table.grow(args.length);
            this.args = new String[args.length];
            for (int i = 0; i < args.length; i++) {
                this.args[i] = args[i].toString();
                this.table.columns.get(i).setMinWidth(this.args[i].length());
            }
        }

        public String toString() {
            Object[] args = new Object[this.table.columns.size()];
            for (int col = 0; col < args.length; col++) {
                Column column = this.table.columns.get(col);
                if (col >= this.args.length) {
                    args[col] = "";
                } else {
                    args[col] = this.args[col].length() > column.getMaxWidth() ? this.args[col].substring(0, column.getMaxWidth()) : this.args[col];
                }
            }
            return String.format(this.table.format, args);
        }

        @Override // org.spongepowered.asm.util.PrettyPrinter.IVariableWidthEntry
        public int getWidth() {
            return toString().length();
        }
    }

    public PrettyPrinter() {
        this(100);
    }

    public PrettyPrinter(int width) {
        this.horizontalRule = new HorizontalRule('*');
        this.lines = new ArrayList();
        this.recalcWidth = false;
        this.width = 100;
        this.wrapWidth = 80;
        this.kvKeyWidth = 10;
        this.kvFormat = makeKvFormat(this.kvKeyWidth);
        this.width = width;
    }

    public PrettyPrinter wrapTo(int wrapWidth) {
        this.wrapWidth = wrapWidth;
        return this;
    }

    public int wrapTo() {
        return this.wrapWidth;
    }

    public PrettyPrinter table() {
        this.table = new Table();
        return this;
    }

    public PrettyPrinter table(String... titles) {
        this.table = new Table();
        for (String title : titles) {
            this.table.addColumn(title);
        }
        return this;
    }

    public PrettyPrinter table(Object... format) {
        this.table = new Table();
        Column column = null;
        for (Object entry : format) {
            if (entry instanceof String) {
                column = this.table.addColumn((String) entry);
            } else if ((entry instanceof Integer) && column != null) {
                int width = ((Integer) entry).intValue();
                if (width > 0) {
                    column.setWidth(width);
                } else if (width < 0) {
                    column.setMaxWidth(-width);
                }
            } else if ((entry instanceof Alignment) && column != null) {
                column.setAlignment((Alignment) entry);
            } else if (entry != null) {
                column = this.table.addColumn(entry.toString());
            }
        }
        return this;
    }

    public PrettyPrinter spacing(int spacing) {
        if (this.table == null) {
            this.table = new Table();
        }
        this.table.setColSpacing(spacing);
        return this;
    }

    /* renamed from: th */
    public PrettyPrinter m7th() {
        return m6th(false);
    }

    /* renamed from: th */
    private PrettyPrinter m6th(boolean onlyIfNeeded) {
        if (this.table == null) {
            this.table = new Table();
        }
        if (!onlyIfNeeded || this.table.addHeader) {
            this.table.headerAdded();
            addLine(this.table);
        }
        return this;
    }

    /* renamed from: tr */
    public PrettyPrinter m5tr(Object... args) {
        m6th(true);
        addLine(this.table.addRow(args));
        this.recalcWidth = true;
        return this;
    }

    public PrettyPrinter add() {
        addLine("");
        return this;
    }

    public PrettyPrinter add(String string) {
        addLine(string);
        this.width = Math.max(this.width, string.length());
        return this;
    }

    public PrettyPrinter add(String format, Object... args) {
        String line = String.format(format, args);
        addLine(line);
        this.width = Math.max(this.width, line.length());
        return this;
    }

    public PrettyPrinter add(Object[] array) {
        return add(array, "%s");
    }

    public PrettyPrinter add(Object[] array, String format) {
        for (Object element : array) {
            add(format, element);
        }
        return this;
    }

    public PrettyPrinter addIndexed(Object[] array) {
        int indexWidth = String.valueOf(array.length - 1).length();
        String format = "[%" + indexWidth + "d] %s";
        for (int index = 0; index < array.length; index++) {
            add(format, Integer.valueOf(index), array[index]);
        }
        return this;
    }

    public PrettyPrinter addWithIndices(Collection<?> c) {
        return addIndexed(c.toArray());
    }

    public PrettyPrinter add(IPrettyPrintable printable) {
        if (printable != null) {
            printable.print(this);
        }
        return this;
    }

    public PrettyPrinter add(Throwable th) {
        return add(th, 4);
    }

    public PrettyPrinter add(Throwable th, int indent) {
        while (th != null) {
            add("%s: %s", th.getClass().getName(), th.getMessage());
            add(th.getStackTrace(), indent);
            th = th.getCause();
        }
        return this;
    }

    public PrettyPrinter add(StackTraceElement[] stackTrace, int indent) {
        String margin = Strings.repeat(" ", indent);
        for (StackTraceElement st : stackTrace) {
            add("%s%s", margin, st);
        }
        return this;
    }

    public PrettyPrinter add(Object object) {
        return add(object, 0);
    }

    public PrettyPrinter add(Object object, int indent) {
        String margin = Strings.repeat(" ", indent);
        return append(object, indent, margin);
    }

    private PrettyPrinter append(Object object, int indent, String margin) {
        if (object instanceof String) {
            return add("%s%s", margin, object);
        }
        if (object instanceof Iterable) {
            for (Object entry : (Iterable) object) {
                append(entry, indent, margin);
            }
            return this;
        } else if (object instanceof Map) {
            kvWidth(indent);
            return add((Map) object);
        } else if (object instanceof IPrettyPrintable) {
            return add((IPrettyPrintable) object);
        } else {
            if (object instanceof Throwable) {
                return add((Throwable) object, indent);
            }
            if (object.getClass().isArray()) {
                return add((Object[]) object, indent + "%s");
            }
            return add("%s%s", margin, object);
        }
    }

    public PrettyPrinter addWrapped(String format, Object... args) {
        return addWrapped(this.wrapWidth, format, args);
    }

    public PrettyPrinter addWrapped(int width, String format, Object... args) {
        String indent = "";
        String line = String.format(format, args).replace("\t", "    ");
        Matcher indentMatcher = Pattern.compile("^(\\s+)(.*)$").matcher(line);
        if (indentMatcher.matches()) {
            indent = indentMatcher.group(1);
        }
        try {
            for (String wrappedLine : getWrapped(width, line, indent)) {
                addLine(wrappedLine);
            }
        } catch (Exception e) {
            add(line);
        }
        return this;
    }

    private List<String> getWrapped(int width, String line, String indent) {
        List<String> lines = new ArrayList<>();
        while (line.length() > width) {
            int wrapPoint = line.lastIndexOf(32, width);
            if (wrapPoint < 10) {
                wrapPoint = width;
            }
            String head = line.substring(0, wrapPoint);
            lines.add(head);
            line = indent + line.substring(wrapPoint + 1);
        }
        if (line.length() > 0) {
            lines.add(line);
        }
        return lines;
    }

    /* renamed from: kv */
    public PrettyPrinter m8kv(String key, String format, Object... args) {
        return m9kv(key, String.format(format, args));
    }

    /* renamed from: kv */
    public PrettyPrinter m9kv(String key, Object value) {
        addLine(new KeyValue(key, value));
        return kvWidth(key.length());
    }

    public PrettyPrinter kvWidth(int width) {
        if (width > this.kvKeyWidth) {
            this.kvKeyWidth = width;
            this.kvFormat = makeKvFormat(width);
        }
        this.recalcWidth = true;
        return this;
    }

    public PrettyPrinter add(Map<?, ?> map) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey() == null ? Configurator.NULL : entry.getKey().toString();
            m9kv(key, entry.getValue());
        }
        return this;
    }

    /* renamed from: hr */
    public PrettyPrinter m11hr() {
        return m10hr('*');
    }

    /* renamed from: hr */
    public PrettyPrinter m10hr(char ruleChar) {
        addLine(new HorizontalRule(ruleChar));
        return this;
    }

    public PrettyPrinter centre() {
        if (!this.lines.isEmpty()) {
            Object lastLine = this.lines.get(this.lines.size() - 1);
            if (lastLine instanceof String) {
                addLine(new CentredText(this.lines.remove(this.lines.size() - 1)));
            }
        }
        return this;
    }

    private void addLine(Object line) {
        if (line == null) {
            return;
        }
        this.lines.add(line);
        this.recalcWidth |= line instanceof IVariableWidthEntry;
    }

    public PrettyPrinter trace() {
        return trace(getDefaultLoggerName());
    }

    public PrettyPrinter trace(Level level) {
        return trace(getDefaultLoggerName(), level);
    }

    public PrettyPrinter trace(String logger) {
        return trace(System.err, LogManager.getLogger(logger));
    }

    public PrettyPrinter trace(String logger, Level level) {
        return trace(System.err, LogManager.getLogger(logger), level);
    }

    public PrettyPrinter trace(Logger logger) {
        return trace(System.err, logger);
    }

    public PrettyPrinter trace(Logger logger, Level level) {
        return trace(System.err, logger, level);
    }

    public PrettyPrinter trace(PrintStream stream) {
        return trace(stream, getDefaultLoggerName());
    }

    public PrettyPrinter trace(PrintStream stream, Level level) {
        return trace(stream, getDefaultLoggerName(), level);
    }

    public PrettyPrinter trace(PrintStream stream, String logger) {
        return trace(stream, LogManager.getLogger(logger));
    }

    public PrettyPrinter trace(PrintStream stream, String logger, Level level) {
        return trace(stream, LogManager.getLogger(logger), level);
    }

    public PrettyPrinter trace(PrintStream stream, Logger logger) {
        return trace(stream, logger, Level.DEBUG);
    }

    public PrettyPrinter trace(PrintStream stream, Logger logger, Level level) {
        log(logger, level);
        print(stream);
        return this;
    }

    public PrettyPrinter print() {
        return print(System.err);
    }

    public PrettyPrinter print(PrintStream stream) {
        updateWidth();
        printSpecial(stream, this.horizontalRule);
        for (Object line : this.lines) {
            if (line instanceof ISpecialEntry) {
                printSpecial(stream, (ISpecialEntry) line);
            } else {
                printString(stream, line.toString());
            }
        }
        printSpecial(stream, this.horizontalRule);
        return this;
    }

    private void printSpecial(PrintStream stream, ISpecialEntry line) {
        stream.printf("/*%s*/\n", line.toString());
    }

    private void printString(PrintStream stream, String string) {
        if (string != null) {
            stream.printf("/* %-" + this.width + "s */\n", string);
        }
    }

    public PrettyPrinter log(Logger logger) {
        return log(logger, Level.INFO);
    }

    public PrettyPrinter log(Logger logger, Level level) {
        updateWidth();
        logSpecial(logger, level, this.horizontalRule);
        for (Object line : this.lines) {
            if (line instanceof ISpecialEntry) {
                logSpecial(logger, level, (ISpecialEntry) line);
            } else {
                logString(logger, level, line.toString());
            }
        }
        logSpecial(logger, level, this.horizontalRule);
        return this;
    }

    private void logSpecial(Logger logger, Level level, ISpecialEntry line) {
        logger.log(level, "/*{}*/", new Object[]{line.toString()});
    }

    private void logString(Logger logger, Level level, String line) {
        if (line != null) {
            logger.log(level, String.format("/* %-" + this.width + "s */", line));
        }
    }

    private void updateWidth() {
        if (this.recalcWidth) {
            this.recalcWidth = false;
            for (Object line : this.lines) {
                if (line instanceof IVariableWidthEntry) {
                    this.width = Math.min(4096, Math.max(this.width, ((IVariableWidthEntry) line).getWidth()));
                }
            }
        }
    }

    private static String makeKvFormat(int keyWidth) {
        return String.format("%%%ds : %%s", Integer.valueOf(keyWidth));
    }

    private static String getDefaultLoggerName() {
        String name = new Throwable().getStackTrace()[2].getClassName();
        int pos = name.lastIndexOf(46);
        return pos == -1 ? name : name.substring(pos + 1);
    }

    public static void dumpStack() {
        new PrettyPrinter().add((Throwable) new Exception("Stack trace")).print(System.err);
    }

    public static void print(Throwable th) {
        new PrettyPrinter().add(th).print(System.err);
    }
}
