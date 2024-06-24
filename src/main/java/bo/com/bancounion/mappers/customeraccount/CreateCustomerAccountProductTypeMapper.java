/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.customeraccount;

import bo.com.bancounion.controller.dto.customeraccount.CreateCustomerAccountProductTypeResponse;
import bo.com.bancounion.entity.ProductType;

/**
 *
 * @author alepaco.com
 */
public class CreateCustomerAccountProductTypeMapper {
 
    public static CreateCustomerAccountProductTypeResponse mapperToDto(ProductType model) {
        return new CreateCustomerAccountProductTypeResponse(model.getId(), model.getName());
    }

}
