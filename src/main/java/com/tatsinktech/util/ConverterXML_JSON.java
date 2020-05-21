/*
 * Copyright 2019 olivier.tatsinkou.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tatsinktech.util;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tatsinktech.beans.DeliveryMessage;
import com.tatsinktech.beans.Message_Exchg;
import com.tatsinktech.beans.Process_Request;
import com.tatsinktechnologic.resfull.bean.Credential;
import com.tatsinktechnologic.resfull.bean.WS_Block_Response;
import com.tatsinktechnologic.resfull.bean.WS_Request;
import com.tatsinktechnologic.resfull.bean.WS_Response;
import com.tatsinktechnologic.resfull.bean.Ws_Block_Request;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;

/**
 *
 * @author olivier.tatsinkou
 */
public class ConverterXML_JSON {
    
    
    public static String convertMsgExchToJson( Message_Exchg msg_exch) {
        ObjectMapper mapper = new ObjectMapper();

        String result = null;

        try {
                // Convert object to JSON string and pretty print
                result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msg_exch);

        } catch (JsonGenerationException e) {
                e.printStackTrace();
        } catch (JsonMappingException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return result;
   }

    
  public static Message_Exchg convertJsonToMsgExch(String msg_exch) {
        ObjectMapper mapper = new ObjectMapper();

        Message_Exchg result = null;

        try {
                // Convert JSON string to Object
                result = mapper.readValue(msg_exch, Message_Exchg.class);
                
        } catch (JsonGenerationException e) {
                e.printStackTrace();
        } catch (JsonMappingException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        return result;
   }

     public static String convertDeliveryToJson( DeliveryMessage msg_exch) {
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        try {
                // Convert object to JSON string and pretty print
                result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msg_exch);

        } catch (JsonGenerationException e) {
                e.printStackTrace();
        } catch (JsonMappingException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return result;
   }
     
 
   public static DeliveryMessage convertJsonToDelivery(String msg_exch) {
        ObjectMapper mapper = new ObjectMapper();
        DeliveryMessage result = null;
        try {
                // Convert JSON string to Object
                result = mapper.readValue(msg_exch, DeliveryMessage.class);
                
        } catch (JsonGenerationException e) {
                e.printStackTrace();
        } catch (JsonMappingException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        return result;
   }
   
   
   
    public static String convertProcess_rqToJson( Process_Request msg_exch) {
        ObjectMapper mapper = new ObjectMapper();

        String result = null;

        try {
                // Convert object to JSON string and pretty print
                result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msg_exch);

        } catch (JsonGenerationException e) {
                e.printStackTrace();
        } catch (JsonMappingException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return result;
   }

    
  public static Process_Request convertJsonToProcess_rq(String msg_exch) {
        ObjectMapper mapper = new ObjectMapper();

        Process_Request result = null;

        try {
                // Convert JSON string to Object
                result = mapper.readValue(msg_exch, Process_Request.class);
                
        } catch (JsonGenerationException e) {
                e.printStackTrace();
        } catch (JsonMappingException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        return result;
   }

    
    public static String convertWS_ResponseToXML(WS_Response request) {
     String result = null;
     try{
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(WS_Response.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(request, sw);
             
            //Verify XML Content
            //Verify XML Content
            String stringResp = sw.toString();
            stringResp = stringResp.replace("&lt;", "<");
            stringResp = stringResp.replace("&gt;", ">");
            result = stringResp;

 
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
  
    
      public static String convertWS_RequestToXML(WS_Request request) {
        String result = null;
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(WS_Request.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(request, sw);

            //Verify XML Content
            String stringResp = sw.toString();
            stringResp = stringResp.replace("&lt;", "<");
            stringResp = stringResp.replace("&gt;", ">");
            result = stringResp;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertCredentialToXML(Credential response) {
        String result = null;
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Credential.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(response, sw);

            //Verify XML Content
            String stringResp = sw.toString();
            stringResp = stringResp.replace("&lt;", "<");
            stringResp = stringResp.replace("&gt;", ">");
            result = stringResp;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertWS_Block_ResponseToXML(WS_Block_Response request) {
        String result = null;
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(WS_Block_Response.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(request, sw);

            //Verify XML Content
            String stringResp = sw.toString();
            stringResp = stringResp.replace("&lt;", "<");
            stringResp = stringResp.replace("&gt;", ">");
            result = stringResp;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertWs_Block_RequesttoXML(Ws_Block_Request request) {
        String result = null;
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Ws_Block_Request.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(request, sw);

            //Verify XML Content
            String stringResp = sw.toString();
            stringResp = stringResp.replace("&lt;", "<");
            stringResp = stringResp.replace("&gt;", ">");
            result = stringResp;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, String> convertMultiToRegularMap(MultivaluedMap<String, String> m) {
        Map<String, String> map = new HashMap<String, String>();
        if (m == null) {
            return map;
        }
        for (Map.Entry<String, List<String>> entry : m.entrySet()) {
            StringBuilder sb = new StringBuilder();
            for (String s : entry.getValue()) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(s);
            }
            map.put(entry.getKey(), sb.toString());
        }
        return map;
    }
    
    public static Map<String, String> getSoap_Header1(MimeHeaders hdrs) {

        Map<String, String> headers = new HashMap<String, String>();
        Iterator i = hdrs.getAllHeaders();

        while (i.hasNext()) {
            MimeHeader header = (MimeHeader) i.next();
            if (header != null) {
                headers.put(header.getName(), header.getValue());
            }
        }

        return headers;
    }

   

}
