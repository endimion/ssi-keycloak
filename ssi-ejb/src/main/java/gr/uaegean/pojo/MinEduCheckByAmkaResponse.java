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
public class MinEduCheckByAmkaResponse {

    private String code;

    @JsonProperty("ServiceCallID")
    private String ServiceCallID;
    private boolean success;
    @JsonProperty("Result")
    private AmkaResult Result;
    private String timestamp;

    public MinEduCheckByAmkaResponse() {
    }

    public MinEduCheckByAmkaResponse(AmkaResult Result, String ServiceCallID, String code, boolean success, String timestamp) {
        this.Result = Result;
        this.ServiceCallID = ServiceCallID;
        this.code = code;
        this.success = success;
        this.timestamp = timestamp;
    }

    public AmkaResult getResult() {
        return Result;
    }

    public void setResult(AmkaResult Result) {
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

    public static class AmkaResult {
        @JsonProperty("AcademicID")
        private String academicID;
        @JsonProperty("AcademicStatus")
        private String academicStatus;

        public AmkaResult(String academicID, String academicStatus) {
            this.academicID = academicID;
            this.academicStatus = academicStatus;
        }

        public AmkaResult() {
        }

        public String getAcademicID() {
            return academicID;
        }

        public void setAcademicID(String AcademicID) {
            this.academicID = AcademicID;
        }

        public String getAcademicStatus() {
            return academicStatus;
        }

        public void setAcademicStatus(String AcademicStatus) {
            this.academicStatus = AcademicStatus;
        }

    }

}
