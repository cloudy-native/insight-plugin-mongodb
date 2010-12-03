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
public class MongoCursorOperation extends MongoBasicOperation {
    public static final String NAME = "mongo_cursor_operation";
    public static final OperationType TYPE = OperationType.valueOf(NAME);

    private final String keysWanted;
    private final String query;
    private final String collection;

    public MongoCursorOperation(final SourceCodeLocation scl,
	    final String keysWanted, final String query) {
	super(scl);

	this.keysWanted = keysWanted;
	this.query = query;
	this.collection = "???";
    }

    public String getLabel() {
	return "MongoDB: DBCursor.next()";
    }

    public OperationType getType() {
	return TYPE;
    }

    public String getKeysWanted() {
	return keysWanted;
    }

    public String getQuery() {
	return query;
    }

    public String getCollection() {
	return collection;
    }
}
