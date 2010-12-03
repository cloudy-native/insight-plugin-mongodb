package org.harrison.insight.plugin.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * Utilities for converting method arguments for MongoDB-related operations
 * 
 * @author stephen harrison
 */
public class ArgUtils {
    private static Class<?>[] SIMPLE_TYPES = new Class[] { String.class,
	    Boolean.class, Byte.class, Character.class, Short.class,
	    Integer.class, Long.class, Float.class, Double.class };
    /*
     * This list is probably wrong, or at least incomplete. Please feel free to
     * fix it up.
     */
    private static Class<?>[] MONGODB_TYPES = new Class[] { ObjectId.class,
	    CommandResult.class, BasicDBList.class, DBAddress.class,
	    DBCollection.class, DBObject.class, BasicDBObject.class,
	    Mongo.class, MapReduceOutput.class, MongoException.class,
	    WriteConcern.class, WriteResult.class };
    private static Set<Class<?>> TOSTRING_SAFE_TYPES = new HashSet<Class<?>>() {
	{
	    addAll(Arrays.asList(SIMPLE_TYPES));
	    addAll(Arrays.asList(MONGODB_TYPES));
	}
    };

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

    /**
     * Primitives and "safe" types get a call to {@link #toString()}; everything
     * else is just the class name.
     * 
     * @param object
     * @return
     */
    public static String toString(final Object object) {
	if (object == null) {
	    return "null";
	}

	final Class<? extends Object> cls = object.getClass();

	if (cls.isPrimitive() || TOSTRING_SAFE_TYPES.contains(cls)) {
	    return object.toString();
	}

	return cls.getSimpleName();
    }
}
