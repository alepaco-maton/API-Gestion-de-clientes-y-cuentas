/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.service.customeraccount;

import bo.com.bancounion.controller.dto.customeraccount.CreateUpdateCustomerAccountRequest;
import bo.com.bancounion.entity.CustomerAccount;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.exception.ExceptionNotFoundResponse;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.repository.ICustomerAccountRepository;
import bo.com.bancounion.repository.ICustomerRepository;
import bo.com.bancounion.repository.IProductTypeRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bo.com.bancounion.validator.customeraccount.UpdateCustomerAccountValidator;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@AllArgsConstructor
@Service
@Component
public class UpdateCustomerAccountService {

    @Autowired
    MultiLanguageMessagesService mlms;

    @Autowired
    UpdateCustomerAccountValidator validator;

    @Autowired
    ICustomerAccountRepository repository;

    @Autowired
    IProductTypeRepository productTypeRepository;

    @Autowired
    ICustomerRepository customerRepository;

    @Transactional
    public void update(CreateUpdateCustomerAccountRequest request, int id)
            throws ExceptionResponse, ExceptionNotFoundResponse {
        ErrorCode errorCode = validator.validate(request, id);

        if (!errorCode.isSuccessfull()) {
            if (errorCode.getCode().equals(ErrorCode.UPDATE_CUSTOMER_ACCOUNT_ID_IS_NOT_FOUND.getCode())) {
                throw new ExceptionNotFoundResponse(errorCode.getCode(),
                        mlms.getMessage(errorCode.getCode()));
            }

            throw new ExceptionResponse(errorCode.getCode(),
                    mlms.getMessage(errorCode.getCode()));
        }

        CustomerAccount model = repository.findById(id).get();
        model.setProductType(productTypeRepository.findById(request.getProductTypeId()).get());
        model.setAccountNumber(request.getAccountNumber());
        model.setAmount(request.getAmount());
        model.setCurrency(request.getCurrency());
        model.setBranch(request.getBranch());
        model.setCustomer(customerRepository.findById(request.getCustomerId()).get());
    }

}
