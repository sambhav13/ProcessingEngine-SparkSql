package com.telarix.CDRProcessingEngine;

import com.telarix.DAO.IXCDR_02DAO;
import com.telarix.ProcessTypes.CDRLoad;

import org.apache.log4j.Logger;
import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by patrice.bramble on 5/13/2015.
 */
public class FileImporter {

	public HashMap<String, Object>  GlobalVariables = CDRLoad.getGlbVariable();
	private final Logger logger = Logger.getLogger(FileImporter.class);

	private int RegistryID;
	private int ProcessLogID;
	private String RawTableName;
	private int BERDataStructureID;
	private int ECRDataStructureID;
	private int FileDefinitionID;
	private int SwitchID;
	private int AdjustCallDate;
	private int MaxCallDuration;
	private int DatabaseID;
	private String LoopOn;
	private String LoopOnValue;
	private int AlgorithmID;

	ConfigurableListableBeanFactory factory = null;

	public FileImporter(ConfigurableListableBeanFactory factory){
		
		this.factory = factory;
		RegistryID = Integer.parseInt(GlobalVariables.get("RegistryID").toString());
		ProcessLogID = Integer.parseInt(GlobalVariables.get("ProcessLogID").toString());
		RawTableName = GlobalVariables.get("RawTableName").toString();
		BERDataStructureID = Integer.parseInt(GlobalVariables.get("BERDataStructureID").toString());
		ECRDataStructureID = Integer.parseInt(GlobalVariables.get("ECRDataStructureID").toString());
		FileDefinitionID = Integer.parseInt(GlobalVariables.get("FileDefinitionID").toString());
		SwitchID = Integer.parseInt(GlobalVariables.get("SwitchID").toString());
		AdjustCallDate = 0;//Integer.parseInt(GlobalVariables.get("AdjustCallDate").toString());
		MaxCallDuration = Integer.parseInt(GlobalVariables.get("MaxCallDuration").toString());
		DatabaseID = Integer.parseInt(GlobalVariables.get("DatabaseID").toString());
		//LoopOn = GlobalVariables.get("LoopOn").toString();
		//LoopOnValue = GlobalVariables.get("LoopOnValue").toString();
		AlgorithmID = Integer.parseInt(GlobalVariables.get("AlgorithmID").toString());
	}


	public void FileImport(){
		
		
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("ProcessLogID",ProcessLogID);
		parameters.put("RawTableName", RawTableName);

		IXCDR_02DAO ixcdr02DAO = (IXCDR_02DAO) factory.getBean("iXCDR_02DAO");

		ixcdr02DAO.executeProcedure("bspFileImport_NGN", parameters);
		logger.info("Procedure bspFileImport_NGN executed");
	}

	public void FileTrim(){
		Map<String,Object> parameters = new HashMap<String,Object>();

		parameters.put("ProcessLogID", ProcessLogID);
		parameters.put("BERDataStructureID", BERDataStructureID);
		parameters.put("RawTableName", RawTableName);
		parameters.put("FileDefinitionID", FileDefinitionID);

		IXCDR_02DAO ixcdr02DAO = (IXCDR_02DAO) factory.getBean("iXCDR_02DAO");
		ixcdr02DAO.executeProcedure("bspFileTrim_NGN", parameters);
		logger.info("Procedure bspFileTrim_NGN executed");

	}

	public void FilePreStandardize(){
		Map<String,Object> parameters = new HashMap<String,Object>();

		parameters.put("ProcessLogID",ProcessLogID);
		parameters.put("RegistryID", RegistryID);
		parameters.put("SwitchID", SwitchID);
		parameters.put("AdjustCallDate", AdjustCallDate);
		parameters.put("MaxCallDuration", MaxCallDuration);

		IXCDR_02DAO ixcdr02DAO = (IXCDR_02DAO) factory.getBean("iXCDR_02DAO");
		ixcdr02DAO.executeProcedure("bspFilePreStandardize_NGN", parameters);
		logger.info("Procedure bspFilePreStandardize_NGN executed");
	}

	public void FileStandardize(){

		Map<String,Object> parameters = new HashMap<String,Object>();

		parameters.put("ProcessLogID",ProcessLogID);
		parameters.put("RegistryID", RegistryID);
		parameters.put("SwitchID", SwitchID);
		parameters.put("BERDataStructureID", BERDataStructureID);
		parameters.put("ECRDataStructureID", ECRDataStructureID);
		parameters.put("DatabaseID", DatabaseID);
		parameters.put("AlgorithmID",AlgorithmID);


		IXCDR_02DAO ixcdr02DAO = (IXCDR_02DAO) factory.getBean("iXCDR_02DAO");
		ixcdr02DAO.executeProcedure("bspFileStandardize_iXBill_NGN", parameters);
		logger.info("Procedure bspFileStandardize_iXBill_NGN executed");
	}


	public void runImport(){
		logger.info("File import Started!");
		FileImport();
		FileTrim();
		FilePreStandardize();
		FileStandardize();
		logger.info("File import steps completed!");
	}


}