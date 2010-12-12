package org.harrison.insight.plugin.mongodb;

import java.lang.reflect.Field;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class MongoUtils {
    private static final String COLLECTION_FIELD_IN_DBCURSOR = "_collection";
    private static final String UNKNOWN = "?unknown collection";

    private MongoUtils() {
	// empty OK
    }

    /*
     * An ugly perversion, only legal in select counties in Nevada. Details on
     * request.
     */
    public static String extractCollectionName(final DBCursor cursor) {
	try {
	    final Field f = cursor.getClass().getDeclaredField(COLLECTION_FIELD_IN_DBCURSOR);

	    f.setAccessible(true);

	    final DBCollection collection = (DBCollection) f.get(cursor);

	    return collection == null ? UNKNOWN : collection.getFullName();
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
}
