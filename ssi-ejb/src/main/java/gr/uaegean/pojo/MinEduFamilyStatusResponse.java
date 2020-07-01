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
public class MinEduFamilyStatusResponse implements Serializable {

    private String code;

    @JsonProperty("ServiceCallID")
    private String ServiceCallID;
    private boolean success;
    @JsonProperty("Result")
    private Result Result;
    private String timestamp;

    public MinEduFamilyStatusResponse() {
    }

    public MinEduFamilyStatusResponse(Result Result, String ServiceCallID, String code, boolean success, String timestamp) {
        this.Result = Result;
        this.ServiceCallID = ServiceCallID;
        this.code = code;
        this.success = success;
        this.timestamp = timestamp;
    }

    public Result getResult() {
        return Result;
    }

    public void setResult(Result Result) {
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

    public static class Result {

        @JsonProperty("Records")
        private RecordsElement[] records;
        private String timestamp;

        public Result() {
        }

        public Result(RecordsElement[] records, String timestamp) {
            this.records = records;
            this.timestamp = timestamp;
        }

        public RecordsElement[] getRecords() {
            return records;
        }

        public void setRecords(RecordsElement[] records) {
            this.records = records;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Result{records=").append(records);
            sb.append(", timestamp=").append(timestamp);
            sb.append('}');
            return sb.toString();
        }

    }

    public static class RecordsElement {

        @JsonProperty("NumberOfFamilyMembers")
        private String NumberOfFamilyMembers;
        @JsonProperty("Βεβαίωση Οικογενειακής Κατάστασης")
        private FamilyRecordsElement[] records;

        public RecordsElement() {
        }

        public RecordsElement(String NumberOfFamilyMembers, FamilyRecordsElement[] records) {
            this.NumberOfFamilyMembers = NumberOfFamilyMembers;
            this.records = records;
        }

        public String getNumberOfFamilyMembers() {
            return NumberOfFamilyMembers;
        }

        public void setNumberOfFamilyMembers(String NumberOfFamilyMembers) {
            this.NumberOfFamilyMembers = NumberOfFamilyMembers;
        }

        public FamilyRecordsElement[] getRecords() {
            return records;
        }

        public void setRecords(FamilyRecordsElement[] records) {
            this.records = records;
        }

    }

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

        public FamilyRecordsElement() {
        }

        public FamilyRecordsElement(String marriageactno, String familyShare, String firstname, String birthdate, String gender, String spousemarriagerank, String merida, String membertype, String mansdecentraladmin, String marriageactro, String birthmunicipalunit, String mansmunicipalityname, String birthmunicipal, String eklspecialno, String motherfirstname, String birthprefecture, String surname, String member, String marriageactdate, String birthcountry, String reckind, String mothersurname, String mansrecordyear, String maramunicipality, String maramuniccomm, String maracountry, String mansreckind, String marriagerank, String fathersurname, String mainnationality, String spouseagreementrank, String fatherfirstname, String marriageactyear, String mothergenos, String mansrecordaa, String marriageacttomos, String municipalityname, String maraprefecture, String birthmuniccomm, String gainmunrecdate, String grnatgaindate) {
            this.marriageactno = marriageactno;
            this.familyShare = familyShare;
            this.firstname = firstname;
            this.birthdate = birthdate;
            this.gender = gender;
            this.spousemarriagerank = spousemarriagerank;
            this.merida = merida;
            this.membertype = membertype;
            this.mansdecentraladmin = mansdecentraladmin;
            this.marriageactro = marriageactro;
            this.birthmunicipalunit = birthmunicipalunit;
            this.mansmunicipalityname = mansmunicipalityname;
            this.birthmunicipal = birthmunicipal;
            this.eklspecialno = eklspecialno;
            this.motherfirstname = motherfirstname;
            this.birthprefecture = birthprefecture;
            this.surname = surname;
            this.member = member;
            this.marriageactdate = marriageactdate;
            this.birthcountry = birthcountry;
            this.reckind = reckind;
            this.mothersurname = mothersurname;
            this.mansrecordyear = mansrecordyear;
            this.maramunicipality = maramunicipality;
            this.maramuniccomm = maramuniccomm;
            this.maracountry = maracountry;
            this.mansreckind = mansreckind;
            this.marriagerank = marriagerank;
            this.fathersurname = fathersurname;
            this.mainnationality = mainnationality;
            this.spouseagreementrank = spouseagreementrank;
            this.fatherfirstname = fatherfirstname;
            this.marriageactyear = marriageactyear;
            this.mothergenos = mothergenos;
            this.mansrecordaa = mansrecordaa;
            this.marriageacttomos = marriageacttomos;
            this.municipalityname = municipalityname;
            this.maraprefecture = maraprefecture;
            this.birthmuniccomm = birthmuniccomm;
            this.gainmunrecdate = gainmunrecdate;
            this.grnatgaindate = grnatgaindate;
        }

        public String getSecondname() {
            return secondname;
        }

        public void setSecondname(String secondname) {
            this.secondname = secondname;
        }

        public String getMaramuniccomm() {
            return maramuniccomm;
        }

        public void setMaramuniccomm(String maramuniccomm) {
            this.maramuniccomm = maramuniccomm;
        }

        public String getGrnatgaindate() {
            return grnatgaindate;
        }

        public void setGrnatgaindate(String grnatgaindate) {
            this.grnatgaindate = grnatgaindate;
        }

        public String getGainmunrecdate() {
            return gainmunrecdate;
        }

        public void setGainmunrecdate(String gainmunrecdate) {
            this.gainmunrecdate = gainmunrecdate;
        }

        public String getMarriageactno() {
            return marriageactno;
        }

        public void setMarriageactno(String marriageactno) {
            this.marriageactno = marriageactno;
        }

        public String getFamilyShare() {
            return familyShare;
        }

        public void setFamilyShare(String FamilyShare) {
            this.familyShare = FamilyShare;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getSpousemarriagerank() {
            return spousemarriagerank;
        }

        public void setSpousemarriagerank(String spousemarriagerank) {
            this.spousemarriagerank = spousemarriagerank;
        }

        public String getMerida() {
            return merida;
        }

        public void setMerida(String merida) {
            this.merida = merida;
        }

        public String getMembertype() {
            return membertype;
        }

        public void setMembertype(String membertype) {
            this.membertype = membertype;
        }

        public String getMansdecentraladmin() {
            return mansdecentraladmin;
        }

        public void setMansdecentraladmin(String mansdecentraladmin) {
            this.mansdecentraladmin = mansdecentraladmin;
        }

        public String getMarriageactro() {
            return marriageactro;
        }

        public void setMarriageactro(String marriageactro) {
            this.marriageactro = marriageactro;
        }

        public String getBirthmunicipalunit() {
            return birthmunicipalunit;
        }

        public void setBirthmunicipalunit(String birthmunicipalunit) {
            this.birthmunicipalunit = birthmunicipalunit;
        }

        public String getMansmunicipalityname() {
            return mansmunicipalityname;
        }

        public void setMansmunicipalityname(String mansmunicipalityname) {
            this.mansmunicipalityname = mansmunicipalityname;
        }

        public String getBirthmunicipal() {
            return birthmunicipal;
        }

        public void setBirthmunicipal(String birthmunicipal) {
            this.birthmunicipal = birthmunicipal;
        }

        public String getEklspecialno() {
            return eklspecialno;
        }

        public void setEklspecialno(String eklspecialno) {
            this.eklspecialno = eklspecialno;
        }

        public String getMotherfirstname() {
            return motherfirstname;
        }

        public void setMotherfirstname(String motherfirstname) {
            this.motherfirstname = motherfirstname;
        }

        public String getBirthprefecture() {
            return birthprefecture;
        }

        public void setBirthprefecture(String birthprefecture) {
            this.birthprefecture = birthprefecture;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getMarriageactdate() {
            return marriageactdate;
        }

        public void setMarriageactdate(String marriageactdate) {
            this.marriageactdate = marriageactdate;
        }

        public String getBirthcountry() {
            return birthcountry;
        }

        public void setBirthcountry(String birthcountry) {
            this.birthcountry = birthcountry;
        }

        public String getReckind() {
            return reckind;
        }

        public void setReckind(String reckind) {
            this.reckind = reckind;
        }

        public String getMothersurname() {
            return mothersurname;
        }

        public void setMothersurname(String mothersurname) {
            this.mothersurname = mothersurname;
        }

        public String getMansrecordyear() {
            return mansrecordyear;
        }

        public void setMansrecordyear(String mansrecordyear) {
            this.mansrecordyear = mansrecordyear;
        }

        public String getMaramunicipality() {
            return maramunicipality;
        }

        public void setMaramunicipality(String maramunicipality) {
            this.maramunicipality = maramunicipality;
        }

        public String getMaracountry() {
            return maracountry;
        }

        public void setMaracountry(String maracountry) {
            this.maracountry = maracountry;
        }

        public String getMansreckind() {
            return mansreckind;
        }

        public void setMansreckind(String mansreckind) {
            this.mansreckind = mansreckind;
        }

        public String getMarriagerank() {
            return marriagerank;
        }

        public void setMarriagerank(String marriagerank) {
            this.marriagerank = marriagerank;
        }

        public String getFathersurname() {
            return fathersurname;
        }

        public void setFathersurname(String fathersurname) {
            this.fathersurname = fathersurname;
        }

        public String getMainnationality() {
            return mainnationality;
        }

        public void setMainnationality(String mainnationality) {
            this.mainnationality = mainnationality;
        }

        public String getSpouseagreementrank() {
            return spouseagreementrank;
        }

        public void setSpouseagreementrank(String spouseagreementrank) {
            this.spouseagreementrank = spouseagreementrank;
        }

        public String getFatherfirstname() {
            return fatherfirstname;
        }

        public void setFatherfirstname(String fatherfirstname) {
            this.fatherfirstname = fatherfirstname;
        }

        public String getMarriageactyear() {
            return marriageactyear;
        }

        public void setMarriageactyear(String marriageactyear) {
            this.marriageactyear = marriageactyear;
        }

        public String getMothergenos() {
            return mothergenos;
        }

        public void setMothergenos(String mothergenos) {
            this.mothergenos = mothergenos;
        }

        public String getMansrecordaa() {
            return mansrecordaa;
        }

        public void setMansrecordaa(String mansrecordaa) {
            this.mansrecordaa = mansrecordaa;
        }

        public String getMarriageacttomos() {
            return marriageacttomos;
        }

        public void setMarriageacttomos(String marriageacttomos) {
            this.marriageacttomos = marriageacttomos;
        }

        public String getMunicipalityname() {
            return municipalityname;
        }

        public void setMunicipalityname(String municipalityname) {
            this.municipalityname = municipalityname;
        }

        public String getMaraprefecture() {
            return maraprefecture;
        }

        public void setMaraprefecture(String maraprefecture) {
            this.maraprefecture = maraprefecture;
        }

        public String getBirthmuniccomm() {
            return birthmuniccomm;
        }

        public void setBirthmuniccomm(String birthmuniccomm) {
            this.birthmuniccomm = birthmuniccomm;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("FamilyRecordsElement{marriageactno=").append(marriageactno);
            sb.append(", FamilyShare=").append(familyShare);
            sb.append(", firstname=").append(firstname);
            sb.append(", birthdate=").append(birthdate);
            sb.append(", gender=").append(gender);
            sb.append(", spousemarriagerank=").append(spousemarriagerank);
            sb.append(", merida=").append(merida);
            sb.append(", membertype=").append(membertype);
            sb.append(", mansdecentraladmin=").append(mansdecentraladmin);
            sb.append(", marriageactro=").append(marriageactro);
            sb.append(", birthmunicipalunit=").append(birthmunicipalunit);
            sb.append(", mansmunicipalityname=").append(mansmunicipalityname);
            sb.append(", birthmunicipal=").append(birthmunicipal);
            sb.append(", eklspecialno=").append(eklspecialno);
            sb.append(", motherfirstname=").append(motherfirstname);
            sb.append(", birthprefecture=").append(birthprefecture);
            sb.append(", surname=").append(surname);
            sb.append(", member=").append(member);
            sb.append(", marriageactdate=").append(marriageactdate);
            sb.append(", birthcountry=").append(birthcountry);
            sb.append(", reckind=").append(reckind);
            sb.append(", mothersurname=").append(mothersurname);
            sb.append(", mansrecordyear=").append(mansrecordyear);
            sb.append(", maramunicipality=").append(maramunicipality);
            sb.append(", maracountry=").append(maracountry);
            sb.append(", mansreckind=").append(mansreckind);
            sb.append(", marriagerank=").append(marriagerank);
            sb.append(", fathersurname=").append(fathersurname);
            sb.append(", mainnationality=").append(mainnationality);
            sb.append(", spouseagreementrank=").append(spouseagreementrank);
            sb.append(", fatherfirstname=").append(fatherfirstname);
            sb.append(", marriageactyear=").append(marriageactyear);
            sb.append(", mothergenos=").append(mothergenos);
            sb.append(", mansrecordaa=").append(mansrecordaa);
            sb.append(", marriageacttomos=").append(marriageacttomos);
            sb.append(", municipalityname=").append(municipalityname);
            sb.append(", maraprefecture=").append(maraprefecture);
            sb.append('}');
            return sb.toString();
        }

    }
}
