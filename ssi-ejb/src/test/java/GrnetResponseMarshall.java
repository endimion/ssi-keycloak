
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.uaegean.pojo.EidasGRnetUserResponse;
import java.io.IOException;
import javax.xml.bind.JAXBException;
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
public class GrnetResponseMarshall {

    @Test
    public void testTaxisMarshall() throws IOException, JAXBException {

        String test = "{\"sub\": \"cc1a1a87e5d7d8a3a408756bd40144cf018aefb30f8b1ff212f51f564ae5272edff4920301c19419248478a0d9c3366c0aed693f4cd09c561331dc4b11685226@eid-proxy.aai-dev.grnet.gr\",\n"
                + "            \"given_name\": \"claude\",\n"
                + "            \"family_name\": \"Phil\",\n"
                + "            \"birthdate\": \"1965-01-01\",\n"
                + "            \"person_identifier\": \"GR/GR/11111\"}";

        ObjectMapper mapper = new ObjectMapper();
        EidasGRnetUserResponse user = mapper.readValue(test, EidasGRnetUserResponse.class);
        assertEquals(user.getFamilyName(), "Phil");

    }
}
