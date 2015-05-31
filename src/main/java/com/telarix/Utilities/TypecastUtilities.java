package com.telarix.Utilities;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.DataType;

import com.telarix.constants.ApplicationConstants;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TypecastUtilities {
	public static Timestamp getDate(String dateInString) {
			
		SimpleDateFormat formatter = new SimpleDateFormat(ApplicationConstants.dateFormat);
		Timestamp date = null;
		try {
			date = new Timestamp((formatter.parse(dateInString)).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static DataType getDataType(String dataType) {

		switch (dataType.toLowerCase()) {
		case "int":
			return DataTypes.IntegerType;
		case "date":
			return DataTypes.TimestampType;
        case "datetime":
            return DataTypes.TimestampType;
		case "string":
			return DataTypes.StringType;
		case "float":
			return DataTypes.FloatType;
		case "double":
			return DataTypes.DoubleType;
		case "decimal":
			return DataTypes.DoubleType;
		case "boolean":
			return DataTypes.BooleanType;
		case "char":
			return DataTypes.StringType;
		}
		return null;
	}

	public static Object getValue(String datatype, String value) {

        if(value.toUpperCase().equals("NULL"))
            return null;

		switch (datatype) {
		case "int":
			return Integer.parseInt(value.trim());
		case "date":
			return getDate(value);
		case "string":
			return value;
		case "float":
			return Float.parseFloat(value);
		case "double":
			return Double.parseDouble(value);
		case "decimal":
			return Double.parseDouble(value);
		case "boolean":
			return Boolean.parseBoolean(value);
		case "char":
			return value;

		}
		return null;
	}
}
