/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.customeraccount;

import bo.com.bancounion.controller.dto.customeraccount.CreateCustomerAccountCustomerResponse;
import bo.com.bancounion.entity.Customer;

/**
 *
 * @author alepaco.com
 */
public class CreateCustomerAccountCustomerMapper {

    public static CreateCustomerAccountCustomerResponse mapperToDto(Customer model) {
        return new CreateCustomerAccountCustomerResponse(model.getId(),
                model.getName() + " " + model.getPaternal() + " " + model.getMaternal());
    }

}
