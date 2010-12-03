package org.harrison.insight.plugin.mongodb;

import java.util.List;

import com.springsource.insight.intercept.operation.BasicOperation;
import com.springsource.insight.intercept.operation.OperationType;
import com.springsource.insight.intercept.operation.SourceCodeLocation;

/**
 * A Spring Insight {@link BasicOperation} that collects MongoDB DB operations
 * 
 * @author stephen harrison
 */
public class MongoDbOperation extends MongoBasicOperation {
    public static final String NAME = "mongo_db_operation";
    public static final OperationType TYPE = OperationType.valueOf(NAME);

    private final List<String> args;
    private final String signature;

    public MongoDbOperation(final SourceCodeLocation scl,
	    final List<String> args, final String signature) {
	super(scl);

	this.args = args;
	this.signature = signature;
    }

    public String getLabel() {
	return "MongoDB: " + signature;
    }

    public OperationType getType() {
	return TYPE;
    }

    public String getSignature() {
	return signature;
    }

    public List<String> getArgs() {
	return args;
    }
}
