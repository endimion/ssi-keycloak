/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.services.impl;

import gr.uaegean.contracts.VcRevocationRegistry;
import gr.uaegean.services.EthereumService;
import gr.uaegean.utils.ByteConverters;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

/**
 *
 * @author nikos
 */
@Service
@Slf4j
public class EthereumServiceImpl implements EthereumService {

    private Web3j web3;
    private String mnemonic = "heavy peace decline bean recall budget trigger video era trash also unveil";
    private Credentials credentials;
    private VcRevocationRegistry revocationContract;
    private final String REVOCATION_CONTRACT_ADDRESS;
    private final TransactionManager txManager;

    public EthereumServiceImpl() {
        this.web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/691797f6957f45e7944535265a9c13a6"));
        String password = null; // no encryption
        this.mnemonic = "heavy peace decline bean recall budget trigger video era trash also unveil";
        // Derivation path wanted: // m/44'/60'/0'/0 (this is used in ethereum, in
        // bitcoin it is different
        int[] derivationPath = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT,
            0 | Bip32ECKeyPair.HARDENED_BIT, 0, 0};
        // Generate a BIP32 master keypair from the mnemonic phrase
        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(MnemonicUtils.generateSeed(mnemonic, password));
        // Derived the key using the derivation path
        Bip32ECKeyPair derivedKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath);
        // Load the wallet for the derived key
        this.credentials = Credentials.create(derivedKeyPair);
        this.REVOCATION_CONTRACT_ADDRESS = StringUtils.isEmpty(System.getenv("REVOCATION_CONTRACT_ADDRESS"))
                ? "0x685C453587dcDB36B93D70f0854B408DBD898FcC"
                : System.getenv("REVOCATION_CONTRACT_ADDRESS");
        txManager = new FastRawTransactionManager(web3, credentials);
    }

    @Override
    public Credentials getCredentials() {
        if (this.credentials == null) {
            String ethNodeUrl = StringUtils.isEmpty(System.getenv("ETH_NODE")) ? "https://ropsten.infura.io/v3/691797f6957f45e7944535265a9c13a6" : System.getenv("ETH_NODE");
            String theMnemonic = StringUtils.isEmpty(System.getenv("MNEMONIC")) ? "heavy peace decline bean recall budget trigger video era trash also unveil" : System.getenv("MNEMONIC");

            this.web3 = Web3j.build(new HttpService(ethNodeUrl));
            String password = null; // no encryption
            this.mnemonic = theMnemonic;
            // Derivation path wanted: // m/44'/60'/0'/0 (this is used in ethereum, in
            // bitcoin it is different
            int[] derivationPath = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT,
                0 | Bip32ECKeyPair.HARDENED_BIT, 0, 0};
            // Generate a BIP32 master keypair from the mnemonic phrase
            Bip32ECKeyPair masterKeypair = Bip32ECKeyPair
                    .generateKeyPair(MnemonicUtils.generateSeed(mnemonic, password));
            // Derived the key using the derivation path
            Bip32ECKeyPair derivedKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath);
            // Load the wallet for the derived key
            this.credentials = Credentials.create(derivedKeyPair);
        }

        return this.credentials;
    }

    @Override
    public VcRevocationRegistry getRevocationContract() {
        if (this.revocationContract == null) {
            this.revocationContract = VcRevocationRegistry.load(this.REVOCATION_CONTRACT_ADDRESS, this.web3,
                    this.credentials, new DefaultGasProvider());
        }
        return this.revocationContract;
    }

    @Override
    public boolean checkRevocationStatus(String uuid) {
        try {
            byte[] theUuid = ByteConverters.stringToBytes32(uuid).getValue();
            Boolean result = this.getRevocationContract().isRevoked(theUuid).sendAsync().get();
            return result.booleanValue();
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        } catch (ExecutionException ex) {
            log.error(ex.getMessage());
        }
        log.info("checking of teh revocation status failed for {}", uuid);
        return false;
    }

    @Override
    public List<String> getAllRevockedCredentials() {
        List<String> result = new ArrayList();
        try {
            this.getRevocationContract().getAllCases().sendAsync().get().stream().forEach(byteArray -> {
                result.add(ByteConverters.bytes32ToString((byte[]) byteArray));
            });
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        } catch (ExecutionException ex) {
            log.error(ex.getMessage());
        }
        return result;
    }

}
