package com.seller.box.amazon.gts.api;


import java.util.Date;

import org.apache.log4j.Logger;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.gtsexternalsecurity.GTSExternalSecurityService;
import com.amazonaws.services.gtsexternalsecurity.GTSExternalSecurityServiceClient;
import com.seller.box.utils.SBUtils;


public class GTSService {
    protected GTSExternalSecurityService gts;
    //private static final Logger logger = LogManager.getLogger(GTSService.class);
    private static final Logger logger = Logger.getLogger(GTSService.class);
    public GTSService() {
        logger.info("GTSService Start : " + new Date());
        try {
			String AWSAccessKey = SBUtils.getPropertyValue("AWSAccessKey");
			String AWSSecretKey = SBUtils.getPropertyValue("AWSSecretKey");
			String GTSEndpointURL = SBUtils.getPropertyValue("GTSEndpointURL");
			AWSCredentials awsCredentials = new BasicAWSCredentials(AWSAccessKey, AWSSecretKey);
			ClientConfiguration clientConfiguration = new ClientConfiguration();
			GTSExternalSecurityService gts = new GTSExternalSecurityServiceClient(awsCredentials, clientConfiguration);
			gts.setEndpoint(GTSEndpointURL);
			this.gts = gts;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
        logger.info("GTSService End : " + new Date());
    }

}
