/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.resfull.bean;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author olivier.tatsinkou
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tag_attr")
public class WS_Tag_Attr { 
    private String attr_name;
    private String attr_value;

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public String getAttr_value() {
        return attr_value;
    }

    public void setAttr_value(String attr_value) {
        this.attr_value = attr_value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.attr_name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WS_Tag_Attr other = (WS_Tag_Attr) obj;
        if (!Objects.equals(this.attr_name, other.attr_name)) {
            return false;
        }
        return true;
    }
    
    
    

    @Override
    public String toString() {
        return "Tag_Attr{" + "attr_name=" + attr_name + ", attr_value=" + attr_value + '}';
    }
    
    
}
