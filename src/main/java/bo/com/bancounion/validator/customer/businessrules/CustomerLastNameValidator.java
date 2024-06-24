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
public class CustomerLastNameValidator implements IValidator<CreateUpdateCustomerRequest> {
 
    @Override
    public ErrorCode validate(CreateUpdateCustomerRequest request) {
         if (AppTools.isBlank(request.getPaternal()) && 
                 AppTools.isBlank(request.getMaternal())) {
            return ErrorCode.CREATE_CUSTOMER_PATERNAL_OR_MATERNAL_IS_REQUIRED;
        }
 
        return ErrorCode.SUCCESSFUL;
    }

}
