
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

        String test = "{\"did\":\"did:ethr:0x19029ecf19cecdf2f1227d10c564d5a3d0b28e85\",\"SEAL-EIDAS\":{\"eidas\":{\"given_name\":\"ΧΡΙΣΤΙΝΑ, CHRISTINA\",\"family_name\":\"ΠΑΛΙΟΚΩΣΤΑ, PALIOKOSTA\",\"source\":\"eidas\"}},\"verified\":[{\"iat\":1585811311,\"exp\":1588403311,\"sub\":\"did:ethr:0x19029ecf19cecdf2f1227d10c564d5a3d0b28e85\",\"claim\":{\"SEAL-EIDAS\":{\"eidas\":{\"given_name\":\"ΧΡΙΣΤΙΝΑ, CHRISTINA\",\"family_name\":\"ΠΑΛΙΟΚΩΣΤΑ, PALIOKOSTA\",\"source\":\"eidas\"}}},\"vc\":[\"/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ\"],\"iss\":\"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f\",\"jwt\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1ODU4MTEzMTEsImV4cCI6MTU4ODQwMzMxMSwic3ViIjoiZGlkOmV0aHI6MHgxOTAyOWVjZjE5Y2VjZGYyZjEyMjdkMTBjNTY0ZDVhM2QwYjI4ZTg1IiwiY2xhaW0iOnsiU0VBTC1FSURBUyI6eyJlaWRhcyI6eyJnaXZlbl9uYW1lIjoizqfOoc6ZzqPOpM6Zzp3OkSwgQ0hSSVNUSU5BIiwiZmFtaWx5X25hbWUiOiLOoM6RzpvOmc6fzprOqc6jzqTOkSwgUEFMSU9LT1NUQSIsInNvdXJjZSI6ImVpZGFzIn19fSwidmMiOlsiL2lwZnMvUW1OYmljS1lRS0NzYzdHTVhTU0pNcHZKU1lnZVE5SzJ0SDE1RW5ieFR5ZHhmUSJdLCJpc3MiOiJkaWQ6ZXRocjoweGQ1MDJhMmM3MWU4YzkwZTgyNTAwYTcwNjgzZjc1ZGUzOGQ1N2RkOWYifQ.8gFBXFPtCo3JCGc59vpoevKdeIEX2z8fE3KyPjxhB-DC_b0GYPgzPDy_MQZUzsczoN76zzqRuuDOlXTXZjz06wE\"}],\"invalid\":[]}";
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        VerifiableCredential vc = mapper.readValue(test, VerifiableCredential.class);
        assertEquals(vc.getSealEidas().getEidas().getFamilyName(), "ΠΑΛΙΟΚΩΣΤΑ, PALIOKOSTA");

    }

    @Test
    public void testMarshallAMKA_MITRO() throws IOException {

        String test = "{\"did\":\"did:ethr:0x19029ecf19cecdf2f1227d10c564d5a3d0b28e85\",\"AMKA\":{\"AMKA\":{\"latinLastName\":\"TRIANTAFYLLOU\",\"birthDate\":\"05/10/1983\",\"latinFirstName\":\"NIKOLAOS\",\"father\":\"ANASTASIOS\",\"mother\":\"ANGELIKI\",\"loa\":\"low\",\"source\":\"AMKA\"}},\"MITRO\":{\"MITRO\":{\"gender\":\"Άρρεν\",\"nationality\":\"Ελληνική\",\"maritalStatus\":\"married\",\"loa\":\"low\",\"source\":\"MITRO\"}},\"verified\":[{\"iat\":1588605910,\"exp\":1591197910,\"sub\":\"did:ethr:0x19029ecf19cecdf2f1227d10c564d5a3d0b28e85\",\"claim\":{\"AMKA\":{\"AMKA\":{\"latinLastName\":\"TRIANTAFYLLOU\",\"birthDate\":\"05/10/1983\",\"latinFirstName\":\"NIKOLAOS\",\"father\":\"ANASTASIOS\",\"mother\":\"ANGELIKI\",\"loa\":\"low\",\"source\":\"AMKA\"}}},\"vc\":[\"/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ\"],\"iss\":\"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f\",\"jwt\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1ODg2MDU5MTAsImV4cCI6MTU5MTE5NzkxMCwic3ViIjoiZGlkOmV0aHI6MHgxOTAyOWVjZjE5Y2VjZGYyZjEyMjdkMTBjNTY0ZDVhM2QwYjI4ZTg1IiwiY2xhaW0iOnsiQU1LQSI6eyJBTUtBIjp7ImxhdGluTGFzdE5hbWUiOiJUUklBTlRBRllMTE9VIiwiYmlydGhEYXRlIjoiMDUvMTAvMTk4MyIsImxhdGluRmlyc3ROYW1lIjoiTklLT0xBT1MiLCJmYXRoZXIiOiJBTkFTVEFTSU9TIiwibW90aGVyIjoiQU5HRUxJS0kiLCJsb2EiOiJsb3ciLCJzb3VyY2UiOiJBTUtBIn19fSwidmMiOlsiL2lwZnMvUW1OYmljS1lRS0NzYzdHTVhTU0pNcHZKU1lnZVE5SzJ0SDE1RW5ieFR5ZHhmUSJdLCJpc3MiOiJkaWQ6ZXRocjoweGQ1MDJhMmM3MWU4YzkwZTgyNTAwYTcwNjgzZjc1ZGUzOGQ1N2RkOWYifQ.FYArK-xVn4i0jHTXxDHo88UrxrVSmvSjZ6JCcshG-bRBsCpBPMryzGTgoBUwCQm0kQ_wmlM7UkbOmSlT0RG75AE\"},{\"iat\":1588667551,\"exp\":1591259551,\"sub\":\"did:ethr:0x19029ecf19cecdf2f1227d10c564d5a3d0b28e85\",\"claim\":{\"MITRO\":{\"MITRO\":{\"gender\":\"Άρρεν\",\"nationality\":\"Ελληνική\",\"maritalStatus\":\"married\",\"loa\":\"low\",\"source\":\"MITRO\"}}},\"vc\":[\"/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ\"],\"iss\":\"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f\",\"jwt\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1ODg2Njc1NTEsImV4cCI6MTU5MTI1OTU1MSwic3ViIjoiZGlkOmV0aHI6MHgxOTAyOWVjZjE5Y2VjZGYyZjEyMjdkMTBjNTY0ZDVhM2QwYjI4ZTg1IiwiY2xhaW0iOnsiTUlUUk8iOnsiTUlUUk8iOnsiZ2VuZGVyIjoizobPgc-BzrXOvSIsIm5hdGlvbmFsaXR5IjoizpXOu867zrfOvc65zrrOriIsIm1hcml0YWxTdGF0dXMiOiJtYXJyaWVkIiwibG9hIjoibG93Iiwic291cmNlIjoiTUlUUk8ifX19LCJ2YyI6WyIvaXBmcy9RbU5iaWNLWVFLQ3NjN0dNWFNTSk1wdkpTWWdlUTlLMnRIMTVFbmJ4VHlkeGZRIl0sImlzcyI6ImRpZDpldGhyOjB4ZDUwMmEyYzcxZThjOTBlODI1MDBhNzA2ODNmNzVkZTM4ZDU3ZGQ5ZiJ9.Oa9SWvlLqFGttKV7kp2qc9wWTm2jrpCt6w9zA544DAV432NXO62gpDVTeEwkN26Cc3VWTQ1JfyZM6CDDAstQZwE\"}],\"invalid\":[]}";
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        VerifiableCredential vc = mapper.readValue(test, VerifiableCredential.class);
        System.out.println(vc.toString());
        assertEquals(vc.getAmka().getAmka().getFather(), "ANASTASIOS");

    }
}
