/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.services;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.enums.CustomerStatus;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.exception.ExceptionNotFoundResponse;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import bo.com.bancounion.service.customer.UpdateCustomerService;
import bo.com.bancounion.validator.customer.UpdateCustomerValidator;
import jakarta.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author alepaco.com
 */
@SpringBootTest
public class UpdateCustomerServiceTest {

    private UpdateCustomerService service;

    @Mock
    private UpdateCustomerValidator validator;

    @Mock
    private MultiLanguageMessagesService mlms;

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

    public UpdateCustomerServiceTest() {
    }

    @BeforeEach
    @Transactional
    public void setUp() {
        service = new UpdateCustomerService(mlms, validator, repository,
                documentTypeRepository, genderRepository);
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
    public void createFailAnyErrorCode() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();

        Mockito.when(validator.validate(request, 1)).
                thenReturn(ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.update(request, 1));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailCustomerIdNotFound() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();

        Mockito.when(validator.validate(request, -1)).
                thenReturn(ErrorCode.UPDATE_CUSTOMER_ID_NOT_FOUND);

        ExceptionNotFoundResponse ex = assertThrows(
                ExceptionNotFoundResponse.class,
                () -> service.update(request, -1));

        assertEquals(ex.getCode(),
                ErrorCode.UPDATE_CUSTOMER_ID_NOT_FOUND.getCode());
    }

    @Test
    @Transactional
    public void createSuccessful() throws Exception {
        documentTypeCarnetIdentidad = documentTypeRepository.saveAndFlush(
                new DocumentType(null, "Carnet Identidad"));

        genderMale = genderRepository.saveAndFlush(new Gender(null, "Masculino"));
 
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -19);

        birthdate = calendar.getTime();
        
        Customer model = createCustomer();

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(birthdate);
        request.setGenderId(genderMale.getId());

        Mockito.when(validator.validate(request, model.getId())).
                thenReturn(ErrorCode.SUCCESSFUL);

        service.update(request, model.getId());

        model = repository.findByStatusEqualsAndId(
                CustomerStatus.ACTIVE, model.getId());

        assertEquals(request.getName(), model.getName());
    }

}
