/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.customeraccount;

import bo.com.bancounion.controller.dto.customeraccount.CreateCustomerAccountResponse;
import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.entity.CustomerAccount;
import bo.com.bancounion.enums.CustomerAccountStatus;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IProductTypeRepository;
import java.util.Date;

/**
 *
 * @author alepaco.com
 */
public class CreateCustomerAccountMapper {

    public static CustomerAccount mapperToEntity(CreateUpdateCustomerAccountRequest dto,
            IProductTypeRepository productTypeRepository,
            ICustomerRepository customerRepository) {
        return new CustomerAccount(null,
                productTypeRepository.findById(dto.getProductTypeId()).orElse(null),
                dto.getAccountNumber().trim(),
                dto.getCurrency(),
                dto.getAmount(),
                new Date(),
                dto.getBranch(),
                customerRepository.findById(dto.getCustomerId()).orElse(null),
                CustomerAccountStatus.ACTIVE
        );
    }

    public static CreateCustomerAccountResponse mapperToDto(CustomerAccount model) {
        return new CreateCustomerAccountResponse(model.getId(),
                CreateCustomerAccountProductTypeMapper.mapperToDto(model.getProductType()),
                model.getAccountNumber(),
                model.getCurrency(),
                model.getAmount(),
                model.getCreationDate(),
                model.getBranch(),
                model.getCustomer().getId(),
                CustomerAccountStatus.ACTIVE
        );
    }

}
