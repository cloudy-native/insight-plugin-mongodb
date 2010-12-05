package org.harrison.insight.plugin.mongodb;

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
     * In any case, the maximum number of arguments we decode
     */
    private static final int MAX_ARGS = 10;

    /**
     * How we show there's more
     */
    private static final String ELLIPSIS = "...";

    /**
     * These classes can just be converted willy-nilly
     */
    private static final Class<?>[] SIMPLE_CLASSES = new Class<?>[] {
	    String.class, Boolean.class, Byte.class, Character.class,
	    Short.class, Integer.class, Long.class, Float.class, Double.class };

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
	    final String string = object.toString();

	    return trimWithEllipsis(string);
	}
    };

    /**
     * For a {@link DBCursor}, we get the {@link DBCollection} name (actually
     * not yet), the query and the keys wanted
     */
    private static final StringForm<DBCursor> DBCursorStringForm = new StringForm<DBCursor>() {
	public String stringify(final DBCursor object) {
	    return "DBCursor(" + "???, " + ArgUtils.toString(object.getQuery())
		    + ", " + ArgUtils.toString(object.getKeysWanted()) + ")";
	}
    };

    /**
     * This type is common for inserts. In fact, even a single insert gets
     * converted to an array of one {@link DBObject}
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
    private static Map<Class<?>, StringForm<? extends Object>> STRING_FORM_MAP = new HashMap<Class<?>, StringForm<? extends Object>>() {
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
     * MAX_ARGS arguments and don't make it more than about maxLength long.
     * 
     * Append ellipses to any argument we truncate, or to the whole array cif
     * it's too long.
     * 
     * @param array
     * @param maxLength
     * @return
     */
    public static List<String> toString(final Object[] array,
	    final int maxLength) {
	final int maxArgs = Math.min(MAX_ARGS, array.length);
	final int elementLength = maxLength / Math.min(1, maxArgs);

	return new ArrayList<String>() {
	    {
		for (final Object arg : array) {
		    add(ArgUtils.toString(arg, elementLength));
		}

		if (array.length > MAX_ARGS) {
		    add(ELLIPSIS);
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
	final StringForm stringForm = STRING_FORM_MAP.get(cls);

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
