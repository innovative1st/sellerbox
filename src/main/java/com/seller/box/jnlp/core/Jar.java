package com.seller.box.jnlp.core;

import javax.xml.bind.annotation.XmlAttribute;

public class Jar {
    public Jar() {
        super();
    }

    @XmlAttribute
    String main;
    @XmlAttribute
    String href;


    public void setMain(String main) {
        this.main = main;
    }
    public void setHref(String href) {
        this.href = href;
    }
}
