package org.harrison.insight.plugin.mongodb;

import com.springsource.insight.intercept.operation.BasicOperation;
import com.springsource.insight.intercept.operation.OperationType;
import com.springsource.insight.intercept.operation.SourceCodeLocation;

/**
 * A Spring Insight {@link BasicOperation} that collects MongoDB DBCollection
 * operations
 * 
 * @author stephen harrison
 */
public class MongoCursorOperation extends BasicOperation {
    public static final String NAME = "mongo_cursor_operation";
    public static final OperationType TYPE = OperationType.valueOf(NAME);

    public MongoCursorOperation(final SourceCodeLocation scl) {
	super(scl);
    }

    public String getLabel() {
	return "MongoDB: DBCursor.next()";
    }

    public OperationType getType() {
	return TYPE;
    }
}
