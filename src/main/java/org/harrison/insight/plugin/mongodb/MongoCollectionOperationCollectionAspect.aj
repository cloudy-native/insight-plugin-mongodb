package org.harrison.insight.plugin.mongodb;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import com.springsource.insight.collection.AbstractOperationCollectionAspect;
import com.springsource.insight.intercept.operation.Operation;

public aspect MongoCollectionOperationCollectionAspect extends
	AbstractOperationCollectionAspect {
    public pointcut collectionPoint()
    : execution(* com.mongodb.DBCollection.*(..));

    @Override
    protected Operation createOperation(final JoinPoint joinPoint) {
	final Signature signature = joinPoint.getSignature();
	
	final Object[] args = joinPoint.getArgs();
	
	return new MongoCollectionOperation(getSourceCodeLocation(joinPoint),
		signature.toShortString(), args);
    }
}
