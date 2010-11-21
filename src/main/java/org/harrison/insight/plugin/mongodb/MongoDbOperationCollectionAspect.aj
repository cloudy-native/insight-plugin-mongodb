package org.harrison.insight.plugin.mongodb;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import com.springsource.insight.collection.AbstractOperationCollectionAspect;
import com.springsource.insight.intercept.operation.Operation;

public aspect MongoDbOperationCollectionAspect extends
	AbstractOperationCollectionAspect {
    public pointcut collectionPoint()
    : execution(* com.mongodb.DB.*(..));

    @Override
    protected Operation createOperation(final JoinPoint joinPoint) {
	final Signature signature = joinPoint.getSignature();

	return new MongoDbOperation(getSourceCodeLocation(joinPoint),
		signature.toShortString());
    }
}
