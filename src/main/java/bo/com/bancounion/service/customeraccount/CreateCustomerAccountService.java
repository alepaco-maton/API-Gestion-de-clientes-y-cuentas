/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.service.customeraccount;

import bo.com.bancounion.mappers.customeraccount.CreateCustomerAccountMapper;
import bo.com.bancounion.controller.dto.customeraccount.CreateCustomerAccountResponse;
import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.entity.CustomerAccount;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.repository.ICustomerAccountRepository;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IProductTypeRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import bo.com.bancounion.validator.customeraccount.CreateCustomerAccountValidator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@AllArgsConstructor
@Service
@Component
public class CreateCustomerAccountService {

    @Autowired
    MultiLanguageMessagesService mlms;

    @Autowired
    CreateCustomerAccountValidator validator;

    @Autowired
    ICustomerAccountRepository repository;

    @Autowired
    IProductTypeRepository productTypeRepository;

    @Autowired
    ICustomerRepository customerRepository;

    @Transactional
    public CreateCustomerAccountResponse create(CreateUpdateCustomerAccountRequest request) throws ExceptionResponse {
        ErrorCode errorCode = validator.validate(request);

        if (!errorCode.isSuccessfull()) {
            throw new ExceptionResponse(errorCode.getCode(),
                    mlms.getMessage(errorCode.getCode()));
        }

        CustomerAccount model = repository.save(CreateCustomerAccountMapper.mapperToEntity(
                request, productTypeRepository, customerRepository));

        return CreateCustomerAccountMapper.mapperToDto(model);
    }

}
