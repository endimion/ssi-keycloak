
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.uaegean.pojo.VerifiableCredential;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nikos
 */
public class TestClaimsMarshall {

    @Test
    public void testMarshallUnknown() throws IOException {

        String test = ""
                + "    {\"did\":\"did:ethr:0xd4b90423e473e2b8d16b57d29abcf6c08ef8fd3a\",\n"
                + "    \"eidas\":{\"given_name\":\"ΑΝΔΡΕΑΣ, ANDREAS\",\"family_name\":\"ΠΕΤΡΟΥ, PETROU\",\"person_identifier\":\"GR/GR/ERMIS-11076669\",\"date_of_birth\":\"1980-01-01\",\"source\":\"eidas\",\"loa\":\"http://eidas.europa.eu/LoA/low\"},\n"
                + "    \"verified\":[{\"iat\":1576583514,\"exp\":1579175514,\"sub\":\"did:ethr:0xd4b90423e473e2b8d16b57d29abcf6c08ef8fd3a\",\"claim\":{\"eidas\":{\"given_name\":\"ΑΝΔΡΕΑΣ, ANDREAS\",\"family_name\":\"ΠΕΤΡΟΥ, PETROU\",\"person_identifier\":\"GR/GR/ERMIS-11076669\",\"date_of_birth\":\"1980-01-01\",\"source\":\"eidas\",\"loa\":\"http://eidas.europa.eu/LoA/low\"}},\"vc\":[\"/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ\"],\"iss\":\"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f\",\"jwt\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1NzY1ODM1MTQsImV4cCI6MTU3OTE3NTUxNCwic3ViIjoiZGlkOmV0aHI6MHhkNGI5MDQyM2U0NzNlMmI4ZDE2YjU3ZDI5YWJjZjZjMDhlZjhmZDNhIiwiY2xhaW0iOnsiZWlkYXMiOnsiZ2l2ZW5fbmFtZSI6Is6Rzp3OlM6hzpXOkc6jLCBBTkRSRUFTIiwiZmFtaWx5X25hbWUiOiLOoM6VzqTOoc6fzqUsIFBFVFJPVSIsInBlcnNvbl9pZGVudGlmaWVyIjoiR1IvR1IvRVJNSVMtMTEwNzY2NjkiLCJkYXRlX29mX2JpcnRoIjoiMTk4MC0wMS0wMSIsInNvdXJjZSI6ImVpZGFzIiwibG9hIjoiaHR0cDovL2VpZGFzLmV1cm9wYS5ldS9Mb0EvbG93In19LCJ2YyI6WyIvaXBmcy9RbU5iaWNLWVFLQ3NjN0dNWFNTSk1wdkpTWWdlUTlLMnRIMTVFbmJ4VHlkeGZRIl0sImlzcyI6ImRpZDpldGhyOjB4ZDUwMmEyYzcxZThjOTBlODI1MDBhNzA2ODNmNzVkZTM4ZDU3ZGQ5ZiJ9.0pijmaUsz-UfCnGoeM4foUauMl1chR5jMdBhwdjxc6-0X2fEBmJ3r_urGjAB0ZoKPWygevaKvXNk87H7_jtrjwA\"}],\"invalid\":[]}\n"
                + "     ";
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        VerifiableCredential vc = mapper.readValue(test, VerifiableCredential.class);
        assertEquals(vc.getEidas().getFamilyName(), "ΠΕΤΡΟΥ, PETROU");

    }

}
