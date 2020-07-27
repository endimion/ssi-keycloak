/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

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
public class TaxisRequestLog {

    private String method; // GET POST etc
    private String url; // the call url
    private String auditEndUserHostIp;   // getClientIp
    private String auditEndUserApplUser; // GSIS_USER_NAME
    private String auditEntityProtocolNo;
    private String auditEntityCode; // GSIS_ENTITY
    private String auditEntityReason; // GSIS_ENTITY_REASON
    private String auditUnitCode; // GSIS_UNIT_CODE
    private String auditUnitDescr; // GSIS_UNIT_DESC
    private String auditServerHostIp; // GSIS_SERVER_IP
    private String auditServerHostName; // GSIS_SERVER_NAME
    private String auditEntityProtocolDate;         //  RFC 3339 date
    private String auditEndUserOsUser; //null
    private String auditEndUserHostName; // null
    private String auditEntityTransactionId;  // auditEntityProtocolNo
    private String auditEntityTransactionDate; // auditEntityProtocolDate

}
