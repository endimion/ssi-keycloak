/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 *
 * @author nikos
 */
@ApplicationScoped
@Default
public class PropertiesService {

    private final Properties prop;
    private final static Logger LOG = LoggerFactory.getLogger(PropertiesService.class);

    public PropertiesService() {
        this.prop = new Properties();
        try {
            InputStream input = new FileInputStream("/keyConfig/key.properties");
            this.prop.load(input);
        } catch (IOException e) {
            LOG.error("reading properties only form memory file error");
//            LOG.error(e.getMessage());
        }
    }

    public String getProp(String propertyName) {
        return StringUtils.isEmpty(this.prop.getProperty(propertyName)) ? System.getenv(propertyName) : this.prop.getProperty(propertyName);
    }

}
