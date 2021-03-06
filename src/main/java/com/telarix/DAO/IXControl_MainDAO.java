package com.telarix.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.telarix.Procedures.ProcessAttribute;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.telarix.Procedures.DBStoredProcedures;

public class IXControl_MainDAO {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;	

	private	final static Logger logger = Logger.getLogger(IXControl_MainDAO.class);
	
	public IXControl_MainDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
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
		logger.info("The Procedure " + proc_Name + " on IXControl_MainDAO was executed !!");
		System.out.println("The Procedure "+proc_Name+" on IXControl_MainDAO was executed !!");
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
		logger.info("The Procedure "+proc_Name+" on IXControl_MainDAO was executed !!");
		System.out.println("The Procedure "+proc_Name+" on IXControl_MainDAO was executed !!");
		System.out.println(storedProcResult);

	}

	
	/** executes the stored procedure with  both inut and output parameters specified
	 * @param proc_Name
	 * @param parameterMetaData
	 * @param actualParameters
	 * @param outParameterMetaData
	 * @return
	 */
	public Map<String,Object> execute(String proc_Name,Map<String,String> parameterMetaData,Map<String,Object> actualParameters,Map<String,String> outParameterMetaData)
	{

		logger.info("Executing procedure: "+proc_Name);
		DBStoredProcedures myStoredProcedure = new DBStoredProcedures(this.jdbcTemplateObject, proc_Name);
		Map<String,Object> results = null;
		//myStoredProcedure.

		//Sql parameter mapping		
		Iterator iterator = parameterMetaData.entrySet().iterator();		
		SqlParameter[] inputParams = new SqlParameter[parameterMetaData.size()];


		//Sql output parameter mapping		
		Iterator iterator2 = outParameterMetaData.entrySet().iterator();		
		SqlOutParameter[] outParams = new SqlOutParameter[outParameterMetaData.size()];
		
		SqlParameter[] s = new SqlParameter[parameterMetaData.size()+outParameterMetaData.size()];

		int index = 0; 
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();


			switch(mapEntry.getValue().toString())
			{
			case "Integer":
				inputParams[index] = new SqlParameter(mapEntry.getKey().toString(),Types.INTEGER); 
				s[index] = new SqlParameter(mapEntry.getKey().toString(),Types.INTEGER);
				index++;
				break;
			case "String" : 
				inputParams[index] = new SqlParameter(mapEntry.getKey().toString(),Types.VARCHAR);
				s[index] = new SqlParameter(mapEntry.getKey().toString(),Types.VARCHAR);
				index++;
				break;
			case "NvarChar" : 
				inputParams[index] = new SqlParameter(mapEntry.getKey().toString(),Types.NVARCHAR);
				s[index] = new SqlParameter(mapEntry.getKey().toString(),Types.NVARCHAR);
				index++;
				break;
			default: 
				logger.warn("The values not supplied");
				System.out.println("The values not supplied");
			}

		}

		int index2 = 0; 
		while (iterator2.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator2.next();

			switch(mapEntry.getValue().toString())
			{
			case "Integer":
				
				s[index] = new SqlOutParameter(mapEntry.getKey().toString(),Types.INTEGER); 
				index++;
				break;
			case "String" : 
				
				s[index] = new SqlOutParameter(mapEntry.getKey().toString(),Types.VARCHAR);
				index++;
				break;
			case "NvarChar" : 
				s[index] =  new SqlOutParameter(mapEntry.getKey().toString(),Types.NVARCHAR);
				index++;
				break;
			default: 
				logger.warn("The values not supplied");
				System.out.println("The values not supplied");
			}

		}

		myStoredProcedure.setParameters(s);		
		myStoredProcedure.compile();

		//Call stored procedure
		Map<String,Object> storedProcResult = myStoredProcedure.execute(actualParameters);		
		logger.info("The Procedure "+proc_Name+" on IXControl_MainDAO was executed !!");
		System.out.println("The Procedure "+proc_Name+" on IXControl_MainDAO was executed !!");
		System.out.println(storedProcResult);
		
		return storedProcResult;
	}

	
	/** Executes simple sql query on the DB 
	 * @param sql
	 */
	public void executeSql(String sql)
	{
		logger.info("Executing sql ");
		this.jdbcTemplateObject.execute(sql);
		logger.info("Sql Executed on IXControl_MainDAO: "+sql);


	}
	
	
	/** Executes sql query returning a result set
	 * @param sql
	 * @return
	 */
	public ResultSet executePreparedStatement(String sql)
	{

		logger.info("Executing sql with Result Set");
		SqlRowSet results = this.jdbcTemplateObject.queryForRowSet(sql);
		ResultSet s = ((ResultSetWrappingSqlRowSet) results).getResultSet();

		logger.info("Sql Executed on IXControl_MainDAO: "+sql);
		return s;

	}

	/**
	 * Executes sql query to return an object of the specified class
	 * @param sql - SQL query
	 * @param param - Parameter used to filter queried records
	 * @param c - Class object that method should return
	 * @return
	 */
	public Object queryForObject(String sql, String param, Class c){
		logger.info("Query for object: " + sql);

		Object obj;
		obj = c.cast( this.jdbcTemplateObject.queryForObject(sql, new BeanPropertyRowMapper(c), param));


		return obj;

	}

	/**
	 * Executes sql to return a list of objects of the specified class
	 * @param sql - SQL query
	 * @param param - Paramter used to filter queried records
	 * @param c - Class object that method should return a list of
	 * @return
	 */
	public List<Object> queryForList(String sql, String param, Class c){
		logger.info("Query for list: " + sql);

		List<Object> list = this.jdbcTemplateObject.query(sql, new BeanPropertyRowMapper(c), param);

		return list;
	}


}
