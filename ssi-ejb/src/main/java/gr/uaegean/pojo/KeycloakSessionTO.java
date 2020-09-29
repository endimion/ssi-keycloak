/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author nikos
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KeycloakSessionTO implements Serializable {

    /*
     redirectUri.addParam(OAuth2Constants.RESPONSE_TYPE, mcc.get("response_typ").toString());
        redirectUri.addParam(OAuth2Constants.CLIENT_ID, mcc.get("cliend_it").toString());
        redirectUri.addParam(OAuth2Constants.REDIRECT_URI, clientRedirectUri);
        redirectUri.addParam(OAuth2Constants.STATE, mcc.get("state").toString());
        redirectUri.addParam(OAuth2Constants.SCOPE, mcc.gets("scope").toString());
     */
    private String session;
    private String responseType;
    private String clientId;
    private String clientRedirectUri;
    private String state;
    private String scope;
    private String realm;
    private String nonce;

}
