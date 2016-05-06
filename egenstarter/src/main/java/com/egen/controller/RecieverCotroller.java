package com.egen.controller;

import java.util.List;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.egen.db.dao.MetricsDAO;
import com.egen.db.model.TempModel;
import com.egen.rule.DetectOverWeightRule;
import com.egen.rule.DetectUnderWeightRule;
import com.egen.rule.RuleInput;
import com.mongodb.BasicDBObject;

@Controller
@RequestMapping("/")
public class RecieverCotroller {

	MetricsDAO metricsDAO;
	DetectUnderWeightRule underWeightRule;
	DetectOverWeightRule overWeightRule;
	RulesEngine rulesEngine;
	private static int lastTemp;
	{
		metricsDAO = new MetricsDAO();
		
		underWeightRule = new DetectUnderWeightRule();
		overWeightRule = new DetectOverWeightRule();
		RulesEngineBuilder rulesEngineBuilder = RulesEngineBuilder.aNewRulesEngine();
		
		rulesEngine = rulesEngineBuilder.build();
		rulesEngine.registerRule(underWeightRule);
		rulesEngine.registerRule(overWeightRule);
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String listAllRecords() {
		List<TempModel> records = metricsDAO.readMetrics();
		StringBuffer stringBuffer = new StringBuffer();
		int i=0;
		for(TempModel tempModel:records){
			stringBuffer.append(i++ + " &nbsp; Timestamp: "+tempModel.getTimestamp()+" Temprature: "+tempModel.getValue()+" <br>");
		}
		return "List of All Records:-<br>........................<br>" +stringBuffer.toString();
	}

	@RequestMapping(value="/{t1}/{t2}", method={RequestMethod.GET})
	public @ResponseBody String listRecordsByTimestamp(@PathVariable("t1") long t1, @PathVariable("t2") long t2) {
		List<TempModel> records = metricsDAO.readMetricsByTimestamp(t1, t2);
		StringBuffer stringBuffer = new StringBuffer();
		int i=0;
		for(TempModel tempModel:records){
			stringBuffer.append(i++ + " &nbsp; Timestamp: "+tempModel.getTimestamp()+" Temprature: "+tempModel.getValue()+" <br>");
		}
		return "List of All Records:-<br>........................<br>" +stringBuffer.toString();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void recordTemp(@RequestBody BasicDBObject basicDBObject) {
		TempModel temp = new TempModel();
		temp.setTimestamp(Long.parseLong((String)basicDBObject.get("timeStamp")));
		temp.setValue(Integer.parseInt((String)basicDBObject.get("value")));
		metricsDAO.saveMetrics(temp);
		
		RuleInput ruleInput = new RuleInput(temp.getTimestamp(),lastTemp, temp.getValue());
		
		underWeightRule.setInput(ruleInput);
		overWeightRule.setInput(ruleInput);
		rulesEngine.fireRules();
		lastTemp = temp.getValue();
	}
}