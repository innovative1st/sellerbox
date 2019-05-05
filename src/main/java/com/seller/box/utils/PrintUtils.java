package com.seller.box.utils;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.seller.box.dao.EdiPackStationInfoDao;
import com.seller.box.entities.EdiPackStationInfo;

class PrintUtils {
	private static final Logger logger = LogManager.getLogger(PrintUtils.class);
    private String ipaddress;
    private int listenPort = 0;
    private String errorMessage;
    public static final String PRINT_CALL_POST = "POST";
    public static final String PRINT_CALL_PING = "PING";
    public static final String LINE_SEPERATOR  = "\r\n";

    @Autowired
    EdiPackStationInfoDao ediPSInfoDao;
    
    public PrintUtils(Long packStationId) {
        logger.info("initializing PrintUtils() --------------- START");
        try {
        	if(packStationId != null) {
        		EdiPackStationInfo psInfo = ediPSInfoDao.findByPackStationId(packStationId);
        		if(psInfo != null) {
        			this.setIpaddress(psInfo.getPackStationIpaddress());
                    this.setListenPort(psInfo.getPsListenPort());
                    this.setErrorMessage(null);
                    logger.info("IP Address  : " + this.getIpaddress());
                    logger.info("Listen Port : " + this.getListenPort());
        		} else {
        			this.setErrorMessage("Not a valid Pack Station Id : " + packStationId);
        		}
        	}
        } catch (Exception e) {
            logger.error("Exception Occured :: initializing PrintUtils()", e);
        }
        logger.info("initializing PrintUtils() --------------- END");
    }
    
    public Properties printDocument(String shipmentId, String printerType, String zplContent, String filepath){
        logger.info("printDocument(String shipmentId, String printerType, String zplContent, String filepath) --------------- START");
        Properties result = new Properties();
        Socket echoSocket = null;
        String hostName = this.getIpaddress();
        int portNumber = this.getListenPort();
        try {
            echoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            StringBuffer sbuf = new StringBuffer();
            //METHOD & PARAM
            sbuf.append(PRINT_CALL_POST).append(" ").append(SBUtils.getPhoneUrl()).append("?");
            sbuf.append("IDs=PS0001").append("&");
            sbuf.append("shipment_id=").append(shipmentId).append("&");
            sbuf.append("accessToken=ADFM_PS0001").append("&");
            sbuf.append("printFromStore=Y").append("&");
            if (printerType.equalsIgnoreCase(SBConstant.PRINTER_TYPE_GIFTCARD)) {//giftNoteCard
                sbuf.append("purpose=printZPL").append("&");
                sbuf.append("documentFormat=ZPL").append("&");
                sbuf.append("documentType=giftNoteCard").append("&"); //giftNoteCard
                sbuf.append("contents=").append(zplContent).append(" header");
            } else if(printerType.equalsIgnoreCase(SBConstant.PRINTER_TYPE_SHIPLABEL)) {//shipLabel
                sbuf.append("purpose=printZPL").append("&");
                sbuf.append("documentFormat=ZPL").append("&");
                sbuf.append("documentType=shipLabel").append("&"); //shipLabel
                String contents = new String(FileUtils.readFileToByteArray(new File(filepath)));
                byte[] bytesEncoded = Base64.encodeBase64(contents.getBytes());
                sbuf.append("contents=").append(new String(bytesEncoded)).append(" header");
            } else if(printerType.equalsIgnoreCase(SBConstant.PRINTER_TYPE_INVOICE)) {//packingSlip
                sbuf.append("purpose=printPDF").append("&");
                sbuf.append("documentType=packingSlip").append("&");//giftNoteCard/shipLabel/packingSlip
                sbuf.append("documentFormat=PDF").append("&");
                //String contents = new String(Files.readAllBytes(Paths.get(FILE_TO_SEND)));
                //byte[] bytesEncoded = Base64.encodeBase64(contents.getBytes());
                //System.out.println(new String(bytesEncoded));
                sbuf.append("contents=").append("").append(" header");
            }
             
            //HEADER
            sbuf.append(LINE_SEPERATOR).append("content-length:").append(0);
            sbuf.append(LINE_SEPERATOR).append("content-type:application/octet-stream; format=zpl");
            sbuf.append(LINE_SEPERATOR).append("origin:*");
            if(printerType.equalsIgnoreCase(SBConstant.PRINTER_TYPE_INVOICE)){
                sbuf.append(LINE_SEPERATOR).append("file_path:").append(filepath);
                sbuf.append(LINE_SEPERATOR).append("domain_name:REPROINDIA");
                sbuf.append(LINE_SEPERATOR).append("user_name:ecm_admin");
                sbuf.append(LINE_SEPERATOR).append("user_pass:Repro@666");
            }
            sbuf.append(LINE_SEPERATOR).append(" ");
            sbuf.append(LINE_SEPERATOR).append("content-disposition:name=");
            StringReader strReader = new StringReader(sbuf.toString());
            
            BufferedReader br = new BufferedReader(strReader);
            if(br != null){
                out.println(sbuf);
                out.flush();
                String message = in.readLine();
                StringTokenizer st = new StringTokenizer(message, "&"); 
                while (st.hasMoreTokens()) { 
                    String e = st.nextToken(); 
                    int sep = e.indexOf('='); 
                    if (sep >= 0) { 
                        result.put(e.substring(0, sep).trim(), e.substring(sep + 1)); 
                    } 
                }
                echoSocket.close();
                logger.info("Status  : "+result.getProperty("STATUS"));
                logger.info("Message : "+result.getProperty("MESSAGE"));
            }
        } catch (UnknownHostException e) {
            result = new Properties();
            result.put("STATUS", "401 UnknownHostException"); 
            result.put("MESSAGE", "UnknownHostException "+e.getMessage()); 
            logger.error("UnknownHostException Occured :: printDocument(String shipmentId, String printerType, String zplContent, String filepath)", e);
        } catch (IOException e) {
            result = new Properties();
            result.put("STATUS", "401 UnknownHostException"); 
            result.put("MESSAGE", "Couldn't get I/O for the connection to " + hostName +" - "+e.getMessage()); 
            logger.error("UnknownHostException Occured :: printDocument(String shipmentId, String printerType, String zplContent, String filepath)", e);
        } 
        logger.info("printDocument(String shipmentId, String printerType, String zplContent, String filepath) --------------- END");
        return result;
    }

