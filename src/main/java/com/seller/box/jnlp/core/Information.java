package com.seller.box.jnlp.core;

import javax.xml.bind.annotation.XmlElement;

public class Information {
    public Information() {
        super();
    }
    @XmlElement
    String title;
    @XmlElement
    String vendor;
    @XmlElement
    String description;
    @XmlElement(name ="icon")
    Icon icon;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
