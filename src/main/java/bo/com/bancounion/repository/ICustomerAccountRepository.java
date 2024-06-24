/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bo.com.bancounion.repository;

import bo.com.bancounion.entity.CustomerAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alepaco.com
 */
@Repository
public interface ICustomerAccountRepository extends JpaRepository<CustomerAccount, Integer> {

    List<CustomerAccount> findByCustomerIdEquals(int id);

    CustomerAccount findByStatusEqualsAndId(String status, int id);
    
}
