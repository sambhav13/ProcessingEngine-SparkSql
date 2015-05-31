
drop table GlobalVariable if exists;
drop table Loop if exists;
drop table Statement_Subscription if exists;
drop table RerateDimensionList if exists;
drop table tbDimensionTypeDefinition if exists;
drop table tmpSettlementDate if exists;
drop table tmpUNP1 if exists;

-- 1. Create temporary tables:
Create table GlobalVariable(
	vID int identity,
	VariableName varchar(100),
	VariableType varchar(100),
	VariableValue varchar(100)
);

Create	table Loop
(
	mID int identity,
	SubAlgorithmID int,
	LoopOn varchar(60),
	Value varchar(60)
);

create table Statement_Subscription
(
	StatementID int, 
	Statement varchar(60), 
	BillAccountID int, 
	StatementBeginDate datetime, 
	StatementEndDate datetime, 
	CreateDate datetime, 
	CloseDate datetime, 
	StatementNumber varchar(30), 
	ParentStatementID int, 
	BillPeriodTemplateID int, 
	ResidualTrafficWindow int, 
	StatementTemplateID int, 
	CoverLetterTemplateID int, 
	NumberingTemplateID int, 
	StatusID int, 
	StatementTypeID int, 
	StatementDueDate datetime, 
	ModifiedDate datetime, 
	ModifiedByID int, 
	Flag int
);


create table tmpUNP1
(
		rid int identity,
		UnifiedNumberPlanID int,
		AgreementID int,
		CommercialTrunkID int,
		CallTypeID int,
		DirectionID int
);

Create table RerateDimensionList 
(
		DimensionTypeID int,
		DimensionID	int
);

CREATE TABLE tbDimensionTypeDefinition
(
	DimensionTypeID int,
	DataStructureID int,
	IsInsert int ,
	AdditionalSQL varchar(2000) ,
	JoinDimensionCond varchar(2000) ,
	OtherJoinCond varchar(2000),
	WhereCond varchar(2000)
);

Create table tmpSettlementDate
(
	SettlementDate datetime
);

-- 2. Store ProcessLogID and AlgorithmID in GlobalVariable
insert into	GlobalVariable (VariableName, VariableType, VariableValue) values ('ProcessLogID','int', '4184156');

insert into	GlobalVariable (VariableName, VariableType, VariableValue) values ('AlgorithmID','int', '15');