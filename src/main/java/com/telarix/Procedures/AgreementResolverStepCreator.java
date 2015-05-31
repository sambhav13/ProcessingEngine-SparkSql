package com.telarix.Procedures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom2.Element;

import com.telarix.constants.ApplicationConstants;

public class AgreementResolverStepCreator {

	
	private Map<String,String> executionTotalSteps = new HashMap<String,String>();
	private	final static Logger logger = Logger.getLogger(AgreementResolverStepCreator.class);
	
	
	public Map<String, String> getExecutionTotalSteps() {
		return executionTotalSteps;
	}

	public void setExecutionTotalSteps(Map<String, String> executionTotalSteps) {
		this.executionTotalSteps = executionTotalSteps;
	}

	public Map<String,String> createTotalSteps(List<Element> subSteps)
	{
		
		logger.info("Creating Steps of execution for Agreement Resolver........");
		
		 Map<String,String> executionTotalStepsTemp =  new HashMap<String,String>();
			for(Element subStep : subSteps)
			{

				if(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.sqlQuery_Const))
				{
					executionTotalStepsTemp.put(ApplicationConstants.sqlQuery_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}
				
				if(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.filter_Const))
				{
					executionTotalStepsTemp.put(ApplicationConstants.filter_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}

				if(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.registerTempTable_Const))
				{
					executionTotalStepsTemp.put(ApplicationConstants.registerTempTable_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}
			}
			this.setExecutionTotalSteps(executionTotalStepsTemp);
	
		return this.getExecutionTotalSteps();
	}
}
