package org.harrison.insight.plugin.mongodb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * Utilities for converting method arguments for MongoDB-related operations
 * 
 * It's a bit messy. Sorry.
 * 
 * @author stephen harrison
 */
public class ArgUtils {
    /**
     * In any case, the maximum length of a String we generate for the operation
     */
    private static final int MAX_STRING_LENGTH = 1000;

    /**
     * How we show there's more
     */
    private static final String ELLIPSIS = "...";

    /**
     * These classes can just be converted willy-nilly
     */
    private static final Class<?>[] SIMPLE_CLASSES = new Class<?>[] {
	    String.class, Boolean.class, Byte.class, Character.class,
	    Short.class, Integer.class, Long.class, Float.class, Double.class,
	    BigInteger.class, BigDecimal.class };

    /**
     * With care, we can treat these MongoDB classes simply too
     */
    private static final Class<?>[] SIMPLE_MONGO_CLASSES = new Class<?>[] {
	    ObjectId.class, CommandResult.class, BasicDBList.class,
	    BasicDBObject.class, DBCollection.class, WriteConcern.class,
	    WriteResult.class };

    /**
     * A little helper interface to convert an {@link Object} to a
     * {@link String}
     * 
     * @param <T>
     */
    private interface StringForm<T extends Object> {
	/**
	 * 
	 * @param object
	 *            guaranteed non-null
	 * @return
	 */
	String stringify(T object);
    }

    /**
     * Take an object from one of the "safe" classes, convert to a
     * {@link String} and trim the result, perhaps using ellipses if we truncate
     * it
     */
    private static final StringForm<Object> DefaultStringForm = new StringForm<Object>() {
	public String stringify(final Object object) {
	    return object.toString();
	}
    };

    /**
     * For a {@link DBCursor}, we get the {@link DBCollection} name, the query
     * and the keys wanted
     */
    private static final StringForm<DBCursor> DBCursorStringForm = new StringForm<DBCursor>() {
	public String stringify(final DBCursor cursor) {
	    return "DBCursor(" + MongoUtils.extractCollectionName(cursor)
		    + ", " + ArgUtils.toString(cursor.getQuery()) + ", "
		    + ArgUtils.toString(cursor.getKeysWanted()) + ")";
	}
    };

    /**
     * This type is common for inserts. In fact, even a single insert gets
     * converted to a {@link DBObject}[]
     */
    private static final StringForm<DBObject[]> DBObjectArrayStringForm = new StringForm<DBObject[]>() {
	public String stringify(final DBObject[] array) {
	    return "DBObject" + ArgUtils.toString(array);
	}
    };

    /**
     * A map from a {@link Class} to a helper ({@link StringForm}) that returns
     * a suitable {@link String} value
     * 
     * You'll get used to this style of object creation if you stare at it long
     * enough. It's handy because you don't need to mention the name of the
     * variable (STRING_FORM_MAP) in any of the put() calls, which you'd have to
     * if you did it long hand.
     */
    private static final Map<Class<?>, StringForm<? extends Object>> STRING_FORM_MAP = new HashMap<Class<?>, StringForm<? extends Object>>() {
	{
	    // Wrapper classes
	    //
	    for (final Class<?> cls : SIMPLE_CLASSES) {
		put(cls, DefaultStringForm);
	    }

	    // MongoDB classes
	    //
	    for (final Class<?> cls : SIMPLE_MONGO_CLASSES) {
		put(cls, DefaultStringForm);
	    }

	    put(DBCursor.class, DBCursorStringForm);
	    put(DBObject[].class, DBObjectArrayStringForm);
	}
    };

    private ArgUtils() {
	// empty OK
    }

    public static List<String> toString(final Object[] array) {
	return toString(array, MAX_STRING_LENGTH);
    }

    /**
     * Convert an {@link Object}[] to a {@link String}. Don't convert more than
     * MAX_ARGS arguments and don't make it more than roughly maxLength long.
     * 
     * Append ellipses to any argument we truncate, or to the whole array if
     * it's too long.
     * 
     * @param array
     * @param maxLength
     * @return
     */
    public static List<String> toString(final Object[] array,
	    final int maxLength) {
	return new ArrayList<String>() {
	    {
		int soFar = 0;

		for (final Object arg : array) {
		    final String result = ArgUtils.toString(arg, maxLength
			    - soFar);

		    soFar += result.length();

		    add(result);

		    if (soFar >= maxLength) {
			break;
		    }
		}
	    }
	};
    }

    public static String toString(final Object object) {
	return toString(object, MAX_STRING_LENGTH);
    }

    /**
     * Primitives and "safe" types get a call to {@link #toString()} via the
     * {@link StringForm} helper class; everything else is just the class name.
     * 
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String toString(final Object object, final int maxLength) {
	if (object == null) {
	    return "null";
	}

	final Class<? extends Object> cls = object.getClass();
	final StringForm<Object> stringForm = (StringForm<Object>) STRING_FORM_MAP
		.get(cls);

	if (stringForm != null) {
	    return trimWithEllipsis(stringForm.stringify(object), maxLength);
	}

	return cls.getSimpleName();
    }

    private static String toString(final DBObject dbObject) {
	return dbObject == null ? null : trimWithEllipsis(dbObject.toString());
    }

    private static String trimWithEllipsis(final String string) {
	return trimWithEllipsis(string, MAX_STRING_LENGTH);
    }

    private static String trimWithEllipsis(final String string,
	    final int maxLength) {
	return string.length() <= maxLength + ELLIPSIS.length() ? string
		: string.substring(0, maxLength) + ELLIPSIS;
    }
}
