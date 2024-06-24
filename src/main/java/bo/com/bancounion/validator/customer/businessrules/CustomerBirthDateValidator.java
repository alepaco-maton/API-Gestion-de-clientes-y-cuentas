/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customer.businessrules;

import bo.com.bancounion.commons.AppTools;
import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.IValidator;
import java.time.LocalDate;

/**
 *
 * @author alepaco.com
 */
public class CustomerBirthDateValidator implements IValidator<CreateUpdateCustomerRequest> {

    private static final int MINIMUM_AGE_ALLOWED = 18;

    @Override
    public ErrorCode validate(CreateUpdateCustomerRequest request) {
        if (request.getBirthDate() == null) {
            return ErrorCode.CREATE_CUSTOMER_BIRTH_DATE_IS_REQUIRED;
        }

        LocalDate birthdate = AppTools.convertToDateToLocalDate(request.getBirthDate());

        // Check if the birthdate is in the past
        LocalDate today = LocalDate.now();
        if (birthdate.isAfter(today)) {
            return ErrorCode.CREATE_CUSTOMER_BIRTH_DATE_IS_INVALID_CANNOT_BE_IN_THE_FUTURE;
        }

        // Calculate the age based on the birthdate
        int age = AppTools.calculateAge(birthdate);
        if (age < MINIMUM_AGE_ALLOWED) {
            return ErrorCode.CREATE_CUSTOMER_BIRTH_DATE_IS_INVALID_USER_MUST_BE_AT_LEAST_18_YEARS_OLD;
        }

        return ErrorCode.SUCCESSFUL;
    }

}
