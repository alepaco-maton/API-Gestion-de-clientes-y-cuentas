/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.validator.customer;
 
import bo.com.bancounion.entity.Customer;
import bo.com.bancounion.enums.CustomerStatus;
import bo.com.bancounion.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import bo.com.bancounion.repository.ICustomerRepository;

/**
 *
 * @author alepaco.com
 */
@AllArgsConstructor
@Component
public class DeleteCustomerValidator {
   
    @Autowired
    ICustomerRepository repository;
    
    public ErrorCode validate(int id) {
        Customer model = repository.findByStatusEqualsAndId(CustomerStatus.ACTIVE, id);
        
        if(model == null) {
            return ErrorCode.DELETE_CUSTOMER_ID_NOT_FOUND;
        }
        
        return ErrorCode.SUCCESSFUL;
    }
}
