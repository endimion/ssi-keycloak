/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * Query for AMKA details by AMKA Check API
 *
 * @author nikos
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MinEduBirthCertResponse implements Serializable {

    private String code;

    @JsonProperty("ServiceCallID")
    private String ServiceCallID;
    private boolean success;
    @JsonProperty("Result")
    private Result Result;
    private String timestamp;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Result {

        @JsonProperty("Records")
        private RecordsElement[] records;
        private String timestamp;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class RecordsElement {

        @JsonProperty("NumberOfFamilyMembers")
        private String NumberOfFamilyMembers;
        @JsonProperty("Βεβαίωση Οικογενειακής Κατάστασης")
        private FamilyRecordsElement[] records;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class FamilyRecordsElement {

        private String marriageactno;
        @JsonProperty("FamilyShare")
        private String familyShare;
        private String firstname;
        private String birthdate;
        private String gender;
        private String spousemarriagerank;
        private String merida;
        private String membertype;
        private String mansdecentraladmin;
        private String marriageactro;
        @JsonProperty("birthmunicipalunit")
        private String birthmunicipalunit; //
        private String mansmunicipalityname;
        private String birthmunicipal;
        private String eklspecialno;
        private String motherfirstname;
        private String birthprefecture;
        private String surname;
        private String member;
        private String marriageactdate;
        private String birthcountry;
        private String reckind;
        private String mothersurname;
        private String mansrecordyear;
        private String maramunicipality;
        private String maramuniccomm;
        private String secondname;
        private String maracountry;
        private String mansreckind;
        private String marriagerank;
        private String fathersurname;
        private String mainnationality;
        private String spouseagreementrank;
        private String fatherfirstname;
        private String marriageactyear;
        private String mothergenos;
        private String mansrecordaa;
        private String marriageacttomos;
        private String municipalityname;
        private String maraprefecture;
        private String birthmuniccomm;
        private String gainmunrecdate;
        private String grnatgaindate;
        private String amka;

    }
}
