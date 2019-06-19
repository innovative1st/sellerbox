package com.seller.box.jnlp.core;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ApplicationDesc {
    public ApplicationDesc() {
        super();
    }
    @XmlAttribute(name = "main-class")
    String mainClass;
    @XmlElement (name = "argument")
    List<String> argument;

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public void setArgument(List<String> argument) {
        this.argument = argument;
    }
}
