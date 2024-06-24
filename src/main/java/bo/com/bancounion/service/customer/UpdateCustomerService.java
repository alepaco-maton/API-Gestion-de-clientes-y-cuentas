/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.service.customer;

import bo.com.bancounion.controller.dto.customer.CreateUpdateCustomerRequest;
import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.exception.ExceptionNotFoundResponse;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.repository.IDocumentTypeRepository;
import bo.com.bancounion.repository.IGenderRepository;
import bo.com.bancounion.validator.customer.UpdateCustomerValidator;
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
public class UpdateCustomerService {

    @Autowired
    MultiLanguageMessagesService mlms;

    @Autowired
    UpdateCustomerValidator validator;

    @Autowired
    ICustomerRepository repository;

    @Autowired
    IDocumentTypeRepository documentTypeRepository;

    @Autowired
    IGenderRepository genderRepository;

    @Transactional
    public void update(CreateUpdateCustomerRequest request, int id)
            throws ExceptionResponse, ExceptionNotFoundResponse {
        ErrorCode errorCode = validator.validate(request, id);

        if (!errorCode.isSuccessfull()) {
            if (errorCode.getCode().equals(ErrorCode.UPDATE_CUSTOMER_ID_NOT_FOUND.getCode())) {
                throw new ExceptionNotFoundResponse(errorCode.getCode(),
                        mlms.getMessage(errorCode.getCode()));
            }

            throw new ExceptionResponse(errorCode.getCode(),
                    mlms.getMessage(errorCode.getCode()));
        }

        Customer model = repository.findById(id).get();
        model.setName(request.getName());
        model.setPaternal(request.getPaternal());
        model.setMaternal(request.getMaternal());
        model.setDocumentType(documentTypeRepository.findById(request.getDocumentTypeId()).get());
        model.setIdentityDocument(request.getIdentityDocument());
        model.setBirthDate(request.getBirthDate());
        model.setGender(genderRepository.findById(request.getGenderId()).get());
    }

}
