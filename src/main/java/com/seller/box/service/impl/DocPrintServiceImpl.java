package com.seller.box.service.impl;

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
import org.springframework.stereotype.Service;

import com.seller.box.core.ManifestResponse;
import com.seller.box.service.DocPrintService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@Service
public class DocPrintServiceImpl implements DocPrintService{
	private static final Logger logger = LogManager.getLogger(DocPrintServiceImpl.class);
    public static final String PRINT_CALL_POST = "POST";
    public static final String PRINT_CALL_PING = "PING";
    public static final String LINE_SEPERATOR  = "\r\n";

    @Override
    public Properties printPackingSlip(ManifestResponse response, String filepath){
    	String logPrefix = response.getRequestId()+SBConstant.LOG_SEPRATOR;
        logger.info(logPrefix+"printPackingSlip(String requestId, String ipAddress, String shipmentId, String filepath) --------------- START");
        Properties result 	= new Properties();
        Socket echoSocket 	= null;
        String hostName 	= response.getPsIpAddess();
        int portNumber  	= response.getPsListenPort();
        if(hostName != null){
            try {
                echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
    
                StringBuffer sbuf = new StringBuffer();
                //METHOD & PARAM
                sbuf.append(PRINT_CALL_POST).append(" ").append(SBUtils.getPhoneUrl()).append("?");
                sbuf.append("IDs=PS0001").append("&");
                sbuf.append("shipment_id=").append(response.getShipmentId()).append("&");
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
                    logger.info("Status  : "+result.getProperty(SBConstant.PRINT_PROPERY_STATUS));
                    logger.info("Message : "+result.getProperty(SBConstant.PRINT_PROPERY_MESSAGE));
                }
            } catch (UnknownHostException e) {
                result.put(SBConstant.PRINT_PROPERY_STATUS, SBConstant.PRINT_STATUS_UNKNOWN_HOST); 
                result.put(SBConstant.PRINT_PROPERY_MESSAGE, "UnknownHostException "+e.getMessage()); 
                logger.error(logPrefix+SBConstant.LOG_SEPRATOR+"UnknownHostException Occured :: printPackingSlip(String requestId, String ipAddress, String shipmentId, String filepath)", e);
            } catch (IOException e) {
                result.put(SBConstant.PRINT_PROPERY_STATUS, SBConstant.PRINT_STATUS_IO_EXCEPTION); 
                result.put(SBConstant.PRINT_PROPERY_MESSAGE, "Couldn't get I/O for the connection to " + hostName +" - "+e.getMessage()); 
                logger.error(logPrefix+SBConstant.LOG_SEPRATOR+"UnknownHostException Occured :: printPackingSlip(String requestId, String ipAddress, String shipmentId, String filepath)", e);
            } 
        } else {
            result.put(SBConstant.PRINT_PROPERY_STATUS, SBConstant.PRINT_STATUS_HOSTNAME_MISSING); 
            result.put(SBConstant.PRINT_PROPERY_MESSAGE, "Flex Print connection not established, hostname is missing."); 
        }  
        logger.info(logPrefix+SBConstant.LOG_SEPRATOR+"printPackingSlip(String requestId, String ipAddress, String shipmentId, String filepath) --------------- END");
        return result;
    }
    
    @Override
    public Properties printShipLabel(ManifestResponse response, String filepath){
    	String logPrefix = response.getRequestId()+SBConstant.LOG_SEPRATOR;
        logger.info(logPrefix+SBConstant.LOG_SEPRATOR+"printShipLabel(String requestId, String ipAddress, String shipmentId, String filepath) --------------- START");
        Properties result 	= new Properties();
        Socket echoSocket 	= null;
        String hostName 	= response.getPsIpAddess();
        int portNumber  	= response.getPsListenPort();
        if(hostName != null){
            try {
                echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
    
                StringBuffer sbuf = new StringBuffer();
                //METHOD & PARAM
                sbuf.append(PRINT_CALL_POST).append(" ").append(SBUtils.getPhoneUrl()).append("?");
                sbuf.append("IDs=PS0001").append("&");
                sbuf.append("shipment_id=").append(response.getShipmentId()).append("&");
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
                }
            } catch (UnknownHostException e) {
                result.put(SBConstant.PRINT_PROPERY_STATUS, SBConstant.PRINT_STATUS_UNKNOWN_HOST); 
                result.put(SBConstant.PRINT_PROPERY_MESSAGE, "UnknownHostException "+e.getMessage()); 
                logger.error(logPrefix+SBConstant.LOG_SEPRATOR+"UnknownHostException Occured :: printShipLabel(String requestId, String ipAddress, String shipmentId, String filepath)", e);
            } catch (IOException e) {
                result.put(SBConstant.PRINT_PROPERY_STATUS, SBConstant.PRINT_STATUS_IO_EXCEPTION); 
                result.put(SBConstant.PRINT_PROPERY_MESSAGE, "Couldn't get I/O for the connection to " + hostName +" - "+e.getMessage()); 
                logger.error(logPrefix+SBConstant.LOG_SEPRATOR+"IOException Occured :: printShipLabel(String requestId, String ipAddress, String shipmentId, String filepath)", e);
            } 
        } else {
            result.put(SBConstant.PRINT_PROPERY_STATUS, SBConstant.PRINT_STATUS_HOSTNAME_MISSING); 
            result.put(SBConstant.PRINT_PROPERY_MESSAGE, "Flex Print connection not established, hostname is missing."); 
        }
        logger.info("Status  : "+result.getProperty(SBConstant.PRINT_PROPERY_STATUS));
        logger.info("Message : "+result.getProperty(SBConstant.PRINT_PROPERY_MESSAGE));
        logger.info(logPrefix+SBConstant.LOG_SEPRATOR+"printShipLabel(String requestId, String ipAddress, String shipmentId, String filepath)--------------- END");
        return result;
    }
    
    @Override
    public Properties printGiftNoteCard(ManifestResponse response, List<String> giftNoteCards){
    	String logPrefix = response.getRequestId()+SBConstant.LOG_SEPRATOR;
    	logger.info(logPrefix+SBConstant.LOG_SEPRATOR+"printGiftNoteCard(String requestId, String shipmentId, List<String> giftNoteCards)--------------- START");
        Properties result 	= new Properties();
        Socket echoSocket 	= null;
        String hostName 	= response.getPsIpAddess();
        int portNumber  	= response.getPsListenPort();
        if(hostName != null){
            try {
                for(String giftMessage : giftNoteCards){
                    echoSocket = new Socket(hostName, portNumber);
                    PrintWriter out = new PrintWriter(echoSocket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        
                    StringBuffer sbuf = new StringBuffer();
                    //METHOD & PARAM
                    sbuf.append(PRINT_CALL_POST).append(" ").append(SBUtils.getPhoneUrl()).append("?");
                    sbuf.append("IDs=PS0001").append("&");
                    sbuf.append("shipment_id=").append(response.getShipmentId()).append("&");
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
                        logger.info("Status  : "+result.getProperty(SBConstant.PRINT_PROPERY_STATUS));
                        logger.info("Message : "+result.getProperty(SBConstant.PRINT_PROPERY_MESSAGE));
                    }
                }
            } catch (UnknownHostException e) {
                result.put(SBConstant.PRINT_PROPERY_STATUS, SBConstant.PRINT_STATUS_UNKNOWN_HOST); 
                result.put(SBConstant.PRINT_PROPERY_MESSAGE, "UnknownHostException "+e.getMessage()); 
                logger.error(logPrefix+SBConstant.LOG_SEPRATOR+"UnknownHostException Occured :: printGiftNoteCard(String requestId, String ipAddress, String shipmentId, List<String> giftNoteCards)", e);
            } catch (IOException e) {
                result.put(SBConstant.PRINT_PROPERY_STATUS, SBConstant.PRINT_STATUS_IO_EXCEPTION); 
                result.put(SBConstant.PRINT_PROPERY_MESSAGE, "Couldn't get I/O for the connection to " + hostName +" - "+e.getMessage()); 
                logger.error(logPrefix+SBConstant.LOG_SEPRATOR+"IOException Occured :: printGiftNoteCard(String requestId, String ipAddress, String shipmentId, List<String> giftNoteCards)", e);
            } 
        } else {
            result.put(SBConstant.PRINT_PROPERY_STATUS, SBConstant.PRINT_STATUS_HOSTNAME_MISSING); 
            result.put(SBConstant.PRINT_PROPERY_MESSAGE, "Flex Print connection not established, hostname is missing."); 
        }
        logger.info(logPrefix+SBConstant.LOG_SEPRATOR+"printGiftNoteCard(String requestId, String ipAddress, String shipmentId, List<String> giftNoteCards)--------------- END");
        return result;
    }
}
