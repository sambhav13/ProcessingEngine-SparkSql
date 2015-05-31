package com.telarix.Procedures.ProcedureUtilities;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;

import com.telarix.DAO.IXCDR_02DAO;

public class ExportTable {

	
	
	private IXCDR_02DAO cdr02_Dao;
	private	final static Logger logger = Logger.getLogger(ExportTable.class);
	
	public ExportTable(IXCDR_02DAO cdr02_Dao) {
		
		super();
		this.cdr02_Dao = cdr02_Dao;
	}

	public void exportWorkTable(DataFrame ecr)
	{
		logger.info("Exporting worktable to sql server......");
		
		String[] colnames = ecr.columns();
		ecr.cache();
		List<Row> list = ecr.collectAsList();

		try {
			//Connection dbconn = DriverManager.getConnection(PropertyLoaderAndGetter.getProperty("IXCDR_URL"));
			//Statement statement= dbconn.createStatement();

			StringBuilder sbInsert;
			StringBuilder sbSelect;

			String query = "Truncate table wtbECRTemp";
			cdr02_Dao.executeSql(query);;
			//statement.execute(query);

			System.out.println("Begin Export");
			//statement.execute(query);

			for(Row row : list){

				sbInsert = new StringBuilder("Insert into wtbECRTemp (");// = "Insert into wtbECRTemp\n" +
				sbSelect = new StringBuilder(" Select ");		//"Select ";

				for(int i = 0; i < row.length(); i++) {
					Object obj = row.get(i);

					String objstr;
					if(obj == null) {
						objstr = "NULL";
					}
					else
					{
						if(colnames[i].equalsIgnoreCase("CallDate")){
							String temp = obj.toString();
							String[] timestuffs = temp.split(" ");
							objstr ="\'" + timestuffs[0] + 'T' + timestuffs[1] + "\'";
						}
						else {
							objstr = "\'"+  obj.toString() + "\'";
						}
					}

					if(i == row.length()-1){
						//query += query + obj.toString();
						sbSelect.append(objstr);
						sbInsert.append(colnames[i]).append(")");

					}else {
						//query += query + obj.toString() + ", ";
						sbSelect.append(objstr).append(", ");
						sbInsert.append(colnames[i]).append(", ");

					}
				}

				query = sbInsert.toString() + sbSelect.toString();

				//statement.execute(query);
				cdr02_Dao.executeSql(query);
				//PreparedStatement ps = dbconn.prepareStatement("Select wtbECRTemp.*, COUNT(*) over (partition by 1) total_rows FROM wtbECRTemp", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = cdr02_Dao.executePreparedStatement("Select wtbECRTemp.*, COUNT(*) over (partition by 1) total_rows FROM wtbECRTemp");
				//ResultSet rs = ps.executeQuery();
				rs.last();
				int size = rs.getRow();
				rs.beforeFirst();
				System.out.println("Records currently in wtbECRTemp after export: " + size);
				logger.info("Records currently in wtbECRTemp after export: " + size);

			}
			logger.info("Table finished exporting");
			System.out.println("Table finished exporting");
		}catch(SQLException sql)
		{
			sql.printStackTrace();
		}
		
	}
}
