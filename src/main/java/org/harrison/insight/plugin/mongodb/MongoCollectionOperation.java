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
public class MongoCollectionOperation extends BasicOperation {
    public static final String NAME = "mongo_collection_operation";
    public static final OperationType TYPE = OperationType.valueOf(NAME);

    private final String method;
    private final String signature;
    private final String collection;

    public MongoCollectionOperation(final SourceCodeLocation scl,
	    final String method, final String signature, final String collection) {
	super(scl);

	this.method = method;
	this.signature = signature;
	this.collection = collection;
    }

    public String getLabel() {
	return "MongoDB: " + collection + "." + method + "()";
    }

    public OperationType getType() {
	return TYPE;
    }

    public String getMethod() {
	return method;
    }

    public String getSignature() {
	return signature;
    }

    public String getCollection() {
	return collection;
    }
}
