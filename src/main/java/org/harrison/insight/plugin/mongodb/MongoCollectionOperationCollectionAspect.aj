package org.harrison.insight.plugin.mongodb;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;
import com.mongodb.WriteResult;
import com.springsource.insight.collection.AbstractOperationCollectionAspect;
import com.springsource.insight.intercept.operation.Operation;

public aspect MongoCollectionOperationCollectionAspect extends
	AbstractOperationCollectionAspect {

    public pointcut insertExecute(): execution(WriteResult DBCollection.insert(..));

    public pointcut updateExecute(): execution(WriteResult DBCollection.update(..));

    public pointcut updateMultiExecute(): execution(WriteResult DBCollection.updateMulti(..));

    public pointcut removeExecute(): execution(WriteResult DBCollection.remove(..));

    public pointcut findExecute(): execution(DBCursor DBCollection.find(..));

    public pointcut findOneExecute(): execution(DBObject DBCollection.findOne(..));

    public pointcut findAndModifyExecute(): execution(DBObject DBCollection.findAndModify(..));

    public pointcut findAndRemoveExecute(): execution(DBObject DBCollection.findAndRemove(..));

    public pointcut createIndexExecute(): execution(void DBCollection.createIndex(..));

    public pointcut ensureIndexExecute(): execution(void DBCollection.ensureIndex(..));

    public pointcut saveExecute(): execution(WriteResult DBCollection.save(..));

    public pointcut dropExecute(): execution(void DBCollection.drop());

    public pointcut getCountExecute(): execution(long DBCollection.getCount(..));

    public pointcut groupExecute(): execution(DBObject DBCollection.group());

    public pointcut distinctExecute(): execution(List DBCollection.distinct(..));

    public pointcut mapReduceExecute(): execution(MapReduceOutput DBCollection.mapReduce(..));

    public pointcut dropIndexExecute(): execution(void DBCollection.dropIndex(..));

    public pointcut collectionPoint():
      insertExecute() || 
      updateExecute() || 
      updateMultiExecute() || 
      removeExecute() || 
      findExecute() ||
      findOneExecute() || 
      findAndModifyExecute() ||
      createIndexExecute() || 
      ensureIndexExecute() || 
      saveExecute() ||
      dropExecute() || 
      getCountExecute() ||
      groupExecute() || 
      distinctExecute() || 
      mapReduceExecute() ||
      dropIndexExecute();

    @Override
    protected Operation createOperation(final JoinPoint joinPoint) {
	final Signature signature = joinPoint.getSignature();
	final DBCollection collection = (DBCollection) joinPoint.getThis();

	return new MongoCollectionOperation(getSourceCodeLocation(joinPoint),
		signature.getName(), ArgUtils.toString(joinPoint.getArgs()),
		collection.getFullName());
    }
}
