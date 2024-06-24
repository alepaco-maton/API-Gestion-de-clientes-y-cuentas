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
import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.enums.CustomerStatus;
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
public class DeleteCustomerControllerTest {

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

    public DeleteCustomerControllerTest() {
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
                        MockMvcRequestBuilders.delete(
                                CustomerController.API_RESOURCE + "/999999")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.NOT_FOUND.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.DELETE_CUSTOMER_ID_NOT_FOUND.getCode());
    }
  
    @Test
    public void updateSuccessful() throws Exception {
        int status = mockMvc
                .perform(
                        MockMvcRequestBuilders.delete(
                                CustomerController.API_RESOURCE + "/" + customer.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getStatus();

        Customer modelDelete = repository.findByStatusEqualsAndId(
                CustomerStatus.DELETE, customer.getId());
        
        assertEquals(status, HttpStatus.OK.value());
        assertEquals(customer.getId(), modelDelete.getId());
        
    }

}
