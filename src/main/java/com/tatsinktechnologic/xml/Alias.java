/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.xml;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author olivier.tatsinkou
 */
@Root(name="alias")
public class Alias {
    
    @Element(name="msisdn",required = true) 
    private String msisdn;
    
    @Element(name="fee",required = true) 
    private String fee;
    
    @Element(name="description",required = true) 
    private String description;
    
    public Alias(@Element(name="msisdn",required = true) String msisdn,
               @Element(name="fee",required = true) String fee,
               @Element(name="description",required = true) String description) {
        this.msisdn = msisdn;
        this.fee=fee;
        this.description = description;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getFee() {
        return fee;
    }

    public String getDescription() {
        return description;
    }

   
}
