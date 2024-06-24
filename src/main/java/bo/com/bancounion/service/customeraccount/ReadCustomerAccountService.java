/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.service.customeraccount;

import bo.com.bancounion.mappers.customeraccount.ReadCustomerAccountMapper;
import bo.com.bancounion.controller.dto.customeraccount.ListCustomerAccountResponse;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.repository.ICustomerAccountRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@AllArgsConstructor
@Service
@Component
public class ReadCustomerAccountService {

    @Autowired
    ICustomerAccountRepository repository;

    public List<ListCustomerAccountResponse> findByCustomerId(int id) throws ExceptionResponse {
        return repository.findByCustomerIdEquals(id).stream().
                map(m -> ReadCustomerAccountMapper.mapperToDto(m)).
                collect(Collectors.toList());
    } 
    
}
