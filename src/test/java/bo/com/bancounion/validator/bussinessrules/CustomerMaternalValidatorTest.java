/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator.bussinessrules;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.customer.businessrules.CustomerMaternalValidator;
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
public class CustomerMaternalValidatorTest {

    private CustomerMaternalValidator validator;

    public CustomerMaternalValidatorTest() {
    }

    @BeforeEach
    public void setUp() {
        validator = new CustomerMaternalValidator();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createFailMaternalInvalidMinLength() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setMaternal("S");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_MATERNAL_IS_INVALID.getCode());
    }

    @Test
    public void createFailMaternalInvalidMaxLength() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setMaternal("0123456789012345678901234567891");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_MATERNAL_IS_INVALID.getCode());
    }

    @Test
    public void createSuccessful() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setMaternal("Surco");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

}
