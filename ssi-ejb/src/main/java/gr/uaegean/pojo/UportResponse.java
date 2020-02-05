/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author nikos
 */
public class UportResponse {

    @JsonProperty("access_token")
    String accesToken;

    public UportResponse(String accesToken) {
        this.accesToken = accesToken;
    }

    public UportResponse() {
    }

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    @Override
    public String toString() {
        return "UportResponse{" + "accesToken=" + accesToken + '}';
    }

}
