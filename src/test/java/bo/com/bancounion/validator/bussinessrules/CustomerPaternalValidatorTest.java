/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator.bussinessrules;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.customer.businessrules.CustomerPaternalValidator;
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
public class CustomerPaternalValidatorTest {

    private CustomerPaternalValidator validator;

    public CustomerPaternalValidatorTest() {
    }

    @BeforeEach
    public void setUp() {
        validator = new CustomerPaternalValidator();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createFailPaternalInvalidMinLength() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setPaternal("P");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_PATERNAL_IS_INVALID.getCode());
    }

    @Test
    public void createFailPaternalInvalidMaxLength() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setPaternal("0123456789012345678901234567891");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_PATERNAL_IS_INVALID.getCode());
    }

    @Test
    public void createSuccessful() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setPaternal("Paco");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

}
