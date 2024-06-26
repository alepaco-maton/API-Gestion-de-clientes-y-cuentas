/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.customer;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.controller.dto.customer.CreateCustomerResponse;
import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.enums.CustomerStatus;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;

/**
 *
 * @author alepaco.com
 */
public class CreateCustomerMapper {

    public static Customer mapperToEntity(CreateUpdateCustomerRequest dto,
            IDocumentTypeRepository documentTypeRepository,
            IGenderRepository genderRepository) {
        return new Customer(null, dto.getName().trim(),
                ((dto.getPaternal() == null) ? null : dto.getPaternal().trim()),
                ((dto.getMaternal() == null) ? null : dto.getMaternal().trim()),
                documentTypeRepository.findById(dto.getDocumentTypeId()).orElse(null),
                dto.getIdentityDocument().trim(),
                dto.getBirthDate(),
                genderRepository.findById(dto.getGenderId()).orElse(null),
                CustomerStatus.ACTIVE
        );
    }

    public static CreateCustomerResponse mapperToDto(Customer model) {
        return new CreateCustomerResponse(model.getId(), model.getName(),
                model.getPaternal(),
                model.getMaternal(),
                CreateCustomerDocumentTypeMapper.mapperToDto(model.getDocumentType()),
                model.getIdentityDocument(),
                model.getBirthDate(),
                CreateCustomerGenderMapper.mapperToDto(model.getGender())
        );
    }

}
