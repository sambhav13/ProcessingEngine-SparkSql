package com.telarix.Procedures;

import java.util.Date;

/**
 * Created by patrice.bramble on 5/11/2015.
 */

public class ProcessAttribute {

    private int ProcessLogID;
    private int RegistryID;
    private int SwitchID;
    private int ObjectID;
    private int ProcessID;
    private Date RegistryDateTime;
    private String ExecutingServerDatabaseName;
    private int ExecutingServerID;
    private int DatabaseID;
    private Date BeginDateTime;
    private Date EndDateTime;
    private String Registry;
    private int ProcessTypeID;
    private String ProcessType;
    private String SQLCommand;
    private String JobName;
    private String WorkDatabase;
    private String ServerName;
    private int ProcessLogExecutingServerID;
    private String ProcessLogExecutingServerName;
    private int ProcessLogExecutingDatabaseID;
    private String ProcessLogExecutingDatabaseName;

    public int getProcessLogID() {
        return ProcessLogID;
    }

    public void setProcessLogID(int processLogID) {
        ProcessLogID = processLogID;
    }

    public int getRegistryID() {
        return RegistryID;
    }

    public void setRegistryID(int registryID) {
        RegistryID = registryID;
    }

    public int getSwitchID() {
        return SwitchID;
    }

    public void setSwitchID(int switchID) {
        SwitchID = switchID;
    }

    public int getObjectID() {
        return ObjectID;
    }

    public void setObjectID(int objectID) {
        ObjectID = objectID;
    }

    public int getProcessID() {
        return ProcessID;
    }

    public void setProcessID(int processID) {
        ProcessID = processID;
    }

    public Date getRegistryDateTime() {
        return RegistryDateTime;
    }

    public void setRegistryDateTime(Date registryDateTime) {
        RegistryDateTime = registryDateTime;
    }

    public String getExecutingServerDatabaseName() {
        return ExecutingServerDatabaseName;
    }

    public void setExecutingServerDatabaseName(String executingServerDatabaseName) {
        ExecutingServerDatabaseName = executingServerDatabaseName;
    }

    public int getExecutingServerID() {
        return ExecutingServerID;
    }

    public void setExecutingServerID(int executingServerID) {
        ExecutingServerID = executingServerID;
    }

    public int getDatabaseID() {
        return DatabaseID;
    }

    public void setDatabaseID(int databaseID) {
        DatabaseID = databaseID;
    }

    public Date getBeginDateTime() {
        return BeginDateTime;
    }

    public void setBeginDateTime(Date beginDateTime) {
        BeginDateTime = beginDateTime;
    }

    public Date getEndDateTime() {
        return EndDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        EndDateTime = endDateTime;
    }

    public String getRegistry() {
        return Registry;
    }

    public void setRegistry(String registry) {
        Registry = registry;
    }

    public int getProcessTypeID() {
        return ProcessTypeID;
    }

    public void setProcessTypeID(int processTypeID) {
        ProcessTypeID = processTypeID;
    }

    public String getProcessType() {
        return ProcessType;
    }

    public void setProcessType(String processType) {
        ProcessType = processType;
    }

    public String getSQLCommand() {
        return SQLCommand;
    }

    public void setSQLCommand(String SQLCommand) {
        this.SQLCommand = SQLCommand;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public String getWorkDatabase() {
        return WorkDatabase;
    }

    public void setWorkDatabase(String workDatabase) {
        WorkDatabase = workDatabase;
    }

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public int getProcessLogExecutingServerID() {
        return ProcessLogExecutingServerID;
    }

    public void setProcessLogExecutingServerID(int processLogExecutingServerID) {
        ProcessLogExecutingServerID = processLogExecutingServerID;
    }

    public String getProcessLogExecutingServerName() {
        return ProcessLogExecutingServerName;
    }

    public void setProcessLogExecutingServerName(String processLogExecutingServerName) {
        ProcessLogExecutingServerName = processLogExecutingServerName;
    }

    public int getProcessLogExecutingDatabaseID() {
        return ProcessLogExecutingDatabaseID;
    }

    public void setProcessLogExecutingDatabaseID(int processLogExecutingDatabaseID) {
        ProcessLogExecutingDatabaseID = processLogExecutingDatabaseID;
    }

    public String getProcessLogExecutingDatabaseName() {
        return ProcessLogExecutingDatabaseName;
    }

    public void setProcessLogExecutingDatabaseName(String processLogExecutingDatabaseName) {
        ProcessLogExecutingDatabaseName = processLogExecutingDatabaseName;
    }

}