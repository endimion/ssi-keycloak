/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaxisTokenResponse {

    @JsonProperty("access_token")
    private String token;

}
