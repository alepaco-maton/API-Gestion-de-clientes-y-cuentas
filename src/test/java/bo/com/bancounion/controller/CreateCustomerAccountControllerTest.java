/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.controller;

import bo.com.bancounion.controller.dto.customer.CreateCustomerResponse;
import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.controller.dto.customeraccount.ListCustomerAccountResponse;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.controller.exception.AppException;
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
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class CreateCustomerAccountControllerTest {

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

    Calendar calendar = Calendar.getInstance();
    Date birthdate;

    public CreateCustomerAccountControllerTest() {
    }

    @BeforeEach
    @Transactional
    public void setUp() {
        productType = productTypeRepository.saveAndFlush(new ProductType(null, "Caja de ahorros"));

        currency = currencyRepository.findAll().get(0);
        branch = branchRepository.findAll().get(0);

        documentTypeCarnetIdentidad = documentTypeRepository.saveAndFlush(new DocumentType(null, "Carnet Identidad"));

        genderMale = genderRepository.saveAndFlush(new Gender(null, "Masculino"));

        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -19);

        birthdate = calendar.getTime();
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
    public void createFailProductTypeIdRequired() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(null);
        request.setAccountNumber(null);
        request.setCurrency(null);
        request.setAmount(null);
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ErrorCode.CREATE_CUSTOMER_ACCOUNT_PRODUCT_TYPE_ID_IS_REQUIRED.getCode(),
                ex.getCode());
    }

    @Test
    public void createFailProductTypeIdInvalidNotFound() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(-1);
        request.setAccountNumber(null);
        request.setCurrency(null);
        request.setAmount(null);
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_PRODUCT_TYPE_ID_IS_INVALID.getCode());
    }

    @Test
    public void createFailAccountNumberRequired() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber(null);
        request.setCurrency(null);
        request.setAmount(null);
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_ACCOUNT_NUMBER_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailAccountNumberInvalidMinLength() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("012345678");
        request.setCurrency(null);
        request.setAmount(null);
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_ACCOUNT_NUMBER_IS_INVALID.getCode());
    }

    @Test
    public void createFailAccountNumberInvalidMaxLength() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("0123456789012345678901234567891");
        request.setCurrency(null);
        request.setAmount(null);
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_ACCOUNT_NUMBER_IS_INVALID.getCode());
    }

    @Test
    public void createFailCurrencyRequired() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency(null);
        request.setAmount(null);
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_CURRENCY_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailCurrencyInvalid() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency("BSCHIS");
        request.setAmount(null);
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_CURRENCY_IS_INVALID.getCode());
    }

    @Test
    public void createFailAmountRequired() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency(currency);
        request.setAmount(null);
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_AMOUNT_AMOUNT_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailAmountInvalidMinValue() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency(currency);
        request.setAmount(CustomerAccountAmountValidator.AMOUNT_MIN_VALUE.
                subtract(BigDecimal.ONE));
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_AMOUNT_AMOUNT_IS_INVALID.getCode());
    }

    @Test
    public void createFailAmountInvalidMaxValue() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency(currency);
        request.setAmount(CustomerAccountAmountValidator.AMOUNT_MAX_VALUE.
                add(BigDecimal.ONE));
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_AMOUNT_AMOUNT_IS_INVALID.getCode());
    }

    @Test
    public void createFailBranchRequired() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency(currency);
        request.setAmount(CustomerAccountAmountValidator.AMOUNT_MIN_VALUE.
                add(BigDecimal.ONE));
        request.setBranch(null);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_BRANCH_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailBranchInvalid() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency(currency);
        request.setAmount(CustomerAccountAmountValidator.AMOUNT_MIN_VALUE.
                add(BigDecimal.ONE));
        request.setBranch("nuevo departamento de bolivia");
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_BRANCH_IS_INVALID.getCode());
    }

    @Test
    public void createFailCustomerIdRequired() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency(currency);
        request.setAmount(CustomerAccountAmountValidator.AMOUNT_MIN_VALUE.
                add(BigDecimal.ONE));
        request.setBranch(branch);
        request.setCustomerId(null);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_CUSTOMER_ID_IS_REQUIRED.getCode());
    }

    @Test
    public void createFailCustomerIdInvalid() throws Exception {
        CreateUpdateCustomerAccountRequest request = new CreateUpdateCustomerAccountRequest();
        request.setProductTypeId(productType.getId());
        request.setAccountNumber("2345678901234567890123");
        request.setCurrency(currency);
        request.setAmount(CustomerAccountAmountValidator.AMOUNT_MIN_VALUE.
                add(BigDecimal.ONE));
        request.setBranch(branch);
        request.setCustomerId(-1);

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.post(
                                CustomerAccountController.API_RESOURCE)
                                .content(objectmapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        AppException ex = objectmapper.readValue(jsonResponse, AppException.class);

        assertEquals(ex.getCode(),
                ErrorCode.CREATE_CUSTOMER_ACCOUNT_CUSTOMER_ID_IS_INVALID.getCode());
    }

    @Test
    public void createSuccessful() throws Exception {
        CreateCustomerResponse customer = createCustomer();

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

        ListCustomerAccountResponse response = objectmapper.readValue(
                jsonResponse, ListCustomerAccountResponse.class);

        assertNotNull(response);
    }

}
