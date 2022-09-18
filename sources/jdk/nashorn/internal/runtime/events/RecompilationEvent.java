package jdk.nashorn.internal.runtime.events;

import java.util.logging.Level;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.RewriteException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/events/RecompilationEvent.class */
public final class RecompilationEvent extends RuntimeEvent<RewriteException> {
    private final Object returnValue;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !RecompilationEvent.class.desiredAssertionStatus();
    }

    public RecompilationEvent(Level level, RewriteException rewriteException, Object returnValue) {
        super(level, rewriteException);
        if ($assertionsDisabled || Context.getContext().getLogger(RecompilableScriptFunctionData.class).isEnabled()) {
            this.returnValue = returnValue;
            return;
        }
        throw new AssertionError("Unit test/instrumentation purpose only: RecompilationEvent instances should not be created without '--log=recompile', or we will leak memory in the general case");
    }

    public Object getReturnValue() {
        return this.returnValue;
    }
}
