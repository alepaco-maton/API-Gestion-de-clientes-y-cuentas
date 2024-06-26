/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.mappers.customeraccount;

import bo.com.bancounion.mappers.producttype.ReadProductTypeMapper;
import bo.com.bancounion.controller.dto.customeraccount.ListCustomerAccountResponse;
import bo.com.bancounion.entity.CustomerAccount;

/**
 *
 * @author alepaco.com
 */
public class ReadCustomerAccountMapper {

    public static ListCustomerAccountResponse mapperToDto(CustomerAccount model) {
        return new ListCustomerAccountResponse(model.getId(),
                ReadProductTypeMapper.mapperToDto(model.getProductType()),
                model.getAccountNumber(),
                model.getCurrency(),
                model.getAmount(),
                model.getCreationDate(),
                model.getBranch(),
                model.getStatus(),
                model.getCustomer().getId()
        );
    }

}
