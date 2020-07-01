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
public class MinEduLog {

    private String minEduSessionId;
    private String selectedUniversity;
    private String responseTimeStamp;
    private String recievedTimeStamp;
    private String esmoSessionId;

    public MinEduLog() {
    }

    public MinEduLog(String minEduSessionId, String selectedUniversity, String responseTimeStamp, String recievedTimeStamp, String esmoSessionId) {
        this.minEduSessionId = minEduSessionId;
        this.selectedUniversity = selectedUniversity;
        this.responseTimeStamp = responseTimeStamp;
        this.recievedTimeStamp = recievedTimeStamp;
        this.esmoSessionId = esmoSessionId;
    }

    public String getMinEduSessionId() {
        return minEduSessionId;
    }

    public void setMinEduSessionId(String minEduSessionId) {
        this.minEduSessionId = minEduSessionId;
    }

    public String getSelectedUniversity() {
        return selectedUniversity;
    }

    public void setSelectedUniversity(String selectedUniversity) {
        this.selectedUniversity = selectedUniversity;
    }

    public String getResponseTimeStamp() {
        return responseTimeStamp;
    }

    public void setResponseTimeStamp(String responseTimeStamp) {
        this.responseTimeStamp = responseTimeStamp;
    }

    public String getRecievedTimeStamp() {
        return recievedTimeStamp;
    }

    public void setRecievedTimeStamp(String recievedTimeStamp) {
        this.recievedTimeStamp = recievedTimeStamp;
    }

    public String getEsmoSessionId() {
        return esmoSessionId;
    }

    public void setEsmoSessionId(String esmoSessionId) {
        this.esmoSessionId = esmoSessionId;
    }

    @Override
    public String toString() {
        return "{" + "minEduSessionId:" + minEduSessionId + ", selectedUniversity:" + selectedUniversity + ", responseTimeStamp:" + responseTimeStamp + ", recievedTimeStamp:" + recievedTimeStamp + ", esmoSessionId:" + esmoSessionId + '}';
    }

}
