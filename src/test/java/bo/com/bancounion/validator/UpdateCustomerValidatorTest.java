/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.enums.CustomerStatus;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.validator.customer.UpdateCustomerValidator;
import jakarta.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author alepaco.com
 */
@SpringBootTest
public class UpdateCustomerValidatorTest {

    private UpdateCustomerValidator validator;

    @Autowired
    private ICustomerRepository repository;

    @Autowired
    private IGenderRepository genderRepository;

    @Autowired
    private IDocumentTypeRepository documentTypeRepository;

    private DocumentType documentTypeCarnetIdentidad;
    private Gender genderMale;

    Calendar calendar = Calendar.getInstance();
    Date birthdate;

    public UpdateCustomerValidatorTest() {
    }

    @BeforeEach
    @Transactional
    public void setUp() {
        validator = new UpdateCustomerValidator(repository, genderRepository,
                documentTypeRepository);

        documentTypeCarnetIdentidad = documentTypeRepository.saveAndFlush(
                new DocumentType(null, "Carnet Identidad"));

        genderMale = genderRepository.saveAndFlush(new Gender(null, "Masculino"));

        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -19);

        birthdate = calendar.getTime();
    }

    @AfterEach
    public void tearDown() {
    }

    @Transactional
    public Customer createCustomer() {
        Customer model = new Customer(null, "Alex " + UUID.randomUUID().toString(),
                "Paco", "Surco",
                documentTypeCarnetIdentidad,
                "7721904",
                birthdate,
                genderMale, CustomerStatus.ACTIVE);

        return repository.saveAndFlush(model);
    }

    @Test
    public void createFailIdNotFound() {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();

        ErrorCode ex = validator.validate(request, -1);

        assertEquals(ex.getCode(),
                ErrorCode.UPDATE_CUSTOMER_ID_NOT_FOUND.getCode());
    }

    @Test
    @Transactional
    public void createFailRetrurnAnyCodeError() {
        Customer model = createCustomer();

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();

        ErrorCode ex = validator.validate(request, model.getId());

        assertNotEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

    @Test
    @Transactional
    public void createSuccessful() {
        Customer model = createCustomer();

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Junior " + UUID.randomUUID().toString());
        request.setPaternal("Surco");
        request.setMaternal("Paco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7725984");
        request.setBirthDate(birthdate);
        request.setGenderId(genderMale.getId());

        ErrorCode ex = validator.validate(request, model.getId());

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

}
