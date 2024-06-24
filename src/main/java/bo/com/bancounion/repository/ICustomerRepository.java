/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bo.com.bancounion.repository;

import bo.com.bancounion.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alepaco.com
 */
@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByStatusEqualsAndId(String status, Integer id);

}
