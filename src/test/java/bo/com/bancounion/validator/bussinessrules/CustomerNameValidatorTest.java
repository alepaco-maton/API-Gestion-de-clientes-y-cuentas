/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator.bussinessrules;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.customer.businessrules.CustomerNameValidator;
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
public class CustomerNameValidatorTest {

    private CustomerNameValidator validator;

    public CustomerNameValidatorTest() {
    }

    @BeforeEach
    public void setUp() {
        validator = new CustomerNameValidator();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createFailNameRequiredNull() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName(null);

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailNameRequiredEmpty() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailNameRequiredWithSpace() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("  ");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailNameInvalidMinLength() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("A");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_INVALID.getCode());
    }

    @Test
    public void createFailNameInvalidMaxLength() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("0123456789012345678901234567890123456789012345678912");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_INVALID.getCode());
    }

    @Test
    public void createSuccessful() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex Junior");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

}
