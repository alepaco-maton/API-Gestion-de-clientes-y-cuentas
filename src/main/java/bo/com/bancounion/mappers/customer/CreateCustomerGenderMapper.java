/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.customer;

import bo.com.bancounion.controller.dto.customer.CreateCustomerGenderResponse;
import bo.com.bancounion.entity.Gender;

/**
 *
 * @author alepaco.com
 */
public class CreateCustomerGenderMapper {
 
    public static CreateCustomerGenderResponse mapperToDto(Gender model) {
        return new CreateCustomerGenderResponse(model.getId(), model.getName());
    }

}
