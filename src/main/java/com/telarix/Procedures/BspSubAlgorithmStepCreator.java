package com.telarix.Procedures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

import com.telarix.constants.ApplicationConstants;

public class BspSubAlgorithmStepCreator {

	private Map<String,Map<String,Object>> executionTotalSteps = new HashMap<String,Map<String,Object>>();
	private	final static Logger logger = Logger.getLogger(BspSubAlgorithmStepCreator.class);


	public Map<String, Map<String,Object>> getExecutionTotalSteps() {
		return executionTotalSteps;
	}

	public void setExecutionTotalSteps(Map<String,Map<String,Object>> executionTotalSteps) {
		this.executionTotalSteps = executionTotalSteps;
	}

	public Map<String,Map<String,Object>> createTotalSteps(List<Element> subSteps)
	{

		logger.info("Creating Steps of execution for BspSubAlgorithm Resolver........");

		Map<String,Map<String,Object>> executionTotalStepsTemp =  new HashMap<String,Map<String,Object>>();
		int parametersNum ;
		String procedureName;
		List<Element> parameterList;
		List<String> paramName;

		Map<String,Object> actualParameters = new HashMap<String,Object>(); 

		//create here the hashmap		 
		//or pass it in createTotalSteps
		for(Element subStep : subSteps)
		{

			if(subStep.getAttribute("type").getValue().equals("sqlCall"))
			{
				try {
					parametersNum = subStep.getAttribute("paramNumber").getIntValue();
				} catch (DataConversionException e) {

					e.printStackTrace();
				}
				procedureName = subStep.getChild("Procedure").getValue();
				parameterList = subStep.getChildren("Param");
				if(parameterList.size()==actualParameters.size())
				{

				}
				executionTotalStepsTemp.put(ApplicationConstants.procedure_Const+","+ procedureName,actualParameters);
			}


		}
		this.setExecutionTotalSteps(executionTotalStepsTemp);

		return this.getExecutionTotalSteps();
	}
}
