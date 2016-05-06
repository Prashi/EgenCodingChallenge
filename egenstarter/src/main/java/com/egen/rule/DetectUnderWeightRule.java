package com.egen.rule;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.egen.db.dao.AlertsDAO;
import com.egen.db.model.RuleModel;

@Rule(name = "DetectUnderWeightRule",
description = "Rule to detect UnderWeight")
public class DetectUnderWeightRule {

private AlertsDAO alertsDAO;
private RuleInput input;

{
	alertsDAO = new AlertsDAO();
}

@Condition
public boolean checkInput() {
    return input.getNewValue() < (input.getOldValue() * 0.9)? true : false;
}

@Action
public void executeEvent() throws Exception {
    System.out.println("Under Weight" + input.getOldValue() + " >> "+ input.getNewValue());
    RuleModel ruleModel = input.getRuleModel();
    ruleModel.setEvent("UnderWeight");
    alertsDAO.save(ruleModel);
}

public void setInput(RuleInput input) {
    this.input = input;
}

}