/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.controller;

import bo.com.bancounion.controller.dto.customer.CreateCustomerResponse;
import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.controller.dto.customeraccount.CreateCustomerAccountResponse;
import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.controller.dto.customeraccount.ListCustomerAccountResponse;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.entity.ProductType;
import bo.com.bancounion.repository.IBranchRepository;
import bo.com.bancounion.repository.ICurrencyRepository;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.repository.IProductTypeRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import bo.com.bancounion.validator.customeraccount.businessrules.CustomerAccountAmountValidator;
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
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author alepaco.com
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReadCustomerAccountControllerTest {

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

    @Autowired
    private IProductTypeRepository productTypeRepository;

    @Autowired
    private ICurrencyRepository currencyRepository;

    @Autowired
    private IBranchRepository branchRepository;

    private ProductType productType;
    private String currency;
    private String branch;

    private DocumentType documentTypeCarnetIdentidad;
    private Gender genderMale;

    private CreateCustomerResponse customer;

    private CreateCustomerAccountResponse customerAccount;

    Calendar calendar = Calendar.getInstance();
    Date birthdate;

    public ReadCustomerAccountControllerTest() {
    }

    @BeforeEach
    @Transactional
    public void setUp() throws Exception {
        productType = productTypeRepository.saveAndFlush(new ProductType(null, "Caja de ahorros"));

        currency = currencyRepository.findAll().get(0);
        branch = branchRepository.findAll().get(0);

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

    public CreateCustomerAccountResponse createCustomerAccount() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency(currency);
        request.setAmount(CustomerAccountAmountValidator.AMOUNT_MIN_VALUE.
                add(BigDecimal.ONE));
        request.setBranch(branch);
        request.setCustomerId(customer.getId());

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.CREATED.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        return objectmapper.readValue(
                jsonResponse, CreateCustomerAccountResponse.class);
    }

    @Test
    public void findByCustomerIdWithoutAccountes() throws Exception {
        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerAccountController.API_RESOURCE + "?customerId=" + customer.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        List<ListCustomerAccountResponse> response = objectmapper.readValue(
                jsonResponse, List.class);

        assertNotNull(response);
        Assertions.assertEquals(0, response.size());
    }

    @Test
    public void findByCustomerIdAll() throws Exception {
        customerAccount = createCustomerAccount();

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerAccountController.API_RESOURCE + "?customerId=" + customer.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        List<ListCustomerAccountResponse> response = objectmapper.readValue(
                jsonResponse, List.class);

        assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertTrue(jsonResponse.contains("{\"id\":" + customerAccount.getId() + ","));
        Assertions.assertTrue(jsonResponse.contains("\"customerId\":" + customerAccount.getCustomerId() + "}"));
    }

}
