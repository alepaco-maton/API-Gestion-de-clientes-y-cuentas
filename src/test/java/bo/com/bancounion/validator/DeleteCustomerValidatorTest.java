/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.validator;

import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.enums.CustomerStatus;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.validator.customer.DeleteCustomerValidator;
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
public class DeleteCustomerValidatorTest {

    private DeleteCustomerValidator validator;

    @Autowired
    private ICustomerRepository repository;

    @Autowired
    private IGenderRepository genderRepository;

    @Autowired
    private IDocumentTypeRepository documentTypeRepository;

    public DeleteCustomerValidatorTest() {
    }

    @BeforeEach
    @Transactional
    public void setUp() {
        validator = new DeleteCustomerValidator(repository);
    }

    @AfterEach
    public void tearDown() {
    }

    @Transactional
    public Customer createCustomer() {
        DocumentType documentTypeCarnetIdentidad = documentTypeRepository.saveAndFlush(
                new DocumentType(null, "Carnet Identidad"));

        Gender genderMale = genderRepository.saveAndFlush(new Gender(null, "Masculino"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -19);

        Date birthdate = calendar.getTime();

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
        ErrorCode ex = validator.validate(-1);

        assertEquals(ex.getCode(),
                ErrorCode.DELETE_CUSTOMER_ID_NOT_FOUND.getCode());
    }

    @Test
    @Transactional
    public void createSuccessful() {
        Customer model = createCustomer();

        ErrorCode ex = validator.validate(model.getId());

        assertEquals(ex.getCode(),
                ErrorCode.SUCCESSFUL.getCode());
    }

}
