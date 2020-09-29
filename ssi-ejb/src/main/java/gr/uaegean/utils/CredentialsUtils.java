/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.utils;

import gr.uaegean.pojo.VerifiableCredential;
import gr.uaegean.pojo.VerifiableCredential.VerifiedClaims;
import gr.uaegean.services.EthereumService;
import gr.uaegean.services.impl.EthereumServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author nikos
 */
@Slf4j
public class CredentialsUtils {

    private static EthereumService ETH_SERV = new EthereumServiceImpl();

    public static VerifiedClaims getClaimsFromVerifiedArray(VerifiableCredential credential) {
        VerifiedClaims vc = credential.getVerified()[0].getClaims();
        boolean result = ETH_SERV.checkRevocationStatus(vc.getId());
        log.info("Revocation List result {} !!!!!!!!", result);
        return credential.getVerified()[0].getClaims();
    }

}
