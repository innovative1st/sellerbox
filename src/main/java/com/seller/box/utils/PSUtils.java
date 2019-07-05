package com.seller.box.utils;

import java.io.PrintWriter;

import com.seller.box.core.PrinterConfig;

public class PSUtils {
	
    public static final String PRINT_STATUS_OK      = "OK";    
    public static final String PRINTER_MEDIA_NAME_SHIP_LABEL    = "shipLabel";  
    public static final String PRINTER_MEDIA_NAME_PACKING_SLIP  = "packingSlip";  
    public static final String PRINTER_MEDIA_NAME_GIFT_NOTE_CARD= "giftNoteCard";  
    
    public static final String PRINTER_TYPE_INVOICE   = "INVOICE";
    public static final String PRINTER_TYPE_SHIPLABEL = "SHIPLABEL";
    public static final String PRINTER_TYPE_GIFTCARD  = "GIFTCARD";   
    
    public static final String MEDIA_NAME_SHIPPING_LABEL = "SHIPPING_LABEL";
    public static final String MEDIA_NAME_GIFT_NOTE_CARD = "GIFT_NOTE_CARD";
    public static final String MEDIA_NAME_PACKING_SLIP   = "PACKING_SLIP"; 
    
    public static final String UPDATE_PRINTER_STATUS_SUCCESS = "200 OK";
    public static final String UPDATE_PRINTER_STATUS_FAILED  = "500 Internal Server Error";  

    public static final int PRINTER_STATUS_ACTIVE   = 1;  
    public static final int PRINTER_STATUS_INACTIVE = 0;  
    

    public static final String IP_REGISTER_STATUS_SUCCESS = "SUCCESS";  
    public static final String IP_REGISTER_STATUS_FAILED  = "FAILED"; 
    public static final String PING_STATUS_UNKNOWN_HOST   = "401"; 
    public static final String PING_STATUS_UNREACHABLE    = "404";
    
    public static enum Purposes {
        GET_CONFIGURATION("getConfig"),
        REGISTER_IP_ADDRESS("registerIPAddress"),
        GET_FILE_DATA("getFileData"),
        DEREGISTER_IP_ADDRESS("deRegisterIPAddress"),
        UPDATE_PRINTER_STATUS("updatePrinterStatus"),
        GET_DOCUMENTS("getDocuments"),
        LOG_ERRORS("logErrors"),
        PING("ping");


        private static final long serialVersionUID = 1L;

        private final String purpose;

        private Purposes(String purpose) {
            this.purpose = purpose;
        }

        public String toString() {
            return this.purpose;
        }
    }
    
    public static String getPrinterMediaName(String printerType){
        if(printerType.equalsIgnoreCase(PSUtils.PRINTER_TYPE_INVOICE)){
            return PSUtils.PRINTER_MEDIA_NAME_PACKING_SLIP;
        } else if(printerType.equalsIgnoreCase(PSUtils.PRINTER_TYPE_SHIPLABEL)){
            return PSUtils.PRINTER_MEDIA_NAME_SHIP_LABEL;
        }  else if(printerType.equalsIgnoreCase(PSUtils.PRINTER_TYPE_GIFTCARD)){
            return PSUtils.PRINTER_MEDIA_NAME_GIFT_NOTE_CARD;
        } 
        return null;
    }
    
