/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package bo.com.bancounion.services;

import bo.com.bancounion.controller.dto.customer.ListCustomerResponse;
import bo.com.bancounion.entity.DocumentType;
import bo.com.bancounion.entity.Gender;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.repository.IListCustomerRepository;
import bo.com.bancounion.repository.impl.ListCustomerRepositoryImpl;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import bo.com.bancounion.service.customer.ReadCustomerService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author alepaco.com
 */
//@SpringBootTest
@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReadCustomerServiceTest {

    private ReadCustomerService service;

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    private MultiLanguageMessagesService mlms;

    @Mock
    private IListCustomerRepository listCustomerRepository;

    @Autowired
    private ICustomerRepository repository;

    @Autowired
    private IGenderRepository genderRepository;

    @Autowired
    private IDocumentTypeRepository documentTypeRepository;

    private DocumentType documentTypeCarnetIdentidad;
    private Gender genderMale;
    
    Calendar calendar = Calendar.getInstance(); 
 
    public ReadCustomerServiceTest() {
    }

    @BeforeEach
    public void setUp() {
        listCustomerRepository = new ListCustomerRepositoryImpl(entityManager);

        service = new ReadCustomerService(listCustomerRepository);
        
        documentTypeCarnetIdentidad = documentTypeRepository.saveAndFlush(new DocumentType(null, "Carnet Identidad"));

        genderMale = genderRepository.saveAndFlush(new Gender(null, "Masculino"));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void listAll() throws Exception {
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -19);

//        birthdate = calendar.getTime();
        
        String id = null;
        String name = null;
        String paternal = null;
        String maternal = null;
        Integer documentTypeId = null;
        String identityDocument = null;
        Date birthDate = null;
        Integer genderId = null;
        String status = null;
        String sortBy = null;
        String sortDirection = null;
        int page = 0;
        int size = 5;

//        List<ListCustomerResponse> response = service.list(id, name, paternal, maternal,
//                documentTypeId, identityDocument, birthDate,
//                genderId, status, sortBy, sortDirection, page, size);
//
//        int count = response.size();

//        CreateUpdateCustomerRequest request = new CreateUpdateCustomerRequest();
//        request.setName("Alex_" + UUID.randomUUID().toString());
//        request.setPaternal("Paco_" + UUID.randomUUID().toString());
//        request.setMaternal("Surco_" + UUID.randomUUID().toString());
//        request.setDocumentTypeId(documentTypeCarnetIdentidad.getId());
//        request.setIdentityDocument("7"+(new Date()).getTime());
//        request.setBirthDate(birthdate);
//        request.setGenderId(genderMale.getId());
//
//        CreateCustomerResponse response = service.create(request);
//
//        assertNotNull(response);

//        List<ListCustomerResponse> response = service.list(id, name, paternal, maternal, 
//                documentTypeId, identityDocument, birthDate, 
//                genderId, status, sortBy, sortDirection, page, size);
//        
//        assertnot(response.isEmpty()));
    }

}
