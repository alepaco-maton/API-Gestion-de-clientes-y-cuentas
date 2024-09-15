/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.entity;

import static bo.com.bancounion.validator.customer.businessrules.CustomerIdentityDocumentValidator.IDENTITY_DOCUMENT_MAX_LENGHT;
import static bo.com.bancounion.validator.customer.businessrules.CustomerMaternalValidator.MATERNAL_MAX_LENGHT;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import static bo.com.bancounion.validator.customer.businessrules.CustomerNameValidator.*;
import static bo.com.bancounion.validator.customer.businessrules.CustomerPaternalValidator.PATERNAL_MAX_LENGHT;

/**
 *
 * @author alepaco.com
 */
@EqualsAndHashCode(of = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = NAME_MAX_LENGHT, nullable = false)
    private String name;
    
    @Column(length = PATERNAL_MAX_LENGHT) 
    private String paternal;
    
    @Column(length = MATERNAL_MAX_LENGHT) 
    private String maternal;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "document_type_id", referencedColumnName = "id", nullable = false)
    private DocumentType documentType;
    
    @Column(length = IDENTITY_DOCUMENT_MAX_LENGHT, nullable = false) 
    private String identityDocument;
    
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "gender_id", referencedColumnName = "id", nullable = false)
    private Gender gender;
    
    @Column(length = 15, nullable = false) 
    private String status;

}
