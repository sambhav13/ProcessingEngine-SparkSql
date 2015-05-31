package com.telarix.CDRProcessingEngine;

import com.telarix.DAO.IXCDR_02DAO;
import com.telarix.constants.ProcedureNameConstants;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrice.bramble on 5/13/2015.
 */
public class ReferenceDataSubscription {

    private int ProcessLogID;
    private String Database;
    ConfigurableListableBeanFactory factory;

    public ReferenceDataSubscription (String dBSource, int processLogID, ConfigurableListableBeanFactory factory)
    {
        this.Database = dBSource;
        this.ProcessLogID = processLogID;
        this.factory = factory;
    }

    public void run(){
        //Run ReferenceData  Subscription
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("ProcessLogID",ProcessLogID);
        parameters.put("DatabaseName", Database);

        IXCDR_02DAO ixcdr02DAO = (IXCDR_02DAO) factory.getBean("iXCDR_02DAO");

        ixcdr02DAO.executeProcedure(ProcedureNameConstants.referenceDataSubscription, parameters);
    }
}
