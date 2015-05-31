package com.telarix.Utilities;



import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.telarix.DAO.IXCDR_02DAO;
import com.telarix.constants.ProcedureNameConstants;

public class PrepareData {
	
	private IXCDR_02DAO cdr02_Dao;
	private	final static Logger logger = Logger.getLogger(PrepareData.class);
	
	public PrepareData(IXCDR_02DAO cdr02_Dao) {
		
		super();
		this.cdr02_Dao = cdr02_Dao;
	}

	public void createWorkTable(Timestamp selDate)
	{
		logger.info("Creating work tables, calling procedure....");
		
		Map<String,Object> inputParameters = new HashMap<String,Object>();
		inputParameters.put("selDate", selDate);
		cdr02_Dao.executeProcedure(ProcedureNameConstants.createWorkTables_NGN,inputParameters);
		//cdr02_Dao.executeProcedure(ProcedureNameConstants.createWorkTables_NGN);
				
		logger.info("Procedure executed and work tales created");
		
		
	}
	
	
	/**
	 * 
	 *  Sapre method to include if multiple stored procedure need to be called
	 *  
	 * @param subAlgorithmId
	 * @param processId
	 * @param AlgorithmId
	 * @param OnLoop
	 * @param LoopOnValue
	 */
	public void createWorkTable(int subAlgorithmId,int processId,int AlgorithmId,String OnLoop,String LoopOnValue)
	{
		//cdr02_Dao.executeProcedure(ProcedureNameConstants.createWorkTables_NGN);
		
	}
		
		
}
