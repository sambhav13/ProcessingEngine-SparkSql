package com.telarix.DAO;

import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class SparkSqlDAOBase {


	protected  JavaSparkContext sc;
	protected  SQLContext sqlContext;

	public SQLContext getSqlContext() {
		return sqlContext;
	}


	private	final static Logger logger = Logger.getLogger(SparkSqlDAOBase.class);

	public void setSc(JavaSparkContext sc) {
		this.sc = sc;
	}



	public void setSqlContext(SQLContext sqlContext) {
		this.sqlContext = sqlContext;
	}


	public DataFrame populateDataFrameViaSqlServerQuery(String query){

		logger.info("Populating dataFrame from Spark Sql with query: " + query);
		DataFrame frame = this.sqlContext.sql(query);
		//frame = frame.repartition(4);
		logger.info("Dataframe populated from Spark Sql");
		return frame;

	}
}
