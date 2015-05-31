package com.telarix.DAO;

import java.sql.Types;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.telarix.Procedures.DBStoredProcedures;

public class IXSubscriptionMainDAO {

	private DataSource dataSourceSubscription;
	private JdbcTemplate jdbcTemplateObject;
	
	private	final static Logger logger = Logger.getLogger(IXSubscriptionMainDAO.class);
	
	public IXSubscriptionMainDAO(DataSource dataSourceSubscription) {
		super();
		this.dataSourceSubscription = dataSourceSubscription;
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceSubscription);
	}
	
	
	/**
	 * executes the procedure with the given proc_name(without parameters)
	 *
	 * @param proc_Name procedure name
	 */
	public void executeProcedure(String proc_Name)
	{
		logger.info("Executing procedure: "+proc_Name);
		DBStoredProcedures myStoredProcedure = new DBStoredProcedures(this.jdbcTemplateObject, proc_Name);

		myStoredProcedure.compile();
		//Call stored procedure
		Map storedProcResult = myStoredProcedure.execute();

		logger.info("The Procedure "+proc_Name+" on IXSubscriptionMainDAO  was executed!! !!");
		System.out.println("The Procedure "+proc_Name+" on IXSubscriptionMainDAO  was executed!! !!");
		System.out.println(storedProcResult);

	}
	
	
	/**
	 *  Executes the procedure with procName and the inputParameters
	 * @param procName
	 * @param inputParamMap
	 * @return
	 */
	public Map<String,Object> executeProcedure(String procName,Map<String,Object> inputParamMap)
	{
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplateObject).withProcedureName(procName);
		SqlParameterSource in = new MapSqlParameterSource(inputParamMap);
		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		System.out.println(simpleJdbcCallResult);

		return simpleJdbcCallResult;
	}


	/**
	 * executes the procedure with the given proc_name(with paramters)
	 * @param proc_Name procedure name
	 * @param parameter MetaData Map parameters and  their types
	 * @param actualParameters actual parameters Map with their name as key
	 */
	public void executeProcedure(String proc_Name,Map<String,String> parameterMetaData,Map<String,Object> actualParameters)

	{
		logger.info("Executing procedure: "+proc_Name);
		DBStoredProcedures myStoredProcedure = new DBStoredProcedures(this.jdbcTemplateObject, proc_Name);

		//Sql parameter mapping
		Iterator iterator = parameterMetaData.entrySet().iterator();

		SqlParameter[] inputParams = new SqlParameter[parameterMetaData.size()];

		int index = 0; 
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();


			switch(mapEntry.getValue().toString())
			{
			case "Integer":
				inputParams[index] = new SqlParameter(mapEntry.getKey().toString(),Types.INTEGER);  
				index++;
				break;
			case "String" : 
				inputParams[index] = new SqlParameter(mapEntry.getKey().toString(),Types.VARCHAR);
				break;
			default: 
				logger.warn("The values not supplied");
				System.out.println("The values not supplied");
			}

		}
		myStoredProcedure.setParameters(inputParams);
		myStoredProcedure.compile();

		//Call stored procedure
		Map storedProcResult = myStoredProcedure.execute(actualParameters);
		
		logger.info("The Procedure "+proc_Name+" on IXSubscriptionMainDAO  was executed!!");
		System.out.println("The Procedure "+proc_Name+" on IXSubscriptionMainDAO  was executed!!");
		System.out.println(storedProcResult);

	}
}
