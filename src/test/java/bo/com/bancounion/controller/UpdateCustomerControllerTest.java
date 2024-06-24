/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.controller;

import bo.com.bancounion.controller.dto.customer.CreateCustomerResponse;
import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.controller.exception.AppException;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import jakarta.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author alepaco.com
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UpdateCustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectmapper;

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

    CreateCustomerResponse customer;

    public UpdateCustomerControllerTest() {
    }

    @BeforeEach
    @Transactional
    public void setUp() throws Exception {
        documentTypeCarnetIdentidad = documentTypeRepository.saveAndFlush(new DocumentType(null, "Carnet Identidad"));

        genderMale = genderRepository.saveAndFlush(new Gender(null, "Masculino"));

        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -19);

        birthdate = calendar.getTime();

        customer = createCustomer();
    }

    @AfterEach
    public void tearDown() {
    }

    public CreateCustomerResponse createCustomer() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(birthdate);
        request.setGenderId(genderMale.getId());

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.CREATED.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));
 
        return objectmapper.readValue(jsonResponse, CreateCustomerResponse.class);
    }

    @Test
    public void updateFailIdNotFound() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/999999")
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.NOT_FOUND.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.UPDATE_CUSTOMER_ID_NOT_FOUND.getCode());
    }

    @Test
    public void updateFailNameRequiredNull() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName(null);
        request.setPaternal(null);
        request.setMaternal(null);
        request.setDocumentTypeId(null);
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED.getCode());
    }

    @Test
    public void updateFailNameRequiredEmpty() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("");
        request.setPaternal(null);
        request.setMaternal(null);
        request.setDocumentTypeId(null);
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED.getCode());
    }

    @Test
    public void updateFailNameRequiredWithSpace() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("  ");
        request.setPaternal(null);
        request.setMaternal(null);
        request.setDocumentTypeId(null);
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_REQUIRED.getCode());
    }

    @Test
    public void updateFailNameInvalidMaxLength() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("0123456789012345678901234567890123456789012345678912");
        request.setPaternal(null);
        request.setMaternal(null);
        request.setDocumentTypeId(null);
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_NAME_IS_INVALID.getCode());
    }

    @Test
    public void updateFailPaternalOrMaternalRequired() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal(null);
        request.setMaternal(null);
        request.setDocumentTypeId(null);
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_PATERNAL_OR_MATERNAL_IS_REQUIRED.getCode());
    }

    @Test
    public void updateFailPaternalInvalidMaxLength() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("0123456789012345678901234567891");
        request.setMaternal(null);
        request.setDocumentTypeId(null);
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_PATERNAL_IS_INVALID.getCode());
    }

    @Test
    public void updateFailMaternalInvalidMaxLength() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal(null);
        request.setMaternal("0123456789012345678901234567891");
        request.setDocumentTypeId(null);
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_MATERNAL_IS_INVALID.getCode());
    }

    @Test
    public void updateFailDocumentTypeIdRequired() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(null);
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_DOCUMENT_TYPE_ID_IS_REQUIRED.getCode());
    }

    @Test
    public void updateFailDocumentTypeIdInvalidIdNotFound() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(-1);
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_DOCUMENT_TYPE_ID_IS_INVALID.getCode());
    }

    @Test
    public void updateFailIdentityDocumentRequired() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument(null);
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_REQUIRED.getCode());
    }

    @Test
    public void updateFailIdentityDocumentInvalidMinLength() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("1234");
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_INVALID.getCode());
    }

    @Test
    public void updateFailIdentityDocumentInvalidMaxLength() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("012345678901234567891");
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_IDENTITY_DOCUMENT_IS_INVALID.getCode());
    }

    @Test
    public void updateFailBirthDateRequired() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(null);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_BIRTH_DATE_IS_REQUIRED.getCode());
    }

    @Test
    public void updateFailBirthDateInvalidLess18YearOlds() throws Exception {
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1); // debe ser mayor de 18 anios

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(calendar.getTime());
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_BIRTH_DATE_IS_INVALID_USER_MUST_BE_AT_LEAST_18_YEARS_OLD.getCode());
    }

    @Test
    public void updateFailBirthDateInvalidCannotBeInTheFuture() throws Exception {
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1); // no existen fechas futuras

        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(calendar.getTime());
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_BIRTH_DATE_IS_INVALID_CANNOT_BE_IN_THE_FUTURE.getCode());
    }

    @Test
    public void updateFailGenderIdRequired() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(birthdate);
        request.setGenderId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_GENDER_ID_IS_REQUIRED.getCode());
    }

    @Test
    public void updateFailGenderIdInvalid() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(birthdate);
        request.setGenderId(-1);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_GENDER_ID_IS_INVALID.getCode());
    }

    @Test
    public void updateSuccessfulWithOnlyPaternalAndMaternalNull() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal(null);
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(birthdate);
        request.setGenderId(genderMale.getId());

        int status = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(status, HttpStatus.OK.value());
    }

    @Test
    public void updateSuccessfulWithOnlyMaternalAndPaternalNull() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal(null);
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(birthdate);
        request.setGenderId(genderMale.getId());

        int status = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(status, HttpStatus.OK.value());
    }

    @Test
    public void updateSuccessful() throws Exception {
        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
        request.setName("Alex " + UUID.randomUUID().toString());
        request.setPaternal("Paco");
        request.setMaternal("Surco");
        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
        request.setIdentityDocument("7721904");
        request.setBirthDate(birthdate);
        request.setGenderId(genderMale.getId());

        int status = mockMvc
                .perform(
                        MockMvcRequestBuilders.put(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(status, HttpStatus.OK.value());
    }

}
