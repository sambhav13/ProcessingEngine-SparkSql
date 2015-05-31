package com.telarix.CDRProcessingEngine;

import com.telarix.Beans.RefDataHashMaps;
import com.telarix.DAO.IXCDR02SparkSqlDAO;
import com.telarix.LookUpData.ReferenceDataMaps;
import com.telarix.LookUpData.ReferenceDataMapsVS;
import com.telarix.Procedures.AgreementResolveStep;
import com.telarix.Procedures.TrunkResolveStep;
import com.telarix.Procedures.ProcedureUtilities.ExportTable;
import com.telarix.ProcessTypes.ProcessCDR;
import com.telarix.UDF.AgreementResolveUDF;
import com.telarix.Utilities.PrepareData;
import com.telarix.constants.ApplicationErrors;
import com.telarix.constants.BeanConstants;
import com.telarix.constants.TableNameConstants;

import org.apache.log4j.Logger;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.jdom2.Document;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by patrice.bramble on 5/13/2015.
 */
public class BatchProcessor extends Thread{

  
    private Timestamp callDate;
    private String threadName;
    public ConfigurableListableBeanFactory factory = null;
    public DataFrame unpartionedTable;

    //HASHMAP for Reference Data
  	public static HashMap<String, String> inTrunkData = new HashMap<>(); 
	public static HashMap<String, String> outTrunkData = new HashMap<>(); 
  	public static HashMap<String, String> inAgreementData = new HashMap<>();
  	public static HashMap<String, String> outAgreementData = new HashMap<>();
  //public static HashMap<Integer, ArrayList<Object>> SwitchData = new HashMap<>();
  	public static HashMap<Integer, ArrayList<Object>> GlobalVariable = new HashMap<>();

    
    //private Thread t;
    protected final Logger logger = Logger.getLogger(BatchProcessor.class);

    public BatchProcessor(Timestamp callDate, String threadName, ConfigurableListableBeanFactory fact,DataFrame unpartionedTable){
      
        this.callDate = callDate;
        this.threadName = threadName;
        this.factory = fact;
        this.unpartionedTable = unpartionedTable;
    }

    public BatchProcessor(String threadName){
        this.threadName = threadName;
    }
    public void run(){
    	
    	logger.info("Running BatchProcessor....");
    	long batchRefData_Start = System.currentTimeMillis();
     	getReferenceData(callDate);
     	
     	long batchRefData_End = System.currentTimeMillis();
     	long batchrefDataTime = (batchRefData_End - batchRefData_Start)/1000;
     	logger.info("The batch reference Data time is :--"+batchrefDataTime);
     	logger.info("Reference Data Obtained for callDate :--"+ callDate);
    	/*TrunkResolveStep trStep = new TrunkResolveStep();
    	trStep.startProcess(this.factory);
    	AgreementResolveStep agrStep = new AgreementResolveStep();
    	agrStep.startProcess(this.factory);*/
    	/*AlgoStepExecutor asExe = (AlgoStepExecutor) factory.getBean(BeanConstants.algoStepExecutor_bean);
        Document xmlSteps = asExe.readXmlSteps();
        List<String> callDates = null;
        asExe.executeSteps(xmlSteps, callDates);/*

       // export();
        System.out.println(this.threadName + " is running ");

        /*try{
            for (int i = 0; i < 10; i++){
                System.out.println("Thread: " + threadName + " Iteration: " + i);
                Thread.sleep(85);
            }
        }
        catch (InterruptedException ie){
            ie.printStackTrace();
            System.err.println(this.threadName + " has been interuppted.");
        }
        finally {
            System.out.println(this.threadName + " exiting.");
        }*/
    }

