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
public class CustomerIdentityDocumentValidator implements IValidator<CreateUpdateCustomerRequest> {
     
    public static final int IDENTITY_DOCUMENT_MIN_LENGHT = 5;
    public static final int IDENTITY_DOCUMENT_MAX_LENGHT = 20;

    @Override
    public ErrorCode validate(CreateUpdateCustomerRequest request) {
        if (AppTools.isBlank(request.getIdentityDocument())) {
            return ErrorCode.CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_REQUIRED;
        }

        int length = request.getIdentityDocument().trim().length();
        
        if (length < IDENTITY_DOCUMENT_MIN_LENGHT || length > IDENTITY_DOCUMENT_MAX_LENGHT) {
            return ErrorCode.CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_INVALID;
        }
 
        return ErrorCode.SUCCESSFUL;
    }
    
}
