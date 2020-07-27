/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.utils;

import gr.uaegean.pojo.TaxisRequestLog;
import gr.uaegean.services.PropertiesService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author nikos
 */
public class TaxisRequestLogFactory {

    private static final PropertiesService propServ = new PropertiesService();
    private static int callId = 0;

    public static TaxisRequestLog build(String url, String method, String userIp) {
        DateTime dt = DateTime.now(DateTimeZone.UTC);
        callId++;
        TaxisRequestLog logEntry = new TaxisRequestLog();
        logEntry.setUrl(url);
        logEntry.setMethod(method);
        logEntry.setAuditEndUserApplUser(propServ.getProp("GSIS_USER_NAME")); // e.g. "Δήμος Αθηναίων"
        logEntry.setAuditEndUserHostIp(userIp);
        logEntry.setAuditEndUserHostName(null);
        logEntry.setAuditEndUserOsUser(null);
        logEntry.setAuditEntityCode(propServ.getProp("GSIS_ENTITY")); // e.g. LRYHLK7J47OFONBTE0DXDEN3B8B645Q31N0DYZVI  ???
        logEntry.setAuditEntityProtocolDate(dt.toString());
        logEntry.setAuditEntityProtocolNo(dt.toString() + "-" + callId);
        logEntry.setAuditEntityReason(propServ.getProp("GSIS_ENTITY_REASON")); // e.g. 1
        logEntry.setAuditEntityTransactionDate(dt.toString());
        logEntry.setAuditEntityTransactionId(dt.toString() + "-" + callId);
        logEntry.setAuditServerHostIp(propServ.getProp("GSIS_SERVER_IP"));  //e.g. 78.46.163.193
        logEntry.setAuditServerHostName(propServ.getProp("GSIS_SERVER_NAME")); // e.g. kep
        logEntry.setAuditUnitCode(propServ.getProp("GSIS_UNIT_CODE"));  // e.g. 1
        logEntry.setAuditUnitDescr(propServ.getProp("GSIS_UNIT_DESC")); // e.g. Register

        return logEntry;

    }

}
