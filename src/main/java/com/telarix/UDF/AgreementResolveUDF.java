package com.telarix.UDF;

import java.util.HashMap;

import org.apache.spark.sql.api.java.UDF3;

import com.telarix.CDRProcessingEngine.BatchProcessor;
import com.telarix.Utilities.SpecialCharacter;

public class AgreementResolveUDF implements UDF3<Object,Object,Integer,String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//HASHMAP for Reference Data
	
	public static HashMap<String, String> inAgreementData = BatchProcessor.inAgreementData;
	public static HashMap<String, String> outAgreementData = BatchProcessor.outAgreementData;
	private String seprator = SpecialCharacter.getSpecialCharacter();



	public String call(Object commericalId,Object direction,Integer colIndex) 
	{
		String key = commericalId.toString();
		String value;
		
		int index = colIndex.intValue();

		if(direction.equals("inbound"))
		{
			value = inAgreementData.get(key);
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
			value  = outAgreementData.get(key);
			if(index==0)
			{
				return value.split(seprator)[0];
			
			}
			else if (index ==1)
			{
				return value.split(seprator)[1];
			}
		}
		
		return null;
	}
}
