/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customer.businessrules;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.validator.IValidator; 

/**
 *
 * @author alepaco.com
 */
public class CustomerGenderValidator implements IValidator<CreateUpdateCustomerRequest> {
    
    private final IGenderRepository genderRepository;

    public CustomerGenderValidator(IGenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }
    
    @Override
    public ErrorCode validate(CreateUpdateCustomerRequest request) {
        if (request.getGenderId() == null) {
            return ErrorCode.CREATE_CUSTOMER_GENDER_ID_IS_REQUIRED;
        }
        
        Gender model = genderRepository.findById(
                request.getGenderId()).orElse(null);

        if (model == null) {
            return ErrorCode.CREATE_CUSTOMER_GENDER_ID_IS_INVALID;
        }

        return ErrorCode.SUCCESSFUL;
    }
    
}
