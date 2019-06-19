package com.seller.box.jnlp.core;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Resources {
    public Resources() {
        super();
    }
    @XmlElement (name = "j2se")
    J2se j2se;
    @XmlElement
    List<Jar> jar;

    public void setJ2se(J2se j2se) {
        this.j2se = j2se;
    }

    public void setJar(List<Jar> jar) {
        this.jar = jar;
    }
}
