/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator.bussinessrules;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.customer.businessrules.CustomerIdentityDocumentValidator;
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
public class CustomerIdentityDocumentValidatorTest {

    private CustomerIdentityDocumentValidator validator;

    public CustomerIdentityDocumentValidatorTest() {
    }

    @BeforeEach 
    public void setUp() {
        validator = new CustomerIdentityDocumentValidator();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createFailIdentityDocumentRequired() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setIdentityDocument(null);

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailIdentityDocumentInvalidMinLength() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setIdentityDocument("1234");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_INVALID.getCode());
    }

    @Test
    public void createFailIdentityDocumentInvalidMaxLength() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setIdentityDocument("012345678901234567891");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_INVALID.getCode());
    }

    @Test
    public void createSuccessful() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setIdentityDocument("7721904");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

}
