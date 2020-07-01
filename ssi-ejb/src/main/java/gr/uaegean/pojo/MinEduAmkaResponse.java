/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

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

    public static class AmkaResponse implements Serializable {

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
        public AmkaResponse() {
        }

        public AmkaResponse(String birthMunicipalityGreekCode, String birthDate, String fatherEN, String fatherGR, String tid, String birthCountry, String ssn, String birthCountryCode, String lastModDate, String surnameCurGr, String idType, String surnameCurEn, String surnameBirthGr, String deathNote, String citizenship, String sex, String surnameBirthEn, String match, String citizenshipCode, String bdateIsTrue, String birthMunicipality, String deathDate, String amkaCurrent, String motherEn, String motherGr, String idNum, String nameGr, String idIn, String idCreationYear, String nameEn) {
            this.birthMunicipalityGreekCode = birthMunicipalityGreekCode;
            this.birthDate = birthDate;
            this.fatherEN = fatherEN;
            this.fatherGR = fatherGR;
            this.tid = tid;
            this.birthCountry = birthCountry;
            this.ssn = ssn;
            this.birthCountryCode = birthCountryCode;
            this.lastModDate = lastModDate;
            this.surnameCurGr = surnameCurGr;
            this.idType = idType;
            this.surnameCurEn = surnameCurEn;
            this.surnameBirthGr = surnameBirthGr;
            this.deathNote = deathNote;
            this.citizenship = citizenship;
            this.sex = sex;
            this.surnameBirthEn = surnameBirthEn;
            this.match = match;
            this.citizenshipCode = citizenshipCode;
            this.bdateIsTrue = bdateIsTrue;
            this.birthMunicipality = birthMunicipality;
            this.deathDate = deathDate;
            this.amkaCurrent = amkaCurrent;
            this.motherEn = motherEn;
            this.motherGr = motherGr;
            this.idNum = idNum;
            this.nameGr = nameGr;
            this.idIn = idIn;
            this.idCreationYear = idCreationYear;
            this.nameEn = nameEn;
        }

        public String getBirthMunicipalityGreekCode() {
            return birthMunicipalityGreekCode;
        }

        public void setBirthMunicipalityGreekCode(String birthMunicipalityGreekCode) {
            this.birthMunicipalityGreekCode = birthMunicipalityGreekCode;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getFatherEN() {
            return fatherEN;
        }

        public void setFatherEN(String fatherEN) {
            this.fatherEN = fatherEN;
        }

        public String getFatherGR() {
            return fatherGR;
        }

        public void setFatherGR(String fatherGR) {
            this.fatherGR = fatherGR;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getBirthCountry() {
            return birthCountry;
        }

        public void setBirthCountry(String birthCountry) {
            this.birthCountry = birthCountry;
        }

        public String getSsn() {
            return ssn;
        }

        public void setSsn(String ssn) {
            this.ssn = ssn;
        }

        public String getBirthCountryCode() {
            return birthCountryCode;
        }

        public void setBirthCountryCode(String birthCountryCode) {
            this.birthCountryCode = birthCountryCode;
        }

        public String getLastModDate() {
            return lastModDate;
        }

        public void setLastModDate(String lastModDate) {
            this.lastModDate = lastModDate;
        }

        public String getSurnameCurGr() {
            return surnameCurGr;
        }

        public void setSurnameCurGr(String surnameCurGr) {
            this.surnameCurGr = surnameCurGr;
        }

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getSurnameCurEn() {
            return surnameCurEn;
        }

        public void setSurnameCurEn(String surnameCurEn) {
            this.surnameCurEn = surnameCurEn;
        }

        public String getSurnameBirthGr() {
            return surnameBirthGr;
        }

        public void setSurnameBirthGr(String surnameBirthGr) {
            this.surnameBirthGr = surnameBirthGr;
        }

        public String getDeathNote() {
            return deathNote;
        }

        public void setDeathNote(String deathNote) {
            this.deathNote = deathNote;
        }

        public String getCitizenship() {
            return citizenship;
        }

        public void setCitizenship(String citizenship) {
            this.citizenship = citizenship;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSurnameBirthEn() {
            return surnameBirthEn;
        }

        public void setSurnameBirthEn(String surnameBirthEn) {
            this.surnameBirthEn = surnameBirthEn;
        }

        public String getMatch() {
            return match;
        }

        public void setMatch(String match) {
            this.match = match;
        }

        public String getCitizenshipCode() {
            return citizenshipCode;
        }

        public void setCitizenshipCode(String citizenshipCode) {
            this.citizenshipCode = citizenshipCode;
        }

        public String getBdateIsTrue() {
            return bdateIsTrue;
        }

        public void setBdateIsTrue(String bdateIsTrue) {
            this.bdateIsTrue = bdateIsTrue;
        }

        public String getBirthMunicipality() {
            return birthMunicipality;
        }

        public void setBirthMunicipality(String birthMunicipality) {
            this.birthMunicipality = birthMunicipality;
        }

        public String getDeathDate() {
            return deathDate;
        }

        public void setDeathDate(String deathDate) {
            this.deathDate = deathDate;
        }

        public String getAmkaCurrent() {
            return amkaCurrent;
        }

        public void setAmkaCurrent(String amkaCurrent) {
            this.amkaCurrent = amkaCurrent;
        }

        public String getMotherEn() {
            return motherEn;
        }

        public void setMotherEn(String motherEn) {
            this.motherEn = motherEn;
        }

        public String getMotherGr() {
            return motherGr;
        }

        public void setMotherGr(String motherGr) {
            this.motherGr = motherGr;
        }

        public String getIdNum() {
            return idNum;
        }

        public void setIdNum(String idNum) {
            this.idNum = idNum;
        }

        public String getNameGr() {
            return nameGr;
        }

        public void setNameGr(String nameGr) {
            this.nameGr = nameGr;
        }

        public String getIdIn() {
            return idIn;
        }

        public void setIdIn(String idIn) {
            this.idIn = idIn;
        }

        public String getIdCreationYear() {
            return idCreationYear;
        }

        public void setIdCreationYear(String idCreationYear) {
            this.idCreationYear = idCreationYear;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Result{birthMunicipalityGreekCode=").append(birthMunicipalityGreekCode);
            sb.append(", birthDate=").append(birthDate);
            sb.append(", fatherEN=").append(fatherEN);
            sb.append(", fatherGR=").append(fatherGR);
            sb.append(", tid=").append(tid);
            sb.append(", birthCountry=").append(birthCountry);
            sb.append(", ssn=").append(ssn);
            sb.append(", birthCountryCode=").append(birthCountryCode);
            sb.append(", lastModDate=").append(lastModDate);
            sb.append(", surnameCurGr=").append(surnameCurGr);
            sb.append(", idType=").append(idType);
            sb.append(", surnameCurEn=").append(surnameCurEn);
            sb.append(", surnameBirthGr=").append(surnameBirthGr);
            sb.append(", deathNote=").append(deathNote);
            sb.append(", citizenship=").append(citizenship);
            sb.append(", sex=").append(sex);
            sb.append(", surnameBirthEn=").append(surnameBirthEn);
            sb.append(", match=").append(match);
            sb.append(", citizenshipCode=").append(citizenshipCode);
            sb.append(", bdateIsTrue=").append(bdateIsTrue);
            sb.append(", birthMunicipality=").append(birthMunicipality);
            sb.append(", deathDate=").append(deathDate);
            sb.append(", amkaCurrent=").append(amkaCurrent);
            sb.append(", motherEn=").append(motherEn);
            sb.append(", motherGr=").append(motherGr);
            sb.append(", idNum=").append(idNum);
            sb.append(", nameGr=").append(nameGr);
            sb.append(", idIn=").append(idIn);
            sb.append(", idCreationYear=").append(idCreationYear);
            sb.append(", nameEn=").append(nameEn);
            sb.append('}');
            return sb.toString();
        }

    }

}
