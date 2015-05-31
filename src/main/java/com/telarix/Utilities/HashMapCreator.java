package com.telarix.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.spark.sql.Row;

import com.telarix.constants.ApplicationConstants;

public class HashMapCreator {


	private	final static Logger logger = Logger.getLogger(PrepareData.class);
	private String seprator = SpecialCharacter.getSpecialCharacter();

	public  HashMap<String,String> convertToHashMap(List<Row> l, int type,String direction)
	{
		logger.info("Creating hash maps from dataFrames rows");
		HashMap<String, String> resultSet = new HashMap<>();
		//String obj = new ArrayList<>(l.size());

		switch (type) {
		case ApplicationConstants.SWITCH_DATATYPE:
			for (Row row : l) {
				//obj.add(0, Integer.parseInt(row.get(0).toString()));
				resultSet.put(row.get(0).toString(),row.get(0).toString());//KEY: SWITCHID
			}
			break;

		case ApplicationConstants.TRUNK_DATATYPE:

			if(direction.equals("inbound")){
				for (Row row : l) {
					//obj.add(0, Integer.parseInt(row.get(0).toString())); //SwitchID
					//obj.add(1, row.get(2).toString()); //Trunk
					//obj.add(2, row.get(3).toString()); //CDRMatch
					//obj.add(3, Integer.parseInt(row.get(4).toString())); //DirectionID
					//obj.add(4, Integer.parseInt(row.get(5).toString())); //TrunkTypeID
					//obj.add(5, Integer.parseInt(row.get(6).toString())); //CommercialTrunkID
					int agrDirMatchValue = Integer.parseInt(row.get(4).toString()); 
					if(agrDirMatchValue==3||agrDirMatchValue==2){

						resultSet.put(row.get(3).toString()+seprator+row.get(0).toString(), row.get(1).toString()+seprator+row.get(6).toString());//KEY: TRUNKID
					}

				}
			}
			else if(direction.equals("outbound")){

				for (Row row : l) {
					//obj.add(0, Integer.parseInt(row.get(0).toString())); //SwitchID
					//obj.add(1, row.get(2).toString()); //Trunk
					//obj.add(2, row.get(3).toString()); //CDRMatch
					//obj.add(3, Integer.parseInt(row.get(4).toString())); //DirectionID
					//obj.add(4, Integer.parseInt(row.get(5).toString())); //TrunkTypeID
					//obj.add(5, Integer.parseInt(row.get(6).toString())); //CommercialTrunkID
					int agrDirMatchValue = Integer.parseInt(row.get(4).toString());
					if(agrDirMatchValue==3||agrDirMatchValue==1){
						resultSet.put(row.get(3).toString()+seprator+row.get(0).toString(),row.get(1).toString()+seprator+row.get(6).toString());//KEY: TRUNKID
					}

				}

			}
			break;
		case ApplicationConstants.AGREEMENT_DATATYPE:
			if(direction.equals("inbound")){
				for (Row row : l) {
					//obj.add(0, Integer.parseInt(row.get(0).toString())); //AgreementPOIID
					//obj.add(1, Integer.parseInt(row.get(1).toString())); //POIDirectionID
					//obj.add(2, Integer.parseInt(row.get(2).toString())); //TrunkID
					//obj.add(3, Integer.parseInt(row.get(4).toString())); //AccountID
					//resultSet.put(row.get(3).toString(), obj);//KEY: AGREEMENTID

					int agrDirMatchValue = Integer.parseInt(row.get(1).toString()); 


					if(agrDirMatchValue==3||agrDirMatchValue==2){
						resultSet.put(row.get(2).toString(),row.get(1).toString());

					}

				}
			}

			if(direction.equals("outbound"))
			{
				for (Row row : l) {

					int agrDirMatchValue = Integer.parseInt(row.get(1).toString()); 
					if(agrDirMatchValue==3||agrDirMatchValue==1){
						resultSet.put(row.get(2).toString(),row.get(1).toString());

					}
				}
			}
			break;
		case ApplicationConstants.GLOBALVARIABLE_DATATYPE:
			break;
		default:
			break;
		}

		logger.info("Hash maps created from dataFrames");
		return resultSet;

	}

}
