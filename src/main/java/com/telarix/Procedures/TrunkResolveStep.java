package com.telarix.Procedures;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.telarix.DAO.IXCDR02SparkSqlDAO;
import com.telarix.UDF.AgreementResolveUDF;
import com.telarix.UDF.TrunkResolveUDF;
import com.telarix.UDF.TrunkResolveVSUDF;

public class TrunkResolveStep {

	private IXCDR02SparkSqlDAO iXCDR02SparkSqlDAO; 
	
	public void startProcess(ConfigurableListableBeanFactory factory)
	{
		
		iXCDR02SparkSqlDAO = (IXCDR02SparkSqlDAO)factory.getBean("iXCDR02SparkSqlDAO");
		
		iXCDR02SparkSqlDAO.getSqlContext().udf().register("trunkresolve", new TrunkResolveUDF(),DataTypes.StringType);
		
		
		DataFrame ecrs_in = iXCDR02SparkSqlDAO.populateDataFrameViaSqlServerQuery("select RegistryID,BERID,trunkresolve(InTrunkName,InSwitchID,'inbound',"+0+") As InTrunkID,"
				+ "trunkresolve(InTrunkName,InSwitchID,'inbound',"+1+") As InCommercialTrunkID,trunkresolve(OutTrunkName,InSwitchID,'outbound',"+0+") As OutTrunkID,trunkresolve(OutTrunkName,OutSwitchID,'outbound',"+1+") As OutCommercialTrunkID from wtbECRTemp");
		
		ecrs_in.show();
		ecrs_in.cache();
		DataFrame updatedEcrs_in = ecrs_in.where(ecrs_in.col("InTrunkID").isNotNull());
		updatedEcrs_in.show();
		
		updatedEcrs_in.registerTempTable("wtbECRTempAftTrunk");
		
	
	}
}
