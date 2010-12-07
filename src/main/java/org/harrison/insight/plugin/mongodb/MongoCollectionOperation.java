package org.harrison.insight.plugin.mongodb;

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
public class MongoCollectionOperation extends MongoBasicOperation {
    public static final String NAME = "mongo_collection_operation";
    public static final OperationType TYPE = OperationType.valueOf(NAME);

    private final List<String> args;
    private final String collection;

    public MongoCollectionOperation(final SourceCodeLocation scl,
	    final String method, final List<String> args,
	    final String collection) {
	super(scl, method, TYPE);

	this.args = args;
	this.collection = collection;
    }

    public String getLabel() {
	return "MongoDB: " + collection + "." + getMethod() + "()";
    }

    public String getCollection() {
	return collection;
    }

    public List<String> getArgs() {
	return args;
    }
}
