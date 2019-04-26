package com.seller.box.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TimeZone;

public class SBUtils {
    public static String getPropertyValue(String param) {
        Properties propertiesFile = new Properties();
        String key = "";
        try {
            if (System.getenv("RIL_PROPERTIES_PATH") != null) {
                StringBuffer filepath = new StringBuffer(System.getenv("RIL_PROPERTIES_PATH").concat("SellerBox.properties").replace('\\', '/'));
                propertiesFile.load(new FileInputStream(filepath.toString()));
                Enumeration<?> e = propertiesFile.propertyNames();
                while (e.hasMoreElements()) {
                    key = (String)e.nextElement();
                    if (key.equalsIgnoreCase(param)) {
                        return propertiesFile.getProperty(key);
                    }
                }
            } else {
                System.out.println("RIL_PROPERTIES_PATH is null");
            }
        } catch (IOException e) {
        	System.out.println("getPropertyValue Exception");
        }
        return null;
    }
    
    public static String getTxnSysDateTime() {
    	String dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());
    	return dateTime;
    }
    
    public static boolean isNull(Object value) {
    	try {
    		if (value == null) {
    			return true;
    		} else {
    			if (value instanceof String) {
    				if(((String)value).isEmpty() || value == null) {
    					return true;
    				}
    			} else if (value instanceof Integer) {
    				if((Integer)value == 0) {
    					return true;
    				}
    			}
    		}
		} catch (Exception e) {
			return true;
		}
    	return false;
    }
    
    public static boolean isNotNull(String value) {
    	try {
			if(value.isEmpty() || value == null) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
    	return true;
    }
    public static Date convertGMTtoISTDate(String dateAsString){
        Date date = null;
        try {
            SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            //datef.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = datef.parse(dateAsString);
            String timeZone = "IST";
            if (date != null){
                SimpleDateFormat sdf = new SimpleDateFormat();
                timeZone = Calendar.getInstance().getTimeZone().getID();
                sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
                date = sdf.parse(sdf.format(date));
            }
        } catch (ParseException e) {
        	date = new Date();
            //logger.error("ParseException :: convertGMTtoISTDate(String dateAsString)", e);
        }
        return date;
    }
}