    public static String getMediaName(String printerType){
        if(printerType.equalsIgnoreCase(PSUtils.PRINTER_MEDIA_NAME_PACKING_SLIP)){
            return PSUtils.MEDIA_NAME_PACKING_SLIP;
        } else if(printerType.equalsIgnoreCase(PSUtils.PRINTER_MEDIA_NAME_SHIP_LABEL)){
            return PSUtils.MEDIA_NAME_SHIPPING_LABEL;
        }  else if(printerType.equalsIgnoreCase(PSUtils.PRINTER_MEDIA_NAME_GIFT_NOTE_CARD)){
            return PSUtils.MEDIA_NAME_GIFT_NOTE_CARD;
        } 
        return null;
    }
//    
//    public static String getPrinterConfiguration(PrinterConfig pc, String printerMediaName){
//        String config = null;
//        if(printerMediaName != null){
//            StringBuffer sb = new StringBuffer();
//            sb.append("<entry key=\"").append(printerMediaName).append(".RetryCount\">5</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".SocketTimeoutMs\">5000</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".Media.Orientation\">portrait</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".HeavyDelayMs\">3000</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".Media.Name\">").append(PSUtils.getMediaName(printerMediaName)).append("</entry>\n");//Dynamic
//            if(pc.getPrinterType().equalsIgnoreCase(PSUtils.PRINTER_TYPE_INVOICE)){
//                sb.append("<entry key=\"").append(printerMediaName).append(".MimeType\">application/octet-stream</entry>\n");
//            } else {
//                sb.append("<entry key=\"").append(printerMediaName).append(".MimeType\">application/octet-stream; format=zpl</entry>\n");
//            }
//            sb.append("<entry key=\"").append(printerMediaName).append(".LightThreshold\">5</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".FlowControl\">false</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".Media.X\">").append(pc.getMediaX()).append("</entry>\n");//Dynamic
//            sb.append("<entry key=\"").append(printerMediaName).append(".Media.Y\">").append(pc.getMediaY()).append("</entry>\n");//Dynamic
//            sb.append("<entry key=\"").append(printerMediaName).append(".Media.W\">").append(pc.getMediaW()).append("</entry>\n");//Dynamic
//            sb.append("<entry key=\"").append(printerMediaName).append(".Media.H\">").append(pc.getMediaH()).append("</entry>\n");//Dynamic
//            sb.append("<entry key=\"").append(printerMediaName).append(".Media.Units\">").append("mm").append("</entry>\n");//Dynamic
//            sb.append("<entry key=\"").append(printerMediaName).append(".Printer\">name:").append(pc.getPrinterName()).append("</entry>\n");//Dynamic
//            
//            sb.append("<entry key=\"").append(printerMediaName).append(".ModerateThreshold\">10</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".RetryDelayMs\">1000</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".HeavyThreshold\">20</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".ModerateDelayMs\">1000</entry>\n");
//            sb.append("<entry key=\"").append(printerMediaName).append(".LightDelayMs\">100</entry>\n");
//            config = sb.toString();  
//        }
//        return config;
//    }
    
    public static void getPrinterConfiguration(PrinterConfig pc, String printerMediaName, PrintWriter out){
        if(printerMediaName != null){
            out.println("<entry key=\""+printerMediaName+".RetryCount\">5</entry>");
            out.println("<entry key=\""+printerMediaName+".SocketTimeoutMs\">5000</entry>");
            out.println("<entry key=\""+printerMediaName+".Media.Orientation\">portrait</entry>");
            out.println("<entry key=\""+printerMediaName+".HeavyDelayMs\">3000</entry>");
            out.println("<entry key=\""+printerMediaName+".Media.Name\">"+PSUtils.getMediaName(printerMediaName)+"</entry>");//Dynamic
            if(pc.getPrinterType().equalsIgnoreCase(PSUtils.PRINTER_TYPE_INVOICE)){
                out.println("<entry key=\""+printerMediaName+".MimeType\">application/octet-stream</entry>");
            } else {
                out.println("<entry key=\""+printerMediaName+".MimeType\">application/octet-stream; format=zpl</entry>");
            }
            out.println("<entry key=\""+printerMediaName+".LightThreshold\">5</entry>");
            out.println("<entry key=\""+printerMediaName+".FlowControl\">false</entry>");
            out.println("<entry key=\""+printerMediaName+".Media.X\">"+pc.getMediaX()+"</entry>");//Dynamic
            out.println("<entry key=\""+printerMediaName+".Media.Y\">"+pc.getMediaY()+"</entry>");//Dynamic
            out.println("<entry key=\""+printerMediaName+".Media.W\">"+pc.getMediaW()+"</entry>");//Dynamic
            out.println("<entry key=\""+printerMediaName+".Media.H\">"+pc.getMediaH()+"</entry>");//Dynamic
            out.println("<entry key=\""+printerMediaName+".Media.Units\">"+"mm"+"</entry>");//Dynamic
            out.println("<entry key=\""+printerMediaName+".Printer\">name:"+pc.getPrinterName()+"</entry>");//Dynamic
            
            out.println("<entry key=\""+printerMediaName+".ModerateThreshold\">10</entry>");
            out.println("<entry key=\""+printerMediaName+".RetryDelayMs\">1000</entry>");
            out.println("<entry key=\""+printerMediaName+".HeavyThreshold\">20</entry>");
            out.println("<entry key=\""+printerMediaName+".ModerateDelayMs\">1000</entry>");
            out.println("<entry key=\""+printerMediaName+".LightDelayMs\">100</entry>");
        }
    }
    
}
