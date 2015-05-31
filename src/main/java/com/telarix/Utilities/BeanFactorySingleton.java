package com.telarix.Utilities;


import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.telarix.constants.ApplicationConstants;

public class BeanFactorySingleton {



	private static ConfigurableListableBeanFactory factory = new XmlBeanFactory(new ClassPathResource(ApplicationConstants.beansXMLFile));
	private static BeanFactorySingleton singleton = new BeanFactorySingleton( );

	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private BeanFactorySingleton(){ }



	public static ConfigurableListableBeanFactory getFactory() {
		return factory;
	}


	public static BeanFactorySingleton getInstance( ) {
		return singleton;
	}


}