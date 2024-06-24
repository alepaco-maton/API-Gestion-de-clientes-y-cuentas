/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator.bussinessrules;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.validator.customer.businessrules.CustomerLastNameValidator;
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
public class CustomerLastNameValidatorTest {

    private CustomerLastNameValidator validator;

    public CustomerLastNameValidatorTest() {
    }

    @BeforeEach
    public void setUp() {
        validator = new CustomerLastNameValidator();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createFailPaternalIsBlanckAndMaternalIsBlanck() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setPaternal(null);
        request.setMaternal(null);

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_PATERNAL_OR_MATERNAL_IS_REQUIRED.getCode());
    }

    @Test
    public void createSuccessfulPaternalIsNotBlanckAndMaternalIsBlanck() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setPaternal("Paco");
        request.setMaternal(null);

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

    @Test
    public void createSuccessfulPaternalIsBlanckAndMaternalIsNotBlanck() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setPaternal(null);
        request.setMaternal("Surco");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

    @Test
    public void createSuccessfulPaternalIsNotBlanckAndMaternalIsNotBlanck() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setPaternal("Paco");
        request.setMaternal("Surco");

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

}
