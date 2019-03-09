package com.seller.box.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

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
    
    public static boolean isNull(String value) {
    	try {
			if(value.isEmpty() || value == null) {
				return true;
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
    
}
