/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller.dto.customeraccount;

import static bo.com.bancounion.validator.customeraccount.businessrules.CustomerAccountAccountNumberValidator.*; 
import static bo.com.bancounion.validator.customeraccount.businessrules.CustomerAccountAmountValidator.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
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
public class CreateUpdateCustomerAccountRequest {

    @Schema(name = "productTypeId", description = "Identificador de tipo de producto.",
            nullable = false, example = "1")
    private Integer productTypeId;

    @Schema(name = "accountNumber", description = "Numero de cuenta.",
            minLength = ACCOUNT_NUMBER_MIN_LENGHT, maxLength = ACCOUNT_NUMBER_MAX_LENGHT,
            nullable = false, example = "")
    private String accountNumber;
      
    @Schema(name = "currency", description = "Moneda con la que la cuenta trabaja.",
            nullable = false, example = "BO")
    private String currency;

    @Schema(name = "amount", 
            description = "Saldo en la cuenta.",
            minimum = AMOUNT_MIN_VALUE_STR, 
            maximum = AMOUNT_MAX_VALUE_STR,
            nullable = false, example = "0.00")
    private BigDecimal amount;

    @Schema(name = "branch", description = "Sucursal donde se crea la cuenta.",
            nullable = false, example = "La Paz")
    private String branch;
    
    @Schema(name = "customerId", description = "Identificador unico del cliente.",
            nullable = false)
    private Integer customerId;

}
