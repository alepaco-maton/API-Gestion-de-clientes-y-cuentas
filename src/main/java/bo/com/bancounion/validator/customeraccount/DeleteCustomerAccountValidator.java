/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customeraccount;

import bo.com.bancounion.entity.CustomerAccount;
import bo.com.bancounion.enums.CustomerAccountStatus;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.ICustomerAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author alepaco.com
 */
@AllArgsConstructor
@Component
public class DeleteCustomerAccountValidator {

    @Autowired
    ICustomerAccountRepository repository;

    public ErrorCode validate(int id) {
        CustomerAccount model = repository.findByStatusEqualsAndId(CustomerAccountStatus.ACTIVE, id);

        if (model == null) {
            return ErrorCode.DELETE_CUSTOMER_ACCOUNT_CUSTOMER_ID_NOT_FOUND;
        }

        return ErrorCode.SUCCESSFUL;
    }
}
