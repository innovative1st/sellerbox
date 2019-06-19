package com.seller.box.jnlp.core;
import javax.xml.bind.annotation.XmlAttribute;

public class J2se {
    public J2se() {
        super();
    }
    
    @XmlAttribute
    String version;

    public void setVersion(String version) {
        this.version = version;
    }
}
