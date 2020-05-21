/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author olivier.tatsinkou
 */
@Root(name="tag_attr")
public class Tag_Attr {
    @Element(name="attr_name",required = true) 
    private String attr_name;
    
    @Element(name="attr_value",required = true) 
    private String attr_value;

    public Tag_Attr(@Element(name="attr_name",required = true) String attr_name, 
                    @Element(name="attr_value",required = true)String attr_value) {
        this.attr_name = attr_name;
        this.attr_value = attr_value;
    }

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
    public String toString() {
        return "Tag_Attr{" + "attr_name=" + attr_name + ", attr_value=" + attr_value + '}';
    }
    
    
}
