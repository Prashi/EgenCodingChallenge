package com.egen.db.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

import com.egen.db.model.RuleModel;
import com.egen.db.util.DBConnector;

public class AlertsDAO extends BasicDAO<RuleModel, ObjectId> {
	
	public AlertsDAO() {
		super(RuleModel.class, DBConnector.instance().getDatastore());
	}
	
   public Object saveMetrics(RuleModel RuleModel){
	    	Key<RuleModel> savedtemp = save(RuleModel); 
			return savedtemp.getId();
   }
   
   public List<RuleModel> readMetrics(){
	   QueryResults<RuleModel> records = find();
	   List<RuleModel> temps = new ArrayList<RuleModel>();
	   for(RuleModel RuleModel:records){
		   temps.add(RuleModel);
	   }
	   return temps;
   }
   
   public List<RuleModel> readMetricsByTimestamp(long startTime, long endTime){
	   
	   Query query = createQuery();
	   query = query.filter("timeStamp >", startTime).filter("timeStamp <", endTime);
	   QueryResults<RuleModel> records = find(query);
	   List<RuleModel> temps = new ArrayList<RuleModel>();
	   for(RuleModel RuleModel:records){
		   temps.add(RuleModel);
	   }
	   return temps;
   }
}