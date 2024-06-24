/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customer;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.validator.IValidator;
import bo.com.bancounion.validator.customer.businessrules.CustomerBirthDateValidator;
import bo.com.bancounion.validator.customer.businessrules.CustomerDocumentTypeValidator;
import bo.com.bancounion.validator.customer.businessrules.CustomerGenderValidator;
import bo.com.bancounion.validator.customer.businessrules.CustomerIdentityDocumentValidator;
import bo.com.bancounion.validator.customer.businessrules.CustomerLastNameValidator;
import bo.com.bancounion.validator.customer.businessrules.CustomerMaternalValidator;
import bo.com.bancounion.validator.customer.businessrules.CustomerNameValidator;
import bo.com.bancounion.validator.customer.businessrules.CustomerPaternalValidator;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author alepaco.com
 */
@AllArgsConstructor
@Component
public class CreateCustomerValidator {

    @Autowired
    IGenderRepository genderRepository;

    @Autowired
    IDocumentTypeRepository documentTypeRepository;

    public ErrorCode validate(CreateUpdateCustomerRequest request) {
        List<IValidator<CreateUpdateCustomerRequest>> validators = new ArrayList<>();
        validators.add(new CustomerNameValidator());
        validators.add(new CustomerLastNameValidator());
        validators.add(new CustomerPaternalValidator());
        validators.add(new CustomerMaternalValidator());
        validators.add(new CustomerDocumentTypeValidator(documentTypeRepository));
        validators.add(new CustomerIdentityDocumentValidator());
        validators.add(new CustomerBirthDateValidator());
        validators.add(new CustomerGenderValidator(genderRepository));

        for (IValidator val : validators) {
            ErrorCode errorCode = val.validate(request);

            if (!errorCode.isSuccessfull()) {
                return errorCode;
            }
        }

        return ErrorCode.SUCCESSFUL;
    }
}
