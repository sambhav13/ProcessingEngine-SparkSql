package com.telarix.Procedures;

/**
 * Created by patrice.bramble on 5/11/2015.
 */
public class ObjectParam {

    private String ParamName;
    private Object ParamValue;

    public String getParamName() {
        return ParamName;
    }

    public void setParamName(String paramName) {
        ParamName = paramName;
    }

   public Object getParamValue(){
       return  ParamValue;
   }

    public void setParamValue(Object paramValue){
        ParamValue = paramValue;
    }
}
