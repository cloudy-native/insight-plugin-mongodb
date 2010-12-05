package org.harrison.insight.plugin.mongodb;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.springsource.insight.collection.AbstractOperationCollectionAspect;
import com.springsource.insight.intercept.operation.Operation;

public aspect MongoCursorOperationCollectionAspect extends
	AbstractOperationCollectionAspect {
    private static final String UNKNOWN = "?unknown?";

    public pointcut collectionPoint(): execution(DBObject DBCursor.next());

    @Override
    protected Operation createOperation(final JoinPoint joinPoint) {
	final DBCursor cursor = (DBCursor) joinPoint.getTarget();

	if (true || cursor == null) {
	    return new MongoCursorOperation(getSourceCodeLocation(joinPoint),
		    UNKNOWN, UNKNOWN, UNKNOWN);
	}

	final DBCollection collection = extractCollectionFieldTheUglyWay(cursor);
	final String collectionName = collection == null ? UNKNOWN : collection
		.getFullName();

	return new MongoCursorOperation(getSourceCodeLocation(joinPoint),
		nullsafeToString(cursor.getKeysWanted()),
		nullsafeToString(cursor.getQuery()), collectionName);
    }

    /*
     * An ugly perversion, only legal in select counties in Nevada. Details on
     * request.
     */
    private static DBCollection extractCollectionFieldTheUglyWay(
	    final DBCursor cursor) {
	try {
	    final Field f = cursor.getClass().getDeclaredField("_collection");

	    f.setAccessible(true);

	    return (DBCollection) f.get(cursor);
	} catch (final SecurityException e) {
	    return null;
	} catch (final IllegalArgumentException e) {
	    return null;
	} catch (final NoSuchFieldException e) {
	    return null;
	} catch (final IllegalAccessException e) {
	    return null;
	}
    }

    private static String nullsafeToString(final Object object) {
	return object == null ? "null" : object.toString();
    }
}
