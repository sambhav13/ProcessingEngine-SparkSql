package com.telarix.Procedures;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.spark.sql.types.DataTypes;

import com.telarix.DAO.IXCDR02SparkSqlDAO;
import com.telarix.DAO.IXCDR_02DAO;
import com.telarix.UDF.AgreementResolveUDF;
import com.telarix.Utilities.ValueComparator;
import com.telarix.constants.ApplicationConstants;

public class BspSubAlgorithmStepExector {

	private IXCDR_02DAO iXCDR_02DAO;
	private	final static Logger logger = Logger.getLogger(BspSubAlgorithmStepExector.class);

	public BspSubAlgorithmStepExector(IXCDR_02DAO iXCDR_02DAO) {
		super();
		this.iXCDR_02DAO = iXCDR_02DAO;
	}



	public boolean executeTotalSteps(Map<String,Map<String,Object>> totalSteps)

	{
		int subStepsOrderCount = 0;
		for(Map.Entry<String,Map<String,Object>> entry : totalSteps.entrySet()){
			StringTokenizer st= new StringTokenizer(entry.getKey().toString(),",");
			String subStepType = st.nextToken();
			String procName = st.nextToken();

			totalSteps.get(0);
			if(subStepType.equals(ApplicationConstants.procedure_Const))
			{
				this.iXCDR_02DAO.executeProcedure(procName, entry.getValue());
			}
			subStepsOrderCount++;
		}

		logger.info("Sub steps executed for Agreement Resolver!!");
		if(subStepsOrderCount==1)
		{
			return true;
		}
		else
		{
			return false;
		}


	}

}
