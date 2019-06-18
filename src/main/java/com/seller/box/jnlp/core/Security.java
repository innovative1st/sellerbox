package com.seller.box.jnlp.core;

import javax.xml.bind.annotation.XmlElement;

public class Security {
    public Security() {
        super();
    }
    
    @XmlElement (name = "all-permissions")
    String allPermissions;
    @XmlElement(name = "all-permissions")
    AllPermissions allpetrmissions;
    
    public void setAllPermissions(String allPermissions) {
        this.allPermissions = allPermissions;
    }

    public void setAllpetrmissions(AllPermissions allpetrmissions) {
        this.allpetrmissions = allpetrmissions;
    }
}
