package org.harrison.insight.plugin.mongodb;

import org.aspectj.lang.JoinPoint;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.springsource.insight.collection.AbstractOperationCollectionAspect;
import com.springsource.insight.intercept.operation.Operation;

public aspect MongoDbOperationCollectionAspect extends
	AbstractOperationCollectionAspect {

    public pointcut collectionPoint(): execution(CommandResult DB.command(..));

    @Override
    protected Operation createOperation(final JoinPoint joinPoint) {
	return new MongoDbOperation(getSourceCodeLocation(joinPoint), joinPoint
		.getSignature().getName(), ArgUtils.toString(joinPoint
		.getArgs()));
    }
}