    public Properties printPackingSlip(String shipmentId, String filepath){
        logger.info("printPackingSlip(String shipmentId, String filepath) --------------- START");
        Properties result = new Properties();
        Socket echoSocket = null;
        String hostName = this.getIpaddress();
        if(hostName != null && this.getListenPort() > 0){
            int portNumber = this.getListenPort();
            try {
                echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
    
                StringBuffer sbuf = new StringBuffer();
                //METHOD & PARAM
                sbuf.append(PRINT_CALL_POST).append(" ").append(SBUtils.getPhoneUrl()).append("?");
                sbuf.append("IDs=PS0001").append("&");
                sbuf.append("shipment_id=").append(shipmentId).append("&");
                sbuf.append("accessToken=ADFM_PS0001").append("&");
                sbuf.append("printFromStore=Y").append("&");
                sbuf.append("purpose=printPDF").append("&");
                sbuf.append("documentType=packingSlip").append("&");//giftNoteCard/shipLabel/packingSlip
                sbuf.append("documentFormat=PDF").append("&");
                //String contents = new String(Files.readAllBytes(Paths.get(FILE_TO_SEND)));
                //byte[] bytesEncoded = Base64.encodeBase64(contents.getBytes());
                //System.out.println(new String(bytesEncoded));
                sbuf.append("contents=").append("").append(" header");
                
                 
                //HEADER
                sbuf.append(LINE_SEPERATOR).append("content-length:").append(0);
                sbuf.append(LINE_SEPERATOR).append("content-type:application/octet-stream; format=zpl");
                sbuf.append(LINE_SEPERATOR).append("origin:*");
                sbuf.append(LINE_SEPERATOR).append("file_path:").append(filepath);
                sbuf.append(LINE_SEPERATOR).append("domain_name:REPROINDIA");
                sbuf.append(LINE_SEPERATOR).append("user_name:ecm_admin");
                sbuf.append(LINE_SEPERATOR).append("user_pass:Repro@666");
    
                sbuf.append(LINE_SEPERATOR).append(" ");
                sbuf.append(LINE_SEPERATOR).append("content-disposition:name=");
                StringReader strReader = new StringReader(sbuf.toString());
                
                BufferedReader br = new BufferedReader(strReader);
                if(br != null){
                    out.println(sbuf);
                    out.flush();
                    String message = in.readLine();
                    StringTokenizer st = new StringTokenizer(message, "&"); 
                    while (st.hasMoreTokens()) { 
                        String e = st.nextToken(); 
                        int sep = e.indexOf('='); 
                        if (sep >= 0) { 
                            result.put(e.substring(0, sep).trim(), e.substring(sep + 1)); 
                        } 
                    }
                    echoSocket.close();
                    logger.info("Status  : "+result.getProperty("STATUS"));
                    logger.info("Message : "+result.getProperty("MESSAGE"));
                }
            } catch (UnknownHostException e) {
                result = new Properties();
                result.put("STATUS", "401 UnknownHostException"); 
                result.put("MESSAGE", "UnknownHostException "+e.getMessage()); 
                logger.error("UnknownHostException Occured :: printPackingSlip(String shipmentId, String filepath)", e);
            } catch (IOException e) {
                result = new Properties();
                result.put("STATUS", "401 IOException"); 
                result.put("MESSAGE", "Couldn't get I/O for the connection to " + hostName +" - "+e.getMessage()); 
                logger.error("UnknownHostException Occured :: printPackingSlip(String shipmentId, String filepath)", e);
            } 
        } else {
            result = new Properties();
            result.put("STATUS", "404 IOException"); 
            result.put("MESSAGE", "Flex Print connection not established."); 
        }  
        logger.info("printPackingSlip(String shipmentId, String filepath) --------------- END");
        return result;
    }
    
