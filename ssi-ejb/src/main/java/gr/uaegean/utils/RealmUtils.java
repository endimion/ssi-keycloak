/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nikos
 */
public class RealmUtils {

    public static String updateRealm(String url, String replacementRealm) throws IOException {
        Pattern patt = Pattern.compile("realms/([^<]*)/protocol");
        Matcher m = patt.matcher(url);
        StringBuffer sb = new StringBuffer(url.length());
        while (m.find()) {
            String text = m.group(1);
            // ... possibly process 'text' ...
            m.appendReplacement(sb, "realms/" + replacementRealm + "/protocol");
        }
        m.appendTail(sb);
        return sb.toString();

    }

}
