package com.telarix.Properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoaderAndGetter {

	static Properties prop = null;
	
	public static String getProperty(String propertyName){
		
		if(prop == null){
			loadProperties();
		}
		
		String propertyValue = prop.getProperty(propertyName);
		
		return propertyValue;
	} 
	
	public static void loadProperties(){
		
		prop 				  = new Properties();
		InputStream inStream  = null;
		
		try{
			inStream =  PropertyLoaderAndGetter.class.getResourceAsStream("/config.properties");
			prop.load(inStream);
			
		}catch(IOException e){
			System.out.println("Failed to load property file");
			e.printStackTrace();
		}finally{
			if(inStream != null){
				try{
					inStream.close();
				}catch(IOException e){
					System.out.println("Failed to close inStream");
				}
			}
		}
	}	
}
