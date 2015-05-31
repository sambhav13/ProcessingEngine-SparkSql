package com.telarix.Utilities;

import java.util.Comparator;
import java.util.Map;
import java.util.StringTokenizer;

public class ValueComparator implements Comparator<String> {

    Map<String, String> base;
    public ValueComparator(Map<String, String> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
    	
    	StringTokenizer st1 = new  StringTokenizer(a,",");
    	StringTokenizer st2 = new  StringTokenizer(b,",");
    	st1.nextToken();
    	st2.nextToken();
    	
    	
        if (Integer.parseInt(st1.nextToken()) >= Integer.parseInt(st2.nextToken())) {
            return 1;
        } else {
            return -1;
        } // returning 0 would merge keys
    }
}