/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bo.com.bancounion.repository;

import bo.com.bancounion.entity.Customer;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author alepaco.com
 */
@Component
public interface IListCustomerRepository {

    List<Customer> filter(String id, String name, String paternal,
            String maternal, Integer documentTypeId, String identityDocument,
            Date birthDate, Integer genderId, String status, String sortBy,
            String sortDirection, int page, int size);

    long count(String id, String name, String paternal, String maternal, 
            Integer documentTypeId, String identityDocument, Date birthDate, 
            Integer genderId, String status);

}
