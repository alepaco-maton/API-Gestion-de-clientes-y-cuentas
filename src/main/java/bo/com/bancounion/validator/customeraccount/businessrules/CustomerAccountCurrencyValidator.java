/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customeraccount.businessrules;
 
import bo.com.bancounion.commons.AppTools;
import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.ICurrencyRepository;
import bo.com.bancounion.validator.IValidator;
import java.util.List;


/**
 *
 * @author alepaco.com
 */
public class CustomerAccountCurrencyValidator implements IValidator<CreateUpdateCustomerAccountRequest> {
    
    private final ICurrencyRepository currencyRepository;

    public CustomerAccountCurrencyValidator(ICurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    
    @Override
    public ErrorCode validate(CreateUpdateCustomerAccountRequest request) {
        if (AppTools.isBlank(request.getCurrency())) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_CURRENCY_IS_REQUIRED;
        }
        
        List<String> currencies = currencyRepository.findAll();

        if (!currencies.contains(request.getCurrency())) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_CURRENCY_IS_INVALID;
        }

        return ErrorCode.SUCCESSFUL;
    }
    
}
