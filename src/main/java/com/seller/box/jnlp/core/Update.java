package com.seller.box.jnlp.core;

import javax.xml.bind.annotation.XmlAttribute;

public class Update {
    public Update() {
        super();
    }
    @XmlAttribute(name = "check")
    String check;
    @XmlAttribute(name = "policy")
    String policy;

    public void setCheck(String check) {
        this.check = check;
    }
    public void setPolicy(String policy) {
        this.policy = policy;
    }
}
