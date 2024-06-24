/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.customer;

import bo.com.bancounion.mappers.gender.ReadGenderMapper;
import bo.com.bancounion.mappers.documenttype.ReadDocumentTypeMapper;
import bo.com.bancounion.controller.dto.customer.ListCustomerResponse;
import bo.com.bancounion.entity.Customer;

/**
 *
 * @author alepaco.com
 */
public class ReadCustomerMapper {
        
    public static ListCustomerResponse mapperToDto(Customer model) {
        return new ListCustomerResponse(model.getId(), model.getName(),
                model.getPaternal(),
                model.getMaternal(),
                ReadDocumentTypeMapper.mapperToDto(model.getDocumentType()),
                model.getIdentityDocument(),
                model.getBirthDate(),
                ReadGenderMapper.mapperToDto(model.getGender()),
                model.getStatus()
        );
    }
    
}
