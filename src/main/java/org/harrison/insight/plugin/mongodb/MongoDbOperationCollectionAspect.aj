package org.harrison.insight.plugin.mongodb;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.springsource.insight.collection.AbstractOperationCollectionAspect;
import com.springsource.insight.intercept.operation.Operation;

public aspect MongoDbOperationCollectionAspect extends
	AbstractOperationCollectionAspect {

    public pointcut collectionPoint(): execution(CommandResult DB.command(..));

    @Override
    protected Operation createOperation(final JoinPoint joinPoint) {
	final Signature signature = joinPoint.getSignature();

	return new MongoDbOperation(getSourceCodeLocation(joinPoint),
		ArgUtils.toString(joinPoint.getArgs()),
		signature.toShortString());
    }
}
