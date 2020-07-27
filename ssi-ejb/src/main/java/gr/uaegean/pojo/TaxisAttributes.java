/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

/**
 *
 * @author nikos
 */
public class TaxisAttributes {

    private String firstname;
    private String fathername;
    private String birthyear;
    private String taxid;
    private String mothername;
    private String userid;
    private String lastname;

    public TaxisAttributes() {
    }

    public TaxisAttributes(String firstname, String fathername, String birthyear, String taxid, String mothername, String userid, String lastname) {
        this.firstname = firstname;
        this.fathername = fathername;
        this.birthyear = birthyear;
        this.taxid = taxid;
        this.mothername = mothername;
        this.userid = userid;
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

    public String getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(String birthyear) {
        this.birthyear = birthyear;
    }

    public String getTaxid() {
        return taxid;
    }

    public void setTaxid(String taxid) {
        this.taxid = taxid;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
