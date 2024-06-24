/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator.bussinessrules;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.customer.businessrules.CustomerBirthDateValidator;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author alepaco.com
 */
@SpringBootTest
public class CustomerBirthDateValidatorTest {

    private CustomerBirthDateValidator validator;

    Calendar calendar = Calendar.getInstance();

    public CustomerBirthDateValidatorTest() {
    }

    @BeforeEach
    public void setUp() {
        validator = new CustomerBirthDateValidator();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createOrUpdateFailBirthDateRequired() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setBirthDate(null);

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_BIRTH_DATE_IS_REQUIRED.getCode());
    }

    @Test
    public void createOrUpdateFailBirthDateInvalidLess18YearOlds() {
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1); // debe ser mayor de 18 anios

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setBirthDate(calendar.getTime());

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_BIRTH_DATE_IS_INVALID_USER_MUST_BE_AT_LEAST_18_YEARS_OLD.getCode());
    }

    @Test
    public void createOrUpdateFailBirthDateInvalidCannotBeInTheFuture() {
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1); // no existen fechas futuras

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setBirthDate(calendar.getTime());

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_BIRTH_DATE_IS_INVALID_CANNOT_BE_IN_THE_FUTURE.getCode());
    }

    @Test
    public void createSuccessful() {
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -19);

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setBirthDate(calendar.getTime());

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

}