    public void getReferenceData(Timestamp CallDate)
	{
		try{		

			IXCDR02SparkSqlDAO ixCdrSparkSql = (IXCDR02SparkSqlDAO)factory.getBean(BeanConstants.iXCDR02SparkSqlDAO_bean); 


			Timestamp callDate = CallDate ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String callDateNew = sdf.format(new Date(callDate.getTime()));



			System.out.println(callDate.toString());
			//Traffic_unpartitioned.where(Traffic_unpartitioned(""))
			unpartionedTable.registerTempTable("wtbECRTemp_Unpartitioned");




			DataFrame wtbECRTemp = ixCdrSparkSql.populateDataFrameViaSqlServerQuery("select "
					+ "RegistryID,InSwitchID,InTrunkName,InternalOutTrunkName,InternalInTrunkName,OutTrunkName,OutSwitch,"
					+ "CallingNumber,OriginalCalledNumber,CalledNumber,CallType,cast(CallDate as string) as CallDate,CallHour,CallMinute,CallSecond,"
					+ "CallDuration,CircuitDuration,Answered,Seized,FaultCode,RingBusy,UserBusy,TerminalReject,SeqNum,"
					+ "BERID,InSwitchID,OutSwitchID,PartitionID,CallTypeID,CDRDirectionID,InNumberPlanID,InDestinationID,"
					+ "InCountryID,InCallCharID,InServiceID,InProductID,InServiceLevelID,InRouteClassID,OutNumberPlanID,"
					+ "OutDestinationID,OutCountryID,OutCallCharID,OutServiceID,OutProductID,OutServiceLevelID,OutRouteClassID,"
					+ "RoutingDestinationID,RoutingCalendarID,RoutingDateTimeBandID,RoutingProductID,RoutingServiceID,"
					+ "RoutingCallCharID,InEventCaseID,InCaseRatingMethodID,OutEventCaseID,OutCaseRatingMethodID,"
					+ "CaseRatingMethodID,CDRTariffTypeCombinationID,UsedForVSR,InBoundClosed,OutBoundClosed,"
					+ "InAccountingTreatment,OutAccountingTreatment,NPRN,CustomField1,CustomField2,CustomField3,"
					+ "InRefDestinationID,NetworkCode,Portability_PreresolvedFlag,PortedFlag,OriginID,PortedFromID,"
					+ "PortedToID,PortedDestinationID,InJitter,OutJitter,InPacketDelay,OutPacketDelay,InPacketLoss,OutPacketLoss"
					+ ",InMOS,OutMOS,InternalCodec,InExternalCodec,OutExternalCodec,InternalCodecNumber,InExternalCodecNumber,"
					+ "OutExternalCodecNumber,CustomField4,CustomField5,CustomField6,CustomField7,CustomField8,CustomField9,"
					+ "CustomField10,CustomField11,CustomField12,tmpCalledNumber1,tmpCalledNumber2,tmpCalledNumber3,tmpCalledNumber4"
					+ ",tmpCalledNumber5,tmpCalledNumber6,tmpCalledNumber7,tmpCalledNumber8,tmpCalledNumber9,tmpCalledNumber10,"
					+ "tmpCalledNumber11,tmpCalledNumber12,tmpCalledNumber13,tmpCalledNumber14,tmpCalledNumber15,tmpCalledNumber16"
					+ ",tmpCalledNumber17,tmpCalledNumber18,tmpOtherNumber1,tmpOtherNumber2,tmpOtherNumber3,tmpOtherNumber4,"
					+ "tmpOtherNumber5,tmpOtherNumber6,tmpOtherNumber7,tmpOtherNumber8,tmpOtherNumber9,tmpOtherNumber10,"
					+ "tmpOtherNumber11,tmpOtherNumber12,tmpOtherNumber13,tmpOtherNumber14,tmpOtherNumber15,tmpOtherNumber16,"
					+ "tmpOtherNumber17,tmpOtherNumber18,InSettlementDate,OutSettlementDate,InSettlementDateTime,"
					+ "OutSettlementDateTime,InSettlementTimeZoneShift,OutSettlementTimeZoneShift,InUNPID,InAccountingNumberPlanID,"
					+ "InAccountingDestinationID,InAccountingEventCaseID,InContractID,InisOperationalFTR,InisAccountingFTR,"
					+ "InisMissingFTR,OutUNPID,OutAccountingNumberPlanID,OutAccountingDestinationID,OutAccountingEventCaseID,"
					+ "OutContractID,OutisOperationalFTR,OutisAccountingFTR,OutisMissingFTR,InAccountingContractID,"
					+ "OutAccountingContractID,InAccountingCallCharID,OutAccountingCallCharID,InAccountingServiceID,"
					+ "OutAccountingServiceID,InAccountingProductID,OutAccountingProductID,RoutingDestinationTypeID,"
					+ "InProvisionalStatementID,OutProvisionalStatementID,CustomField13,CustomField14,CustomField15,"
					+ "CustomField16,CustomField17,CustomField18,CustomField19,CustomField20,CustomField21,CustomField22,"
					+ "CustomField23,CustomField24,CustomField25,CustomField26,CustomField27,CustomField28,CustomFieldD1,"
					+ "CustomFieldD2,CustomFieldD3,MDD,MNPDestinationID,MNPDialCode,ProcessInbound,NPANXXDialCode,"
					+ "UnRoundedCallDuration,InAccountingCountryID,OutAccountingCountryID,InSettlementDD,OutSettlementDD,"
					+ "InAccountingSettlementDD,OutAccountingSettlementDD,ProcessOutbound,InCompatibility70Flag,"
					+ "OutCompatibility70Flag,OriginCountryID,tmpCallingNumber1,tmpCallingNumber2,tmpCallingNumber3,"
					+ "tmpCallingNumber4,tmpCallingNumber5,tmpCallingNumber6,tmpCallingNumber7,tmpCallingNumber8,"
					+ "tmpCallingNumber9,tmpCallingNumber10,tmpCallingNumber11,tmpCallingNumber12,tmpCallingNumber13,"
					+ "tmpCallingNumber14,tmpCallingNumber15,tmpCallingNumber16,tmpCallingNumber17,tmpCallingNumber18,"
					+ "InGPDestinationID1,InGPDestinationID2,InGPDestinationID3,InGPDestinationID4,OutGPDestinationID1,"
					+ "OutGPDestinationID2,OutGPDestinationID3,OutGPDestinationID4,InGPContractID1,InGPContractID2,InGPContractID3,"
					+ "InGPContractID4,OutGPContractID1,OutGPContractID2,OutGPContractID3,OutGPContractID4,InIsOriginBasedRating,"
					+ "OutIsOriginBasedRating,InTrunkID,InTrunkTypeID,InCommercialTrunkID,OutTrunkID,OutTrunkTypeID,"
					+ "OutCommercialTrunkID,InAgreementID,InAccountID,OutAgreementID,OutAccountID"
					+ " from wtbECRTemp_Unpartitioned where CallDate ='"+callDateNew+"'");
			//wtbECRTemp.show(100);

			wtbECRTemp.registerTempTable("wtbECRTemp");
			
			
			//wtbECRTemp.save("D:/winutil/output");
			//wtbECRTemp.registerTempTable("wtbECRTempBefTrunk");
			


			logger.info("Preparing WorkTables.......");
			PrepareData ppData = (PrepareData)factory.getBean("prepareData");
			ppData.createWorkTable(callDate);
			logger.info("WorkTables Prepared.......");
			
			//Load The Reference Data
			//DataFrame wtbBERTemp = ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbBERTemp_RefData);
			//List<Row> BERData = wtbBERTemp.collectAsList();
			

			//Load dataframes for reference Data into Spark Sql
			//ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbTrunk_RefData).registerTempTable(TableNameConstants.wtbTrunk_RefData);
			//ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbAgreementPOI_RefData).registerTempTable(TableNameConstants.wtbAgreementPOI_RefData);

			logger.info("Reference Data Loaded into spark sql!!");

			//Reference data - Pulling data into Hashmap (not currently used anywhere - logic still uses data frame)

			logger.info("Pulling Reference Data into HashMaps.......");
			//ReferenceDataMaps rdMap = (ReferenceDataMaps)factory.getBean(BeanConstants.referenceDataMaps_bean);
			//rdMap.populateMaps();

			ReferenceDataMapsVS rdMap = (ReferenceDataMapsVS)factory.getBean(BeanConstants.referenceDataMapsVS_bean);
			rdMap.populateMaps();
			
			//bean loaded with hashmaps
			RefDataHashMaps rdHashMap =rdMap.getRefDatahshMaps();
			//SwitchData = rdHashMap.getSwitchData();
			inTrunkData = rdHashMap.getInTrunkData();
			outTrunkData = rdHashMap.getOutTrunkData();
			
			
			inAgreementData = rdHashMap.getInAgreementData();
			outAgreementData = rdHashMap.getOutAgreementData();


			logger.info("Reference Data pulled into HashMaps.......");

		}
		catch(NoSuchBeanDefinitionException be)
		{
			logger.error(be.toString(), (Throwable)be);
			logger.info("Exiting system since bean defenition not there");
			System.exit(-1);
			be.printStackTrace();
		}
		catch(Exception ex)
		{
			logger.error(ex.toString(),(Throwable)ex);
			logger.error("Exiting system with error code: "+ApplicationErrors.ErrorCode.Terminate);
			System.exit(-1);
		}
		logger.info("initialization done");
	}
    
    
    
    public void export(){

    	IXCDR02SparkSqlDAO iXCDR02SparkSqlDAO = (IXCDR02SparkSqlDAO)factory.getBean("iXCDR02SparkSqlDAO");
		DataFrame wtbECRTemp = iXCDR02SparkSqlDAO.populateDataFrameViaSqlServerQuery("select * from wtbECRTemp");
		
    	ExportTable et = (ExportTable)factory.getBean("exportTable");
		et.exportWorkTable(wtbECRTemp);
		
    }
}
