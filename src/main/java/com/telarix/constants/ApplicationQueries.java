package com.telarix.constants;

/**
 * Created by patrice.bramble on 5/8/2015.
 */
public class ApplicationQueries {

    public final static String Config_Details = "Select SCName, SCValue from tbConfig";
    public final static String ProcessLog_Details = "Select DISTINCT ProcessLogID, R.RegistryID,SW.SwitchID, O.ObjectID, PL.ProcessID, R.RegistryDateTime, P.ExecutingServerDatabaseName,P.ExecutingServerID,D.DatabaseID,R.BeginDateTime,R.EndDateTime,R.Registry,PT.ProcessTypeID, PT.ProcessType, SQLCommand, P.JobName, S.WorkDatabase, S.ServerName, PL.ExecutingServerID as ProcessLogExecutingServerID, S.ServerName as ProcessLogExecutingServerName, ExecutingDatabaseID as ProcessLogExecutingDatabaseID, D.DatabaseName as ProcessLogExecutingDatabaseName, R.ObjectID, RegistryDateTime, BeginDateTime, EndDateTime, Registry" +
                                                        " from tbProcessLog PL with (nolock)" +
                                                        " left join tbDatabase D with (nolock)" +
                                                        " on PL.ExecutingDatabaseID = D.DatabaseID" +
                                                        " left join tbServer S with (nolock)" +
                                                        " on PL.ExecutingServerID = S.ServerID" +
                                                        " left join tbRegistry R with (nolock)" +
                                                        " on PL.RegistryID = R.RegistryID" +
                                                        " left join tbObject O" +
                                                        " on O.ObjectID = R.ObjectID"+
                                                        " left join iXControl_Port.dbo.tsSwitch sw on sw.SwitchID = O.SwitchID" +
                                                        " left join tbProcess P on P.ProcessID = PL.ProcessID " +
                                                        " left join tbServerDatabase SD on SD.ServerID = P.ExecutingServerID" +
                                                        " left join tbProcessType PT on P.ProcessTypeID = PT.ProcessTypeID" +
                                                        " left join tbServer SV on Sv.ServerID = P.ExecutingServerID "+
                                                        " where PL.ProcessLogID = ? ";

    public final static String ObjectParam_Details = "Select ParamName, ParamValue from " +
                                                        " tbObjectParam OP" +
                                                        " inner join tbObjectTypeParam OTP on OP.ObjectTypeParamID = OTP.ObjectTypeParamID " +
                                                        " where OP.ObjectID = ? ";

    public final static String Trunk_Details = "";
    
    public final static String ProcessType_Details = "Select 'ProcessType_' + replace(ProcessType, ' ','') as ProcessType, ProcessTypeID " +
            "from tbProcessType " +
            "where flag & 1 = 0 ";
}
