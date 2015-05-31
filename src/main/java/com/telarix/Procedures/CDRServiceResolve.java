package com.telarix.Procedures;

import java.util.HashMap;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;

import scala.collection.Seq;

import com.telarix.DAO.IXSubscriptionMainSparkSqlDAO;
import com.telarix.ProcessTypes.CDRLoad;

public class CDRServiceResolve {


	private IXSubscriptionMainSparkSqlDAO ixSubscriptionMainSparkSqlDAO;
	private HashMap<String,Object> GlbVariable;

	public CDRServiceResolve(
			IXSubscriptionMainSparkSqlDAO ixSubscriptionMainSparkSqlDAO) {
		super();
		this.ixSubscriptionMainSparkSqlDAO = ixSubscriptionMainSparkSqlDAO;
	}

	public void process()	
	{
		int processTypeID;
		int dimensionTypeID;
		int processTypeID_DimSpecRerate;
		
		//-- -----------	Resolve Global Variables  ---------------------------------------------------------
		int processLogID;
		int switchID;

		this.GlbVariable = CDRLoad.getGlbVariable();

		processTypeID = (int) this.GlbVariable.get("ProcessID");
		dimensionTypeID = (int) this.GlbVariable.get("DimensionTypeID");
		processTypeID_DimSpecRerate = (int) this.GlbVariable.get("ProcessType_DimensionSpecificRerate");


		if(processTypeID == processTypeID_DimSpecRerate && dimensionTypeID == 1)
		{
			return;
		}


		processLogID = (int) this.GlbVariable.get("ProcessLogID");
		switchID = (int) this.GlbVariable.get("SwitchID");

	
		//DataFrame tbServiceDf = ixSubscriptionMainSparkSqlDAO.loadDataFrameViaSqlServer("tbService");
		DataFrame tbServiceDetailDf = ixSubscriptionMainSparkSqlDAO.loadDataFrameViaSqlServer("wtbServiceDetail");

		//tbServiceDf.registerTempTable("tbServiceDf");
		tbServiceDetailDf.registerTempTable("tbServiceDetail");
		DataFrame wtbServiceDetail = ixSubscriptionMainSparkSqlDAO.populateDataFrameViaSqlServerQuery("select sd.ServiceID,sd.CallTypeID,sd.CallCharID,IF(UseFlag & power(2,8)) = power(2,8),1,0) from tbServiceDf s join tbServiceDetail sd");

		wtbServiceDetail.registerTempTable("wtbServiceDetail");
		DataFrame tbECRTemp2 =ixSubscriptionMainSparkSqlDAO.populateDataFrameViaSqlServerQuery("select copyColumn(SD.ServiceID) as RoutingServiceID , sd.UsedForVSR as  UsedForVSR  from wtbServiceDetail SD inner join tbECRTemp E E.CallTypeID = SD.CallTypeID"+
				"and E.RoutingCallCharID = SD.CallCharID ");

		DataFrame tbECRTemp3 = tbECRTemp2.where(tbECRTemp2.col("RoutingServiceID").isNull());

	}



}

