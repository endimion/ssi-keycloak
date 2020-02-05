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
public class VerifiableCredential {

    /*
    {"did":"did:ethr:0xd4b90423e473e2b8d16b57d29abcf6c08ef8fd3a",
    "eidas":{"given_name":"ΑΝΔΡΕΑΣ, ANDREAS","family_name":"ΠΕΤΡΟΥ, PETROU","person_identifier":"GR/GR/ERMIS-11076669","date_of_birth":"1980-01-01","source":"eidas","loa":"http://eidas.europa.eu/LoA/low"},
    "verified":[{"iat":1576583514,"exp":1579175514,"sub":"did:ethr:0xd4b90423e473e2b8d16b57d29abcf6c08ef8fd3a","claim":{"eidas":{"given_name":"ΑΝΔΡΕΑΣ, ANDREAS","family_name":"ΠΕΤΡΟΥ, PETROU","person_identifier":"GR/GR/ERMIS-11076669","date_of_birth":"1980-01-01","source":"eidas","loa":"http://eidas.europa.eu/LoA/low"}},"vc":["/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ"],"iss":"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f","jwt":"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1NzY1ODM1MTQsImV4cCI6MTU3OTE3NTUxNCwic3ViIjoiZGlkOmV0aHI6MHhkNGI5MDQyM2U0NzNlMmI4ZDE2YjU3ZDI5YWJjZjZjMDhlZjhmZDNhIiwiY2xhaW0iOnsiZWlkYXMiOnsiZ2l2ZW5fbmFtZSI6Is6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTIiwiZmFtaWx5X25hbWUiOiLOoM6VzqTOoc6fzqUsIFBFVFJPVSIsInBlcnNvbl9pZGVudGlmaWVyIjoiR1IvR1IvRVJNSVMtMTEwNzY2NjkiLCJkYXRlX29mX2JpcnRoIjoiMTk4MC0wMS0wMSIsInNvdXJjZSI6ImVpZGFzIiwibG9hIjoiaHR0cDovL2VpZGFzLmV1cm9wYS5ldS9Mb0EvbG93In19LCJ2YyI6WyIvaXBmcy9RbU5iaWNLWVFLQ3NjN0dNWFNTSk1wdkpTWWdlUTlLMnRIMTVFbmJ4VHlkeGZRIl0sImlzcyI6ImRpZDpldGhyOjB4ZDUwMmEyYzcxZThjOTBlODI1MDBhNzA2ODNmNzVkZTM4ZDU3ZGQ5ZiJ9.0pijmaUsz-UfCnGoeM4foUauMl1chR5jMdBhwdjxc6-0X2fEBmJ3r_urGjAB0ZoKPWygevaKvXNk87H7_jtrjwA"}],"invalid":[]}
     */
    private String did;
    private EidasClaim eidas;

    public VerifiableCredential(String did, EidasClaim eidas) {
        this.did = did;
        this.eidas = eidas;
    }

    public VerifiableCredential() {
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public EidasClaim getEidas() {
        return eidas;
    }

    public void setEidas(EidasClaim eidas) {
        this.eidas = eidas;
    }

    public class EidasClaim {

        @JsonProperty("given_name")
        private String givenName;
        @JsonProperty("family_name")
        private String familyName;
        @JsonProperty("person_identifier")
        private String personIdentifier;
        @JsonProperty("date_of_birth")
        private String dateOfBirth;
        private String source;
        private String loa;

        public EidasClaim(String givenName, String familyName, String personIdentifier, String dateOfBirth, String source, String loa) {
            this.givenName = givenName;
            this.familyName = familyName;
            this.personIdentifier = personIdentifier;
            this.dateOfBirth = dateOfBirth;
            this.source = source;
            this.loa = loa;
        }

        public EidasClaim() {
        }

        public String getGivenName() {
            return givenName;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public String getPersonIdentifier() {
            return personIdentifier;
        }

        public void setPersonIdentifier(String personIdentifier) {
            this.personIdentifier = personIdentifier;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLoa() {
            return loa;
        }

        public void setLoa(String loa) {
            this.loa = loa;
        }

    }

}
