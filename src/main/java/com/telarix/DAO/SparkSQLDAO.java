package com.telarix.DAO;

import com.telarix.constants.ApplicationConstants;
import org.apache.log4j.Logger;
import org.apache.spark.sql.DataFrame;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrice.bramble on 5/14/2015.
 */
public class SparkSQLDAO extends SparkSqlDAOBase {
    private String driver;
    private String dbUrl;

    private	final static Logger logger = Logger.getLogger(SparkSQLDAO.class);

    public void setDriver(String driver) {
        this.driver = driver;
    }
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * This method retrieves data from a given data source, (dbtable)
     * @param dataSource
     * @return DataFrame containing reference data being pulled from remote database
     */
    public DataFrame loadDataFrameViaSqlServer(String dataSource){

        logger.info("Loading DataFrame with SparkSql from Dastasource "+dataSource);
        Map<String, String> options = new HashMap<String, String>();
        options.put(ApplicationConstants.driver_Const,this.driver);
        options.put(ApplicationConstants.url_Const,this. dbUrl);
        options.put(ApplicationConstants.dbTable_Const,dataSource);
        DataFrame frame = this.sqlContext.load(ApplicationConstants.jdbc_Const, options);
        logger.info("DataFrame Loaded");
        //frame.show();
        return frame;

    }
}
