package com.telarix.Procedures.test;

import com.telarix.CDRProcessingEngine.CDRProcessingEngineDriver;
import com.telarix.Properties.PropertyLoaderAndGetter;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.junit.Before;

/**
 * Created by ashley.rose on 4/16/2015.
 */
public class GlobalVariableResolverTest {

    private String master;
    private String url;
    private String driver;
    private String appName;

    SparkConf conf;
    JavaSparkContext sc;
    SQLContext sqlContext;

    @Before
    public void setUp() {

        //CDRProcessingEngineDriver.loadDlls();
    	/*CDRProcessingEngineDriver cdrProcEng = new CDRProcessingEngineDriver();
    	cdrProcEng.loadDlls();
        System.setProperty("hadoop.home.dir", PropertyLoaderAndGetter.getProperty("HADOOP_HOME"));

        master = PropertyLoaderAndGetter.getProperty("SPARK_MASTER");
        url = PropertyLoaderAndGetter.getProperty("IXCDR_URL");
        driver = PropertyLoaderAndGetter.getProperty("JDBC_DRIVER");
        appName = "Global Variable Test";

        conf = new SparkConf().setAppName(appName).setMaster("local");
        sc = new JavaSparkContext(conf);
        sqlContext = new SQLContext(sc);*/
    }

}
