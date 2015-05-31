package com.telarix.CDRProcessingEngine;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.telarix.ProcessTypes.CDRLoad;
import com.telarix.constants.ApplicationConstants;

public class CDRProcessingEngineDriver {


	public static void main(String[] args){

		//int ProcesLogID = 4254964;
		int ProcesLogID = 4465475 ;
		String Database = "iXCDR_02";
		int ObjectTypeID = 102;
	
		
		System.out.println("ProcessLogID: " + String.valueOf(ProcesLogID) + " Database: " + Database + " ObjectTypeID: " + ObjectTypeID );
		switch(ObjectTypeID) {
			case ApplicationConstants.OT_CDR_FILE:
				long start = System.currentTimeMillis();
				CDRLoad cdrload = new CDRLoad(ProcesLogID, Database);
				cdrload.run();
				System.out.println("CDR Load complete.");
				long end = System.currentTimeMillis();
				long time = (end -start )/1000;
				System.out.println("The spent is ----->"+time);
				break;
			case ApplicationConstants.OT_Standardized_CDR:
				break;
		}
	}

}
