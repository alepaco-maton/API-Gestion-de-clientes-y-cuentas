/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.gender;

import bo.com.bancounion.controller.dto.gender.ListGenderResponse;
import bo.com.bancounion.entity.Gender;

/**
 *
 * @author alepaco.com
 */
public class ReadGenderMapper {
 
    public static ListGenderResponse mapperToDto(Gender model) {
        return new ListGenderResponse(model.getId(), model.getName());
    }

}
