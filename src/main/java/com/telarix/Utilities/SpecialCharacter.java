package com.telarix.Utilities;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class SpecialCharacter {
	
	

	public static String getSpecialCharacter()
	{
		String hexString = "c2BF";    
		String seprator = null;
		byte[] bytes = null;
		try {
			bytes = Hex.decodeHex(hexString .toCharArray());
			seprator = new String(bytes, "UTF-8");
			System.out.println(new String(bytes, "UTF-8"));
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seprator;
	}
}
