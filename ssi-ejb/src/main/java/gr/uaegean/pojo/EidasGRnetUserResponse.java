/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author nikos
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class EidasGRnetUserResponse {

    @JsonProperty("given_name")
    private String givenName;
    private String birthdate;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("person_identifier")
    private String personIdentnifier;
    private String sub;

}
