/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.service.customer;
 
import bo.com.bancounion.mappers.customer.CreateCustomerMapper;
import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.controller.dto.customer.CreateCustomerResponse;
import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.validator.customer.CreateCustomerValidator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@AllArgsConstructor
@Service
@Component
public class CreateCustomerService {

    @Autowired
    MultiLanguageMessagesService mlms;

    @Autowired
    CreateCustomerValidator validator;

    @Autowired 
    ICustomerRepository repository;

    @Autowired 
    IDocumentTypeRepository documentTypeRepository;

    @Autowired 
    IGenderRepository genderRepository;

    @Transactional
    public CreateCustomerResponse create(CreateUpdateCustomerRequest request) throws ExceptionResponse {
        ErrorCode errorCode = validator.validate(request);

        if (!errorCode.isSuccessfull()) {
            throw new ExceptionResponse(errorCode.getCode(),
                    mlms.getMessage(errorCode.getCode()));
        }

        Customer model = repository.save(CreateCustomerMapper.mapperToEntity(
                        request, documentTypeRepository, genderRepository));

        return CreateCustomerMapper.mapperToDto(model);
    }
 
}
