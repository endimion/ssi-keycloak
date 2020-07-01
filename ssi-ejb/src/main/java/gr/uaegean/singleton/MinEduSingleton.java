/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.singleton;

import gr.uaegean.services.MinEduService;
import gr.uaegean.services.PropertiesService;
import gr.uaegean.services.impl.MinEduServiceImpl;
import java.io.IOException;

/**
 *
 * @author nikos
 */
public class MinEduSingleton {

    public static MinEduService minEduServ;
    public static boolean isInit = false;

    private static void initServ() throws IOException {
        minEduServ = new MinEduServiceImpl(new PropertiesService());
        isInit = true;
    }

    public static MinEduService getService() throws IOException {
        if (isInit) {
            return minEduServ;
        } else {
            initServ();
            return minEduServ;
        }

    }

}
