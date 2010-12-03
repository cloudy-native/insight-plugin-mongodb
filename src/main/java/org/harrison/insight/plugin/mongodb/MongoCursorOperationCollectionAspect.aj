package org.harrison.insight.plugin.mongodb;

import org.aspectj.lang.JoinPoint;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.springsource.insight.collection.AbstractOperationCollectionAspect;
import com.springsource.insight.intercept.operation.Operation;

public aspect MongoCursorOperationCollectionAspect extends
	AbstractOperationCollectionAspect {

    public pointcut nextExecute() 
    : execution(DBObject DBCursor.next());

    /**
     * Many of the MongoDB Java driver methods are chained, so we use cflowbelow
     * to cull subsequent calls.
     */
    public pointcut collectionPoint()
    : (nextExecute() && !cflowbelow(nextExecute()));

    @Override
    protected Operation createOperation(final JoinPoint joinPoint) {
	final DBCursor target = (DBCursor) joinPoint.getTarget();

	if (target == null) {
	    return new MongoCursorOperation(getSourceCodeLocation(joinPoint),
		    "???", "???");
	}

	return new MongoCursorOperation(getSourceCodeLocation(joinPoint),
		nullsafeToString(target.getKeysWanted()), nullsafeToString(target.getQuery()));
    }

    private static String nullsafeToString(Object object) {
	return object == null ? "null" : object.toString();
    }
}
