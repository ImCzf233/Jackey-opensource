package org.apache.log4j.chainsaw;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/* loaded from: Jackey Client b2.jar:org/apache/log4j/chainsaw/MyTableModel.class */
public class MyTableModel extends AbstractTableModel {
    private static final Logger LOG;
    private static final Comparator MY_COMP;
    private static final String[] COL_NAMES;
    private static final EventDetails[] EMPTY_LIST;
    private static final DateFormat DATE_FORMATTER;
    private final Object mLock = new Object();
    private final SortedSet mAllEvents = new TreeSet(MY_COMP);
    private EventDetails[] mFilteredEvents = EMPTY_LIST;
    private final List mPendingEvents = new ArrayList();
    private boolean mPaused = false;
    private String mThreadFilter = "";
    private String mMessageFilter = "";
    private String mNDCFilter = "";
    private String mCategoryFilter = "";
    private Priority mPriorityFilter = Priority.DEBUG;
    static Class class$org$apache$log4j$chainsaw$MyTableModel;
    static Class class$java$lang$Boolean;
    static Class class$java$lang$Object;

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError().initCause(x1);
        }
    }

    static {
        Class cls;
        if (class$org$apache$log4j$chainsaw$MyTableModel == null) {
            cls = class$("org.apache.log4j.chainsaw.MyTableModel");
            class$org$apache$log4j$chainsaw$MyTableModel = cls;
        } else {
            cls = class$org$apache$log4j$chainsaw$MyTableModel;
        }
        LOG = Logger.getLogger(cls);
        MY_COMP = new Comparator() { // from class: org.apache.log4j.chainsaw.MyTableModel.1
            @Override // java.util.Comparator
            public int compare(Object aObj1, Object aObj2) {
                if (aObj1 == null && aObj2 == null) {
                    return 0;
                }
                if (aObj1 == null) {
                    return -1;
                }
                if (aObj2 == null) {
                    return 1;
                }
                EventDetails le1 = (EventDetails) aObj1;
                EventDetails le2 = (EventDetails) aObj2;
                if (le1.getTimeStamp() < le2.getTimeStamp()) {
                    return 1;
                }
                return -1;
            }
        };
        COL_NAMES = new String[]{"Time", "Priority", "Trace", "Category", "NDC", "Message"};
        EMPTY_LIST = new EventDetails[0];
        DATE_FORMATTER = DateFormat.getDateTimeInstance(3, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:org/apache/log4j/chainsaw/MyTableModel$Processor.class */
    public class Processor implements Runnable {
        private final MyTableModel this$0;

        private Processor(MyTableModel myTableModel) {
            this.this$0 = myTableModel;
        }

        Processor(MyTableModel x0, C17121 x1) {
            this(x0);
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
                synchronized (this.this$0.mLock) {
                    if (!this.this$0.mPaused) {
                        boolean toHead = true;
                        boolean needUpdate = false;
                        for (EventDetails event : this.this$0.mPendingEvents) {
                            this.this$0.mAllEvents.add(event);
                            toHead = toHead && event == this.this$0.mAllEvents.first();
                            needUpdate = needUpdate || this.this$0.matchFilter(event);
                        }
                        this.this$0.mPendingEvents.clear();
                        if (needUpdate) {
                            this.this$0.updateFilteredEvents(toHead);
                        }
                    }
                }
            }
        }
    }

    public MyTableModel() {
        Thread t = new Thread(new Processor(this, null));
        t.setDaemon(true);
        t.start();
    }

    public int getRowCount() {
        int length;
        synchronized (this.mLock) {
            length = this.mFilteredEvents.length;
        }
        return length;
    }

    public int getColumnCount() {
        return COL_NAMES.length;
    }

    public String getColumnName(int aCol) {
        return COL_NAMES[aCol];
    }

    public Class getColumnClass(int aCol) {
        if (aCol == 2) {
            if (class$java$lang$Boolean != null) {
                return class$java$lang$Boolean;
            }
            Class class$ = class$("java.lang.Boolean");
            class$java$lang$Boolean = class$;
            return class$;
        } else if (class$java$lang$Object != null) {
            return class$java$lang$Object;
        } else {
            Class class$2 = class$("java.lang.Object");
            class$java$lang$Object = class$2;
            return class$2;
        }
    }

    public Object getValueAt(int aRow, int aCol) {
        synchronized (this.mLock) {
            EventDetails event = this.mFilteredEvents[aRow];
            if (aCol == 0) {
                return DATE_FORMATTER.format(new Date(event.getTimeStamp()));
            } else if (aCol == 1) {
                return event.getPriority();
            } else if (aCol == 2) {
                return event.getThrowableStrRep() == null ? Boolean.FALSE : Boolean.TRUE;
            } else if (aCol == 3) {
                return event.getCategoryName();
            } else if (aCol == 4) {
                return event.getNDC();
            } else {
                return event.getMessage();
            }
        }
    }

    public void setPriorityFilter(Priority aPriority) {
        synchronized (this.mLock) {
            this.mPriorityFilter = aPriority;
            updateFilteredEvents(false);
        }
    }

    public void setThreadFilter(String aStr) {
        synchronized (this.mLock) {
            this.mThreadFilter = aStr.trim();
            updateFilteredEvents(false);
        }
    }

    public void setMessageFilter(String aStr) {
        synchronized (this.mLock) {
            this.mMessageFilter = aStr.trim();
            updateFilteredEvents(false);
        }
    }

    public void setNDCFilter(String aStr) {
        synchronized (this.mLock) {
            this.mNDCFilter = aStr.trim();
            updateFilteredEvents(false);
        }
    }

    public void setCategoryFilter(String aStr) {
        synchronized (this.mLock) {
            this.mCategoryFilter = aStr.trim();
            updateFilteredEvents(false);
        }
    }

    public void addEvent(EventDetails aEvent) {
        synchronized (this.mLock) {
            this.mPendingEvents.add(aEvent);
        }
    }

    public void clear() {
        synchronized (this.mLock) {
            this.mAllEvents.clear();
            this.mFilteredEvents = new EventDetails[0];
            this.mPendingEvents.clear();
            fireTableDataChanged();
        }
    }

    public void toggle() {
        synchronized (this.mLock) {
            this.mPaused = !this.mPaused;
        }
    }

    public boolean isPaused() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPaused;
        }
        return z;
    }

    public EventDetails getEventDetails(int aRow) {
        EventDetails eventDetails;
        synchronized (this.mLock) {
            eventDetails = this.mFilteredEvents[aRow];
        }
        return eventDetails;
    }

    public void updateFilteredEvents(boolean aInsertedToFront) {
        long start = System.currentTimeMillis();
        List filtered = new ArrayList();
        int size = this.mAllEvents.size();
        for (EventDetails event : this.mAllEvents) {
            if (matchFilter(event)) {
                filtered.add(event);
            }
        }
        EventDetails lastFirst = this.mFilteredEvents.length == 0 ? null : this.mFilteredEvents[0];
        this.mFilteredEvents = (EventDetails[]) filtered.toArray(EMPTY_LIST);
        if (aInsertedToFront && lastFirst != null) {
            int index = filtered.indexOf(lastFirst);
            if (index < 1) {
                LOG.warn("In strange state");
                fireTableDataChanged();
            } else {
                fireTableRowsInserted(0, index - 1);
            }
        } else {
            fireTableDataChanged();
        }
        long end = System.currentTimeMillis();
        LOG.debug(new StringBuffer().append("Total time [ms]: ").append(end - start).append(" in update, size: ").append(size).toString());
    }

    public boolean matchFilter(EventDetails aEvent) {
        if (!aEvent.getPriority().isGreaterOrEqual(this.mPriorityFilter) || aEvent.getThreadName().indexOf(this.mThreadFilter) < 0 || aEvent.getCategoryName().indexOf(this.mCategoryFilter) < 0) {
            return false;
        }
        if (this.mNDCFilter.length() == 0 || (aEvent.getNDC() != null && aEvent.getNDC().indexOf(this.mNDCFilter) >= 0)) {
            String rm = aEvent.getMessage();
            return rm == null ? this.mMessageFilter.length() == 0 : rm.indexOf(this.mMessageFilter) >= 0;
        }
        return false;
    }
}
