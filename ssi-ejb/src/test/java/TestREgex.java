
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class TestREgex {

    @Test
    public void testRegex() throws IOException {
        String htmlString = "http://localhost:8080/auth/realms/test/protocol/openid-connect/auth";
        Pattern patt = Pattern.compile("realms/([^<]*)/protocol");
        Matcher m = patt.matcher(htmlString);
        StringBuffer sb = new StringBuffer(htmlString.length());
        while (m.find()) {
            String text = m.group(1);
            // ... possibly process 'text' ...
            m.appendReplacement(sb, "realms/mock/protocol");
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
        assertEquals(sb.toString(), "http://localhost:8080/auth/realms/mock/protocol/openid-connect/auth");

    }

}
