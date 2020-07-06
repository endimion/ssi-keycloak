/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@ToString
public class VerifiableCredential {

    /*
    {"did":"did:ethr:0xd4b90423e473e2b8d16b57d29abcf6c08ef8fd3a",
    "sealEidas":{"given_name":"ΑΝΔΡΕΑΣ, ANDREAS","family_name":"ΠΕΤΡΟΥ, PETROU","person_identifier":"GR/GR/ERMIS-11076669","date_of_birth":"1980-01-01","source":"sealEidas","loa":"http://eidas.europa.eu/LoA/low"},
    "verified":[{"iat":1576583514,"exp":1579175514,"sub":"did:ethr:0xd4b90423e473e2b8d16b57d29abcf6c08ef8fd3a","claim":{"sealEidas":{"given_name":"ΑΝΔΡΕΑΣ, ANDREAS","family_name":"ΠΕΤΡΟΥ, PETROU","person_identifier":"GR/GR/ERMIS-11076669","date_of_birth":"1980-01-01","source":"sealEidas","loa":"http://eidas.europa.eu/LoA/low"}},"vc":["/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ"],"iss":"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f","jwt":"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1NzY1ODM1MTQsImV4cCI6MTU3OTE3NTUxNCwic3ViIjoiZGlkOmV0aHI6MHhkNGI5MDQyM2U0NzNlMmI4ZDE2YjU3ZDI5YWJjZjZjMDhlZjhmZDNhIiwiY2xhaW0iOnsiZWlkYXMiOnsiZ2l2ZW5fbmFtZSI6Is6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTIiwiZmFtaWx5X25hbWUiOiLOoM6VzqTOoc6fzqUsIFBFVFJPVSIsInBlcnNvbl9pZGVudGlmaWVyIjoiR1IvR1IvRVJNSVMtMTEwNzY2NjkiLCJkYXRlX29mX2JpcnRoIjoiMTk4MC0wMS0wMSIsInNvdXJjZSI6ImVpZGFzIiwibG9hIjoiaHR0cDovL2VpZGFzLmV1cm9wYS5ldS9Mb0EvbG93In19LCJ2YyI6WyIvaXBmcy9RbU5iaWNLWVFLQ3NjN0dNWFNTSk1wdkpTWWdlUTlLMnRIMTVFbmJ4VHlkeGZRIl0sImlzcyI6ImRpZDpldGhyOjB4ZDUwMmEyYzcxZThjOTBlODI1MDBhNzA2ODNmNzVkZTM4ZDU3ZGQ5ZiJ9.0pijmaUsz-UfCnGoeM4foUauMl1chR5jMdBhwdjxc6-0X2fEBmJ3r_urGjAB0ZoKPWygevaKvXNk87H7_jtrjwA"}],"invalid":[]}

     */
    private String did;

    @JsonProperty("TAXIS")
    private TaxisRoute taxisRoute;

    @JsonProperty("SEAL-EIDAS")
    private SealEidas sealEidas;

    @JsonProperty("AMKA")
    private AMKA amka;

    @JsonProperty("MITRO")
    private MITRO mitro;

    @JsonProperty("SEAL-isErasmusAegean")
    private isErasmus erasmus;

    @JsonProperty("SELF")
    private SelfRoute self;

    @JsonProperty("E1")
    private E1Route e1;

    @JsonProperty("EBILL")
    private EBillRoute ebill;

    @JsonProperty("CONTACT-DETAILS")
    private ContactRoute contact;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class ContactRoute {

        @JsonProperty("contact")
        private ContactClaim contact;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class EBillRoute {

        @JsonProperty("ebill")
        private EBillClaim ebill;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class E1Route {

        @JsonProperty("E1")
        private E1Claim e1;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class TaxisRoute {

        @JsonProperty("TAXIS")
        private TaxisClaim taxis;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class SelfRoute {

        private SelfClaim self;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class SealEidas {

        private EidasClaim eidas;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class AMKA {

        @JsonProperty("AMKA")
        private AMKAClaim amka;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class isErasmus {

        @JsonProperty("eidas")
        private isErasmusClaim mitro;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class MITRO {

        @JsonProperty("MITRO")
        private MITROClaim mitro;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class ContactClaim {

        String email;
        String landline;
        String iban;
        String mobilePhone;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class EBillClaim {

        String ownership;
        String supplyType;
        String meterNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class E1Claim {

        String salaries;
        String pensionIncome;
        String farmingActivity;
        String freelanceActivity;
        String rentIncome;
        String unemploymentBenefit;
        String otherBenefitsIncome;
        String ekas;
        String additionalIncomes;
        String ergome;
        String depositInterest;
        String deposits;
        String valueOfRealEstate;
        String valueOfRealEstateInOtherCountries;
        String valueOfOwnedVehicles;
        String investments;
        String householdComposition;
        String name;
        String surname;
        String dateOfBirth;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class SelfClaim {

        String oaedid;
        String oaedDate;
        String hospitalized;
        String hospitalizedSpecific;
        String monk;
        String luxury;
        String none;
        String employed;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class TaxisClaim {

        @JsonProperty("afm")
        private String afm;
        @JsonProperty("amka")
        private String amka;
        @JsonProperty("lastName")
        private String lastName;
        @JsonProperty("fistName")
        private String fistName;
        @JsonProperty("fathersName")
        private String fathersName;
        @JsonProperty("mothersName")
        private String mothersName;
        @JsonProperty("loa")
        private String loa;
        @JsonProperty("source")
        private String source;
        @JsonProperty("dateOfBirth")
        private String dateOfBirth;

        @JsonProperty("lastNameLatin")
        private String lastNameLatin;
        @JsonProperty("firstNameLatin")
        private String firstNameLatin;
        @JsonProperty("fathersNameLatin")
        private String fathersNameLatin;
        @JsonProperty("mothersNameLatin")
        private String mothersNameLatin;
        private String gender;
        private String nationality;

        @JsonProperty("householdComposition")
        private HouseholdComposition[] household;

        @JsonProperty("address")
        private Address address;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class HouseholdComposition {

        private String name;
        private String relation;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Address {

        private String street;
        private String streetNumber;
        @JsonProperty("PO")
        private String po;
        private String municipality;
        private String prefecture;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class AMKAClaim {

        private String latinLastName;
        private String birthDate;
        private String latinFirstName;
        private String father;
        private String mother;
        private String loa;
        private String source;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class EidasClaim {

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
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class MITROClaim {

        private String gender;
        private String nationality;
        private String maritalStatus;
        private String loa;
        private String source;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class isErasmusClaim {

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
        private String expires;
        private String affiliation;
        private String hostingInstitution;
    }

}
