/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.resfull.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author olivier.tatsinkou
 */
@XmlRootElement(name = "Block_Response")
public class WS_Block_Response implements Serializable{
    
  private String ws_block_name;
    private Long fee_charge;
    private List<WS_Response> listws_response = new ArrayList<WS_Response>();

    public String getWs_block_name() {
        return ws_block_name;
    }

    public void setWs_block_name(String ws_block_name) {
        this.ws_block_name = ws_block_name;
    }

    public Long getFee_charge() {
        return fee_charge;
    }

    public void setFee_charge(Long fee_charge) {
        this.fee_charge = fee_charge;
    }

    public List<WS_Response> getListws_response() {
        return listws_response;
    }

    public void setListws_response(List<WS_Response> listws_response) {
        this.listws_response = listws_response;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.ws_block_name);
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
        final WS_Block_Response other = (WS_Block_Response) obj;
        if (!Objects.equals(this.ws_block_name, other.ws_block_name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WS_Block_Response{" + "ws_block_name=" + ws_block_name + '}';
    }

}
