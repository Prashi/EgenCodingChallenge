package com.egen.rule;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.egen.db.dao.AlertsDAO;
import com.egen.db.model.RuleModel;

@Rule(name = "DetectOverWeightRule",
description = "Rule to detect UnderWeight")
public class DetectOverWeightRule {

private AlertsDAO alertsDAO;
private RuleInput input;

{
	alertsDAO = new AlertsDAO();
}

@Condition
public boolean checkInput() {
   return input.getNewValue() > (input.getOldValue() * 1.1)? true : false;
}

@Action
public void executeEvent() throws Exception {
    System.out.println("Over Weight" + input.getOldValue() + " >> "+ input.getNewValue());
    RuleModel ruleModel = input.getRuleModel();
    ruleModel.setEvent("OverWeight");
    alertsDAO.save(ruleModel);
}

public void setInput(RuleInput input) {
    this.input = input;
}

}