package org.harrison.insight.plugin.mongodb;

import java.util.Arrays;
import java.util.List;

import com.springsource.insight.intercept.operation.BasicOperation;
import com.springsource.insight.intercept.operation.OperationType;
import com.springsource.insight.intercept.operation.SourceCodeLocation;

/**
 * A Spring Insight {@link BasicOperation} that collects MongoDB DBCollection
 * operations
 * 
 * @author stephen harrison
 */
public class MongoCollectionOperation extends BasicOperation {
    public static final String NAME = "mongo_collection_operation";
    public static final OperationType TYPE = OperationType.valueOf(NAME);

    private final String method;
    private final Object[] args;

    public MongoCollectionOperation(final SourceCodeLocation scl,
	    final String method, final Object[] args) {
	super(scl);

	this.method = method;
	this.args = new Object[] { "???" };
    }

    public String getLabel() {
	return method + " -> " + getReturnValue();
    }

    public OperationType getType() {
	return TYPE;
    }

    public List<?> getArgs() {
	return Arrays.asList(args);
    }
}
