/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.service.customer;

import bo.com.bancounion.mappers.customer.ReadCustomerMapper;
import bo.com.bancounion.controller.dto.customer.ListCustomerResponse;
import bo.com.bancounion.exception.ExceptionResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import bo.com.bancounion.repository.IListCustomerRepository;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@AllArgsConstructor
@Service
@Component
public class ReadCustomerService {

    @Autowired
    IListCustomerRepository repository;

    public List<ListCustomerResponse> list(String id, String name, String paternal,
            String maternal, String documentType, String identityDocument,
            String birthDate, String gender, String status,
            String sortBy, String sortDirection, int page, int size) throws ExceptionResponse {

        List<ListCustomerResponse> response = repository.filter(id, name, paternal,
                maternal, documentType, identityDocument, birthDate,
                gender, status, sortBy, sortDirection, page, size).stream().
                map(m -> ReadCustomerMapper.mapperToDto(m)).
                collect(Collectors.toList());

        return response;
    }

    public long count(String id, String name, String paternal, String maternal,
            String documentType, String identityDocument, String birthDate,
            String gender, String status) {
        return repository.count(id, name, paternal,
                maternal, documentType, identityDocument,
                birthDate, gender, status);
    }

}
