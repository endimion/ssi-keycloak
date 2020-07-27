/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxisUserResponse {

    /*
    <root><userinfo userid="User068933130   " taxid="068933130   "
    lastname="ΒΑΒΟΥΛΑ" firstname="ΕΥΤΥΧΙΑ" fathername="ΕΜΜΑΝΟΥΗΛ" mothername="ΑΝΝΑ" birthyear="1950"/></root>
     */
    @XmlElement(name = "userinfo")
    private UserInfoDetails userInfo;

    @XmlRootElement(name = "userinfo")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UserInfoDetails {

        @XmlAttribute
        private String userid;
        @XmlAttribute
        private String taxid;
        @XmlAttribute
        private String lastname;
        @XmlAttribute
        private String firstname;
        @XmlAttribute
        private String fathername;
        @XmlAttribute
        private String mothername;
        @XmlAttribute
        private String birthyear;

        public UserInfoDetails() {
        }

        public UserInfoDetails(String userid, String taxid, String lastname, String firstname, String fathername, String mothername, String birthyear) {
            this.userid = userid;
            this.taxid = taxid;
            this.lastname = lastname;
            this.firstname = firstname;
            this.fathername = fathername;
            this.mothername = mothername;
            this.birthyear = birthyear;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTaxid() {
            return taxid;
        }

        public void setTaxid(String taxid) {
            this.taxid = taxid;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getFathername() {
            return fathername;
        }

        public void setFathername(String fathername) {
            this.fathername = fathername;
        }

        public String getMothername() {
            return mothername;
        }

        public void setMothername(String mothername) {
            this.mothername = mothername;
        }

        public String getBirthyear() {
            return birthyear;
        }

        public void setBirthyear(String birthyear) {
            this.birthyear = birthyear;
        }

    }

}
