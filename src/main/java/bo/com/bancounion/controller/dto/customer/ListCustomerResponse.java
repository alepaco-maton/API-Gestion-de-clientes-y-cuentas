/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller.dto.customer;

import bo.com.bancounion.controller.dto.documenttype.ListDocumentTypeResponse;
import bo.com.bancounion.controller.dto.gender.ListGenderResponse;
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
public class ListCustomerResponse {
 
    private Integer id; 
    private String name; 
    private String paternal; 
    private String maternal; 
    private ListDocumentTypeResponse documentType; 
    private String identityDocument; 
    private Date birthDate; 
    private ListGenderResponse gender; 
    private String status;
        
}
