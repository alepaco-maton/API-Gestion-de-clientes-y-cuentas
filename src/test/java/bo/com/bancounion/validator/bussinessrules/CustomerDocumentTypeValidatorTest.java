/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator.bussinessrules;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.validator.customer.businessrules.CustomerDocumentTypeValidator;
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
public class CustomerDocumentTypeValidatorTest {

    private CustomerDocumentTypeValidator validator;

    @Autowired
    private IDocumentTypeRepository documentTypeRepository;

    public CustomerDocumentTypeValidatorTest() {
    }

    @BeforeEach
    public void setUp() {
        validator = new CustomerDocumentTypeValidator(documentTypeRepository);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createFailDocumentTypeIdRequired() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setDocumentTypeId(null);

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_DOCUMENT_TYPE_ID_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailDocumentTypeIdInvalidIdNotFound() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setDocumentTypeId(-1);

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_DOCUMENT_TYPE_ID_IS_INVALID.getCode());
    }

    @Test
    @Transactional
    public void createSuccessful() {
        DocumentType documentTypeCarnetIdentidad
                = documentTypeRepository.saveAndFlush(
                        new DocumentType(null, "Carnet Identidad"));

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());

        ErrorCode ex = validator.validate(request);

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

}
