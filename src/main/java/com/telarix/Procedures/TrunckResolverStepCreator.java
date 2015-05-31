package com.telarix.Procedures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom2.Element;

import com.telarix.constants.ApplicationConstants;

public class TrunckResolverStepCreator {


	private Map<String,String> executionTotalSteps = new HashMap<String,String>();
	private	final static Logger logger = Logger.getLogger(TrunckResolverStepCreator.class);
	
	public Map<String, String> getExecutionTotalSteps() {
		return executionTotalSteps;
	}

	public void setExecutionTotalSteps(Map<String, String> executionTotalSteps) {
		this.executionTotalSteps = executionTotalSteps;
	}
	
	public Map<String,String> createTotalSteps(String virtualSwitch,List<Element> subSteps)
	{

		logger.info("Creating Steps of execution for TrunckResolver........");
		
		if(virtualSwitch.equals(ApplicationConstants.virtualSwitch_Val_First))
		{

			for(Element subStep : subSteps)
			{

				//String tempsql = subStep.getText();
				if(subStep.getAttribute(ApplicationConstants.virtualSwitch_Const).getValue().equals(ApplicationConstants.virtualSwitch_Val_First)&&(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.sqlQuery_Const)))
				{
					this.executionTotalSteps.put(ApplicationConstants.sqlQuery_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}

				if(subStep.getAttribute(ApplicationConstants.virtualSwitch_Const).getValue().equals(ApplicationConstants.virtualSwitch_Val_Third)&&(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.registerTempTable_Const)))
				{
					this.executionTotalSteps.put(ApplicationConstants.registerTempTable_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}

				if(subStep.getAttribute(ApplicationConstants.virtualSwitch_Const).getValue().equals(ApplicationConstants.virtualSwitch_Val_Third)&&(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.sqlQuery_Const)))
				{
					this.executionTotalSteps.put(ApplicationConstants.sqlQuery_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}
				
				if(subStep.getAttribute(ApplicationConstants.virtualSwitch_Const).getValue().equals(ApplicationConstants.virtualSwitch_Val_Third)&&(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.filter_Const)))
				{
					this.executionTotalSteps.put(ApplicationConstants.filter_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}
			}
		}
		else if(virtualSwitch.equals(ApplicationConstants.virtualSwitch_Val_Second))
		{
			for(Element subStep : subSteps)
			{
				//String tempsql = subStep.getText();
				if(subStep.getAttribute(ApplicationConstants.VariableName_Const).getValue().equals(ApplicationConstants.virtualSwitch_Val_First)&&(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.sqlQuery_Const)))
				{
					this.executionTotalSteps.put(ApplicationConstants.sqlQuery_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}

				if(subStep.getAttribute(ApplicationConstants.virtualSwitch_Const).getValue().equals(ApplicationConstants.virtualSwitch_Val_Third)&&(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.registerTempTable_Const)))
				{
					this.executionTotalSteps.put(ApplicationConstants.registerTempTable_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}

				if(subStep.getAttribute(ApplicationConstants.virtualSwitch_Const).getValue().equals(ApplicationConstants.virtualSwitch_Val_Third)&&(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.sqlQuery_Const)))
				{
					this.executionTotalSteps.put(ApplicationConstants.sqlQuery_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}

				if(subStep.getAttribute(ApplicationConstants.virtualSwitch_Const).getValue().equals(ApplicationConstants.virtualSwitch_Val_Third)&&(subStep.getAttribute(ApplicationConstants.type_Const).getValue().equals(ApplicationConstants.filter_Const)))
				{
					this.executionTotalSteps.put(ApplicationConstants.filter_Const+","+subStep.getAttribute(ApplicationConstants.orderID_Const).getValue(), subStep.getText());
				}

			}
		}
		
		logger.info("Steps For Trunk Resolver Created!!");
		return this.executionTotalSteps;
	}
}
