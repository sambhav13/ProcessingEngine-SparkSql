package com.telarix.LookUpData;

import org.apache.log4j.Logger;

import com.telarix.Beans.RefDataHashMaps;
import com.telarix.CDRProcessingEngine.CDRProcessingEngineDriver;
import com.telarix.DAO.IXCDR02SparkSqlDAO;
import com.telarix.Utilities.HashMapCreator;
import com.telarix.Utilities.HashMapCreatorVS;
import com.telarix.constants.ApplicationConstants;
import com.telarix.constants.TableNameConstants;

public class ReferenceDataMapsVS {
	
	private HashMapCreatorVS hmc;
	private IXCDR02SparkSqlDAO ixCdrSparkSql;
	private RefDataHashMaps refDatahshMaps;//POJO of Maps
	
	private	final static Logger logger = Logger.getLogger(ReferenceDataMaps.class);
	
	public RefDataHashMaps getRefDatahshMaps() {
		return refDatahshMaps;
	}

	public void setRefDatahshMaps(RefDataHashMaps refDatahshMaps) {
		this.refDatahshMaps = refDatahshMaps;
	}

	public ReferenceDataMapsVS(HashMapCreatorVS hmc,
			IXCDR02SparkSqlDAO ixCdrSparkSql, RefDataHashMaps refDatahshMaps) {
		super();
		this.hmc = hmc;
		this.ixCdrSparkSql = ixCdrSparkSql;
		this.refDatahshMaps = refDatahshMaps;
	}

/*	public ReferenceDataMapsVS(HashMapCreatorVS hmc,
			IXCDR02SparkSqlDAO ixCdrSparkSql) {
		super();
		this.hmc = hmc;
		this.ixCdrSparkSql = ixCdrSparkSql;
		
	}*/

	/*public ReferenceDataMapsVS(HashMapCreatorVS hmc) {
		super();
		this.hmc = hmc;
	}*/
	
	
	public void populateMaps()
	{
		logger.info("populating hashmaps in the refDataHash Bean");
		//this.refDatahshMaps.setSwitchData(this.hmc.convertToHashMap(ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbSwitch_RefData).collectAsList(),ApplicationConstants.SWITCH_DATATYPE));
		this.refDatahshMaps.setInTrunkData(this.hmc.convertToHashMap(ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbTrunk_RefData).collectAsList(),ApplicationConstants.TRUNK_DATATYPE,"inbound"));
		this.refDatahshMaps.setOutTrunkData(this.hmc.convertToHashMap(ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbTrunk_RefData).collectAsList(),ApplicationConstants.TRUNK_DATATYPE,"outbound"));
		this.refDatahshMaps.setInAgreementData(this.hmc.convertToHashMap(ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbAgreementPOI_RefData).collectAsList(),ApplicationConstants.AGREEMENT_DATATYPE,"inbound"));
		this.refDatahshMaps.setOutAgreementData(this.hmc.convertToHashMap(ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbAgreementPOI_RefData).collectAsList(),ApplicationConstants.AGREEMENT_DATATYPE,"outbound"));
		//this.refDatahshMaps.setAgreementData(this.hmc.convertToHashMap(ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbAgreementPOI_RefData).collectAsList(),ApplicationConstants.AGREEMENT_DATATYPE));
		//this.refDatahshMaps.setGlobalVariable(this.hmc.convertToHashMap(ixCdrSparkSql.loadDataFrameViaSqlServer(TableNameConstants.wtbGlobalVariable_RefData).collectAsList(),ApplicationConstants.GLOBALVARIABLE_DATATYPE));
		logger.info("refDataHash Bean populated");
	}

	
	
}
