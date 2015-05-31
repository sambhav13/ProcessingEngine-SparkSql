package com.telarix.Procedures;

import java.util.Map;
import java.util.TreeMap;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.types.DataTypes;

import com.telarix.DAO.IXCDR02SparkSqlDAO;
import com.telarix.DAO.IXSubscriptionMainSparkSqlDAO;
import com.telarix.UDF.TrunkResolveVSUDF;
import com.telarix.Utilities.ValueComparator;
import com.telarix.constants.ApplicationConstants;
import com.telarix.constants.TableNameConstants;

public class TrunckResolverStepExecutor {


	//private IXSubscriptionMainSparkSqlDAO subDAO = null;
	private IXCDR02SparkSqlDAO iXCDR02SparkSqlDAO = null;

	private	final static Logger logger = Logger.getLogger(TrunckResolverStepExecutor.class);

	private DataFrame ecrs;
	private DataFrame updatedEcrs;
	//private DataFrame wtbECRTempAftTrunk;

	public TrunckResolverStepExecutor(IXCDR02SparkSqlDAO iXCDR02SparkSqlDAO) {
		super();
		this.iXCDR02SparkSqlDAO = iXCDR02SparkSqlDAO;
	}


	public boolean executeTotalSteps(Map<String,String> totalSteps,String virtualSwitch)
	{
		logger.info("Executing sub steps for Trunk Resolver.....");		
		int subStepsOrderCount = 0;
		
		iXCDR02SparkSqlDAO.getSqlContext().udf().register("trunkresolveVS", new TrunkResolveVSUDF(),DataTypes.StringType);

		ValueComparator bvc  = new ValueComparator(totalSteps);
		TreeMap<String,String> sorted_map = new TreeMap<String,String>(bvc);

		sorted_map.putAll(totalSteps);

		for(Map.Entry<String,String> entry : sorted_map.entrySet()){

			StringTokenizer st= new StringTokenizer(entry.getKey().toString(),",");
			String subStepType = st.nextToken();
			String subStepOrder = st.nextToken();

			if(subStepType.equals(ApplicationConstants.sqlQuery_Const))
			{
				switch(Integer.parseInt(subStepOrder))
				{

				case 1: 
					ecrs = this.iXCDR02SparkSqlDAO.populateDataFrameViaSqlServerQuery(entry.getValue());
					ecrs.cache();
					//ecrs.show();
					subStepsOrderCount++;
					break;

				default:
				}
			}
			else if(subStepType.equals(ApplicationConstants.filter_Const))
			{
				switch(Integer.parseInt(subStepOrder))
				{
				case 2: 
					updatedEcrs = ecrs.where(ecrs.col(entry.getValue().trim()).isNotNull());
					
					//updatedEcrs.show();	
					subStepsOrderCount++;
					break;

				default:
				}
			}
			else if(subStepType.equals(ApplicationConstants.registerTempTable_Const))
			{
				switch(Integer.parseInt(subStepOrder))
				{

				case 3: 
					updatedEcrs.registerTempTable(entry.getValue().trim());
					subStepsOrderCount++;
					break;

				default:
				}
			}


		}

		logger.info("Sub steps executed for Trunk Resolver!!");

		//DataFrame dt = wtbECRTemp.groupBy(ApplicationConstants.callDate_Const).count();
		//dt.show();
		if(subStepsOrderCount==3)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
}

	