package com.telarix.Utilities;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.telarix.constants.ApplicationConstants;
import com.telarix.constants.ApplicationErrors;

public class AlgorithmXMLStepsLoader {

	
	
	private Document document;
	private	final static Logger logger = Logger.getLogger(AlgorithmXMLStepsLoader.class);
	
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	/**
	 * Reads the XML config file in order to the get the subalgorithms and steps
	 */
	public Document ReadXMLFile() {

		logger.info("Reading the xml file.......");
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream xmlFile = classLoader.getResourceAsStream(ApplicationConstants.stepsFile);
		
		
		try {

			Document document = builder.build(xmlFile);
			this.setDocument(document);
		
		} catch (IOException io) {
			logger.error(io.toString(),(Throwable)io);
			logger.error("Exiting system with error code: "+ApplicationErrors.ErrorCode.Terminate);
			System.exit(-1);
			
		} catch (Exception jdomex) {
			logger.error(jdomex.toString(),(Throwable)jdomex);
			logger.error("Exiting system with error code: "+ApplicationErrors.ErrorCode.Terminate);
			System.exit(-1);
		}
		
		logger.info("Xml File Read!!");
		return this.getDocument();
	}
}
