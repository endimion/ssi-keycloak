
import gr.uaegean.pojo.TaxisUserResponse;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
public class TaxisResponseMarshall {

    @Test
    public void testTaxisMarshall() throws IOException, JAXBException {

        String test = "<root><userinfo userid=\"User068933130   \" taxid=\"068933130   \" lastname=\"ΒΑΒΟΥΛΑ\" firstname=\"ΕΥΤΥΧΙΑ\" fathername=\"ΕΜΜΑΝΟΥΗΛ\" mothername=\"ΑΝΝΑ\" birthyear=\"1950\"/></root>";

        JAXBContext context = JAXBContext.newInstance(TaxisUserResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        TaxisUserResponse userResponse = (TaxisUserResponse) unmarshaller.unmarshal(new StringReader(test));

        assertEquals(userResponse.getUserInfo().getBirthyear(), "1950");

    }
}
