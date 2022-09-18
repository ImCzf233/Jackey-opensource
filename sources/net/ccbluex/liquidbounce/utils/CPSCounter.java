package net.ccbluex.liquidbounce.utils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/CPSCounter.class */
public class CPSCounter {
    private static final int MAX_CPS = 50;
    private static final RollingArrayLongBuffer[] TIMESTAMP_BUFFERS = new RollingArrayLongBuffer[MouseButton.values().length];

    static {
        for (int i = 0; i < TIMESTAMP_BUFFERS.length; i++) {
            TIMESTAMP_BUFFERS[i] = new RollingArrayLongBuffer(50);
        }
    }

    public static void registerClick(MouseButton button) {
        TIMESTAMP_BUFFERS[button.getIndex()].add(System.currentTimeMillis());
    }

    public static int getCPS(MouseButton button) {
        return TIMESTAMP_BUFFERS[button.getIndex()].getTimestampsSince(System.currentTimeMillis() - 1000);
    }

    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/CPSCounter$MouseButton.class */
    public enum MouseButton {
        LEFT(0),
        MIDDLE(1),
        RIGHT(2);
        
        private int index;

        MouseButton(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }
}
