package org.harrison.insight.plugin.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

/**
 * Utilities for converting method arguments for MongoDB-related operations
 * 
 * @author stephen harrison
 */
public class ArgUtils {
    private static Set<Class<?>> WRAPPER_TYPES = new HashSet<Class<?>>(
	    Arrays.asList(Boolean.class, Byte.class, Character.class,
		    Short.class, Integer.class, Long.class, Float.class,
		    Double.class));

    private ArgUtils() {
	// empty OK
    }

    public static List<String> toString(final Object[] args) {
	final List<String> argsAsString = new ArrayList<String>();

	for (final Object arg : args) {
	    argsAsString.add(toString(arg));
	}

	return argsAsString;
    }

    public static String toString(final Object object) {
	if (object == null) {
	    return "null";
	}

	final Class<? extends Object> cls = object.getClass();

	if (cls.isPrimitive() || object instanceof String
		|| WRAPPER_TYPES.contains(cls) || object instanceof ObjectId
		|| object instanceof DBObject) {
	    return object.toString();
	}

	return object.getClass().getSimpleName();
    }
}
