package com.telarix.ProcessTypes;

import com.telarix.CDRProcessingEngine.BatchProcessor;
import com.telarix.CDRProcessingEngine.FileImporter;
import com.telarix.CDRProcessingEngine.ReferenceDataSubscription;
import com.telarix.DAO.SparkSQLDAO;
import com.telarix.Properties.PropertyLoaderAndGetter;
import com.telarix.Utilities.PrepareData;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by patrice.bramble on 5/14/2015.
 */
public abstract class ProcessCDR {

    protected SparkConf conf;
    protected JavaSparkContext sc;
    protected SQLContext sqlcontext;

    //protected final Logger logger = Logger.getLogger(ProcessCDR.class);
    protected final String HADOOP_HOME = PropertyLoaderAndGetter.getProperty("HADOOP_HOME");

    protected ConfigurableListableBeanFactory factory;

    protected int ProcessLogID;
    protected String Database;

    protected ReferenceDataSubscription refDataSub;
    protected static HashMap<String,Object> GlbVariable;
    protected FileImporter fileImporter;
    protected BatchProcessor batchProcessor;

    protected SparkSQLDAO sparkSQLDAO;
    protected PrepareData ppData;

    protected DataFrame Traffic_unpartitioned;
    protected DataFrame distinctCallDates;
    protected int threadCount = 0;

    public abstract void initialize();

    public abstract void process();

    public void run(){
        initialize();
        process();
    }

    public void setConfiguration(SparkConf conf)
    {
        this.conf = conf;

    }
    public void setContexts(JavaSparkContext sc,SQLContext sqlcontext)
    {
        this.sc = sc;
        this.sqlcontext = sqlcontext;
    }

    protected DataFrame getDistinctCallDates(){
        DataFrame df = this.Traffic_unpartitioned.select("CallDate").distinct();
        return df;
    }

    public void logInfo(String message, int type){
        //1 - info
        //2 - warn
        //3 - error
     /*   switch (type) {
            case 1 :
                logger.info(message);
                break;
            case 2 :
                logger.warn(message);
                break;
            case 3 :
                logger.error(message);
                break;
        }*/
    }

    public static HashMap<String,Object> getGlbVariable(){
        return GlbVariable;
    }
}
