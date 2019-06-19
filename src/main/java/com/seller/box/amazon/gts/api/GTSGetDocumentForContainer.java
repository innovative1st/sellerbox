package com.seller.box.amazon.gts.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.GetDocumentForContainerRequest;
import com.amazonaws.services.gtsexternalsecurity.model.GetDocumentForContainerResult;
import com.amazonaws.services.gtsexternalsecurity.model.InvalidRequestException;
import com.amazonaws.services.gtsexternalsecurity.model.RecoverableException;
import com.amazonaws.services.gtsexternalsecurity.model.UnrecoverableException;
import com.seller.box.amazon.gts.GTSExternalServiceImpl;
import com.seller.box.amazon.gts.data.GetDocumentForContainerData;
import com.seller.box.form.Shipment;



public class GTSGetDocumentForContainer extends GTSService {
	private static final Logger logger = LogManager.getLogger(GTSExternalServiceImpl.class);
	private GetDocumentForContainerRequest request;
        
	public void buildGetDocumentForContainerReq(Shipment ps){
	    logger.info("buildGetDocumentForContainerReq()------------------------------- START : "+ ps.getShipmentId());
            request=GetDocumentForContainerData.buildRequest(ps);
            //System.out.println("GetDocumentForContainer Request>>>" + request.toString());
            logger.info("buildGetDocumentForContainerReq()------------------------------- END : "+ ps.getShipmentId());
	}
	
	public GetDocumentForContainerResult callgetDocumentForContainer() {
	    logger.info("callgetDocumentForContainer()------------------------------- START : ");
            try{
                GetDocumentForContainerResult result = gts.getDocumentForContainer(this.request);
                //Container document format
                result.getContainerDocument().getDocumentFormat();
                //Container document content
                result.getContainerDocument().getDocumentContent();
                //Container document type
                result.getContainerDocument().getDocumentType();
                
                // Client Ref Shipment Id from Client Ref Shipment Package Id
                result.getRequestIdentifiers().getClientRefShipmentPackageId().getClientRefShipmentId();
                // Client Ref Package Id from Client Ref Shipment Package Id
                result.getRequestIdentifiers().getClientRefShipmentPackageId().getClientRefPackageId();
                // Identifiers
                result.getRequestIdentifiers().getIdentifiers();
                
                return result;
            }catch(RecoverableException re){
                logger.error("*******RecoverableException occured*******",re);
                int retries = 0;
                boolean retry = false;
                do{
                    GTSGetDocumentForContainer result = new GTSGetDocumentForContainer();
                    if(!result.callgetDocumentForContainer().equals("")){
                        retry = false;
                    }else
                        retry = true;                        
                }while(retry && (retries++ < 3));
            }catch(UnrecoverableException ure){
                logger.error("*******UnrecoverableException occured*******",ure);
            }catch(InvalidRequestException ire){
                logger.error("*******UnrecoverableException occured*******",ire);
            }
        
	    logger.info("callgetDocumentForContainer()------------------------------- END : ");
                
/*		System.out.println("GetDocumentForContainer Result>>>" + result.toString());
		ContainerDocuments doc = result.getContainerDocument();
		System.out.println("Document Type = " + doc.getDocumentType());
		System.out.println("Document = " + doc.getDocumentContent());
		System.out.println("Document Format = " + doc.getDocumentFormat());
		if (doc.getDocumentContent() != null)
		{
			ByteBuffer CIStream = doc.getDocumentContent();
	        
			System.out.println("Document size"+ CIStream.array().length);
	        String CIString = DatatypeConverter.printBase64Binary(CIStream.array());

	        try {
		        FileWriter fw = new FileWriter("C:\\Users\\devanshu\\Documents\\CI.txt");
		        fw.write(CIString);
		        fw.close();
	        } catch(Exception ex) {
	        	System.out.println("Failed to write in file");
	        }
	        //System.out.println("CI = \n" +CIString);
		}
*/
             return null;
	}
   
}
