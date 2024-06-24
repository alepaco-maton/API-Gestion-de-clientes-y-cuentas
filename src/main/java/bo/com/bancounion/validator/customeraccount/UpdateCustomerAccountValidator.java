/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customeraccount;

import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.entity.CustomerAccount;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.IBranchRepository;
import bo.com.bancounion.repository.ICurrencyRepository;
import bo.com.bancounion.repository.ICustomerAccountRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IProductTypeRepository;
import bo.com.bancounion.validator.IValidator;
import bo.com.bancounion.validator.customeraccount.businessrules.CustomerAccountAccountNumberValidator;
import bo.com.bancounion.validator.customeraccount.businessrules.CustomerAccountAmountValidator;
import bo.com.bancounion.validator.customeraccount.businessrules.CustomerAccountBranchValidator;
import bo.com.bancounion.validator.customeraccount.businessrules.CustomerAccountCurrencyValidator;
import bo.com.bancounion.validator.customeraccount.businessrules.CustomerAccountCustomerValidator;
import bo.com.bancounion.validator.customeraccount.businessrules.CustomerAccountProductTypeValidator;
import java.util.Optional;

/**
 *
 * @author alepaco.com
 */
@AllArgsConstructor
@Component
public class UpdateCustomerAccountValidator {

    @Autowired
    ICustomerAccountRepository repository;

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IProductTypeRepository productTypeRepository;

    @Autowired
    ICurrencyRepository currencyRepository;

    @Autowired
    IBranchRepository branchRepository;

    public ErrorCode validate(CreateUpdateCustomerAccountRequest request, int id) {
        Optional<CustomerAccount> optional = repository.findById(id);

        if (!optional.isPresent()) {
            return ErrorCode.UPDATE_CUSTOMER_ACCOUNT_ID_IS_NOT_FOUND;
        }

        List<IValidator<CreateUpdateCustomerAccountRequest>> validators = new ArrayList<>();
        validators.add(new CustomerAccountProductTypeValidator(productTypeRepository));
        validators.add(new CustomerAccountAccountNumberValidator());
        validators.add(new CustomerAccountCurrencyValidator(currencyRepository));
        validators.add(new CustomerAccountAmountValidator());
        validators.add(new CustomerAccountBranchValidator(branchRepository));
        validators.add(new CustomerAccountCustomerValidator(customerRepository));

        for (IValidator val : validators) {
            ErrorCode errorCode = val.validate(request);

            if (!errorCode.isSuccessfull()) {
                return errorCode;
            }
        }

        return ErrorCode.SUCCESSFUL;
    }
}
