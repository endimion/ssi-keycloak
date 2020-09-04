/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.pojo;

import gr.uaegean.pojo.MinEduAmkaResponse.AmkaResponse;
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
public class AmkaAndMitroResponse {

    private AmkaResponse amkaRes;
    private MinEduFamilyStatusResponse famRes;

}
