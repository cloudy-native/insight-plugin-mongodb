package org.harrison.insight.plugin.mongodb;

import com.springsource.insight.intercept.operation.BasicOperation;
import com.springsource.insight.intercept.operation.OperationType;
import com.springsource.insight.intercept.operation.SourceCodeLocation;

/**
 * A Spring Insight {@link BasicOperation} that handles the return value a
 * little better for MongoDB operations
 * 
 * @author stephen harrison
 */
public abstract class MongoBasicOperation extends BasicOperation {
    private final String method;
    private final OperationType type;
    private String returnValueAsString = "???";

    public MongoBasicOperation(final SourceCodeLocation scl,
	    final String method, final OperationType type) {
	super(scl);

	this.method = method;
	this.type = type;
    }

    public String getMethod() {
	return method;
    }

    public OperationType getType() {
	return type;
    }

    @Override
    public void setReturnValue(final Object returnValue) {
	super.setReturnValue(returnValue);

	returnValueAsString = ArgUtils.toString(returnValue);
    }

    @Override
    public String getReturnValue() {
	return returnValueAsString;
    }
}
