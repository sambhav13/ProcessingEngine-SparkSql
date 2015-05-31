package com.telarix.UDF;


import java.util.HashMap;




import org.apache.spark.sql.api.java.UDF4;

import com.telarix.CDRProcessingEngine.BatchProcessor;
import com.telarix.Utilities.SpecialCharacter;


public class TrunkResolveUDF implements UDF4<Object,Object,Object,Integer,String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//HASHMAP for Reference Data
	public static HashMap<String, String> inTrunkData = BatchProcessor.inTrunkData;
	public static HashMap<String, String> outTrunkData = BatchProcessor.outTrunkData;
	private String seprator = SpecialCharacter.getSpecialCharacter();

	public String call(Object trunkName,Object switchId,Object direction,Integer colIndex) 
	{

		if(trunkName!=null&&switchId!=null){
			String key = trunkName.toString() + seprator +switchId.toString();
			String value;

			int index = colIndex.intValue();

			if(direction.equals("inbound"))
			{
				value = inTrunkData.get(key);
				if(value==null)
					return null;
				if(index==0)
				{
					
					return value.split(seprator)[0];
					//return out[0];
				}
				else if (index ==1)
				{
					return value.split(seprator)[1];
				}
			}
			else if (direction.equals("outbound"))
			{
				value  = outTrunkData.get(key);
				if(value==null)
				{
					return null;					
				}
				
				if(index==0)
				{
					return value.split(seprator)[0];

				}
				else if (index ==1)
				{
					return value.split(seprator)[1];
				}
			}
		}

		return null;
	}
}
