/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.resfull.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author olivier.tatsinkou
 */
@XmlRootElement(name = "block_Request")
public class Ws_Block_Request {
    
    private String ws_block_name;
    

    private List<Request_log> listws_request= new ArrayList<Request_log>();

    public String getWs_block_name() {
        return ws_block_name;
    }

    public void setWs_block_name(String ws_block_name) {
        this.ws_block_name = ws_block_name;
    }

    public List<Request_log> getListws_request() {
        return listws_request;
    }

    public void setListws_request(List<Request_log> listws_request) {
        this.listws_request = listws_request;
    }
    
    
}
