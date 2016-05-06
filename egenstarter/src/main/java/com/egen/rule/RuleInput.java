package com.egen.rule;

import com.egen.db.model.RuleModel;

public class RuleInput {
	public RuleInput(Long timestamp, int oldValue, int newValue) {
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.timestamp = timestamp;
	}
	private Long timestamp;
	private int oldValue;
	private int newValue;
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public int getOldValue() {
		return oldValue;
	}
	public void setOldValue(int oldValue) {
		this.oldValue = oldValue;
	}
	public int getNewValue() {
		return newValue;
	}
	public void setNewValue(int newValue) {
		this.newValue = newValue;
	}

	public RuleModel getRuleModel(){
		RuleModel ruleModel =new RuleModel();
		ruleModel.setOldValue(this.getOldValue());
		ruleModel.setNewValue(this.getNewValue());
		ruleModel.setTimestamp(this.getTimestamp());
		return ruleModel;
	}

}
