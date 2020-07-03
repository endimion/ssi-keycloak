
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

    //{"did":"did:ethr:0x71c67e7a9d0e203aca7305ad7ab39853dd3e5cc0","TAXIS":{"TAXIS":{"mothersNameLatin":"Aggeliki","fathersNameLatin":"Anastasios","lastNameLatin":"Triantafyllou","lastName":"Τριανταφύλλου","dateOfBirth":"05/10/1983","afm":"070892XX","gender":"male","mothersName":"Αγγελική","fathersName":"Αναστάσιος","fistName":"Νικόλαος","loa":"low","amka":"051083046XX","source":"TAXIS","firstNameLatin":"Nikolaos","nationality":"Greek"}},"verified":[{"iat":1593497155,"exp":1596089155,"sub":"did:ethr:0x71c67e7a9d0e203aca7305ad7ab39853dd3e5cc0","claim":{"TAXIS":{"TAXIS":{"afm":"070892XX","amka":"051083046XX","lastName":"Τριανταφύλλου","fistName":"Νικόλαος","fathersName":"Αναστάσιος","mothersName":"Αγγελική","fathersNameLatin":"Anastasios","mothersNameLatin":"Aggeliki","firstNameLatin":"Nikolaos","lastNameLatin":"Triantafyllou","gender":"male","nationality":"Greek","loa":"low","source":"TAXIS","dateOfBirth":"05/10/1983"}}},"vc":["/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ"],"iss":"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f","jwt":"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1OTM0OTcxNTUsImV4cCI6MTU5NjA4OTE1NSwic3ViIjoiZGlkOmV0aHI6MHg3MWM2N2U3YTlkMGUyMDNhY2E3MzA1YWQ3YWIzOTg1M2RkM2U1Y2MwIiwiY2xhaW0iOnsiVEFYSVMiOnsiVEFYSVMiOnsiYWZtIjoiMDcwODkyWFgiLCJhbWthIjoiMDUxMDgzMDQ2WFgiLCJsYXN0TmFtZSI6Is6kz4HOuc6xzr3PhM6xz4bPjc67zrvOv8-FIiwiZmlzdE5hbWUiOiLOnc65zrrPjM67zrHOv8-CIiwiZmF0aGVyc05hbWUiOiLOkc69zrHPg8-EzqzPg865zr_PgiIsIm1vdGhlcnNOYW1lIjoizpHOs86zzrXOu865zrrOriIsImZhdGhlcnNOYW1lTGF0aW4iOiJBbmFzdGFzaW9zIiwibW90aGVyc05hbWVMYXRpbiI6IkFnZ2VsaWtpIiwiZmlyc3ROYW1lTGF0aW4iOiJOaWtvbGFvcyIsImxhc3ROYW1lTGF0aW4iOiJUcmlhbnRhZnlsbG91IiwiZ2VuZGVyIjoibWFsZSIsIm5hdGlvbmFsaXR5IjoiR3JlZWsiLCJsb2EiOiJsb3ciLCJzb3VyY2UiOiJUQVhJUyIsImRhdGVPZkJpcnRoIjoiMDUvMTAvMTk4MyJ9fX0sInZjIjpbIi9pcGZzL1FtTmJpY0tZUUtDc2M3R01YU1NKTXB2SlNZZ2VROUsydEgxNUVuYnhUeWR4ZlEiXSwiaXNzIjoiZGlkOmV0aHI6MHhkNTAyYTJjNzFlOGM5MGU4MjUwMGE3MDY4M2Y3NWRlMzhkNTdkZDlmIn0.mtqqBhsMoSRSZpQMa8qeJ6o4aX_QusuVk0ZsLUyOzGFzzYsRWfFRyA4_2tEODsQ5Q60NdKtR7M125LWPsgtyzAA"}],"invalid":[]}
    @Test
    public void testTaxisClaim() throws IOException {

        String test = "{\"did\":\"did:ethr:0x71c67e7a9d0e203aca7305ad7ab39853dd3e5cc0\",\"TAXIS\":{\"TAXIS\":{\"mothersNameLatin\":\"Aggeliki\",\"fathersNameLatin\":\"Anastasios\",\"householdComposition\":[{\"name\":\"Katerina\",\"relation\":\"wife\"},{\"name\":\"xx\",\"relation\":\"daughter\"}],\"lastNameLatin\":\"Triantafyllou\",\"lastName\":\"Τριανταφύλλου\",\"dateOfBirth\":\"05/10/1983\",\"afm\":\"070892XX\",\"gender\":\"male\",\"mothersName\":\"Αγγελική\",\"fathersName\":\"Αναστάσιος\",\"fistName\":\"Νικόλαος\",\"address\":{\"street\":\"Καλλ***\",\"streetNumber\":\"**\",\"PO\":\"15***\",\"municipality\":\"Ζ**\",\"prefecture\":\"Αττικής\"},\"loa\":\"low\",\"amka\":\"051083046XX\",\"source\":\"TAXIS\",\"firstNameLatin\":\"Nikolaos\",\"nationality\":\"Greek\"}},\"verified\":[{\"iat\":1593767579,\"exp\":1596359579,\"sub\":\"did:ethr:0x71c67e7a9d0e203aca7305ad7ab39853dd3e5cc0\",\"claim\":{\"TAXIS\":{\"TAXIS\":{\"afm\":\"070892XX\",\"amka\":\"051083046XX\",\"lastName\":\"Τριανταφύλλου\",\"fistName\":\"Νικόλαος\",\"fathersName\":\"Αναστάσιος\",\"mothersName\":\"Αγγελική\",\"fathersNameLatin\":\"Anastasios\",\"mothersNameLatin\":\"Aggeliki\",\"firstNameLatin\":\"Nikolaos\",\"lastNameLatin\":\"Triantafyllou\",\"gender\":\"male\",\"nationality\":\"Greek\",\"loa\":\"low\",\"source\":\"TAXIS\",\"dateOfBirth\":\"05/10/1983\",\"householdComposition\":[{\"name\":\"Katerina\",\"relation\":\"wife\"},{\"name\":\"xx\",\"relation\":\"daughter\"}],\"address\":{\"street\":\"Καλλ***\",\"streetNumber\":\"**\",\"PO\":\"15***\",\"municipality\":\"Ζ**\",\"prefecture\":\"Αττικής\"}}}},\"vc\":[\"/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ\"],\"iss\":\"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f\",\"jwt\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1OTM3Njc1NzksImV4cCI6MTU5NjM1OTU3OSwic3ViIjoiZGlkOmV0aHI6MHg3MWM2N2U3YTlkMGUyMDNhY2E3MzA1YWQ3YWIzOTg1M2RkM2U1Y2MwIiwiY2xhaW0iOnsiVEFYSVMiOnsiVEFYSVMiOnsiYWZtIjoiMDcwODkyWFgiLCJhbWthIjoiMDUxMDgzMDQ2WFgiLCJsYXN0TmFtZSI6Is6kz4HOuc6xzr3PhM6xz4bPjc67zrvOv8-FIiwiZmlzdE5hbWUiOiLOnc65zrrPjM67zrHOv8-CIiwiZmF0aGVyc05hbWUiOiLOkc69zrHPg8-EzqzPg865zr_PgiIsIm1vdGhlcnNOYW1lIjoizpHOs86zzrXOu865zrrOriIsImZhdGhlcnNOYW1lTGF0aW4iOiJBbmFzdGFzaW9zIiwibW90aGVyc05hbWVMYXRpbiI6IkFnZ2VsaWtpIiwiZmlyc3ROYW1lTGF0aW4iOiJOaWtvbGFvcyIsImxhc3ROYW1lTGF0aW4iOiJUcmlhbnRhZnlsbG91IiwiZ2VuZGVyIjoibWFsZSIsIm5hdGlvbmFsaXR5IjoiR3JlZWsiLCJsb2EiOiJsb3ciLCJzb3VyY2UiOiJUQVhJUyIsImRhdGVPZkJpcnRoIjoiMDUvMTAvMTk4MyIsImhvdXNlaG9sZENvbXBvc2l0aW9uIjpbeyJuYW1lIjoiS2F0ZXJpbmEiLCJyZWxhdGlvbiI6IndpZmUifSx7Im5hbWUiOiJ4eCIsInJlbGF0aW9uIjoiZGF1Z2h0ZXIifV0sImFkZHJlc3MiOnsic3RyZWV0IjoizprOsc67zrsqKioiLCJzdHJlZXROdW1iZXIiOiIqKiIsIlBPIjoiMTUqKioiLCJtdW5pY2lwYWxpdHkiOiLOlioqIiwicHJlZmVjdHVyZSI6Is6Rz4TPhM65zrrOrs-CIn19fX0sInZjIjpbIi9pcGZzL1FtTmJpY0tZUUtDc2M3R01YU1NKTXB2SlNZZ2VROUsydEgxNUVuYnhUeWR4ZlEiXSwiaXNzIjoiZGlkOmV0aHI6MHhkNTAyYTJjNzFlOGM5MGU4MjUwMGE3MDY4M2Y3NWRlMzhkNTdkZDlmIn0.ImkccpkCNK_tJOOKeEZS3TjDUg0OyC60A05MRODfPxo_ZW2jf6_vOB66TD8XJelOUxqPSyICpoI277h2fwcUYwE\"}],\"invalid\":[]}";
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        VerifiableCredential vc = mapper.readValue(test, VerifiableCredential.class);
        System.out.println(vc.toString());
        assertEquals(vc.getTaxisRoute().getTaxis().getFathersName(), "Αναστάσιος");
        assertEquals(vc.getTaxisRoute().getTaxis().getFirstNameLatin(), "Nikolaos");
        assertEquals("wife", vc.getTaxisRoute().getTaxis().getHousehold()[0].getRelation());
        assertEquals("**", vc.getTaxisRoute().getTaxis().getAddress().getStreetNumber());

    }

    //"did":"did:ethr:0x71c67e7a9d0e203aca7305ad7ab39853dd3e5cc0","SELF":{"self":{"oaedid":"213","oaedDate":"10/03/20","personal":"none","source":"self","loa":"low"}},"verified":[{"iat":1593512397,"exp":1596104397,"sub":"did:ethr:0x71c67e7a9d0e203aca7305ad7ab39853dd3e5cc0","claim":{"SELF":{"self":{"oaedid":"213","oaedDate":"10/03/20","personal":"none","source":"self","loa":"low"}}},"vc":["/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ"],"iss":"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f","jwt":"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1OTM1MTIzOTcsImV4cCI6MTU5NjEwNDM5Nywic3ViIjoiZGlkOmV0aHI6MHg3MWM2N2U3YTlkMGUyMDNhY2E3MzA1YWQ3YWIzOTg1M2RkM2U1Y2MwIiwiY2xhaW0iOnsiU0VMRiI6eyJzZWxmIjp7Im9hZWRpZCI6IjIxMyIsIm9hZWREYXRlIjoiMTAvMDMvMjAiLCJwZXJzb25hbCI6Im5vbmUiLCJzb3VyY2UiOiJzZWxmIiwibG9hIjoibG93In19fSwidmMiOlsiL2lwZnMvUW1OYmljS1lRS0NzYzdHTVhTU0pNcHZKU1lnZVE5SzJ0SDE1RW5ieFR5ZHhmUSJdLCJpc3MiOiJkaWQ6ZXRocjoweGQ1MDJhMmM3MWU4YzkwZTgyNTAwYTcwNjgzZjc1ZGUzOGQ1N2RkOWYifQ.UuUXtTYGguFgWr75tzlCEFGBc6FaaDleBJPXfY7U85GQwlrSqv7Vfkz_XrDIftMBjLjHeclwoNXGV_QcfRtW6AA"}],"invalid":[]}
    @Test
    public void testSelfClaim() throws IOException {

        String test = "{\"did\":\"did:ethr:0x71c67e7a9d0e203aca7305ad7ab39853dd3e5cc0\",\"SELF\":{\"self\":{\"oaedid\":\"213\",\"oaedDate\":\"10/03/20\",\"personal\":\"none\",\"source\":\"self\",\"loa\":\"low\"}},\"verified\":[{\"iat\":1593512397,\"exp\":1596104397,\"sub\":\"did:ethr:0x71c67e7a9d0e203aca7305ad7ab39853dd3e5cc0\",\"claim\":{\"SELF\":{\"self\":{\"oaedid\":\"213\",\"oaedDate\":\"10/03/20\",\"personal\":\"none\",\"source\":\"self\",\"loa\":\"low\"}}},\"vc\":[\"/ipfs/QmNbicKYQKCsc7GMXSSJMpvJSYgeQ9K2tH15EnbxTydxfQ\"],\"iss\":\"did:ethr:0xd502a2c71e8c90e82500a70683f75de38d57dd9f\",\"jwt\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NkstUiJ9.eyJpYXQiOjE1OTM1MTIzOTcsImV4cCI6MTU5NjEwNDM5Nywic3ViIjoiZGlkOmV0aHI6MHg3MWM2N2U3YTlkMGUyMDNhY2E3MzA1YWQ3YWIzOTg1M2RkM2U1Y2MwIiwiY2xhaW0iOnsiU0VMRiI6eyJzZWxmIjp7Im9hZWRpZCI6IjIxMyIsIm9hZWREYXRlIjoiMTAvMDMvMjAiLCJwZXJzb25hbCI6Im5vbmUiLCJzb3VyY2UiOiJzZWxmIiwibG9hIjoibG93In19fSwidmMiOlsiL2lwZnMvUW1OYmljS1lRS0NzYzdHTVhTU0pNcHZKU1lnZVE5SzJ0SDE1RW5ieFR5ZHhmUSJdLCJpc3MiOiJkaWQ6ZXRocjoweGQ1MDJhMmM3MWU4YzkwZTgyNTAwYTcwNjgzZjc1ZGUzOGQ1N2RkOWYifQ.UuUXtTYGguFgWr75tzlCEFGBc6FaaDleBJPXfY7U85GQwlrSqv7Vfkz_XrDIftMBjLjHeclwoNXGV_QcfRtW6AA\"}],\"invalid\":[]}";
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        VerifiableCredential vc = mapper.readValue(test, VerifiableCredential.class);
        System.out.println(vc.toString());
        assertEquals(vc.getSelf().getSelf().getOaedid(), "213");

    }

}
