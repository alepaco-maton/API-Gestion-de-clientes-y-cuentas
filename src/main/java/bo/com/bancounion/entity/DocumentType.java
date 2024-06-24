/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.entity;

import static bo.com.bancounion.validator.customer.businessrules.CustomerNameValidator.NAME_MAX_LENGHT;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author alepaco.com
 */
@EqualsAndHashCode(of = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 50, nullable = false)
    private String name;

}
