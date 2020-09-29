/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.services;

import gr.uaegean.contracts.VcRevocationRegistry;
import java.util.List;
import org.web3j.crypto.Credentials;

/**
 *
 * @author nikos
 */
public interface EthereumService {

    public Credentials getCredentials();

    public VcRevocationRegistry getRevocationContract();

    public boolean checkRevocationStatus(String uuid);

    public List<String> getAllRevockedCredentials();

}
