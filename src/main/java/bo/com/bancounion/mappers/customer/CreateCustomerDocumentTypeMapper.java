/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.customer;

import bo.com.bancounion.controller.dto.customer.CreateCustomerDocumentTypeResponse;
import bo.com.bancounion.entity.DocumentType;

/**
 *
 * @author alepaco.com
 */
public class CreateCustomerDocumentTypeMapper {
 
    public static CreateCustomerDocumentTypeResponse mapperToDto(DocumentType model) {
        return new CreateCustomerDocumentTypeResponse(model.getId(), model.getName());
    }

}
