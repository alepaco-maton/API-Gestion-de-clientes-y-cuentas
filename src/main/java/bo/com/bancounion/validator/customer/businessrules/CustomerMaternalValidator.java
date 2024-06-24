/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customer.businessrules;

import bo.com.bancounion.commons.AppTools;
import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.IValidator;

/**
 *
 * @author alepaco.com
 */
public class CustomerMaternalValidator implements IValidator<CreateUpdateCustomerRequest> {

    public static final int MATERNAL_MIN_LENGHT = 2;
    public static final int MATERNAL_MAX_LENGHT = 30;

    @Override
    public ErrorCode validate(CreateUpdateCustomerRequest request) {
        if (!AppTools.isBlank(request.getMaternal())) {
            int length = request.getMaternal().trim().length();

            if (length < MATERNAL_MIN_LENGHT || length > MATERNAL_MAX_LENGHT) {
                return ErrorCode.CREATE_CUSTOMER_MATERNAL_IS_INVALID;
            }
        }

        return ErrorCode.SUCCESSFUL;
    }

}
