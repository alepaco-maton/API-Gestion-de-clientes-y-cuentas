/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customeraccount.businessrules;

import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.validator.IValidator;

/**
 *
 * @author alepaco.com
 */
public class CustomerAccountCustomerValidator implements IValidator<CreateUpdateCustomerAccountRequest> {

    private final ICustomerRepository customerRepository;

    public CustomerAccountCustomerValidator(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ErrorCode validate(CreateUpdateCustomerAccountRequest request) {
        if (request.getCustomerId() == null) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_CUSTOMER_ID_IS_REQUIRED;
        }

        Customer model = customerRepository.findById(
                request.getCustomerId()).orElse(null);

        if (model == null) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_CUSTOMER_ID_IS_INVALID;
        }

        return ErrorCode.SUCCESSFUL;
    }

}
