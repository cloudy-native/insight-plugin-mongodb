package org.harrison.insight.plugin.mongodb;

import com.springsource.insight.intercept.operation.BasicOperation;
import com.springsource.insight.intercept.operation.SourceCodeLocation;

/**
 * A Spring Insight {@link BasicOperation} that handles the return value a
 * little better for MongoDB operations
 * 
 * @author stephen harrison
 */
public abstract class MongoBasicOperation extends BasicOperation {
    private String returnValueAsString;

    public MongoBasicOperation(final SourceCodeLocation scl) {
	super(scl);
    }
    
    /**
     * A version of this method that knows about MongoDB types
     */
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
