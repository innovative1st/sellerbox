package com.seller.box.jnlp.core;

import javax.xml.bind.annotation.XmlAttribute;

public class Icon {
    public Icon() {
        super();
    } 
    @XmlAttribute
    String kind;
    @XmlAttribute
    String href;

    public void setKind(String kind) {
        this.kind = kind;
    }
    public void setHref(String href) {
        this.href = href;
    }
  
}
