package com.egen.db.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

import com.egen.db.model.TempModel;
import com.egen.db.util.DBConnector;

public class MetricsDAO extends BasicDAO<TempModel, ObjectId> {
	
	public MetricsDAO() {
		super(TempModel.class, DBConnector.instance().getDatastore());
	}
	
   public Object saveMetrics(TempModel tempModel){
	    	Key<TempModel> savedtemp = save(tempModel); 
			return savedtemp.getId();
   }
   
   public List<TempModel> readMetrics(){
	   QueryResults<TempModel> records = find();
	   List<TempModel> temps = new ArrayList<TempModel>();
	   for(TempModel tempModel:records){
		   temps.add(tempModel);
	   }
	   return temps;
   }
   
   public List<TempModel> readMetricsByTimestamp(long startTime, long endTime){
	   
	   Query query = createQuery();
	   query = query.filter("timeStamp >", startTime).filter("timeStamp <", endTime);
	   QueryResults<TempModel> records = find(query);
	   List<TempModel> temps = new ArrayList<TempModel>();
	   for(TempModel tempModel:records){
		   temps.add(tempModel);
	   }
	   return temps;
   }
}