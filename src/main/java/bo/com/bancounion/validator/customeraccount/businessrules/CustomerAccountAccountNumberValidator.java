/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customeraccount.businessrules;

import bo.com.bancounion.commons.AppTools;
import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.IValidator;

/**
 *
 * @author alepaco.com
 */
public class CustomerAccountAccountNumberValidator implements IValidator<CreateUpdateCustomerAccountRequest> {

    public static final int ACCOUNT_NUMBER_MIN_LENGHT = 10;
    public static final int ACCOUNT_NUMBER_MAX_LENGHT = 30;

    @Override
    public ErrorCode validate(CreateUpdateCustomerAccountRequest request) {
        if (AppTools.isBlank(request.getAccountNumber())) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_ACCOUNT_NUMBER_IS_REQUIRED;
        }
        

        int length = request.getAccountNumber().trim().length();

        if (length < ACCOUNT_NUMBER_MIN_LENGHT || length > ACCOUNT_NUMBER_MAX_LENGHT) {
            return ErrorCode.CREATE_CUSTOMER_ACCOUNT_ACCOUNT_NUMBER_IS_INVALID;
        }
        
        return ErrorCode.SUCCESSFUL;
    }

}
