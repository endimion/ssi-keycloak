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
public class MinEduAmkaResponse implements Serializable {

    private String code;

    @JsonProperty("ServiceCallID")
    private String ServiceCallID;
    private boolean success;
    @JsonProperty("Result")
    private AmkaResponse Result;
    private String timestamp;

    public MinEduAmkaResponse() {
    }

    public MinEduAmkaResponse(AmkaResponse Result, String ServiceCallID, String code, boolean success, String timestamp) {
        this.Result = Result;
        this.ServiceCallID = ServiceCallID;
        this.code = code;
        this.success = success;
        this.timestamp = timestamp;
    }

    public AmkaResponse getResult() {
        return Result;
    }

    public void setResult(AmkaResponse Result) {
        this.Result = Result;
    }

    public String getServiceCallID() {
        return ServiceCallID;
    }

    public void setServiceCallID(String ServiceCallID) {
        this.ServiceCallID = ServiceCallID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MinEduResponse{" + "code=" + code + ", ServiceCallID=" + ServiceCallID + ", success=" + success + ", Result=" + Result.toString() + ", timestamp=" + timestamp + '}';
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class AmkaResponse implements Serializable {

        /*
        {"gender":"Άρρεν","nationality":"Ελληνική",
        "singleParent":"false",
        "maritalStatus":"married",
        "motherLatin":"ANGELIKI",
        "fatherLatin":"ANASTASIOS",
        "nameLatin":"NIKOLAOS",
        "surnameLatin":"TRIANTAFYLLOU",
        "birthdate":"05/10/1983",
        "amka":"05108304675",
        "loa":"low","source":"MITRO"}}}
         */
        @JsonProperty("birth_municipality_greek_code")
        private String birthMunicipalityGreekCode;
        @JsonProperty("birth_date")
        private String birthDate;
        @JsonProperty("father_en")
        private String fatherEN;
        @JsonProperty("father_gr")
        private String fatherGR;
        private String tid;
        @JsonProperty("birth_country")
        private String birthCountry;
        private String ssn;
        @JsonProperty("birth_country_code")
        private String birthCountryCode;
        @JsonProperty("last_mod_date")
        private String lastModDate;
        @JsonProperty("surname_cur_gr")
        private String surnameCurGr;
        @JsonProperty("id_type")
        private String idType;
        @JsonProperty("surname_cur_en")
        private String surnameCurEn;
        @JsonProperty("surname_birth_gr")
        private String surnameBirthGr;
        @JsonProperty("death_note")
        private String deathNote;
        private String citizenship;
        private String sex;
        @JsonProperty("surname_birth_en")
        private String surnameBirthEn;
        private String match;
        @JsonProperty("citizenship_code")
        private String citizenshipCode;
        @JsonProperty("bdate_istrue")
        private String bdateIsTrue;
        @JsonProperty("birth_municipality")
        private String birthMunicipality;
        @JsonProperty("death_date")
        private String deathDate;
        @JsonProperty("amka_cur")
        private String amkaCurrent;
        @JsonProperty("mother_en")
        private String motherEn;
        @JsonProperty("mother_gr")
        private String motherGr;
        @JsonProperty("id_num")
        private String idNum;
        @JsonProperty("name_gr")
        private String nameGr;
        @JsonProperty("id_in")
        private String idIn;
        @JsonProperty("id_creation_year")
        private String idCreationYear;
        @JsonProperty("name_en")
        private String nameEn;

        /*
    "birth_municipality_greek_code": "ΑΤΤΙ",
    "birth_date": "",
    "father_en": "",
    "father_gr": "",
    "tid": "",
    "birth_country": "ΕΛΛΑΔΑ",
    "ssn": "",
    "birth_country_code": "",
    "last_mod_date": "", //last modification date id
    "surname_cur_gr": "ΓΕΩΡΓΑΚΟΠΟΥΛΟΣ",
    "id_type": "Τ", //Τύπος ταυτότητας
    "surname_cur_en": "GEORGAKOPOULOS",
    "surname_birth_gr": "ΓΕΩΡΓΑΚΟΠΟΥΛΟΣ",
    "death_note": "",
    "citizenship": "ΕΛΛΑΔΑ",
    "sex": "ΑΡΡΕΝ",
    "surname_birth_en": "GEORGAKOPOULOS",
    "match": "true",
    "citizenship_code": "GR",
    "bdate_istrue": "",
    "birth_municipality": "ΧΟΛΑΡΓΟΥ",
    "death_date": "",
    "amka_cur": "",
    "mother_en": "",
    "mother_gr": "",
    "id_num": "", //arithmos tautotitas
    "name_gr": "ΠΑΝΑΓΙΩΤΗΣ",
    "id_in": "",
    "id_creation_year": "2009", //etos teleutaias ekdosis tautotitas
    "name_en": "PANAGIOTIS"
         */
    }

}
