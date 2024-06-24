/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.service.customeraccount;

import bo.com.bancounion.entity.CustomerAccount;
import bo.com.bancounion.enums.CustomerAccountStatus;
import bo.com.bancounion.enums.CustomerStatus;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.exception.ExceptionNotFoundResponse;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.repository.ICustomerAccountRepository;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bo.com.bancounion.validator.customeraccount.DeleteCustomerAccountValidator;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@AllArgsConstructor
@Service
@Component
public class DeleteCustomerAccountService {

    @Autowired
    MultiLanguageMessagesService mlms;

    @Autowired
    DeleteCustomerAccountValidator validator;

    @Autowired
    ICustomerAccountRepository repository;

    @Transactional
    public void delete(int id) throws ExceptionResponse, ExceptionNotFoundResponse {
        ErrorCode errorCode = validator.validate(id);

        if (!errorCode.isSuccessfull()) {
            throw new ExceptionNotFoundResponse(errorCode.getCode(),
                    mlms.getMessage(errorCode.getCode()));
        }

        CustomerAccount model = repository.findById(id).orElse(null);

        model.setStatus(CustomerAccountStatus.DELETE);
    }

}
