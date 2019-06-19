package com.seller.box.service.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.seller.box.jnlp.core.AllPermissions;
import com.seller.box.jnlp.core.ApplicationDesc;
import com.seller.box.jnlp.core.Icon;
import com.seller.box.jnlp.core.Information;
import com.seller.box.jnlp.core.J2se;
import com.seller.box.jnlp.core.Jar;
import com.seller.box.jnlp.core.JnlpRoot;
import com.seller.box.jnlp.core.Resources;
import com.seller.box.jnlp.core.Security;
import com.seller.box.jnlp.core.Update;
import com.seller.box.service.PackStationService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@Service
public class PackStationServiceImpl implements PackStationService {
	private static final Logger logger = LogManager.getLogger(PackStationServiceImpl.class);
	
	@Override
	public String downloadJnlp(String requestId, String warehouseCode, String psName, String token) {
		logger.info(requestId+SBConstant.LOG_SEPRATOR+"downloadPrinterJnlp(...)"+SBConstant.LOG_SEPRATOR_WITH_START);
        String phoneUrl = SBUtils.getPropertyValue("jnlp.phone.url");
        String jnlpString = null;
        if(psName == null || warehouseCode == null || phoneUrl == null || token == null) {
        	logger.error(requestId+SBConstant.LOG_SEPRATOR+"Required argumets not sufficient for pack station runnable file.");
        } else {
            try {
            	JnlpRoot jnlpRoot = new JnlpRoot();
				jnlpRoot.setSpec("1.0+");
				jnlpRoot.setCodebase(SBUtils.getPropertyValue("jnlp.codebase"));
				Information information = new Information();
				information.setTitle(SBUtils.getPropertyValue("jnlp.title"));
				information.setVendor(SBUtils.getPropertyValue("jnlp.vendor"));
				information.setDescription(SBUtils.getPropertyValue("jnlp.description"));
				Icon icon = new Icon();
				icon.setHref(SBUtils.getPropertyValue("jnlp.icon.href"));
				icon.setKind(SBUtils.getPropertyValue("jnlp.icon.kind"));
				information.setIcon(icon);
				jnlpRoot.setInformation(information);

				Security security = new Security();
				AllPermissions allpetrmissions = new AllPermissions();
				security.setAllpetrmissions(allpetrmissions);
				jnlpRoot.setSecurity(security);

				Resources resources = new Resources();
				J2se j2se = new J2se();
				j2se.setVersion(SBUtils.getPropertyValue("jnlp.j2se.version"));
				resources.setJ2se(j2se);
				Jar jar = new Jar();
				jar.setHref(SBUtils.getPropertyValue("jnlp.main.jar"));
				jar.setMain("true");
				List<Jar> jars = new ArrayList<Jar>();
				jars.add(jar);
				String[] jasrVals = SBUtils.getPropertyValue("jnlp.supporting.jars").split("#");
				for (String jarval : jasrVals) {
				    Jar jar1 = new Jar();
				    jar1.setHref(jarval);
				    jars.add(jar1);
				}
				resources.setJar(jars);
				jnlpRoot.setResources(resources);

				ApplicationDesc appdesc = new ApplicationDesc();
				appdesc.setMainClass(SBUtils.getPropertyValue("jnlp.appdesc.mainclass"));
				List<String> appArgs = new ArrayList<String>();
				appArgs.add(warehouseCode);
				appArgs.add(psName);
				appArgs.add(phoneUrl);
				appArgs.add(token);
				appdesc.setArgument(appArgs);
				jnlpRoot.setApplicationDesc(appdesc);

				Update update = new Update();
				update.setCheck("always");
				update.setPolicy("always");
				jnlpRoot.setUpdate(update);

				StringWriter sw = new StringWriter();
				try {
					JAXBContext context = JAXBContext.newInstance(JnlpRoot.class);
					Marshaller m = context.createMarshaller();
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

					m.marshal(jnlpRoot, sw);
					jnlpString = sw.toString();
				} catch (JAXBException e) {
					logger.error(requestId+SBConstant.LOG_SEPRATOR+"JAXBException :: downloadJnlpFile(...)", e);
				}
			} catch (Exception  e) {
				logger.error("JAXBException | IOException :: downloadPrinterJnlp(...)", e);
			}
        }
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"downloadPrinterJnlp(...)"+SBConstant.LOG_SEPRATOR_WITH_START);
		return jnlpString;
	}
	
}
