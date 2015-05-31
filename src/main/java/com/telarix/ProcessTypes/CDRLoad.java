package com.telarix.ProcessTypes;

import com.telarix.CDRProcessingEngine.*;
import com.telarix.DAO.SparkSQLDAO;
import com.telarix.Properties.PropertyLoaderAndGetter;
import com.telarix.Utilities.BeanFactorySingleton;
import com.telarix.Utilities.DLLLoader;
import com.telarix.constants.ApplicationConstants;
import com.telarix.constants.ApplicationErrors;
import com.telarix.constants.BeanConstants;
import com.telarix.constants.TableNameConstants;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by patrice.bramble on 5/13/2015.
 */
public class CDRLoad extends ProcessCDR {
	
	private final Logger logger = Logger.getLogger(CDRLoad.class);

	public CDRLoad(int ProcessLogID, String Database){
		this.ProcessLogID = ProcessLogID;
		this.Database = Database;
	}

	public void initialize(){

		try{
			
			long initialize_Start = System.currentTimeMillis();
			logger.info("initialization started");
			//Factory for getting Beans
			BeanFactorySingleton bfs = BeanFactorySingleton.getInstance();
			factory = bfs.getFactory();

			//Property file addition for beans creation
			PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
			cfg.setLocation(new ClassPathResource(ApplicationConstants.configPropertyFileName));
			cfg.postProcessBeanFactory(factory);

			SparkConf conf =  (SparkConf)factory.getBean(BeanConstants.sparkConf_bean);
			conf.setAppName(ApplicationConstants.APP_NAME).setMaster(ApplicationConstants.sparkMaster).set(ApplicationConstants.sparkExectorMemory_Const, "1g").set(ApplicationConstants.sparkExectorCores_Const,"2").set(ApplicationConstants.sparkSchedulerMode_Const, "FAIR")
			.set(ApplicationConstants.sparkDefaultParallelismConst,"20");
			
			conf.set("spark.io.compression.codec","lzf");
			conf.set("spark.speculation.execution","true");
			
			//conf.setJars(new String[]{"D:\\workspace\\CDRProcessingEngine_HashMapImpl\\target\\CDRProcessingEngine_Hash.jar"});

			JavaSparkContext sc = (JavaSparkContext) factory.getBean(BeanConstants.javaSparkContext_bean);
			SQLContext sqlContext = (SQLContext) factory.getBean(BeanConstants.sqlContext_bean);

			System.setProperty(ApplicationConstants.HADOOP_HOME_DIR, HADOOP_HOME);
			
			setConfiguration(conf);
			setContexts(sc, sqlContext);

			
			PropertyLoaderAndGetter.loadProperties();
			

			DLLLoader dllLdr = (DLLLoader)factory.getBean(BeanConstants.dllLoader_bean);
			dllLdr.loadDlls();
			logger.info("Dll loaded on to classpath");

			sparkSQLDAO = (SparkSQLDAO) factory.getBean(BeanConstants.SparkSQLDAO_bean);
			long initialize_End = System.currentTimeMillis();
			long time = (initialize_End - initialize_Start)/1000;
			logger.info("The time spend in initialization is :--" +time);
			

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
			logger.error("Exiting system with error code: "+ ApplicationErrors.ErrorCode.Terminate);
			System.exit(-1);
		}
		logger.info("initialization done");
	}

	public void process(){

		//Step1: Subscription Step
		//refDataSub = new ReferenceDataSubscription(Database,ProcessLogID,factory);
		//refDataSub.run();

		//Step2: Populate Global Variable
		long process_Sql__Start = System.currentTimeMillis();
		logger.info("populating Global Variables");
		GlbVariable = new GlobalVariables(ProcessLogID, factory).variables;

		//Step3: File Import
		logger.info("Starting file import.....");
		fileImporter = new FileImporter(factory);
		fileImporter.runImport();
		logger.info("File import steps done");
		
		Traffic_unpartitioned = sparkSQLDAO.loadDataFrameViaSqlServer(TableNameConstants.wtbECRTempUnpartitioned_Table);
	
		
		long process_Sql_End = System.currentTimeMillis();
		
		long timeSql = (process_Sql_End - process_Sql__Start)/1000;
		
		logger.info("The time in executing sql stored procedures is :--"+ timeSql);
		
		long process_date_Start = System.currentTimeMillis();
		logger.info("Unpartioned Traffic loaded from DB");
		distinctCallDates = getDistinctCallDates();
		List<Row> allCallDates = distinctCallDates.collectAsList();
		logger.info("disinct Call Dates Obtained");
		logger.info("Call Print: "+ allCallDates.toString());
		
		Timestamp callDate = (Timestamp) allCallDates.get(0).get(0);
		SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstants.TimeStampFormat_Const);
		long process_date_End = System.currentTimeMillis();

		long timeCallDataFrame = (process_date_End -process_date_Start)/1000;
		logger.info("The time in callDate DataFrame op is :--"+timeCallDataFrame);
		//Step4: Batch Processing - Multi-threaded (loop on call date)
		logger.info("Starting Batch Process for call date :--"+ allCallDates.get(0));
		threadCount = allCallDates.size();
		
		Thread a = new Thread(new BatchProcessor(callDate ,"Thread_" + allCallDates.get(0),factory,Traffic_unpartitioned));
		
		try {
			a.start();
			a.join();
			
			sc.stop();
			//System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("The Batch Processor process completed and ran the steps!");
		//Thread t = new Thread(new BatchProcessor(callDate ,"thread_"+callDate,factory));
		//t.run();
		/*BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(1);
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadCount, ApplicationConstants.MAX_POOLSIZE, ApplicationConstants.KEEP_ALIVE_TIME_MIN, TimeUnit.MINUTES, workQueue);

		if(threadCount == 0){
			System.out.println("No Batches to process");
		}
		else {
			System.out.println("Batches to process: " + threadCount);

			String threadName = "";
			try {
				for (int i = 0; i < threadCount; i++) {
					threadName = "Thread_" + String.valueOf(i) + "_" + allCallDates.get(i);
					threadPool.execute(new BatchProcessor(callDate ,threadName,factory,Traffic_unpartitioned));
					//threadPool.execute(new BatchProcessor(threadName));

				}

				threadPool.shutdown();
				while (!threadPool.awaitTermination(10, TimeUnit.MINUTES)) {
					System.out.println("Threads still running.");
				}

				
			} catch (Exception ie) {
				ie.printStackTrace();
			}
		}*/

		

		
		//			AlgoStepExecutor asExe = (AlgoStepExecutor) factory.getBean(BeanConstants.algoStepExecutor_bean);
		//
		//			Document xmlSteps = asExe.readXmlSteps();
		//
		//			asExe.executeSteps(xmlSteps, callDates);
		//Perform Trunk Resolve
	}



}
