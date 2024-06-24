/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.services;

import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.enums.CustomerStatus;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.exception.ExceptionNotFoundResponse;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import bo.com.bancounion.service.customer.DeleteCustomerService;
import bo.com.bancounion.validator.customer.DeleteCustomerValidator;
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
public class DeleteCustomerServiceTest {

    private DeleteCustomerService service;

    @Mock
    private DeleteCustomerValidator validator;

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

    public DeleteCustomerServiceTest() {
    }

    @BeforeEach
    @Transactional
    public void setUp() {
        service = new DeleteCustomerService(mlms, validator, repository);
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
    public void createFailCustomerIdNotFound() throws Exception {
        Mockito.when(validator.validate(-1)).
                thenReturn(ErrorCode.DELETE_CUSTOMER_ID_NOT_FOUND);

        ExceptionNotFoundResponse ex = assertThrows(
                ExceptionNotFoundResponse.class,
                () -> service.delete(-1));

        assertEquals(ex.getCode(),
                ErrorCode.DELETE_CUSTOMER_ID_NOT_FOUND.getCode());
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

        Mockito.when(validator.validate(model.getId())).
                thenReturn(ErrorCode.SUCCESSFUL);

        service.delete(model.getId());

        Customer modelDelete = repository.findByStatusEqualsAndId(
                CustomerStatus.DELETE, model.getId());

        assertEquals(model.getName(), modelDelete.getName());
    }

}
