/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller.dto.customeraccount;

import bo.com.bancounion.controller.dto.producttype.ListProductTypeResponse;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author alepaco.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCustomerAccountResponse {
 
    private Integer id; 
    private ListProductTypeResponse productType; 
    private String accountNumber; 
    private String currency; 
    private BigDecimal amount; 
    private Date creationDate; 
    private String branch;  
    private String status;
    private Integer customerId;
        
}
