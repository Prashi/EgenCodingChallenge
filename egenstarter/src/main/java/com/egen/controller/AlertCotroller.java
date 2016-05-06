package com.egen.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.egen.db.dao.AlertsDAO;
import com.egen.db.model.RuleModel;

@Controller
@RequestMapping("/alerts")
public class AlertCotroller {

	AlertsDAO alertsDAO;
	{
		alertsDAO = new AlertsDAO();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String listAllRecords() {
		List<RuleModel> records = alertsDAO.readMetrics();
		StringBuffer stringBuffer = new StringBuffer();
		int i=0;
		for(RuleModel ruleModel:records){
			stringBuffer.append(i++ + " &nbsp; Event "+ruleModel.getEvent()+" Timestamp: "+ruleModel.getTimestamp()+" Old Value: "+ruleModel.getOldValue()+" New Value: "+ruleModel.getNewValue()+" <br>");
		}
		return "List of All Records:-<br>........................<br>" +stringBuffer.toString();
	}

	@RequestMapping(value="/{t1}/{t2}", method={RequestMethod.GET})
	public @ResponseBody String listRecordsByTimestamp(@PathVariable("t1") long t1, @PathVariable("t2") long t2) {
		List<RuleModel> records = alertsDAO.readMetricsByTimestamp(t1, t2);
		StringBuffer stringBuffer = new StringBuffer();
		int i=0;
		for(RuleModel ruleModel:records){
			stringBuffer.append(i++ + " &nbsp; Event "+ruleModel.getEvent()+" Timestamp: "+ruleModel.getTimestamp()+" Old Value: "+ruleModel.getOldValue()+" New Value: "+ruleModel.getNewValue()+" <br>");
		}
		return "List of All Records:-<br>........................<br>" +stringBuffer.toString();
	}
}