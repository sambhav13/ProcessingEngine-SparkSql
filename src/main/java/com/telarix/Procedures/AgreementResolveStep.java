package com.telarix.Procedures;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.telarix.DAO.IXCDR02SparkSqlDAO;
import com.telarix.UDF.AgreementResolveUDF;
import com.telarix.UDF.TrunkResolveUDF;

public class AgreementResolveStep {
private IXCDR02SparkSqlDAO iXCDR02SparkSqlDAO; 
	
	public void startProcess(ConfigurableListableBeanFactory factory)
	{
		
		iXCDR02SparkSqlDAO = (IXCDR02SparkSqlDAO)factory.getBean("iXCDR02SparkSqlDAO");
		//iXCDR02SparkSqlDAO.getSqlContext().udf().register("trunkresolve", new TrunkResolveUDF(),DataTypes.StringType); 
		iXCDR02SparkSqlDAO.getSqlContext().udf().register("agreementresolve", new AgreementResolveUDF(),DataTypes.StringType);
		DataFrame ecrs_in = iXCDR02SparkSqlDAO.populateDataFrameViaSqlServerQuery("SELECT RegistryID,BERID,InTrunkID,"+
				"InCommercialTrunkID, OutTrunkID,OutCommercialTrunkID,"+
				"agreementresolve(InCommercialTrunkID,'inbound',"+0+") as InAgreementID,agreementresolve(InCommercialTrunkID,'inbound',"+1+") as InAccountID,"+
				"agreementresolve(OutCommercialTrunkID,'outbound',"+0+") as OutAgreementID,agreementresolve(OutCommercialTrunkID,'outbound',"+1+") as OutAccountID"+
				" FROM wtbECRTempAftTrunk");
		
		ecrs_in.show();
		ecrs_in.cache();
		DataFrame updatedEcrs_in = ecrs_in.where(ecrs_in.col("InTrunkID").isNotNull());
		updatedEcrs_in.show();
		
		updatedEcrs_in.registerTempTable("wtbECRTempAftAgreement");
		
		
			
	
	}

}
