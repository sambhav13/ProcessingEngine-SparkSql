package com.telarix.DAO;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.telarix.CDRProcessingEngine.CDRProcessingEngineDriver;
import com.telarix.Procedures.DBStoredProcedures;

public class IXAdmin_MainDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;	

	private	final static Logger logger = Logger.getLogger(IXAdmin_MainDAO.class);
	public IXAdmin_MainDAO(DataSource dataSource) {
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
		DBStoredProcedures myStoredProcedure = new DBStoredProcedures(this.jdbcTemplateObject, proc_Name);	
		myStoredProcedure.compile();

		//Call stored procedure
		Map storedProcResult = myStoredProcedure.execute();		
		System.out.println("The Procedure "+proc_Name+" on IXCDR_02DAO was executed !!");
		System.out.println(storedProcResult);

	}


	/**
	 * executes the procedure with the given proc_name(with paramters)
	 * @param proc_Name procedure name
	 * @param parameter MetaData Map parameters and  their types
	 * @param actualParameters actual parameters Map with their name as key
	 */
	public void executeProcedure(String proc_Name,Map<String,String> parameterMetaData,Map<String,Object> actualParameters)

	{
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
			default: System.out.println("The values not supplied");
			}

		}
		myStoredProcedure.setParameters(inputParams);		
		myStoredProcedure.compile();

		//Call stored procedure
		Map storedProcResult = myStoredProcedure.execute(actualParameters);		
		System.out.println("The Procedure "+proc_Name+" on IXCDR_02DAO was executed !!");
		System.out.println(storedProcResult);

	}

	public Map<String,Object> execute(String proc_Name,Map<String,String> parameterMetaData,Map<String,Object> actualParameters,Map<String,String> outParameterMetaData)
	{

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
			default: System.out.println("The values not supplied");
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
			default: System.out.println("The values not supplied");
			}

		}

		myStoredProcedure.setParameters(s);		
		myStoredProcedure.compile();

		//Call stored procedure
		Map<String,Object> storedProcResult = myStoredProcedure.execute(actualParameters);		
		System.out.println("The Procedure "+proc_Name+" on IXCDR_02DAO was executed !!");
		System.out.println(storedProcResult);
		
		//int deptId  = (int) storedProcResult.get("DepartmentId");
		//int employeeCount  = (int) storedProcResult.get("EmployeeCount");
		
		//declareParameter( new SqlParameter( "id", Types.INTEGER)); //declaring sql in parameter to pass input declareParameter( new SqlOutParameter( "name", Types.VARCHAR ) );
		return storedProcResult;
	}


	public void executeSql(String sql)
	{
		this.jdbcTemplateObject.execute(sql);

	}


	public ResultSet executePreparedStatement(String sql)
	{

		SqlRowSet results = this.jdbcTemplateObject.queryForRowSet(sql);
		ResultSet s = ((ResultSetWrappingSqlRowSet) results).getResultSet();

		//this.jdbcTemplateObject.queryForRowSet(sql)

		return s;

	}
}
