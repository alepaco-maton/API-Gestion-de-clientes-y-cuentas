/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator.bussinessrules;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.validator.customer.businessrules.CustomerGenderValidator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author alepaco.com
 */
@SpringBootTest
public class CustomerGenderValidatorTest {

    private CustomerGenderValidator validator;

    @Autowired
    private IGenderRepository genderRepository;

    public CustomerGenderValidatorTest() {
    }

    @BeforeEach
    public void setUp() {
        validator = new CustomerGenderValidator(genderRepository);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createFailGenderIdRequired() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setGenderId(null);

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_GENDER_ID_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailGenderIdInvalid() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setGenderId(-1);

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_GENDER_ID_IS_INVALID.getCode());
    }

    @Test 
    @Transactional
    public void createSuccessful() throws Exception {
        Gender genderMale = genderRepository.saveAndFlush(new Gender(null, "Masculino"));

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setGenderId(genderMale.getId());

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }
 
}
