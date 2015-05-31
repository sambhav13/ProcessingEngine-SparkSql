package com.telarix.CDRProcessingEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.telarix.Procedures.AgreementResolverStepCreator;
import com.telarix.Procedures.AgreementResolverStepExecutor;
import com.telarix.Procedures.BspSubAlgorithmStepCreator;
import com.telarix.Procedures.BspSubAlgorithmStepExector;
import com.telarix.Procedures.TrunckResolverStepCreator;
import com.telarix.Procedures.TrunckResolverStepExecutor;
import com.telarix.Utilities.AlgorithmXMLStepsLoader;
import com.telarix.Utilities.BeanFactorySingleton;
import com.telarix.constants.ApplicationConstants;
import com.telarix.constants.BeanConstants;

import org.apache.log4j.Logger;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class AlgoStepExecutor {

	private AlgorithmXMLStepsLoader algoXmlStepLoader;
	private List<List<Element>> algoSteps;
	private int subAlgorithId;
	private String subAlgorithmName;
	private int subAlgorithmLoopDef;
	private String loopOn;
	private String loopOnValue;
	private List<String> callDates;

	private long algoStepsProcess_Start = 0;
	private long algoStepsProcess_End = 0;
	private long algoStepsProcessTime = 0;

	private final static Logger logger = Logger.getLogger(AlgoStepExecutor.class);

	public AlgoStepExecutor(AlgorithmXMLStepsLoader algoXmlStepLoader) {
		super();
		this.algoXmlStepLoader = algoXmlStepLoader;
	}

	public Document readXmlSteps()
	{
		Document xmlStep = this.algoXmlStepLoader.ReadXMLFile();
		return xmlStep;
	}


	public void executeSteps(Document document,List<String> callDates)
	{
		algoStepsProcess_Start = System.currentTimeMillis();
		Element rootNode = document.getRootElement();
		List<Element> algorithm = rootNode.getChildren(ApplicationConstants.subAlgorithm_Const);

		this.callDates = callDates;	
		//Iterate through subalgorithms
		algoSteps = new ArrayList<List<Element>>();
		for (Element subAlgorithm : algorithm) {

			try {
				this.subAlgorithId = subAlgorithm.getAttribute(ApplicationConstants.ID_Const).getIntValue();
				this.subAlgorithmLoopDef = subAlgorithm.getAttribute(ApplicationConstants.loopDef_Const).getIntValue();
			} catch (DataConversionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.subAlgorithmName = subAlgorithm.getAttribute(ApplicationConstants.Name_Const).getValue();


			List<Element> steps = subAlgorithm.getChildren(ApplicationConstants.step_Const);
			algoSteps.add(steps);


		}

		executeSteps();
	}

	public void executeSteps(Document document,String callDate) {
		Element rootNode = document.getRootElement();
		List<Element> algorithm = rootNode.getChildren(ApplicationConstants.subAlgorithm_Const);

		//Iterate through subalgorithms
		algoSteps = new ArrayList<List<Element>>();
		for (Element subAlgorithm : algorithm) {

			try {
				this.subAlgorithId = subAlgorithm.getAttribute(ApplicationConstants.ID_Const).getIntValue();
				this.subAlgorithmLoopDef = subAlgorithm.getAttribute(ApplicationConstants.loopDef_Const).getIntValue();
			} catch (DataConversionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.subAlgorithmName = subAlgorithm.getAttribute(ApplicationConstants.Name_Const).getValue();


			List<Element> steps = subAlgorithm.getChildren(ApplicationConstants.step_Const);
			algoSteps.add(steps);
		}

		executeSteps();
	}

	/**
	 * After establishing a jdbc connection to database and reading in XML config file,
	 * this method executes each SubAlgorithm.
	 *
	 * @return true if all SubAlgorithms were successfully executed
	 */
	public boolean executeSteps() {

		//iterates through subalgorithms
		for (List<Element> subAlgorithm : algoSteps) {

			boolean flag = executeSubAlgorithm(subAlgorithm);
			if (!flag) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Helper method called by executeStoredProcedures, this method iterates through all
	 * of the steps of the given SubAlgorithm and executes each stored procedure.
	 * @param subAlgorithm is the list of steps for the current SubAlgorithm
	 * @return true if all steps were executed successfully
	 */
	public boolean executeSubAlgorithm(List<Element> subAlgorithm) {

		int subAlgorithmID = -1;
		String loopOn = "";
		String loopOnValue = "";
		int stepID ;
		String stepName = null;
		int  stepOrder ;
		boolean isLoop;
		boolean isIXBill;

		algoStepsProcess_End = System.currentTimeMillis();

		algoStepsProcessTime = (algoStepsProcess_End - algoStepsProcess_Start)/1000;
		logger.info("The time in algo xml processing is :--"+algoStepsProcessTime);
		/* Iterates through all the substeps of steps that are there in the a given SubAlgorithm
		 * 
		 * 
		 */
		for (Element step : subAlgorithm) {

			try {
				stepID = step.getAttribute(ApplicationConstants.ID_Const).getIntValue();
				stepName = step.getAttribute(ApplicationConstants.Name_Const).getValue();
				stepOrder = step.getAttribute(ApplicationConstants.StepOrder_Const).getIntValue();
				isLoop = step.getAttribute(ApplicationConstants.isLoop_Const).getBooleanValue();
				isIXBill = step.getAttribute(ApplicationConstants.IsIXBill_Const).getBooleanValue();

				executeSubSteps(step,stepID,this.subAlgorithmLoopDef);

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;


	}



	public boolean executeSubSteps(Element Step,int StepID,int loopDef)
	{


		List<Element> subSteps = Step.getChildren(ApplicationConstants.subStep_Const);

		String virtualSwitch =ApplicationConstants.virtualSwitch_Val_First;

		BeanFactorySingleton bfs = BeanFactorySingleton.getInstance();
		ConfigurableListableBeanFactory factory = bfs.getFactory();

		//SparkConf conf =  (SparkConf)factory.getBean(BeanConstants.sparkConf_bean);

		//conf.setAppName("test").setMaster("local");

		Map<String,String> executionTotalSteps ;
		Map<String,Map<String,Object>> storedProcExecutionTotalSteps;
		int stepCount = 0; 



		switch (loopDef){

		case 0: 		loopOn = "NULL";
		loopOnValue = "NULL";
		break;
		case 1: 		loopOn = "CallDate";
		loopOnValue = "CallDate";
		break;
		case 2: 		loopOn = "Partition";
		loopOnValue = "Partition";
		break;
		}


		long trunkstart = 0;
		long trunkend = 0; 
		long agrstart = 0;
		long agrend = 0;

		switch(StepID)
		{
		case 7:
			trunkstart = System.currentTimeMillis();

			TrunckResolverStepCreator trkResolCre = (TrunckResolverStepCreator)factory.getBean(BeanConstants.trunckResolverStepCreator_bean);
			executionTotalSteps = trkResolCre.createTotalSteps(virtualSwitch, subSteps);
			TrunckResolverStepExecutor trsExe =  (TrunckResolverStepExecutor) factory.getBean(BeanConstants.trunckResolverStepExecutor_bean);					
			trsExe.executeTotalSteps(executionTotalSteps,"Yes");
			stepCount++;
			trunkend = System.currentTimeMillis();

			System.out.println("Trunk Time : " + ( (trunkend - trunkstart) / 60000) + " minutes "  + (( (trunkend - trunkstart) / 1000) % 60) + " seconds ");
			break;

		case 34:

			agrstart = System.currentTimeMillis();		

			AgreementResolverStepCreator agrResolCre = (AgreementResolverStepCreator)factory.getBean(BeanConstants.agreementResolverStepCreator_bean);
			executionTotalSteps = agrResolCre.createTotalSteps(subSteps);
			AgreementResolverStepExecutor agreExe =  (AgreementResolverStepExecutor) factory.getBean(BeanConstants.agreementResolverStepExecutor_bean);					
			agreExe.executeTotalSteps(executionTotalSteps);
			stepCount++;

			agrend = System.currentTimeMillis();

			System.out.println("Agreement Time : " + ( (agrend - agrstart)  / 60000) + " minutes "  + (( (agrend - agrstart) / 1000) % 60) + " seconds ");
			break;

		case 3:

			//agrstart = System.currentTimeMillis();		

			BspSubAlgorithmStepCreator subAlgoCre = (BspSubAlgorithmStepCreator)factory.getBean(BeanConstants.bspSubAlgorithmStepCreator_bean);
			storedProcExecutionTotalSteps = subAlgoCre.createTotalSteps(subSteps);
			BspSubAlgorithmStepExector subAlgoExe =  (BspSubAlgorithmStepExector) factory.getBean(BeanConstants.bspSubAlgorithmStepExecutor_bean);					
			subAlgoExe.executeTotalSteps(storedProcExecutionTotalSteps);
			stepCount++;

			//agrend = System.currentTimeMillis();

			//System.out.println("Agreement Time : " + ( (agrend - agrstart)  / 60000) + " minutes "  + (( (agrend - agrstart) / 1000) % 60) + " seconds ");
			break;


		default:
		}

		if(stepCount==2)
		{
			return true;
		}
		else {
			return false;
		}

	}
}