    public Properties printShipLabel(String shipmentId, String filepath){
        logger.info("printShipLabel(String shipmentId, String filepath) --------------- START");
        Properties result = new Properties();
        Socket echoSocket = null;
        String hostName = this.getIpaddress();
        if(hostName != null && this.getListenPort() > 0){
            int portNumber = this.getListenPort();
            try {
                echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
    
                StringBuffer sbuf = new StringBuffer();
                //METHOD & PARAM
                sbuf.append(PRINT_CALL_POST).append(" ").append(SBUtils.getPhoneUrl()).append("?");
                sbuf.append("IDs=PS0001").append("&");
                sbuf.append("shipment_id=").append(shipmentId).append("&");
                sbuf.append("accessToken=ADFM_PS0001").append("&");
                sbuf.append("printFromStore=Y").append("&");
                sbuf.append("purpose=printZPL").append("&");
                sbuf.append("documentFormat=ZPL").append("&");
                sbuf.append("documentType=shipLabel").append("&"); //shipLabel
                String contents = new String(FileUtils.readFileToByteArray(new File(filepath)));
                byte[] bytesEncoded = Base64.encodeBase64(contents.getBytes());
                sbuf.append("contents=").append(new String(bytesEncoded)).append(" header");
                
                //HEADER
                sbuf.append(LINE_SEPERATOR).append("content-length:").append(0);
                sbuf.append(LINE_SEPERATOR).append("content-type:application/octet-stream; format=zpl");
                sbuf.append(LINE_SEPERATOR).append("origin:*");
                sbuf.append(LINE_SEPERATOR).append(" ");
                sbuf.append(LINE_SEPERATOR).append("content-disposition:name=");
                StringReader strReader = new StringReader(sbuf.toString());
                
                BufferedReader br = new BufferedReader(strReader);
                if(br != null){
                    out.println(sbuf);
                    out.flush();
                    String message = in.readLine();
                    StringTokenizer st = new StringTokenizer(message, "&"); 
                    while (st.hasMoreTokens()) { 
                        String e = st.nextToken(); 
                        int sep = e.indexOf('='); 
                        if (sep >= 0) { 
                            result.put(e.substring(0, sep).trim(), e.substring(sep + 1)); 
                        } 
                    }
                    echoSocket.close();
                    logger.info("Status  : "+result.getProperty("STATUS"));
                    logger.info("Message : "+result.getProperty("MESSAGE"));
                }
            } catch (UnknownHostException e) {
                result = new Properties();
                result.put("STATUS", "401 UnknownHostException"); 
                result.put("MESSAGE", "UnknownHostException "+e.getMessage()); 
                logger.error("UnknownHostException Occured :: printShipLabel(String shipmentId, String filepath)", e);
            } catch (IOException e) {
                result = new Properties();
                result.put("STATUS", "401 IOException"); 
                result.put("MESSAGE", "Couldn't get I/O for the connection to " + hostName +" - "+e.getMessage()); 
                logger.error("IOException Occured :: printShipLabel(String shipmentId, String filepath)", e);
            } 
        } else {
            result = new Properties();
            result.put("STATUS", "404 IOException"); 
            result.put("MESSAGE", "Flex Print connection not established."); 
        }
        logger.info("printShipLabel(String shipmentId, String filepath)--------------- END");
        return result;
    }
    
