package com.seller.box.jnlp.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "jnlp")
public class JnlpRoot {
    public JnlpRoot() {
        super();
    }
    @XmlAttribute(name = "codebase")
    String codebase;
    @XmlAttribute(name = "spec")
    String spec;
  
    @XmlElement(name = "information")
    Information information;
    @XmlElement(name = "security")
    Security security;
    @XmlElement(name = "resources")
    Resources resources;
    @XmlElement(name = "application-desc")
    ApplicationDesc applicationDesc;
    @XmlElement(name = "update")
    Update update;
 
    public void setCodebase(String codebase) {
        this.codebase = codebase;
    }
    public void setSpec(String spec) {
        this.spec = spec;
    }
    public void setInformation(Information information) {
        this.information = information;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public void setApplicationDesc(ApplicationDesc applicationDesc) {
        this.applicationDesc = applicationDesc;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
