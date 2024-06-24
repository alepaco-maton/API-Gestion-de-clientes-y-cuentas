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
public class CustomerPaternalValidator implements IValidator<CreateUpdateCustomerRequest> {

    public static final int PATERNAL_MIN_LENGHT = 2;
    public static final int PATERNAL_MAX_LENGHT = 30;

    @Override
    public ErrorCode validate(CreateUpdateCustomerRequest request) {
        if (!AppTools.isBlank(request.getPaternal())) {
            int length = request.getPaternal().trim().length();

            if (length < PATERNAL_MIN_LENGHT || length > PATERNAL_MAX_LENGHT) {
                return ErrorCode.CREATE_CUSTOMER_PATERNAL_IS_INVALID;
            }
        }

        return ErrorCode.SUCCESSFUL;
    }

}