    public Properties printGiftNoteCard(String shipmentId, List<String> giftNoteCards){
        logger.info("printGiftNoteCard(String shipmentId, List<String> giftNoteCards)--------------- START");
        Properties result = new Properties();
        Socket echoSocket = null;
        String hostName = this.getIpaddress();
        if(hostName != null && this.getListenPort() > 0){
            int portNumber = this.getListenPort();
            try {
                for(String giftMessage : giftNoteCards){
                    echoSocket = new Socket(hostName, portNumber);
                    PrintWriter out = new PrintWriter(echoSocket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        
                    StringBuffer sbuf = new StringBuffer();
                    //METHOD & PARAM
                    sbuf.append(PRINT_CALL_POST).append(" ").append(SBUtils.getPhoneUrl()).append("?");
                    sbuf.append("IDs=PS0001").append("&");
                    sbuf.append("shipment_id=").append(shipmentId).append("&");
                    sbuf.append("accessToken=ADFM_PS0001").append("&");
                    sbuf.append("printFromStore=Y").append("&");
                    sbuf.append("purpose=printZPL").append("&");
                    sbuf.append("documentFormat=ZPL").append("&");
                    sbuf.append("documentType=giftNoteCard").append("&"); //giftNoteCard
                    sbuf.append("contents=").append(giftMessage).append(" header");
                    
                    //HEADER
                    sbuf.append(LINE_SEPERATOR).append("content-length:").append(0);
                    sbuf.append(LINE_SEPERATOR).append("content-type:application/octet-stream; format=zpl");
                    sbuf.append(LINE_SEPERATOR).append("origin:*");
                    sbuf.append(LINE_SEPERATOR).append(" ");
                    sbuf.append(LINE_SEPERATOR).append("content-disposition:name=");
                    StringReader strReader = new StringReader(sbuf.toString());
                    
                    BufferedReader br = new BufferedReader(strReader);
                    if(br != null){
                        out.println(sbuf);
                        out.flush();
                        String message = in.readLine();
                        StringTokenizer st = new StringTokenizer(message, "&"); 
                        while (st.hasMoreTokens()) { 
                            String e = st.nextToken(); 
                            int sep = e.indexOf('='); 
                            if (sep >= 0) { 
                                result.put(e.substring(0, sep).trim(), e.substring(sep + 1)); 
                            } 
                        }
                        echoSocket.close();
                        logger.info("Status  : "+result.getProperty("STATUS"));
                        logger.info("Message : "+result.getProperty("MESSAGE"));
                    }
                }
            } catch (UnknownHostException e) {
                result = new Properties();
                result.put("STATUS", "401 UnknownHostException"); 
                result.put("MESSAGE", "UnknownHostException "+e.getMessage()); 
                logger.error("UnknownHostException Occured :: printGiftNoteCard(String shipmentId, List<String> giftNoteCards)", e);
            } catch (IOException e) {
                result = new Properties();
                result.put("STATUS", "401 IOException"); 
                result.put("MESSAGE", "Couldn't get I/O for the connection to " + hostName +" - "+e.getMessage()); 
                logger.error("IOException Occured :: printGiftNoteCard(String shipmentId, List<String> giftNoteCards)", e);
            } 
        } else {
            result = new Properties();
            result.put("STATUS", "404 IOException"); 
            result.put("MESSAGE", "Flex Print connection not established."); 
        }
        logger.info("printGiftNoteCard(String shipmentId, List<String> giftNoteCards)--------------- END");
        return result;
    }
    
    public String pingRunningPSUtils(String hostName, String psName){
        logger.info("pingRunningPSUtils(String hostName, String psName) --------------- START");
        logger.info("IP Address : " + hostName);
        logger.info("PS Name    : " + psName);
        String status = null;
        Socket echoSocket = null;
        try {
            int portNumber = Integer.parseInt(SBUtils.getPropertyValue("ps.server.listen.port"));
            echoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            StringBuffer sbuf = new StringBuffer();
            //METHOD & PARAM
            sbuf.append(PRINT_CALL_PING).append(" ").append(SBUtils.getPhoneUrl()).append("?");
            sbuf.append("instanceName=").append(psName) .append("&");
            sbuf.append("purpose=ping").append(" ");
           
            StringReader strReader = new StringReader(sbuf.toString());
            
            BufferedReader br = new BufferedReader(strReader);
            if(br != null){
                out.println(sbuf);
                out.flush();
                String message = in.readLine();
                StringTokenizer st = new StringTokenizer(message, "&"); 
                Properties parms = new Properties();
                while (st.hasMoreTokens()) { 
                    String e = st.nextToken(); 
                    int sep = e.indexOf('='); 
                    if (sep >= 0) { 
                        parms.put(e.substring(0, sep).trim(), e.substring(sep + 1)); 
                    } 
                }
                echoSocket.close();
                status = parms.getProperty("STATUS");
                logger.info("Ping PS Status  : " +parms.getProperty("STATUS"));
                logger.info("Ping PS Message : " +parms.getProperty("MESSAGE"));
            }
        } catch (UnknownHostException e) {
            logger.error("Don't know about host " + hostName+" ::: " + e.getMessage());
            status = SBConstant.PING_STATUS_UNKNOWN_HOST;
        } catch (IOException e) {
            logger.error(e.getMessage());
            status = SBConstant.PING_STATUS_UNREACHABLE;
        } 
        logger.info("pingRunningPSUtils(String hostName, String psName) --------------- END");
        return status;
    }
    
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public int getListenPort() {
        return listenPort;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public static void main(){
        
    }
}