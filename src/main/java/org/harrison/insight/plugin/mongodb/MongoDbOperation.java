package org.harrison.insight.plugin.mongodb;

import com.springsource.insight.intercept.operation.BasicOperation;
import com.springsource.insight.intercept.operation.OperationType;
import com.springsource.insight.intercept.operation.SourceCodeLocation;

/**
 * A Spring Insight {@link BasicOperation} that collects MongoDB DB operations
 * 
 * @author stephen harrison
 */
public class MongoDbOperation extends BasicOperation {
    public static final String NAME = "mongo_db_operation";
    public static final OperationType TYPE = OperationType.valueOf(NAME);

    private final String method;

    public MongoDbOperation(final SourceCodeLocation scl, final String method) {
	super(scl);

	this.method = method;
    }

    public String getLabel() {
	return method + " -> " + getReturnValue();
    }

    public OperationType getType() {
	return TYPE;
    }

    public Object[] getArgs() {
	return new Object[] { "???" };
    }
}
