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
 * @author nikos
 */
public class MinEduResponse implements Serializable {

    private String code;

    @JsonProperty("ServiceCallID")
    private String ServiceCallID;
    private boolean success;
    @JsonProperty("Result")
    private Result Result;
    private String timestamp;

    public MinEduResponse() {
    }

    public MinEduResponse(Result Result, String ServiceCallID, String code, boolean success, String timestamp) {
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

    public static class Result implements Serializable {

        private InspectionResult inspectionResult;
        private String response;
        private String errorReason;

        public Result(InspectionResult inspectionResult, String response, String errorReason) {
            this.inspectionResult = inspectionResult;
            this.response = response;
            this.errorReason = errorReason;
        }

        public Result() {
        }

        public InspectionResult getInspectionResult() {
            return inspectionResult;
        }

        public void setInspectionResult(InspectionResult inspectionResult) {
            this.inspectionResult = inspectionResult;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getErrorReason() {
            return errorReason;
        }

        public void setErrorReason(String errorReason) {
            this.errorReason = errorReason;
        }

        @Override
        public String toString() {
            return "Result{" + "inspectionResult=" + inspectionResult.toString() + ", response=" + response + ", errorReason=" + errorReason + '}';
        }

    }

    public static class InspectionResult implements Serializable {

        private String academicId;
        private String amka;
        private String residenceLocation;
        private String universityLocation;
        private String studentshipType;
        private String greekFirstName;
        private String greekLastName;
        private String latinFirstName;
        private String latinLastName;
        private String departmentName;
        private String entryYear;
        private String currentSemester;
        private String postGraduateProgram;
        private String pasoValidity;
        private String pasoExpirationDate;
        private String submissionDate;
        private String applicationStatus;
        private String cancellationDate;
        private String cancellationReason;
        private String erasmus;
        private String studentNumber;
        private String photoUrl;
        private String webServiceSuccess;
        private String validationError;

        public InspectionResult() {
        }

        public InspectionResult(String academicId, String amka, String residenceLocation, String universityLocation, String studentshipType,
                String greekFirstName, String greekLastName, String latinFirstName, String latinLastName, String departmentName,
                String entryYear, String currentSemester, String postGraduateProgram, String pasoValidity, String pasoExpirationDate,
                String submissionDate, String applicationStatus, String cancellationDate, String cancellationReason, String erasmus,
                String studentNumber, String photoUrl, String webServiceSuccess, String validationError) {
            this.academicId = academicId;
            this.amka = amka;
            this.residenceLocation = residenceLocation;
            this.universityLocation = universityLocation;
            this.studentshipType = studentshipType;
            this.greekFirstName = greekFirstName;
            this.greekLastName = greekLastName;
            this.latinFirstName = latinFirstName;
            this.latinLastName = latinLastName;
            this.departmentName = departmentName;
            this.entryYear = entryYear;
            this.currentSemester = currentSemester;
            this.postGraduateProgram = postGraduateProgram;
            this.pasoValidity = pasoValidity;
            this.pasoExpirationDate = pasoExpirationDate;
            this.submissionDate = submissionDate;
            this.applicationStatus = applicationStatus;
            this.cancellationDate = cancellationDate;
            this.cancellationReason = cancellationReason;
            this.erasmus = erasmus;
            this.studentNumber = studentNumber;
            this.photoUrl = photoUrl;
            this.webServiceSuccess = webServiceSuccess;
            this.validationError = validationError;
        }

        public String getAcademicId() {
            return academicId;
        }

        public void setAcademicId(String academicId) {
            this.academicId = academicId;
        }

        public String getAmka() {
            return amka;
        }

        public void setAmka(String amka) {
            this.amka = amka;
        }

        public String getResidenceLocation() {
            return residenceLocation;
        }

        public void setResidenceLocation(String residenceLocation) {
            this.residenceLocation = residenceLocation;
        }

        public String getUniversityLocation() {
            return universityLocation;
        }

        public void setUniversityLocation(String universityLocation) {
            this.universityLocation = universityLocation;
        }

        public String getStudentshipType() {
            return studentshipType;
        }

        public void setStudentshipType(String studentshipType) {
            this.studentshipType = studentshipType;
        }

        public String getGreekFirstName() {
            return greekFirstName;
        }

        public void setGreekFirstName(String greekFirstName) {
            this.greekFirstName = greekFirstName;
        }

        public String getGreekLastName() {
            return greekLastName;
        }

        public void setGreekLastName(String greekLastName) {
            this.greekLastName = greekLastName;
        }

        public String getLatinFirstName() {
            return latinFirstName;
        }

        public void setLatinFirstName(String latinFirstName) {
            this.latinFirstName = latinFirstName;
        }

        public String getLatinLastName() {
            return latinLastName;
        }

        public void setLatinLastName(String latinLastName) {
            this.latinLastName = latinLastName;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getEntryYear() {
            return entryYear;
        }

        public void setEntryYear(String entryYear) {
            this.entryYear = entryYear;
        }

        public String getCurrentSemester() {
            return currentSemester;
        }

        public void setCurrentSemester(String currentSemester) {
            this.currentSemester = currentSemester;
        }

        public String getPostGraduateProgram() {
            return postGraduateProgram;
        }

        public void setPostGraduateProgram(String postGraduateProgram) {
            this.postGraduateProgram = postGraduateProgram;
        }

        public String getPasoValidity() {
            return pasoValidity;
        }

        public void setPasoValidity(String pasoValidity) {
            this.pasoValidity = pasoValidity;
        }

        public String getPasoExpirationDate() {
            return pasoExpirationDate;
        }

        public void setPasoExpirationDate(String pasoExpirationDate) {
            this.pasoExpirationDate = pasoExpirationDate;
        }

        public String getSubmissionDate() {
            return submissionDate;
        }

        public void setSubmissionDate(String submissionDate) {
            this.submissionDate = submissionDate;
        }

        public String getApplicationStatus() {
            return applicationStatus;
        }

        public void setApplicationStatus(String applicationStatus) {
            this.applicationStatus = applicationStatus;
        }

        public String getCancellationDate() {
            return cancellationDate;
        }

        public void setCancellationDate(String cancellationDate) {
            this.cancellationDate = cancellationDate;
        }

        public String getCancellationReason() {
            return cancellationReason;
        }

        public void setCancellationReason(String cancellationReason) {
            this.cancellationReason = cancellationReason;
        }

        public String getErasmus() {
            return erasmus;
        }

        public void setErasmus(String erasmus) {
            this.erasmus = erasmus;
        }

        public String getStudentNumber() {
            return studentNumber;
        }

        public void setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getWebServiceSuccess() {
            return webServiceSuccess;
        }

        public void setWebServiceSuccess(String webServiceSuccess) {
            this.webServiceSuccess = webServiceSuccess;
        }

        public String getValidationError() {
            return validationError;
        }

        public void setValidationError(String validationError) {
            this.validationError = validationError;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("InspectionResult{academicId=").append(academicId);
            sb.append(", amka=").append(amka);
            sb.append(", residenceLocation=").append(residenceLocation);
            sb.append(", universityLocation=").append(universityLocation);
            sb.append(", studentshipType=").append(studentshipType);
            sb.append(", greekFirstName=").append(greekFirstName);
            sb.append(", greekLastName=").append(greekLastName);
            sb.append(", latinFirstName=").append(latinFirstName);
            sb.append(", latinLastName=").append(latinLastName);
            sb.append(", departmentName=").append(departmentName);
            sb.append(", entryYear=").append(entryYear);
            sb.append(", currentSemester=").append(currentSemester);
            sb.append(", postGraduateProgram=").append(postGraduateProgram);
            sb.append(", pasoValidity=").append(pasoValidity);
            sb.append(", pasoExpirationDate=").append(pasoExpirationDate);
            sb.append(", submissionDate=").append(submissionDate);
            sb.append(", applicationStatus=").append(applicationStatus);
            sb.append(", cancellationDate=").append(cancellationDate);
            sb.append(", cancellationReason=").append(cancellationReason);
            sb.append(", erasmus=").append(erasmus);
            sb.append(", studentNumber=").append(studentNumber);
            sb.append(", photoUrl=").append(photoUrl);
            sb.append(", webServiceSuccess=").append(webServiceSuccess);
            sb.append(", validationError=").append(validationError);
            sb.append('}');
            return sb.toString();
        }

    }

}
