package com.fullstack.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IamportResponseWrapper {
    private IamportResponse response;

    public IamportResponse getResponse() {
        return response;
    }

    public void setResponse(IamportResponse response) {
        this.response = response;
    }
    
    
    private String code;
    private String message;

  

   
    @Override
    public String toString() {
        return "IamportResponseWrapper{" +
                "response=" + response +
                '}';
    }
}
