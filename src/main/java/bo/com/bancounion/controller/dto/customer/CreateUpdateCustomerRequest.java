/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller.dto.customer;

import static bo.com.bancounion.validator.customer.businessrules.CustomerIdentityDocumentValidator.*;
import static bo.com.bancounion.validator.customer.businessrules.CustomerMaternalValidator.*;
import static bo.com.bancounion.validator.customer.businessrules.CustomerNameValidator.*;
import static bo.com.bancounion.validator.customer.businessrules.CustomerPaternalValidator.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author alepaco.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de datos para la solicitud de creación de cliente")
public class CreateUpdateCustomerRequest {

    @Schema(name = "name", description = "Nombre del cliente.",
            minLength = NAME_MIN_LENGHT, maxLength = NAME_MAX_LENGHT,
            nullable = false, example = "Alex Junior")
    private String name;
    
    @Schema(name = "paternal", description = "Apellido paterno.",
            minLength = PATERNAL_MIN_LENGHT, maxLength = PATERNAL_MAX_LENGHT,
            nullable = false, example = "Paco")
    private String paternal;
    
    @Schema(name = "maternal", description = "Apellido materno.",
            minLength = MATERNAL_MIN_LENGHT, maxLength = MATERNAL_MAX_LENGHT,
            nullable = false, example = "Surco")
    private String maternal;
    
    @Schema(name = "documentTypeId", 
            description = "Tipo de documento de identificacion",
            nullable = false, example = "1")
    private Integer documentTypeId;
    
    @Schema(name = "identityDocument", description = "Documento de identidad.",
            minLength = IDENTITY_DOCUMENT_MIN_LENGHT, 
            maxLength = IDENTITY_DOCUMENT_MAX_LENGHT,
            nullable = false, example = "7721904")
    private String identityDocument;
    
    @Schema(name = "birthDate", description = "Fecha de nacimiento.",
            nullable = false, example = "2000-06-18T05:15:17.573Z")
    private Date birthDate;
    
    @Schema(name = "genderId", description = "Genero.",
            nullable = false, example = "1")
    private Integer genderId;

}
