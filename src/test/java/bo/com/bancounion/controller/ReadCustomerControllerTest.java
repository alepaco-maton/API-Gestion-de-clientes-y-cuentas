/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.controller;

import bo.com.bancounion.commons.AppTools;
import bo.com.bancounion.controller.dto.PageTest;
import bo.com.bancounion.controller.dto.customer.CreateCustomerResponse;
import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.controller.dto.customer.ListCustomerResponse;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
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
import java.text.SimpleDateFormat;
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
public class ReadCustomerControllerTest {

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

    public ReadCustomerControllerTest() {
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

    public String formatQueryParameter(String id, String name, String paternal,
            String maternal, String documentType, String identityDocument,
            String birthDate, String gender, String status, String page,
            String size, String sort) {

        StringBuilder url = new StringBuilder("?");

        if (AppTools.isBlank(page)) {
            page = "0";
        }

        if (AppTools.isBlank(size)) {
            size = "2000";
        }

        url.append("page=").append(page).append("&");
        url.append("size=").append(size).append("&");

        if (!AppTools.isBlank(id)) {
            url.append("id=").append(id).append("&");
        }
        if (!AppTools.isBlank(name)) {
            url.append("name=").append(name).append("&");
        }
        if (!AppTools.isBlank(paternal)) {
            url.append("paternal=").append(paternal).append("&");
        }
        if (!AppTools.isBlank(maternal)) {
            url.append("maternal=").append(maternal).append("&");
        }
        if (documentType != null) {
            url.append("documentType=").append(documentType).append("&");
        }
        if (!AppTools.isBlank(identityDocument)) {
            url.append("identityDocument=").append(identityDocument).append("&");
        }
        if (!AppTools.isBlank(birthDate)) {
            url.append("birthDate=").append(birthDate).append("&");
        }
        if (gender != null) {
            url.append("gender=").append(gender).append("&");
        }
        if (!AppTools.isBlank(status)) {
            url.append("status=").append(status).append("&");
        }
        if (!AppTools.isBlank(sort)) {
            url.append("sort=").append(sort);
        }

        return url.toString();
    }

    @Test
    public void listAllWithPageNullAndSizeNull() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = null;
        String size = null;
        String sort = null;

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listAll() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = null;//name,desc

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listOrderByIdAsc() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "id,asc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listByIdAndOrderByIdDesc() throws Exception {
        String id = customer.getId().toString();
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "id,desc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listOrderByNameAsc() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "name,asc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listByIdAndNameAndOrderByNameDesc() throws Exception {
        String id = customer.getId().toString();
        String name = customer.getName();
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "name,desc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listOrderByPaternalAsc() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "paternal,asc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listByIdAndNameAndPaternalAndOrderByPaternalDesc() throws Exception {
        String id = customer.getId().toString();
        String name = customer.getName();
        String paternal = customer.getPaternal();
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "paternal,desc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listOrderByMaternalAsc() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "maternal,asc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listByIdAndNameAndPaternalAndMaternalAndOrderByMaternalDesc() throws Exception {
        String id = customer.getId().toString();
        String name = customer.getName();
        String paternal = customer.getPaternal();
        String maternal = customer.getMaternal();
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "maternal,desc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listByDocumentTypeIdAsc() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "documentType,asc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listByIdAndNameAndPaternalAndMaternalAndDocumentTypeIdAndOrderByDocumentTypeIdDesc() throws Exception {
        String id = customer.getId().toString();
        String name = customer.getName();
        String paternal = customer.getPaternal();
        String maternal = customer.getMaternal();
        String documentType = customer.getDocumentType().getName();
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "documentType,desc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listOrderByIdentityDocumentAsc() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "identityDocument,asc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listByIdAndNameAndPaternalAndMaternalAndDocumentTypeIdAndIdentityDocumentAndOrderByIdentityDocumentDesc() throws Exception {
        String id = customer.getId().toString();
        String name = customer.getName();
        String paternal = customer.getPaternal();
        String maternal = customer.getMaternal();
        String documentType = customer.getDocumentType().getName();
        String identityDocument = customer.getIdentityDocument();
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "identityDocument,desc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listOrderByBirthDateAsc() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "birthDate,asc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listByIdAndNameAndPaternalAndMaternalAndDocumentTypeIdAndIdentityDocumentAndBirthDateAndOrderByBirthDateDesc() throws Exception {
        String id = customer.getId().toString();
        String name = customer.getName();
        String paternal = customer.getPaternal();
        String maternal = customer.getMaternal();
        String documentType = customer.getDocumentType().getName();
        String identityDocument = customer.getIdentityDocument();
        String birthDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(customer.getBirthDate());
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "birthDate,desc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listOrderByGenderAsc() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "gender,asc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listByIdAndNameAndPaternalAndMaternalAndDocumentTypeIdAndIdentityDocumentAndBirthDateAndGenderAndOrderByGenderDesc() throws Exception {
        String id = customer.getId().toString();
        String name = customer.getName();
        String paternal = customer.getPaternal();
        String maternal = customer.getMaternal();
        String documentType = customer.getDocumentType().getName();
        String identityDocument = customer.getIdentityDocument();
        String birthDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(customer.getBirthDate());
        String gender = genderMale.getName();
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "gender,desc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listOrderByStatusAsc() throws Exception {
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        String documentType = null;
        String identityDocument = null;
        String birthDateStr = null;
        String gender = null;
        String status = null;
        String page = "0";
        String size = "2000";
        String sort = "status,asc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

    @Test
    public void listAllAndOrderByStatusDesc() throws Exception {
        String id = customer.getId().toString();
        String name = customer.getName();
        String paternal = customer.getPaternal();
        String maternal = customer.getMaternal();
        String documentType = customer.getDocumentType().getName();
        String identityDocument = customer.getIdentityDocument();
        String birthDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(customer.getBirthDate());
        String gender = genderMale.getName();
        String status = CustomerStatus.ACTIVE;
        String page = "0";
        String size = "2000";
        String sort = "status,desc";

        String jsonResponse = mockMvc
                .perform(
                        MockMvcRequestBuilders.get(
                                CustomerController.API_RESOURCE
                                + formatQueryParameter(id, name, paternal,
                                        maternal, documentType, identityDocument,
                                        birthDateStr, gender, status,
                                        page, size, sort))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().
                        is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        PageTest<ListCustomerResponse> response = objectmapper.readValue(
                jsonResponse, PageTest.class);

        assertNotNull(response);
        Assertions.assertFalse(response.getContent().isEmpty());
        Assertions.assertTrue(jsonResponse.contains(customer.getName()));
    }

}
