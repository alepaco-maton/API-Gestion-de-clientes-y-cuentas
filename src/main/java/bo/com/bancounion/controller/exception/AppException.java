/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 *
 * @author alepaco.com
 */
@Data
@AllArgsConstructor
public class AppException implements Serializable {

    private static final long serialVersionUID = 1321060619595537832L;

    private HttpStatus status;
    private String code;
    private String message;
    
}
