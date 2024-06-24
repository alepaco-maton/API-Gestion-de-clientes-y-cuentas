/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customeraccount.businessrules;

import bo.com.bancounion.commons.AppTools;
import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.IValidator;
import java.math.BigDecimal;

/**
 *
 * @author alepaco.com
 */
public class CustomerAccountAmountValidator implements IValidator<CreateUpdateCustomerAccountRequest> {

    public static final String AMOUNT_MIN_VALUE_STR = "0";
    public static final BigDecimal AMOUNT_MIN_VALUE = new BigDecimal(AMOUNT_MIN_VALUE_STR);
    public static final String AMOUNT_MAX_VALUE_STR = "1000000";
    public static final BigDecimal AMOUNT_MAX_VALUE = new BigDecimal(AMOUNT_MAX_VALUE_STR);

    @Override
    public ErrorCode validate(CreateUpdateCustomerAccountRequest request) {
        if (request.getAmount() == null) {
            return ErrorCode.CREATE_CUSTOMER_AMOUNT_AMOUNT_IS_REQUIRED;
        }
        
        double amount = AppTools.reoundAmountForCustomerAccount(request.getAmount()).doubleValue();
        
        if (amount < AMOUNT_MIN_VALUE.doubleValue() || amount > AMOUNT_MAX_VALUE.doubleValue()) {
            return ErrorCode.CREATE_CUSTOMER_AMOUNT_AMOUNT_IS_INVALID;
        }

        return ErrorCode.SUCCESSFUL;
    }

}
