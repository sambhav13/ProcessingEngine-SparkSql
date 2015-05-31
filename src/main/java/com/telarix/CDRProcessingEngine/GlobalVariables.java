package com.telarix.CDRProcessingEngine;

import com.telarix.DAO.IXAdmin_MainDAO;
import com.telarix.DAO.IXControl_MainDAO;
import com.telarix.Procedures.ObjectParam;
import com.telarix.Procedures.ProcessAttribute;
import com.telarix.ProcessTypes.CDRLoad;
import com.telarix.constants.ApplicationErrors;
import com.telarix.constants.ApplicationQueries;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by patrice.bramble on 5/13/2015.
 */
public class GlobalVariables {

    private ConfigurableListableBeanFactory factory;
    private int ProcessLogID;
    public HashMap<String, Object> variables = new HashMap<>();
    private final Logger logger = Logger.getLogger(GlobalVariables.class);
    
    public GlobalVariables(int ProcessLogID, ConfigurableListableBeanFactory factory){
        this.ProcessLogID = ProcessLogID;
        this.factory = factory;
        populateGlobalVariables();
    }

    public void populateGlobalVariables(){
    	
    	
    	
        IXAdmin_MainDAO iXAdmin_MainDAO = null;
        IXControl_MainDAO iXControl_mainDAO = null;
        try{
            iXAdmin_MainDAO	= (IXAdmin_MainDAO)factory.getBean("iXAdmin_MainDAO");
            ResultSet globalVarRS = iXAdmin_MainDAO.executePreparedStatement(ApplicationQueries.Config_Details);
            while(globalVarRS.next()){
                variables.put(globalVarRS.getString(1), globalVarRS.getString(2));
            }


            //process Attributes (based off iXControl_Main.dbo.bspGetProcessAttribute
            iXControl_mainDAO = (IXControl_MainDAO) factory.getBean("iXControl_MainDAO");
            ProcessAttribute processAttribute = (ProcessAttribute) iXControl_mainDAO.queryForObject(ApplicationQueries.ProcessLog_Details,String.valueOf(ProcessLogID),ProcessAttribute.class);
            variables.put("ProcessLogID", ProcessLogID);
            variables.put("ProcessID", processAttribute.getProcessID());
            variables.put("RegistryID", processAttribute.getRegistryID());
            variables.put("SwitchID", processAttribute.getSwitchID());
            variables.put("ObjectID", processAttribute.getObjectID());
            variables.put("RegistryDateTime", processAttribute.getRegistryDateTime());
            variables.put("ExecutingServerDatabaseName", processAttribute.getExecutingServerDatabaseName());
            variables.put("ExecutingServerID", processAttribute.getExecutingServerID());
            variables.put("DatabaseID", processAttribute.getDatabaseID());
            variables.put("BeginDateTime", processAttribute.getBeginDateTime());
            variables.put("EndDateTime", processAttribute.getEndDateTime());
            variables.put("Registry", processAttribute.getRegistry());
            variables.put("ProcessTypeID", processAttribute.getProcessTypeID());
            variables.put("ProcessType", processAttribute.getProcessType());
            variables.put("SQLCommand", processAttribute.getSQLCommand());
            variables.put("JobName", processAttribute.getJobName());
            variables.put("WorkDatabase", processAttribute.getWorkDatabase());
            variables.put("ServerName", processAttribute.getServerName());
            variables.put("ProcessLogExecutingServerID", processAttribute.getProcessLogExecutingServerID());
            variables.put("ProcessLogExecutingServerName", processAttribute.getProcessLogExecutingServerName());
            variables.put("ProcessLogExecutingDatabaseID", processAttribute.getProcessLogExecutingDatabaseID());
            variables.put("ProcessLogExecutingDatabaseName", processAttribute.getProcessLogExecutingDatabaseName());

            //ObjectParam
            List<ObjectParam> objectParams = (List<ObjectParam>)(List<?>)iXControl_mainDAO.queryForList(ApplicationQueries.ObjectParam_Details, String.valueOf(variables.get("ObjectID")), ObjectParam.class);
            if(objectParams.size() > 0){
                for(ObjectParam op : objectParams){
                    variables.put(op.getParamName(), op.getParamValue());
                }
            }

            //Process Types
            ResultSet processTypes = iXControl_mainDAO.executePreparedStatement(ApplicationQueries.ProcessType_Details);
            while(processTypes.next()){
                variables.put(processTypes.getString(1), processTypes.getInt(2));
            }

            //
            int processType_DSR = (int) variables.get("ProcessType_DimensionSpecificRerate");
            if ((int) variables.get("ProcessTypeID")== processType_DSR){
                Map<String,Object> inputParameters = new HashMap<String,Object>();
                inputParameters.put("RegistryID", (int)variables.get("RegistryID"));
                //ResultSet reprocessDetails = iXControl_mainDAO.executeProcedure(ProcedureNameConstants.reprocessDetailDimension, inputParameters).;
                //iXControl_mainDAO.executePreparedStatement(ProcedureNameConstants.reprocessDetailDimension);
            }
            //Other variables
            variables.put("RawTableName", "tbBER_" + variables.get("RegistryID"));
            variables.put("PartitionID", variables.get("DatabaseID"));
            variables.put("AlgorithmID", variables.get("Load AlgorithmID"));

            System.out.println(variablesToString());
            logger.info("variables String Form is :--"+ variablesToString());


        }
        catch(NoSuchBeanDefinitionException be)
        {
//            logger.error(be.toString(), (Throwable)be);
//            logger.info("Exiting system since bean definition not there");
            System.exit(-1);
            be.printStackTrace();

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
//            logger.error(e.toString(), (Throwable)e);
//            logger.info("Exiting system");
            System.exit(-1);
            e.printStackTrace();
        }

        catch(Exception ex)
        {
//            logger.error(ex.toString(), (Throwable)ex);
//            logger.error("Exiting system with error code: " + ApplicationErrors.ErrorCode.Terminate);
            ex.printStackTrace();
            System.exit(-1);

        }
        logger.info("global variables populated");
        //logger.info("global variables populated!!");
    }

    public String variablesToString(){
        StringBuilder results = new StringBuilder("");
        for(Map.Entry<String,Object> entry : variables.entrySet()){
            if(entry.getValue() != null ) {
                System.out.println(entry.getKey() + " = " + entry.getValue().toString());
            }
            else
            {
                System.out.println(entry.getKey() + " = " + "");
            }
        }
        return results.toString();
    }
}
