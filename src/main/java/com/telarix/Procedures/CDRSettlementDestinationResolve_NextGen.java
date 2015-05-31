package com.telarix.Procedures;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.sql.DataFrame;

import com.telarix.DAO.IXControl_ClientDAO;
import com.telarix.DAO.IXSubscriptionMainSparkSqlDAO;

public class CDRSettlementDestinationResolve_NextGen {

	private IXSubscriptionMainSparkSqlDAO ixSubscriptionMainSparkSqlDAO;
	private IXControl_ClientDAO iXControl_ClientDAO;


	public CDRSettlementDestinationResolve_NextGen(
			IXSubscriptionMainSparkSqlDAO ixSubscriptionMainSparkSqlDAO,
			IXControl_ClientDAO iXControl_ClientDAO) {
		super();
		this.ixSubscriptionMainSparkSqlDAO = ixSubscriptionMainSparkSqlDAO;
		this.iXControl_ClientDAO = iXControl_ClientDAO;
	}


	public void process()	
	{


		this.ixSubscriptionMainSparkSqlDAO.populateDataFrameViaSqlServerQuery("select registryID,BERID,"+
				"functCnvert(InSettlementDate,'/','-'),"+
				"functCnvert(OutSettlementDate,'/','-'),"+
				"IF (A.InDestinationID or   (@TrafficCloseType = 2 and A.InboundClosed = 1)"

				);

		int TrafficCloseType =2;
		int CnfUseOrginalCalledNumberInDestination = 1;
		DataFrame tbECRTemp1 = this.ixSubscriptionMainSparkSqlDAO.populateDataFrameViaSqlServerQuery("select registryID,BERID,"+
				"functCnvert(InSettlementDate,'/','-'),"+
				"functCnvert(OutSettlementDate,'/','-'),"+
				"CASE when "+
				"(isnotnull(A.InDestinationID) or "+TrafficCloseType+" = 2 and A.InboundClosed = 1)"+
				"isnull(ProcessInbound, 1) = 0 or isnull(InCompatibility70Flag,0) = 1"+
				" then null"+
				" when "+
				CnfUseOrginalCalledNumberInDestination+" = 1 and isnull(inuNPID)"+
				" then OriginalCalledNumber "+
				"else CalledNumber end");

		/* = 1 and inuNPID is null 
then OriginalCalledNumber 
else CalledNumber end, 


				);


		SELECT
		  CASE circle= "Panjab" THEN 2 else 'some other value' END
		FROM
		  siteinfo
		or isnull(ProcessInbound, 1) = 0 or isnull(InCompatibility70Flag,0) = 1
		select 

		replace(CONVERT(varchar(30), InSettlementDate, 111), '/','-'), 
		replace(CONVERT(varchar(30), OutSettlementDate, 111), '/','-'), 

		when A.InDestinationID is not null or (@TrafficCloseType = 2 and A.InboundClosed = 1)
		or isnull(ProcessInbound, 1) = 0 or isnull(InCompatibility70Flag,0) = 1
		then null*/
		/*
		@CnfUseOrginalCalledNumberInDestination = 1 and inuNPID is null 
				then OriginalCalledNumber 
			else CalledNumber end,

		DataFrame tbServiceDf = ixSubscriptionMainSparkSqlDAO.loadDataFrameViaSqlServer("tbService");
		DataFrame tbServiceDetailDf = ixSubscriptionMainSparkSqlDAO.loadDataFrameViaSqlServer("tbServiceDetail");

		tbServiceDf.registerTempTable("tbServiceDf");
		tbServiceDetailDf.registerTempTable("tbServiceDetail");
		DataFrame ServiceDetailTemp = ixSubscriptionMainSparkSqlDAO.populateDataFrameViaSqlServerQuery("select sd.ServiceID,sd.CallTypeID,sd.CallCharID,IF(UseFlag & power(2,8)) = power(2,8),1,0) from tbServiceDf s join tbServiceDetail sd");

		ServiceDetailTemp.registerTempTable("ServiceDetailTemp");
		DataFrame tbECRTemp2 =ixSubscriptionMainSparkSqlDAO.populateDataFrameViaSqlServerQuery("select copyColumn(SD.ServiceID) as RoutingServiceID , sd.UsedForVSR as  UsedForVSR  from ServiceDetailTemp SD inner join tbECRTemp E E.CallTypeID = SD.CallTypeID"+
				"and E.RoutingCallCharID = SD.CallCharID ");

		DataFrame tbECRTemp3 = tbECRTemp2.where(tbECRTemp2.col("RoutingServiceID").isNull());*/

		Map<String,Object> inputParamMap = new HashMap<String,Object>();


		inputParamMap.put("ProcessLogID", "ProcessLogID");
		inputParamMap.put("SourceDatabase","SettlementDestination");
		inputParamMap.put("SourceTable","tbNGCInput");
		inputParamMap.put("DestinationDatabase","tempdb");
		inputParamMap.put("DestinationTable","#tbNGCOutput");
		inputParamMap.put("APIPath",null);
		inputParamMap.put("TempPath",null);



		Map<String,Object> outputParam = this.iXControl_ClientDAO.executeProcedure("bspNGXResolverWrapper", inputParamMap);
		Object	SQLServerExportSpeed = outputParam.get("SQLServerExportSpeed");
		Object	SQLServerImportSpeed = outputParam.get("SQLServerImportSpeed");
		Object	ExportedRecordCountToAPI = outputParam.get("ExportedRecordCountToAPI");
		Object	LoadedNumberPlanCount = outputParam.get("LoadedNumberPlanCount");
		Object	LoadedBNumberCount = outputParam.get("LoadedBNumberCount");
		Object	BNumberResolutionSpeed = outputParam.get("BNumberResolutionSpeed");
		Object	CDRResolutionSpeed = outputParam.get("CDRResolutionSpeed");
		Object	ImportedRecordCountFromAPI = outputParam.get("ImportedRecordCountFromAPI");
		Object	TotalAPICallTime = outputParam.get("TotalAPICallTime");
		Object Debug = 1;
		//[iXControl_Client].[dbo].[bspNGXResolverWrapper] 

		
		
		//Update	E
		//Set		
		DataFrame tbECRTemp2 = this.ixSubscriptionMainSparkSqlDAO.populateDataFrameViaSqlServerQuery("select InDestinationID = isnull(DD.InDestinationID , E.IndestinationID),"+
			"InCallCharID = isnull(dd.InCallCharID , E.InCallCharID),"+
			"InCountryID = isnull(dd.InCountryID , E.InCountryID),"+
			"InNumberPlanID= isnull(dd.InNumberPlanID , E.InNumberPlanID),"+
			"InAccountingDestinationID  = isnull(dd.InAccountingDestinationID , E.InAccountingDestinationID),"+  
			"InAccountingCallCharID =  isnull(dd.InAccountingCallCharID  , E.InAccountingCallCharID),"+
			"InAccountingCountryID= isnull(dd.InAccountingCountryID  , E.InAccountingCountryID),"+
			"InAccountingNumberPlanID = isnull(dd.InAccountingNumberPlanID  , E.InAccountingNumberPlanID),"+
			"OutDestinationID = isnull(dd.OutDestinationID , E.OutDestinationID),"+
			"OutCallCharID =isnull(dd.OutCallCharID , E.OutCallCharID),"+
			"OutCountryID =isnull(dd.OutCountryID , E.OutCountryID),"+
			"OutNumberPlanID =isnull(dd.OutNumberPlanID , E.OutNumberPlanID),"+
			"OutAccountingDestinationID = isnull(dd.OutAccountingDestinationID  , E.OutAccountingDestinationID),"+
			"OutAccountingCallCharID= isnull(dd.OutAccountingCallCharID , E.OutAccountingCallCharID) ,"+
			"OutAccountingCountryID =isnull(dd.OutAccountingCountryID , E.OutAccountingCountryID) ,"+
			"OutAccountingNumberPlanID =isnull(dd.OutAccountingNumberPlanID , E.OutAccountingNumberPlanID) ,"+
			"InServiceID = isnull(inOpSD.serviceID, E.InServiceID ) ,"+
			"InAccountingServiceID = isnull(inAccSD.serviceID,E.InAccountingServiceID) ,"+
			"outServiceID = isnull(outOpSD.serviceID,E.outServiceID) ,"+
			"outAccountingServiceID = isnull(outAccSD.serviceID,E.outAccountingServiceID) ,"+
			"InContractID  =isnull(dd.InContractID  ,E.InContractID) ,"+
			"OutContractID =isnull(dd.OutContractID ,E.OutContractID) ,"+
			"InAccountingContractID  =isnull(dd.InAccountingContractID  ,E.InAccountingContractID) ,"+
			"OutAccountingContractID =isnull(dd.OutAccountingContractID ,E.OutAccountingContractID) ,"+
			"InisOperationalFTR  =isnull(dd.InisOperationalFTR  ,E.InisOperationalFTR) ,"+
			"InisAccountingFTR =isnull(dd.InisAccountingFTR ,E.InisAccountingFTR) ,"+
			"InisMissingFTR =isnull(dd.InisMissingFTR ,E.InisMissingFTR) ,"+
			"OutisOperationalFTR =isnull(dd.OutisOperationalFTR ,E.OutisOperationalFTR) ,"+
			"OutisAccountingFTR =isnull(dd.OutisAccountingFTR ,E.OutisAccountingFTR) ,"+
			"OutisMissingFTR =isnull(dd.OutisMissingFTR ,E.OutisMissingFTR),"+
			"InSettlementDD=isnull(dd.InDialedDigits ,E.InSettlementDD) ,"+
			"OutSettlementDD=isnull(dd.OutDialedDigits ,E.OutSettlementDD) ,"+
			"InAccountingSettlementDD=isnull(dd.InAccountingDialedDigits, E.InAccountingSettlementDD) ,"+
			"OutAccountingSettlementDD=isnull(dd.OutAccountingDialedDigits,E.OutAccountingSettlementDD)"+
			"From	#tbECRTemp E-- with (index (IX_BERID))"+
			"inner join #tbNGCOutput DD with (index(iX_temp_tbNGCOutput))"+
			"on	e.berid = DD.BERID and E.registryID = DD.registryID"+ 
			"left join iXSubscription_Main.dbo.tbServiceDetail inOpSD on DD.InCallCharID = inOpSD.CallCharID and E.CallTypeID = inOpSD.CallTypeID"+
			"left join iXSubscription_Main.dbo.tbServiceDetail inAccSD on DD.InAccountingCallCharID = inAccSD.CallCharID and E.CallTypeID = inAccSD.CallTypeID"+
			"left join iXSubscription_Main.dbo.tbServiceDetail outOpSD on DD.OutCallCharID = outOpSD.CallCharID and E.CallTypeID = outOpSD.CallTypeID"+
			"left join iXSubscription_Main.dbo.tbServiceDetail outAccSD on DD.OutAccountingCallCharID = outAccSD.CallCharID and E.CallTypeID = outAccSD.CallTypeID");
	}
}
