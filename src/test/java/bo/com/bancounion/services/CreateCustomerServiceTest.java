/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.services;

import bo.com.bancounion.controller.dto.customer.CreateCustomerResponse;
import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import bo.com.bancounion.service.customer.CreateCustomerService;
import bo.com.bancounion.validator.customer.CreateCustomerValidator;
import jakarta.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
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
public class CreateCustomerServiceTest {

    private CreateCustomerService service;

    @Mock
    private CreateCustomerValidator validator;

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

    public CreateCustomerServiceTest() {
    }

    @BeforeEach
    @Transactional
    public void setUp() {
        service = new CreateCustomerService(mlms, validator, repository,
                documentTypeRepository, genderRepository);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createFailAnyErrorCode() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();

        Mockito.when(validator.validate(request)).
                thenReturn(ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED.getCode());
    }

    @Test
    @Transactional
    public void createSuccessful() throws Exception {
        documentTypeCarnetIdentidad = documentTypeRepository.saveAndFlush(new DocumentType(null, "Carnet Identidad"));

        genderMale = genderRepository.saveAndFlush(new Gender(null, "Masculino"));

        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -19);

        birthdate = calendar.getTime();

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(birthdate);
        request.setGenderId(genderMale.getId());

        Mockito.when(validator.validate(request)).
                thenReturn(ErrorCode.SUCCESSFUL);

        CreateCustomerResponse response = service.create(request);

        assertNotNull(response);
    }

    /*
    @Test
    public void createFailByRequestEmailEmpty() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("");
        request.setOccupation("Cajero");
        request.setTelephone("77652889");
        request.setPersonId(1);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CLIENT_EMAIL_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailByRequestTelephoneNull() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("alepaco.maton@gmail.com");
        request.setOccupation("Cajero");
        request.setTelephone(null);
        request.setPersonId(1);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CLIENT_TELEPHONE_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailByRequestTelephoneEmpty() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("alepaco.maton@gmail.com");
        request.setOccupation("Cajero");
        request.setTelephone("");
        request.setPersonId(1);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CLIENT_TELEPHONE_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailByRequestOccupationNull() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("alepaco.maton@gmail.com");
        request.setOccupation(null);
        request.setTelephone("77325584");
        request.setPersonId(1);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CLIENT_OCCUPATION_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailByRequestOccupationEmpty() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("alepaco.maton@gmail.com");
        request.setOccupation("");
        request.setTelephone("77325584");
        request.setPersonId(1);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CLIENT_OCCUPATION_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailByRequestPersonaIdNull() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("alepaco.maton@gmail.com");
        request.setOccupation("Cajero");
        request.setTelephone("77325584");
        request.setPersonId(null);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CLIENT_PERSON_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailByRequestPersonaIdInvalid() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("alepaco.maton@gmail.com");
        request.setOccupation("Cajero");
        request.setTelephone("77325584");
        request.setPersonId(-1);

        Person person = null;

        Mockito.when(personRepository.findById(request.getPersonId())).
                thenReturn(Optional.ofNullable(person));

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CLIENT_PERSON_IS_INVALID.getCode());
    }

    @Test
    public void createFailByRequestPersonLessThan20YearsOld() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("alepaco.maton@gmail.com");
        request.setOccupation("Cajero");
        request.setTelephone("77325584");
        request.setPersonId(1);

        Calendar cal = Calendar.getInstance();
        // con esto se calcula una fecha de nacimiento menor a 20 años
        cal.add(Calendar.YEAR, -15);

        Date dateOfBirth = cal.getTime();

        Person person = new Person();
        person.setId(request.getPersonId());
        person.setDateOfBirth(dateOfBirth);

        Optional<Person> optional = Optional.of(person);

        Mockito.when(personRepository.findById(request.getPersonId())).
                thenReturn(optional);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CLIENT_PERSON_LESS_THAN_20_YEARS_OLD.getCode());
    }

    @Test
    public void createFailByRequestPersonAssociatedOtherClient() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("alepaco.maton@gmail.com");
        request.setOccupation("Cajero");
        request.setTelephone("77325584");
        request.setPersonId(1);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -20);

        Date dateOfBirth = cal.getTime();

        Person person = new Person();
        person.setId(request.getPersonId());
        person.setDateOfBirth(dateOfBirth);

        Optional<Person> optional = Optional.of(person);

        Mockito.when(personRepository.findById(request.getPersonId())).
                thenReturn(optional);

        List<Client> listClients = new ArrayList<>();
        listClients.add(new Client());

        Mockito.when(clientRepository.findAllByPersonId(
                request.getPersonId())).
                thenReturn(listClients);

        ExceptionResponse ex = assertThrows(
                ExceptionResponse.class,
                () -> service.create(request));

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CLIENT_PERSON_IS_ASSOCIATED_WITH_ANOTHER_CUSTOMER.getCode());
    }

    @Test
    public void createSuccesssful() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setEmail("alepaco.maton@gmail.com");
        request.setOccupation("Cajero");
        request.setTelephone("77325584");
        request.setPersonId(1);

        //PERSONA MAYOR O IGUAL a 20 años (Un cliente no puede tener menos de 20 años.)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -20);

        Date dateOfBirth = cal.getTime();

        Person person = new Person();
        person.setId(request.getPersonId());
        person.setDateOfBirth(dateOfBirth);

        Optional<Person> optional = Optional.of(person);

        Mockito.when(personRepository.findById(request.getPersonId())).
                thenReturn(optional);

        List<Client> listClients = new ArrayList<>();

        Mockito.when(clientRepository.findAllByPersonId(
                request.getPersonId())).
                thenReturn(listClients);

        Client model = new Client();
        model.setId(1);
        model.setPerson(person);
        model.setEmail(request.getEmail());
        model.setStatus(ClientStatus.CREATED);
        model.setOccupation(request.getOccupation());
        model.setTelephone(request.getTelephone());

        Mockito.when(clientRepository.save(
                CreateClientMapper.mapperToEntity(
                        request, personRepository))).
                thenReturn(model);

        CreateClientResponse expResult = new CreateClientResponse();
        expResult.setId(model.getId());

        CreateClientResponse result = service.create(request);

        assertEquals(expResult.getId(), result.getId());
    }*/
}
